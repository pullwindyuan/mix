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
@EqualsAndHashCode(callSuper = false)
public class OrderCostQuery extends OrderCost {
    /**
     * 收入金额范围    ____~____
     * 成本小计范围    ____~_____
     * 毛利率小计范围     ____~____
     * 合计成本范围  ____~____
     * 利润率   ____~____
     */
    private String income1;
    private String income2;

    private String costSum1;
    private String costSum2;

    private String profitSumRadio1;
    private String profitSumRadio2;

    private String costTotal1;
    private String costTotal2;

    private String profitRadio1;
    private String profitRadio2;
    private String columnVisit;


}
