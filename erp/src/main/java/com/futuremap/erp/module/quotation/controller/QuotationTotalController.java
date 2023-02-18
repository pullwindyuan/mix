package com.futuremap.erp.module.quotation.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.module.orderCost.entity.OrderCost;
import com.futuremap.erp.module.orderCost.entity.OrderCostQuery;
import com.futuremap.erp.module.quotation.entity.QuotationTotal;
import com.futuremap.erp.module.quotation.entity.QuotationTotalQuery;
import com.futuremap.erp.module.quotation.service.impl.QuotationTotalServiceImpl;
import com.futuremap.erp.utils.EasyPoiExcelUtil;
import com.futuremap.erp.utils.GeneralUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author futuremap
 * @since 2021-06-10
 */
@RestController
@RequestMapping("/quotation/total")
public class QuotationTotalController {
    @Autowired
    QuotationTotalServiceImpl quotationTotalService;

    //    @PostMapping("/listPage/{current}/{size}")
//    @ApiOperation("获取报价汇总表数据(分页)")
//    public IPage<QuotationTotal> getPurchOrderlistPage(Page<QuotationTotal> page, @RequestBody QuotationTotalQuery quotationTotalQuery) {
//        IPage<QuotationTotal> ipage = quotationTotalService.findList(page, quotationTotalQuery);
//        return ipage;
//    }
    @PostMapping("/listPage/{current}/{size}")
    @ApiOperation("获取报价汇总表数据(分页)")
    public Map<String, Object> getPurchOrderlistPage(Page<QuotationTotal> page, @RequestBody QuotationTotalQuery quotationTotalQuery) {
        long size = page.getSize();
        Map<String, Object> map = new HashMap<>();
        IPage<QuotationTotal> ipage = quotationTotalService.findList(page, quotationTotalQuery);
        List<QuotationTotal> records = ipage.getRecords();
        long current = ipage.getCurrent();

        page.setSize(Integer.MAX_VALUE);
        page.setCurrent(1);
        IPage<QuotationTotal> _ipage = quotationTotalService.findList(page, quotationTotalQuery);
        page.setRecords(records);
        page.setSize(size);
        ipage.setCurrent(current);
        QuotationTotal hoc = getHoc(_ipage.getRecords());
        map.put("page", ipage);
        map.put("materialCostH", hoc.getMaterialCost());
        map.put("manufactureCostH", hoc.getManufactureCost());
        map.put("laborCostH", hoc.getLaborCost());
        map.put("mgrCostH", hoc.getMgrCost());
        map.put("shippingCostH", hoc.getShippingCost());
        map.put("profitH", hoc.getProfit());
        map.put("quotationMaterialCostH", hoc.getQuotationMaterialCost());
        map.put("quotationManufactureCostH", hoc.getQuotationManufactureCost());
        map.put("quotationLaborCostH", hoc.getQuotationLaborCost());
        map.put("quotationMgrCostH", hoc.getQuotationMgrCost());
        map.put("quotationShippingCostH", hoc.getQuotationShippingCost());
        map.put("quotationProfitH", hoc.getQuotationProfit());
        map.put("quotationUnitpriceH", hoc.getQuotationUnitprice());
        map.put("quotationCostH", hoc.getQuotationCost());
        map.put("actualCostH", hoc.getActualCost());
        map.put("diffH", hoc.getDiff());

        return map;
    }

