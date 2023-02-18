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
package com.futuremap.erp.module.saleorder.entity;

import org.apache.poi.hpsf.Decimal;

/**
 * @author Administrator
 * @title SaleOrderErp
 * @description 销售订单
 * @date 2021/5/26 9:20
 */
public class SaleOrderErp {
    /**
     * 销售订单号
     */
    private String csocode;
    /**
     * 销售订单行号
     */
    private Integer irowno;



    /**
     * 公司编码
     */
    private String companyCode;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 客户编码
     */
    private String cCusCode;
    /**
     * 客户简称
     */
    private String cCusabbName;
    /**
     * 客户名称
     */
    private String cCusName;
    /**
     * 币种名称
     */
    private String cexchName;
    /**
     * 原币金额
     */
    private Decimal fsummoney;
    /**
     * 本币金额
     */
    private Decimal fnatsummoney;
    /**
     * 出口订单标记
     */
    private Integer exFlag;
}
