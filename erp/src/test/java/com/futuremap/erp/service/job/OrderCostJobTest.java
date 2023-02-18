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
package com.futuremap.erp.service.job;

import com.futuremap.erp.BaseTest;
import com.futuremap.erp.module.job.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Administrator
 * @title OrderProcessJobTest
 * @description TODO
 * @date 2021/5/28 14:01
 */
public class OrderCostJobTest extends BaseTest {
    @Autowired
    OrderCostExportJob orderCostExportJob;

    @Autowired
    SaleKpiExportJob saleKpiExportJob;

    @Autowired
    CrmMgrExportJob crmMgrExportJob;

    @Test
    public void testOrderCostExportJob() {
        orderCostExportJob.startOrderCostExportFromErpJob();
    }
    @Test
    public void VV() {
        orderCostExportJob.testVV();
    }

    @Test
    public void testSaleKpiExportJob() {
        saleKpiExportJob.startSaleKpiExportFromErpJob();
    }

    @Test
    public void testCrmMgrExportJob() {
        crmMgrExportJob.startCrmMgrExportFromErpJob();
    }


}
