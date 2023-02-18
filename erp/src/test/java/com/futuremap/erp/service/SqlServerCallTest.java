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
package com.futuremap.erp.service;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.erp.BaseTest;
import com.futuremap.erp.module.orderprocess.entity.CloseOrder;
import com.futuremap.erp.module.erp.mapper.CloseVouchMapper;
import com.futuremap.erp.module.erp.mapper.OperationMapper;
import com.futuremap.erp.module.orderprocess.entity.PurchOrder;
import com.futuremap.erp.module.erp.mapper.PurchMapper;
import com.futuremap.erp.module.saleorder.entity.SaleOrder;
import com.futuremap.erp.module.erp.mapper.SaleOrderErpMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Administrator
 * @title SqlServerCallTest
 * @description TODO
 * @date 2021/5/25 17:59
 */
public class SqlServerCallTest extends BaseTest {
    @Autowired
    CloseVouchMapper closeVouchMapper;
    @Autowired
    OperationMapper operationMapper;
    @Autowired
    PurchMapper purchMapper;
    @Autowired
    SaleOrderErpMapper saleOrderErpMapper;

    @Test
    public void testSqlServerCall(){
        List<CloseOrder> closeVouch = closeVouchMapper.getCloseListBySaleOrder("WT2020111E");
    }
    @Test
    public void testGetExOrderCountData(){
        List<JSONObject> exOrderCountData = operationMapper.getExOrderCountData("2021-03-01", "2021-04-01");
    }
    @Test
    public void testGetSaleOrderCountData(){
        List<JSONObject> exOrderCountData = operationMapper.getSaleOrderCountData("2021-03-01", "2021-04-01", "");
    }
    @Test
    public void testGetSaleOrderList(){
        List<SaleOrder> saleOrderErpList = saleOrderErpMapper.getSaleOrderList("2021-03-01", "2021-04-01");
    }
    @Test
    public void testGetExSaleOrderList(){
        List<SaleOrder> saleOrderErpList = saleOrderErpMapper.getExSaleOrderList("2021-03-01", "2021-04-01");
    }

    @Test
    public void testGetPurchOrderInfoByCsocode(){
        List<PurchOrder> wt2020111E = purchMapper.getPurchOrderInfoByCsocode("WT2020111E");
    }

    @Test
    public void testGetLedgerReport(){
        operationMapper.getLedgerReport("temp123","202101","GL14","5101");
    }

    @Test
    public void testDropTemptable(){
        operationMapper.dropTempTable("temp123");
    }

}
