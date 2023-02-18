package com.futuremap.erp.module.orderCost.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.module.constants.Constants;
import com.futuremap.erp.module.orderCost.entity.*;
import com.futuremap.erp.module.orderCost.service.impl.CrmMgrServiceImpl;
import com.futuremap.erp.utils.EasyPoiExcelUtil;
import com.futuremap.erp.utils.GeneralUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/crmMgr")
@Api(tags = {"CRM数据控制器"})
public class CrmMgrController {
    @Autowired
    CrmMgrServiceImpl crmMgrService;

    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation("获取CRM数据(分页)")
    public Map<String, Object> getCrmMgrlistPage(Page<CrmMgr> page, @RequestBody CrmMgrQuery crmMgrQuery) {
        String profitRate1 = crmMgrQuery.getProfitRate1();
        String profitRate2 = crmMgrQuery.getProfitRate2();
        try {
            if (profitRate1 != null) {
                BigDecimal profitRate11 = BigDecimal.valueOf(Long.parseLong(profitRate1));
                BigDecimal _profitRate1 = profitRate11.divide(BigDecimal.valueOf(100), Constants.DIVIDE_LEVEL_12, BigDecimal.ROUND_HALF_UP);
                crmMgrQuery.setProfitRate1(_profitRate1.toString());
            }
            if (profitRate2 != null) {
                BigDecimal profitRate22 = BigDecimal.valueOf(Long.parseLong(profitRate2));
                BigDecimal _profitRate2 = profitRate22.divide(BigDecimal.valueOf(100), Constants.DIVIDE_LEVEL_12, BigDecimal.ROUND_HALF_UP);
                crmMgrQuery.setProfitRate2(_profitRate2.toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        long size = page.getSize();

        Map<String, Object> map = new HashMap<>();
        IPage<CrmMgr> ipage = crmMgrService.findList(page, crmMgrQuery);
        long current = ipage.getCurrent();

        List<CrmMgr> records = ipage.getRecords();
        page.setSize(Integer.MAX_VALUE);
        page.setCurrent(1);
        IPage<CrmMgr> _ipage = crmMgrService.findList(page, crmMgrQuery);

        CrmMgr hoc = getHoc(_ipage.getRecords());
        page.setSize(size);
        ipage.setRecords(records);
        ipage.setCurrent(current);
        map.put("page", ipage);

        map.put("orderValueH", hoc.getOrderValue());
        map.put("returnAmountH", hoc.getReturnAmount());
        map.put("materialCostH", hoc.getMaterialCost());
        map.put("manufactureCostH", hoc.getManufactureCost());
        map.put("laborCostH", hoc.getLaborCost());
        map.put("mgrCostH", hoc.getMgrCost());
        map.put("shippingCostH", hoc.getShippingCost());
        map.put("profitH", hoc.getProfit());
        return map;
    }


    @PostMapping("/download")
    @ApiOperation("CRM表下载")
    public void downloadFile(HttpServletResponse response, Page<CrmMgr> page, @RequestBody CrmMgrQuery orderCostQuery) throws IOException {
        page.setSize(Integer.MAX_VALUE);
        IPage<CrmMgr> ipage = crmMgrService.findList(page, orderCostQuery);
        List<CrmMgr> records = ipage.getRecords();
        CrmMgr hoc = getHoc(records);
        records.add(hoc);

        for (CrmMgr record : records) {
            LocalDateTime ddate = record.getDdate();
            record.set_ddate(GeneralUtils.date2string(ddate));
            String companyCode = record.getCompanyCode();
            record.setCompanyName(GeneralUtils.getShortName(companyCode));
            LocalDateTime returnDate = record.getReturnDate();
            record.set_returnDate(GeneralUtils.date2string(returnDate));
            LocalDateTime returnPlanDate = record.getReturnPlanDate();
            record.set_returnPlanDate(GeneralUtils.date2string(returnPlanDate));

        }

        EasyPoiExcelUtil.exportExcel(records, "CRM表", "CRM表明细", CrmMgr.class, "crmMgr", true, response);

    }


    private CrmMgr getHoc(List<CrmMgr> records) {
        CrmMgr oc = new CrmMgr();

        BigDecimal orderValueH = BigDecimal.ZERO;
        BigDecimal returnAmountH = BigDecimal.ZERO;
        BigDecimal materialCostH = BigDecimal.ZERO;
        BigDecimal manufactureCostH = BigDecimal.ZERO;
        BigDecimal laborCostH = BigDecimal.ZERO;
        BigDecimal mgrCostH = BigDecimal.ZERO;
        BigDecimal shippingCostH = BigDecimal.ZERO;
        BigDecimal profitH = BigDecimal.ZERO;


        for (CrmMgr record : records) {
            BigDecimal orderValue = GeneralUtils.getBigDecimalVal(record.getOrderValue());
            BigDecimal profit = GeneralUtils.getBigDecimalVal(record.getProfit());
            BigDecimal shippingCost = GeneralUtils.getBigDecimalVal(record.getShippingCost());
            BigDecimal mgrCost = GeneralUtils.getBigDecimalVal(record.getMgrCost());
            BigDecimal laborCost = GeneralUtils.getBigDecimalVal(record.getLaborCost());
            BigDecimal manufactureCost = GeneralUtils.getBigDecimalVal(record.getManufactureCost());
            BigDecimal materialCost = GeneralUtils.getBigDecimalVal(record.getMaterialCost());
            BigDecimal returnAmount = GeneralUtils.getBigDecimalVal(record.getReturnAmount());

            profitH = profitH.add(profit);
            shippingCostH = shippingCostH.add(shippingCost);
            mgrCostH = mgrCostH.add(mgrCost);
            laborCostH = laborCostH.add(laborCost);
            manufactureCostH = manufactureCostH.add(manufactureCost);
            materialCostH = materialCostH.add(materialCost);
            returnAmountH = returnAmountH.add(returnAmount);
            orderValueH = orderValueH.add(orderValue);

        }
        oc.setOrderValue(orderValueH);
        oc.setProfit(profitH);
        oc.setMgrCost(mgrCostH);
        oc.setLaborCost(laborCostH);
        oc.setManufactureCost(manufactureCostH);
        oc.setMaterialCost(materialCostH);
        oc.setReturnAmount(returnAmountH);

        return oc;
    }


}