    private QuotationTotal getHoc(List<QuotationTotal> records) {
        QuotationTotal oc = new QuotationTotal();

        BigDecimal materialCostH = BigDecimal.ZERO;
        BigDecimal manufactureCostH = BigDecimal.ZERO;
        BigDecimal laborCostH = BigDecimal.ZERO;
        BigDecimal mgrCostH = BigDecimal.ZERO;
        BigDecimal shippingCostH = BigDecimal.ZERO;
        BigDecimal profitH = BigDecimal.ZERO;
        BigDecimal quotationMaterialCostH = BigDecimal.ZERO;
        BigDecimal quotationManufactureCostH = BigDecimal.ZERO;
        BigDecimal quotationLaborCostH = BigDecimal.ZERO;
        BigDecimal quotationMgrCostH = BigDecimal.ZERO;
        BigDecimal quotationShippingCostH = BigDecimal.ZERO;
        BigDecimal quotationProfitH = BigDecimal.ZERO;
        BigDecimal quotationUnitpriceH = BigDecimal.ZERO;
        BigDecimal quotationCostH = BigDecimal.ZERO;
        BigDecimal actualCostH = BigDecimal.ZERO;
        BigDecimal diffH = BigDecimal.ZERO;

        for (QuotationTotal record : records) {
            BigDecimal materialCost = GeneralUtils.getBigDecimalVal(record.getMaterialCost());
            BigDecimal manufactureCost = GeneralUtils.getBigDecimalVal(record.getManufactureCost());
            BigDecimal laborCost = GeneralUtils.getBigDecimalVal(record.getLaborCost());
            BigDecimal mgrCost = GeneralUtils.getBigDecimalVal(record.getMgrCost());
            BigDecimal profit = GeneralUtils.getBigDecimalVal(record.getProfit());
            BigDecimal shippingCost = GeneralUtils.getBigDecimalVal(record.getShippingCost());
            BigDecimal quotationMaterialCost = GeneralUtils.getBigDecimalVal(record.getQuotationMaterialCost());
            BigDecimal quotationManufactureCost = GeneralUtils.getBigDecimalVal(record.getQuotationManufactureCost());
            BigDecimal quotationLaborCost = GeneralUtils.getBigDecimalVal(record.getQuotationLaborCost());
            BigDecimal quotationMgrCost = GeneralUtils.getBigDecimalVal(record.getQuotationMgrCost());
            BigDecimal quotationShippingCost = GeneralUtils.getBigDecimalVal(record.getQuotationShippingCost());
            BigDecimal quotationProfit = GeneralUtils.getBigDecimalVal(record.getQuotationProfit());
            BigDecimal quotationUnitprice = GeneralUtils.getBigDecimalVal(record.getQuotationUnitprice());
            BigDecimal quotationCost = GeneralUtils.getBigDecimalVal(record.getQuotationCost());
            BigDecimal actualCost = GeneralUtils.getBigDecimalVal(record.getActualCost());
            BigDecimal diff = GeneralUtils.getBigDecimalVal(record.getDiff());

            diffH = diffH.add(diff);
            actualCostH = actualCostH.add(actualCost);
            quotationCostH = quotationCostH.add(quotationCost);
            quotationUnitpriceH = quotationUnitpriceH.add(quotationUnitprice);
            quotationProfitH = quotationProfitH.add(quotationProfit);
            quotationShippingCostH = quotationShippingCostH.add(quotationShippingCost);
            quotationMgrCostH = quotationMgrCostH.add(quotationMgrCost);
            quotationLaborCostH = quotationLaborCostH.add(quotationLaborCost);
            quotationManufactureCostH = quotationManufactureCostH.add(quotationManufactureCost);
            quotationMaterialCostH = quotationMaterialCostH.add(quotationMaterialCost);
            shippingCostH = shippingCostH.add(shippingCost);
            materialCostH = materialCostH.add(materialCost);
            manufactureCostH = manufactureCostH.add(manufactureCost);
            laborCostH = laborCostH.add(laborCost);
            mgrCostH = mgrCostH.add(mgrCost);
            profitH = profitH.add(profit);
        }

        oc.setMaterialCost(materialCostH);
        oc.setManufactureCost(manufactureCostH);
        oc.setLaborCost(laborCostH);
        oc.setMgrCost(mgrCostH);
        oc.setShippingCost(shippingCostH);
        oc.setProfit(profitH);
        oc.setQuotationMaterialCost(quotationMaterialCostH);
        oc.setQuotationManufactureCost(quotationManufactureCostH);
        oc.setQuotationLaborCost(quotationLaborCostH);
        oc.setQuotationMgrCost(quotationMgrCostH);
        oc.setQuotationShippingCost(quotationShippingCostH);
        oc.setQuotationProfit(quotationProfitH);
        oc.setQuotationUnitprice(quotationUnitpriceH);
        oc.setQuotationCost(quotationCostH);
        oc.setActualCost(actualCostH);
        oc.setDiff(diffH);
        return oc;
    }

    @PostMapping("/download")
    @ApiOperation("报价对比表下载")
    public void downloadFile(HttpServletResponse response, Page<QuotationTotal> page, @RequestBody QuotationTotalQuery quotationTotalQuery) throws IOException {
        page.setSize(Integer.MAX_VALUE);
        IPage<QuotationTotal> ipage = quotationTotalService.findList(page, quotationTotalQuery);
        List<QuotationTotal> records = ipage.getRecords();
        QuotationTotal hoc = getHoc(records);
        records.add(hoc);

        for (QuotationTotal qt : records) {
            LocalDateTime ddate = qt.getDdate();
            String _ddate = GeneralUtils.date2string(ddate);
            qt.set_ddate(_ddate);
            String companyCode = qt.getCompanyCode();
            qt.setCompanyName(GeneralUtils.getShortName(companyCode));
        }

        EasyPoiExcelUtil.exportExcel(records, "报价对比表", "报价对比表明细", QuotationTotal.class, "quotationTotal", true, response);

    }


}
