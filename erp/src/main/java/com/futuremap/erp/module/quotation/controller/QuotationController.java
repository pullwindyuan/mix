package com.futuremap.erp.module.quotation.controller;


import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.module.auth.service.DataFilterByRolePermission;
import com.futuremap.erp.module.constants.OrderProcessConstants;
import com.futuremap.erp.module.orderCost.entity.CrmMgr;
import com.futuremap.erp.module.orderCost.entity.SaleKpi;
import com.futuremap.erp.module.orderCost.mapper.CrmMgrDS1Mapper;
import com.futuremap.erp.module.orderCost.mapper.SaleKpiDS1Mapper;
import com.futuremap.erp.module.orderCost.service.impl.CrmMgrServiceImpl;
import com.futuremap.erp.module.orderCost.service.impl.SaleKpiServiceImpl;
import com.futuremap.erp.module.quotation.entity.Quotation;
import com.futuremap.erp.module.quotation.entity.QuotationTotal;
import com.futuremap.erp.module.quotation.service.impl.QuotationServiceImpl;
import com.futuremap.erp.module.quotation.service.impl.QuotationTotalServiceImpl;
import com.futuremap.erp.utils.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author futuremap
 * @since 2021-06-10
 */
@RestController
@Slf4j
@RequestMapping("/quotation")
public class QuotationController  extends DataFilterByRolePermission {

    @Autowired
    QuotationServiceImpl quotationService;
    @Autowired
    QuotationTotalServiceImpl quotationTotalService;

    @Autowired
    SaleKpiDS1Mapper saleKpiDS1Mapper;
    @Autowired
    CrmMgrDS1Mapper crmMgrDS1Mapper;

    @Autowired
    SaleKpiServiceImpl saleKpiServiceImpl;

    @Autowired
    CrmMgrServiceImpl crmMgrServiceImpl;

    /**
     * 导入数据
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public void importExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if(checkWritePermission(request) == false) {
            log.error("您没有操作的权限");
            throw new FuturemapBaseException("您没有操作的权限");
        }
        InputStream fileInputStream = file.getInputStream();
        List<Quotation> resultList = quotationService.exportQuotations(fileInputStream);
        List<QuotationTotal> quotationTotals = quotationTotalService.countQuotationData(resultList);


        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyyMM");
        for (QuotationTotal qt : quotationTotals) {


            LocalDateTime ddate = qt.getDdate();
            String dmonth = GeneralUtils.date2myString(ddate, sdf);
            String companyCode = qt.getCompanyCode();
            String companyLabel = GeneralUtils.getShortDs(companyCode);
            String csocode = qt.getCsocode();
            String irowno = String.valueOf(qt.getIrowno());
            String cinvcode = qt.getCinvcode();

            DynamicDataSourceContextHolder.push(companyLabel);
            SaleKpi sk = saleKpiDS1Mapper.getSaleKpiOne(csocode, irowno, cinvcode);
            if (sk != null) {
                List<SaleKpi> saleKpiList = new ArrayList<>();
                saleKpiList.add(sk);
                saleKpiServiceImpl.buildSaleKpiList(companyLabel, dmonth, saleKpiList);
            }


            DynamicDataSourceContextHolder.push(companyLabel);
            CrmMgr innerCrmMgrOne = crmMgrDS1Mapper.getInnerCrmMgrOne(csocode, irowno, cinvcode);
            CrmMgr outerCrmMgrOne = crmMgrDS1Mapper.getOuterCrmMgrOne(csocode, irowno, cinvcode);
            List<CrmMgr> crmMgrList = new ArrayList<>();
            if (innerCrmMgrOne != null) {
                crmMgrList.add(innerCrmMgrOne);
            }
            if (outerCrmMgrOne != null) {
                crmMgrList.add(outerCrmMgrOne);
            }
            if (!crmMgrList.isEmpty()) {
                crmMgrServiceImpl.buildCrmMgrList(companyLabel, dmonth, crmMgrList);
            }


        }


    }


}
