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
package com.futuremap.erp.module.orderprocess.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.futuremap.erp.common.warning.WarningInfos;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Administrator
 * @title PurchBillQuery
 * @description TODO
 * @date 2021/7/14 11:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PurchBillQuery extends WarningInfos implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 采购员编码
     */
    @ApiModelProperty(value = "采购员编码")
    @Excel(name = "采购员编码")
    private String cpersoncode;

    /**
     * 采购员名称
     */
    @ApiModelProperty(value = "采购员名称")
    @Excel(name = "采购员名称")
    private String cpersonname;

    /**
     * 销售订单号
     */
    @ApiModelProperty(value = "销售订单号")
    private String csocode;

    /**
     * 销售订单行号
     */
    @ApiModelProperty(value = "销售订单行号")
    private Integer irowno;
    /**
     * 采购订单日期
     */
    @ApiModelProperty(value = "采购订单日期")
    private LocalDateTime dpodate;

    /**
     * 采购单号
     */
    @ApiModelProperty(value = "采购单号")
    private String cordercode;

    /**
     * 采购订单子表id
     */
    @ApiModelProperty(value = "采购订单子表id")
    private String autoid;

    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "供应商编码")
    private String cvencode;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private String cvenname;

    /**
     * 存货编码
     */
    @ApiModelProperty(value = "存货编码")
    private String cinvcode;

    /**
     * 存货名称
     */
    @ApiModelProperty(value = "存货名称")
    private String cinvname;

    /**
     * 发票单据日期
     */
    @ApiModelProperty(value = "发票单据日期")
    private LocalDateTime ddate;

    /**
     * 发票单据编号
     */
    @ApiModelProperty(value = "发票单据编号")
    private String billCode;

    /**
     * 币种
     */
    @ApiModelProperty(value = "币种")
    private String cexchName;

    /**
     * 发票本币金额
     */
    @ApiModelProperty(value = "发票本币金额")
    private BigDecimal billNatMoney;

    /**
     * 发票金额
     */
    @ApiModelProperty(value = "发票金额")
    private BigDecimal billMoney;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private BigDecimal ipbvquantity;

    /**
     * 付款日期
     */
    @ApiModelProperty(value = "付款日期")
    private LocalDateTime paymentDvouchDate;

    /**
     * 收款单号
     */
    @ApiModelProperty(value = "收款单号")
    private String paymentReceiptCode;

    /**
     * 已付款金额
     */
    @ApiModelProperty(value = "已付款金额")
    private BigDecimal paymentMoney;

    /**
     * 已付款本币金额
     */
    @ApiModelProperty(value = "已付款本币金额")
    private BigDecimal paymentNatMoney;

    /**
     * 未付款
     */
    @ApiModelProperty(value = "未付款")
    private BigDecimal noPaymentMoney;

    private String startDate;

    private String endDate;

    private String startDvouchDate;

    private String endDvouchDate;

    private Integer paymentFlag;

    /**
     * 客户编码
     */
    @ApiModelProperty(value = "客户编码")
    private String ccusCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String ccusName;
}
