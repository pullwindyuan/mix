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
package com.futuremap.erp.mapper.purchorder;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.BaseTest;
import com.futuremap.erp.module.orderprocess.entity.PurchOrder;
import com.futuremap.erp.module.orderprocess.entity.PurchOrderQuery;
import com.futuremap.erp.module.orderprocess.mapper.PurchOrderMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Administrator
 * @title PurchOrderMapperTest
 * @description TODO
 * @date 2021/6/1 14:03
 */
public class PurchOrderMapperTest  extends BaseTest {
    @Autowired
    PurchOrderMapper purchOrderMapper;
    @Test
    public void testFindList(){
        IPage<PurchOrder> page = new Page<PurchOrder>();

        PurchOrderQuery purchOrderQuery = new PurchOrderQuery();

//        IPage<PurchOrder> list = purchOrderMapper.findList(page, purchOrderQuery);
    } 
}
