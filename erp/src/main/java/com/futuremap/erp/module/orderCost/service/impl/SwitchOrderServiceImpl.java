package com.futuremap.erp.module.orderCost.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.module.constants.Constants;
import com.futuremap.erp.module.orderCost.entity.OrderCost;
import com.futuremap.erp.module.orderCost.entity.SwitchOrder;
import com.futuremap.erp.module.orderCost.entity.SwitchOrderQuery;
import com.futuremap.erp.module.orderCost.mapper.OrderCostDS1Mapper;
import com.futuremap.erp.module.orderCost.mapper.SwitchOrderDS1Mapper;
import com.futuremap.erp.module.orderCost.service.ISwitchOrderService;
import com.futuremap.erp.utils.EasyPoiExcelUtil;
import com.futuremap.erp.utils.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@Slf4j
public class SwitchOrderServiceImpl extends ServiceImpl<SwitchOrderDS1Mapper, SwitchOrder> implements ISwitchOrderService {

    @Autowired
    SwitchOrderDS1Mapper switchOrderDS1Mapper;
    @Autowired
    OrderCostDS1Mapper orderCostDS1Mapper;


    public void buildSwitchOrderList(InputStream fileInputStream) {
        try {
            ByteArrayOutputStream baos = cloneInputStream(fileInputStream);
            InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());
            DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
            String endStr = "01 00:00:00";
            List<SwitchOrder> sos = EasyPoiExcelUtil.importExcel(stream2, 0, 1, SwitchOrder.class);
            sos.stream().forEach(switchOrder -> {
                String ddmonth = switchOrder.getDdmonth();
                LocalDateTime _ddmonth = GeneralUtils.string2myDate(ddmonth, endStr, sdf);
                String orderDetailCode = switchOrder.getOrderDetailCode();
                String productCode = switchOrder.getProductCode();
                String orderSwitch = switchOrder.getOrderSwitch();
                Integer orderCount = switchOrder.getOrderCount();
                if (_ddmonth == null || orderDetailCode == null || productCode == null || orderSwitch == null || orderCount == null) {
                    throw new FuturemapBaseException("上传数据不完整");
                }

                //删除重复的数据
                switchOrderDS1Mapper.delData(ddmonth, orderDetailCode, productCode, orderSwitch);

            });

            saveBatch(sos);

            //需要更新订单成本表
            sos.stream().forEach(switchOrder -> {
                String orderDetailCode = switchOrder.getOrderDetailCode();
                String orderNumber = switchOrder.getOrderNumber();
                String productCode = switchOrder.getProductCode();

                OrderCost oc = orderCostDS1Mapper.getOrderCost1(orderDetailCode, orderNumber, productCode);
                if (oc != null) {
                    Integer id = oc.getId();
                    String orderSwitch = switchOrder.getOrderSwitch();
                    String orderNumberSwitch = switchOrder.getOrderNumberSwitch();
                    OrderCost orderCost = orderCostDS1Mapper.getOrderCost(orderSwitch, orderNumberSwitch, productCode);
                    if (orderCost != null) {
                        orderCost.setId(id);
                        orderCost.setOrderDetailCode(orderDetailCode);
                        orderCost.setOrderNumber(orderNumber);
                        orderCost.setOrderType(Constants.ORDER_TYPE_2);
                        orderCostDS1Mapper.updateById(orderCost);
                    }


                }


            });


        } catch (Exception e) {
            e.printStackTrace();
            log.error("挪货记录批量上传失败", e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private static ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public IPage<SwitchOrder> findList(Page<SwitchOrder> page, SwitchOrderQuery switchOrderQuery) {
        return switchOrderDS1Mapper.findList(page, switchOrderQuery);
    }

    public List<SwitchOrder> findListBySwitchCode(SwitchOrderQuery switchOrderQuery) {
        return switchOrderDS1Mapper.findListBySwitchCode(switchOrderQuery);
    }
}
