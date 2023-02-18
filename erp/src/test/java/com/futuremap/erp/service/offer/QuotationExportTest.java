/*
 * Copyright © 2017-2021 http://www.futuremap.com.cn/ All rights reserved.
 *
 * Statement: This document's code after sufficiently has not permitted does not have
 * any way dissemination and the change, once discovered violates the stipulation, will
 * receive the criminal sanction.
 * Address: Building A, block 1F,  Tian'an Yungu Industrial Park phase I,
 *          Xuegang Road, Bantian street, Longgang District, Shenzhen
 * Tel: 0755-22674916
 */
package com.futuremap.erp.service.offer;

import com.futuremap.erp.BaseTest;
import com.futuremap.erp.module.quotation.entity.Quotation;
import com.futuremap.erp.module.quotation.service.impl.QuotationServiceImpl;
import com.futuremap.erp.module.quotation.service.impl.QuotationTotalServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author Administrator
 * @title QuotationExportTest
 * @description TODO
 * @date 2021/6/11 16:06
 */
public class QuotationExportTest extends BaseTest {
    @Autowired
    QuotationServiceImpl quotationService;
    @Autowired
    QuotationTotalServiceImpl quotationTotalService;

    @Test
    public void exportTest(){
        String filePath = "F:\\mingo\\workspace\\futuremap-erp\\src\\main\\resources\\quotation.xlsx";
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
             List<Quotation> quotations = quotationService.exportQuotations(fis);
            quotationTotalService.countQuotationData(quotations);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
