package com.futuremap.erp.module.orderCost.controller;


import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.module.orderCost.entity.OrderCost;
import com.futuremap.erp.module.orderCost.entity.OrderCostQuery;
import com.futuremap.erp.module.orderCost.mapper.OrderCostDS1Mapper;
import com.futuremap.erp.module.orderCost.service.impl.OrderCostServiceImpl;
import com.futuremap.erp.module.orderprocess.entity.PurchOrder;
import com.futuremap.erp.module.orderprocess.entity.PurchOrderQuery;
import com.futuremap.erp.utils.EasyPoiExcelUtil;
import com.futuremap.erp.utils.GeneralUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/orderCost")
@Api(tags = {"订单成本控制器"})
public class OrderCostController {
    @Autowired
    OrderCostServiceImpl orderCostService;

    @Autowired
    OrderCostDS1Mapper orderCostDS1Mapper;

    private OrderCost getHoc(List<OrderCost> records) {
        OrderCost oc = new OrderCost();

        BigDecimal incomeH = BigDecimal.ZERO;
        BigDecimal materialCostH = BigDecimal.ZERO;
        BigDecimal manufactureCostH = BigDecimal.ZERO;
        BigDecimal laborCostH = BigDecimal.ZERO;
        BigDecimal mgrCostH = BigDecimal.ZERO;
        BigDecimal runCostH = BigDecimal.ZERO;
        BigDecimal costSumH = BigDecimal.ZERO;
        BigDecimal profitSumH = BigDecimal.ZERO;
        BigDecimal costTotalH = BigDecimal.ZERO;
        BigDecimal profitH = BigDecimal.ZERO;


        for (OrderCost record : records) {
            BigDecimal income = GeneralUtils.getBigDecimalVal(record.getIncome());
            BigDecimal materialCost = GeneralUtils.getBigDecimalVal(record.getMaterialCost());
            BigDecimal manufactureCost = GeneralUtils.getBigDecimalVal(record.getManufactureCost());
            BigDecimal laborCost = GeneralUtils.getBigDecimalVal(record.getLaborCost());
            BigDecimal mgrCost = GeneralUtils.getBigDecimalVal(record.getMgrCost());
            BigDecimal runCost = GeneralUtils.getBigDecimalVal(record.getRunCost());
            BigDecimal costSum = GeneralUtils.getBigDecimalVal(record.getCostSum());
            BigDecimal profitSum = GeneralUtils.getBigDecimalVal(record.getProfitSum());
            BigDecimal costTotal = GeneralUtils.getBigDecimalVal(record.getCostTotal());
            BigDecimal profit = GeneralUtils.getBigDecimalVal(record.getProfit());
            incomeH = incomeH.add(income);
            materialCostH = materialCostH.add(materialCost);
            manufactureCostH = manufactureCostH.add(manufactureCost);
            laborCostH = laborCostH.add(laborCost);
            mgrCostH = mgrCostH.add(mgrCost);
            runCostH = runCostH.add(runCost);
            costSumH = costSumH.add(costSum);
            profitSumH = profitSumH.add(profitSum);
            costTotalH = costTotalH.add(costTotal);
            profitH = profitH.add(profit);
        }

        oc.setIncome(incomeH);
        oc.setMaterialCost(materialCostH);
        oc.setManufactureCost(manufactureCostH);
        oc.setLaborCost(laborCostH);
        oc.setMgrCost(mgrCostH);
        oc.setRunCost(runCostH);
        oc.setCostSum(costSumH);
        oc.setProfitSum(profitSumH);
        oc.setCostTotal(costTotalH);
        oc.setProfit(profitH);
        return oc;
    }

    @PostMapping("/listPage/getCCus")
    @ApiOperation("获取客户列表")
    public List<Map<String, Object>> getCCus() {
        return orderCostDS1Mapper.getCCus();
    }

    @PostMapping("/listPage/getCperson")
    @ApiOperation("获取业务员列表")
    public List<Map<String, Object>> getCperson() {
        return orderCostDS1Mapper.getCperson();
    }

    @PostMapping("/listPage/getCven")
    @ApiOperation("获取业务员列表")
    public List<Map<String, Object>> getCven() {
        return orderCostService.getCven();
    }


    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation("获取订单成本数据(分页)")
    public Map<String, Object> getOrderCostlistPage(Page<OrderCost> page, @RequestBody OrderCostQuery orderCostQuery) {
        long size = page.getSize();
        IPage<OrderCost> ipage = orderCostService.findList(page, orderCostQuery);
        List<OrderCost> records = ipage.getRecords();
        long current = ipage.getCurrent();
        page.setSize(Integer.MAX_VALUE);
        page.setCurrent(1);

        IPage<OrderCost> _ipage = orderCostService.findList(page, orderCostQuery);
        OrderCost hoc = getHoc(_ipage.getRecords());
        page.setSize(size);
        ipage.setRecords(records);
        ipage.setCurrent(current);
        Map<String, Object> map = new HashMap<>();
        map.put("page", ipage);
        map.put("incomeH", hoc.getIncome());
        map.put("manufactureCostH", hoc.getManufactureCost());
        map.put("materialCostH", hoc.getMaterialCost());
        map.put("laborCostH", hoc.getLaborCost());
        map.put("mgrCostH", hoc.getMgrCost());
        map.put("runCostH", hoc.getRunCost());
        map.put("costSumH", hoc.getCostSum());
        map.put("profitSumH", hoc.getProfitSum());
        map.put("costTotalH", hoc.getCostTotal());
        map.put("profitH", hoc.getProfit());

        return map;
    }


    @PostMapping("/download")
    @ApiOperation("订单成本表下载")
    public void downloadFile(HttpServletResponse response, Page<OrderCost> page, @RequestBody OrderCostQuery orderCostQuery) throws IOException {
        page.setSize(Integer.MAX_VALUE);
        IPage<OrderCost> ipage = orderCostService.findList(page, orderCostQuery);
        List<OrderCost> records = ipage.getRecords();
        OrderCost hoc = getHoc(records);
        records.add(hoc);
        EasyPoiExcelUtil.exportExcel(records, "订单成本表", "订单成本表明细", OrderCost.class, "orderCost", true, response);

    }


}
