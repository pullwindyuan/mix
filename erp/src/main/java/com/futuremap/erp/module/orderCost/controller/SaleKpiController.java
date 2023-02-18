package com.futuremap.erp.module.orderCost.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.module.orderCost.entity.*;
import com.futuremap.erp.module.orderCost.entity.SaleKpi;
import com.futuremap.erp.module.orderCost.entity.SaleKpiQuery;
import com.futuremap.erp.module.orderCost.service.impl.SaleKpiServiceImpl;
import com.futuremap.erp.module.orderCost.service.impl.SaleKpiServiceImpl;
import com.futuremap.erp.utils.EasyPoiExcelUtil;
import com.futuremap.erp.utils.GeneralUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/saleKpi")
@Api(tags = {"销售KPI数据控制器"})
public class SaleKpiController {
    @Autowired
    SaleKpiServiceImpl saleKpiService;

    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation("获取销售KPI数据(分页)")
    public Map<String, Object> getSaleKpilistPage(Page<SaleKpi> page, @RequestBody SaleKpiQuery saleKpiQuery) {
        long size = page.getSize();
        IPage<SaleKpi> ipage = saleKpiService.findList(page, saleKpiQuery);
        List<SaleKpi> records = ipage.getRecords();
        long current = ipage.getCurrent();

        page.setSize(Integer.MAX_VALUE);
        page.setCurrent(1);
        IPage<SaleKpi> _ipage = saleKpiService.findList(page, saleKpiQuery);
        Map<String, Object> map = new HashMap<>();
        SaleKpi hoc = getHoc(_ipage.getRecords());
        page.setSize(size);
        page.setRecords(records);
        ipage.setCurrent(current);
        map.put("page", ipage);
        map.put("orderValueH", hoc.getOrderValue());
        map.put("retainedProfitH", hoc.getRetainedProfit());
        map.put("currentAmountH", hoc.getCurrentAmount());
        map.put("profitAmountH", hoc.getProfitAmount());
        return map;
    }

    @PostMapping("/download")
    @ApiOperation("销售提成表下载")
    public void downloadFile(HttpServletResponse response, Page<SaleKpi> page, @RequestBody SaleKpiQuery orderCostQuery) throws IOException {
        page.setSize(Integer.MAX_VALUE);
        IPage<SaleKpi> ipage = saleKpiService.findList(page, orderCostQuery);
        List<SaleKpi> records = ipage.getRecords();
        SaleKpi hoc = getHoc(records);
        records.add(hoc);
        for (SaleKpi record : records) {
            LocalDateTime ddate = record.getDdate();
            record.set_ddate(GeneralUtils.date2string(ddate));
            String companyLabel = record.getCompanyCode();
            record.setCompanyLabel(GeneralUtils.getShortName(companyLabel));

        }

        EasyPoiExcelUtil.exportExcel(records, "外销提成表", "外销提成表明细", SaleKpi.class, "saleKpi", true, response);

    }


    private SaleKpi getHoc(List<SaleKpi> records) {
        SaleKpi oc = new SaleKpi();

        BigDecimal orderValueH = BigDecimal.ZERO;
        BigDecimal retainedProfitH = BigDecimal.ZERO;
        BigDecimal currentAmountH = BigDecimal.ZERO;
        BigDecimal profitAmountH = BigDecimal.ZERO;


        for (SaleKpi record : records) {
            BigDecimal orderValue = GeneralUtils.getBigDecimalVal(record.getOrderValue());
            BigDecimal retainedProfit = GeneralUtils.getBigDecimalVal(record.getRetainedProfit());
            BigDecimal currentAmount = GeneralUtils.getBigDecimalVal(record.getCurrentAmount());
            BigDecimal profitAmount = GeneralUtils.getBigDecimalVal(record.getProfitAmount());
            orderValueH = orderValueH.add(orderValue);
            retainedProfitH = retainedProfitH.add(retainedProfit);
            currentAmountH = currentAmountH.add(currentAmount);
            profitAmountH = profitAmountH.add(profitAmount);
        }
        oc.setOrderValue(orderValueH);
        oc.setRetainedProfit(retainedProfitH);
        oc.setCurrentAmount(currentAmountH);
        oc.setProfitAmount(profitAmountH);
        return oc;
    }


}
