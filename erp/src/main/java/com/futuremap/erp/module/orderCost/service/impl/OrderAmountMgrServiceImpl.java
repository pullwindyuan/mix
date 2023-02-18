package com.futuremap.erp.module.orderCost.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.module.orderCost.entity.OrderAmountMgr;
import com.futuremap.erp.module.orderCost.entity.OrderAmountMgrQuery;
import com.futuremap.erp.module.orderCost.mapper.OrderAmountMgrMapper;
import com.futuremap.erp.module.orderCost.service.IOrderAmountMgrService;
import com.futuremap.erp.utils.EasyPoiExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Service
public class OrderAmountMgrServiceImpl extends ServiceImpl<OrderAmountMgrMapper, OrderAmountMgr> implements IOrderAmountMgrService {
    @Autowired
    OrderAmountMgrMapper orderAmountMgrMapper;



    public void buildOrderAmountMgrList(InputStream fileInputStream) {
        try {
            ByteArrayOutputStream baos = cloneInputStream(fileInputStream);
            InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());
            List<OrderAmountMgr> sos = EasyPoiExcelUtil.importExcel(stream2, 0, 1, OrderAmountMgr.class);
            saveBatch(sos);
        } catch (Exception e) {
            e.printStackTrace();
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

    public IPage<OrderAmountMgr> findList(Page<OrderAmountMgr> page, OrderAmountMgrQuery orderAmountMgrQuery) {
        return orderAmountMgrMapper.findList(page, orderAmountMgrQuery);
    }

}
