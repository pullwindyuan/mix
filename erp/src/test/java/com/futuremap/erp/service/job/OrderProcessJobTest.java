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
import com.futuremap.erp.module.job.PurchOrderExportJob;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.futuremap.erp.module.job.SaleOrderExportJob;

/**
 * @author Administrator
 * @title OrderProcessJobTest
 * @description TODO
 * @date 2021/5/28 14:01
 */
public class OrderProcessJobTest extends BaseTest {
    @Autowired
    SaleOrderExportJob saleOrderExportJob;

    @Autowired
    PurchOrderExportJob purchOrderExportJob;
    //销售订单入库
    @Test
    public void testStartSaleOrderExporJob(){
        saleOrderExportJob.startSaleOrderExportFromErpJob();
    }

    //采购订单全流程
    @Test
    public void testStartPurchOrderExportJob(){
        purchOrderExportJob.startPurchOrderExportByDay();
//        purchOrderExportJob.startPurchOrderExport();
    }


    @Test
    public void testStartHistoryPurchOrderExportJob(){
        purchOrderExportJob.startHistoryPurchOrderExportByDay();
    }

    @Test
    public void startHistorySaleoutExportByDay(){
        purchOrderExportJob.startHistorySaleoutExportByDay();
    }
    @Test
    public void startHistoryRecordInExportByDay(){
        purchOrderExportJob.startHistoryRecordInExportByDay();
    }

    //采购供应商与销售客户映射
    @Test
    public void testStartPurchOrderMappingSaleOrderExportJob(){
        purchOrderExportJob.startPurchToSaleOrderExport();
    }

    @Test
    public void testStartHistoryPurchOrderMappingSaleOrderExportJob(){
        purchOrderExportJob.startHistoryPurchToSaleOrderExport();
    }

    @Test
    public void testStartHistoryrecordOutExportJob(){
        purchOrderExportJob.startHistoryrecordOutExportByDay();
    }

    @Test
    public void testStartHistoryrpurchQtyUpdateExportByDay(){
        purchOrderExportJob.startHistoryrpurchQtyUpdateExportByDay();
    }

    //销售发票回款处理
    @Test
    public void testStartInvoiceExportJob(){
        purchOrderExportJob.startInvoiceExport();
    }

    @Test
    public void testStartHistoryInvoiceExportJob(){
        purchOrderExportJob.startHistoryInvoiceExport();
    }


    //采购发票付款处理
    @Test
    public void testStartInvoicePaymentExportJob(){
        purchOrderExportJob.startInvoicePaymentExport();
    }

    @Test
    public void testStartHistoryInvoicePaymentExportJob(){
        purchOrderExportJob.startHistoryInvoicePaymentExport();
    }


    @Test
    public void createSaleOrderDetaileView() {
        saleOrderExportJob.createSaleOrderDetaileView();
    }

    @Test
    public void createPurchOrderDetaileView() {
        purchOrderExportJob.createPurchBillToSaleBillMapper();
    }

    @Test
    public void createAllData() {
        saleOrderExportJob.createAllData();
    }
}
