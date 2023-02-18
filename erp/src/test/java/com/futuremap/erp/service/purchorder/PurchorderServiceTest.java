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
package com.futuremap.erp.service.purchorder;

import com.futuremap.erp.BaseTest;
import com.futuremap.erp.module.orderprocess.entity.PurchOrder;
import com.futuremap.erp.module.orderprocess.entity.PurchOrderQuery;
import com.futuremap.erp.module.orderprocess.service.impl.PurchOrderServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Administrator
 * @title PurchorderServiceTest
 * @description TODO
 * @date 2021/6/9 12:02
 */
public class PurchorderServiceTest extends BaseTest {

    @Autowired
    PurchOrderServiceImpl purchOrderService;
    @Test
    public void testPurchOrderServive() {
        PurchOrderQuery purchOrderQuery = new PurchOrderQuery();
        purchOrderQuery.setCsocode("SS-2020010409");
        List<PurchOrder> list = purchOrderService.findList(purchOrderQuery);
    }

}
