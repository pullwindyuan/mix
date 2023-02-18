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
package com.futuremap.erp.module.orderprocess.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Administrator
 * @title RecordinListDto
 * @description TODO
 * @date 2021/6/15 15:49
 */
public class RecordinListDto  {

    private String companyCode;

    private String companyName;

    /**
     * 销售订单号
     */
    private String csocode;
    /**
     * 入库单号
     */
    private String ccode;

    /**
     * 销售订单行号
     */
    private String irowno;

    /**
     * erp自增id
     */
    private String autoid;

    private String cinvcode;

    private String cinvname;

    private String cinvstd;

    /**
     * 主计量
     */
    private String cinvmUnit;



    /**
     * 生产订单行号
     */
    private Integer imoseq;

    /**
     * 出库日期
     */
    private LocalDateTime ddate;

    /**
     * 数量
     */
    private BigDecimal iquantity;

}
