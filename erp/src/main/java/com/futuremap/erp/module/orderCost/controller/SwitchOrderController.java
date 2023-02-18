package com.futuremap.erp.module.orderCost.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.module.auth.service.DataFilterByRolePermission;
import com.futuremap.erp.module.constants.Constants;
import com.futuremap.erp.module.constants.OrderProcessConstants;
import com.futuremap.erp.module.orderCost.entity.*;
import com.futuremap.erp.module.orderCost.entity.SwitchOrder;
import com.futuremap.erp.module.orderCost.entity.SwitchOrderQuery;
import com.futuremap.erp.module.orderCost.mapper.OrderCostDS1Mapper;
import com.futuremap.erp.module.orderCost.service.impl.OrderCostServiceImpl;
import com.futuremap.erp.module.orderCost.service.impl.SwitchOrderServiceImpl;
import com.futuremap.erp.utils.EasyPoiExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/switchOrder")
@Api(tags = {"挪货记录上传下载控制器"})
@Slf4j
public class SwitchOrderController extends DataFilterByRolePermission {

    @Autowired
    SwitchOrderServiceImpl switchOrderService;
    @Autowired
    OrderCostServiceImpl orderCostServiceImpl;

    /**
     * 导入数据
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    @ApiOperation("挪库上传excel数据")
    public void importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if(checkWritePermission(request) == false) {
            log.error("您没有操作权限");
            throw new FuturemapBaseException("您没有操作权限");
        }
        InputStream fileInputStream = file.getInputStream();
        switchOrderService.buildSwitchOrderList(fileInputStream);

    }


    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation("挪库上传数据(分页)")
    public IPage<SwitchOrder> getSwitchOrderlistPage(Page<SwitchOrder> page, @RequestBody SwitchOrderQuery switchOrderQuery) {
        return switchOrderService.findList(page, switchOrderQuery);
    }

    @PostMapping("/list/findOrderByCode")
    @ApiOperation("通过被挪货订单号获取挪货信息列表数据：订单号指的是被挪货的订单号（订单全过程）")
    public List<SwitchOrder> getSwitchOrderlistByCode(@RequestBody SwitchOrderQuery switchOrderQuery) {
        return switchOrderService.findListBySwitchCode(switchOrderQuery);
    }

    @PostMapping("/download")
    @ApiOperation("挪库记录表下载")
    public void downloadFile(HttpServletResponse response, Page<SwitchOrder> page, @RequestBody SwitchOrderQuery orderCostQuery) throws IOException {
        page.setSize(Integer.MAX_VALUE);
        IPage<SwitchOrder> ipage = switchOrderService.findList(page, orderCostQuery);
        List<SwitchOrder> records = ipage.getRecords();

        EasyPoiExcelUtil.exportExcel(records, "挪库记录表", "挪库记录表明细", SwitchOrder.class, "switchOrder", true, response);

    }


    @PostMapping("/add")
    @ApiOperation("增加挪库记录")
    public String addSwitchOrder(@RequestBody SwitchOrder switchOrder, HttpServletRequest request) {
        if(checkWritePermission(request) == false) {
            log.error("您没有操作权限");
            throw new FuturemapBaseException("您没有操作权限");
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("code", "1");
        jsonObj.put("message", "添加成功");
        try {
            String orderDetailCode = switchOrder.getOrderDetailCode();
            String orderNumber = switchOrder.getOrderNumber();
            String productCode = switchOrder.getProductCode();
            String orderDetailCode_switch = switchOrder.getOrderSwitch();
            String orderNumber_switch = switchOrder.getOrderNumberSwitch();
            String ddmonth = switchOrder.getDdmonth();
            if (orderDetailCode != null && orderNumber != null && productCode != null && orderDetailCode_switch != null && orderNumber_switch != null && ddmonth != null) {
                switchOrderService.save(switchOrder);
                List<OrderCost> orderCost_list = orderCostServiceImpl.list(new QueryWrapper<OrderCost>()
                        .eq("order_detail_code", orderDetailCode)
                        .eq("order_number", orderNumber)
                        .eq("dmonth", ddmonth)
                        .eq("cinvcode", productCode));
                if (orderCost_list != null && orderCost_list.size() == 1) {
                    OrderCost orderCost = orderCost_list.get(0);
                    orderCostServiceImpl.updateMaterialCost(orderCost, BigDecimal.ZERO, Constants.ERP002, orderDetailCode_switch, orderNumber_switch, productCode, orderCost.getFquantity(), ddmonth);
                    DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                    orderCostServiceImpl.updateById(orderCost);
                }


            } else {
                jsonObj.put("message", "添加失败");
            }
        } catch (Exception e) {
            log.error("添加单条挪货记录异常", e);
        }
        return jsonObj.toJSONString();


    }

    @PostMapping("/edit")
    @ApiOperation("修改挪库记录")
    public void editSwitchOrder(@RequestBody SwitchOrder switchOrder, HttpServletRequest request) {
        if(checkWritePermission(request) == false) {
            log.error("您没有操作权限");
            throw new FuturemapBaseException("您没有操作权限");
        }
        System.out.println(switchOrder);
        switchOrderService.updateById(switchOrder);
    }

    @PostMapping("/del")
    @ApiOperation("删除挪库记录")
    public void delSwitchOrder(@RequestBody SwitchOrder switchOrder, HttpServletRequest request) {
        if(checkWritePermission(request) == false) {
            log.error("您没有操作权限");
            throw new FuturemapBaseException("您没有操作权限");
        }
        Integer id = switchOrder.getId();
        switchOrderService.removeById(id);
    }


    @GetMapping("/download")
    @ApiOperation("挪库下载excel模版")
    public void downloadFile(HttpServletResponse response) throws FileNotFoundException {
        String fileName = "switchOrderTemplet.xlsx";// 文件名
        if (fileName != null) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
            byte[] buffer = new byte[1024];
            InputStream fis = null;
            BufferedInputStream bis = null;
            try {
//                fis = this.getClass().getResourceAsStream("/excel/switchOrderTemplet.xlsx");
                File downloadFile = new File("/usr/local/proj/erp/downloadFile/switchOrderTemplet.xlsx");
                fis = new FileInputStream(downloadFile);

                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
