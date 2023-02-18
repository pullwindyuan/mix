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
package com.futuremap.erp.module.orderCost.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class CrmMgrQuery extends CrmMgr {
    /**
     * 订单日期 ____~____ 日期表单 ____~____
     * 订单号   ___________
     * 订单金额  ____~____
     * 利润率   ____~____
     */

    private String ddate1;
    private String ddate2;

    private String orderValue1;
    private String orderValue2;

    private String profitRate1;
    private String profitRate2;

    private String columnVisit;
}
