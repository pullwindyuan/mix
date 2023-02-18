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

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.futuremap.erp.common.warning.Warning;
import com.futuremap.erp.common.warning.WarningEnum;
import com.futuremap.erp.common.warning.WarningInfos;
import com.futuremap.erp.module.orderprocess.entity.PurchBill;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Administrator
 * @title PurchBillDto
 * @description TODO
 * @date 2021/7/14 11:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PurchBillDto extends WarningInfos implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Excel(name = "公司编码")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 销售订单号
     */
    @ApiModelProperty(value = "销售订单号")
    @Excel(name = "销售订单号")
    private String csocode;

    /**
     * 销售订单行号
     */
    @ApiModelProperty(value = "销售订单行号")
    @Excel(name = "销售订单行号")
    private Integer irowno;
    /**
     * 采购订单日期
     */
    @ApiModelProperty(value = "采购订单日期")
    @Excel(name = "采购订单日期")
    private LocalDateTime dpodate;

    /**
     * 采购单号
     */
    @ApiModelProperty(value = "采购单号")
    @Excel(name = "采购单号")
    private String cordercode;

    /**
     * 采购订单子表id
     */
    @ApiModelProperty(value = "采购订单子表id")
    @Excel(name = "采购订单子表id")
    private String autoid;

    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "供应商编码")
    @Excel(name = "供应商编码")
    private String cvencode;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    @Excel(name = "供应商名称")
    private String cvenname;

    /**
     * 存货编码
     */
    @ApiModelProperty(value = "存货编码")
    @Excel(name = "存货编码")
    private String cinvcode;

    /**
     * 发票单据日期
     */
    @ApiModelProperty(value = "发票单据日期")
    @Excel(name = "发票单据日期")
    private LocalDateTime ddate;

    /**
     * 发票单据编号
     */
    @ApiModelProperty(value = "发票单据编号")
    @Excel(name = "发票单据编号")
    private String billCode;

    /**
     * 币种
     */
    @ApiModelProperty(value = "币种")
    @Excel(name = "币种")
    private String cexchName;

    /**
     * 发票本币金额
     */
    @ApiModelProperty(value = "发票本币金额")
    @Excel(name = "发票本币金额")
    private BigDecimal billNatMoney;

    /**
     * 发票金额
     */
    @ApiModelProperty(value = "发票金额")
    @Excel(name = "发票金额")
    private BigDecimal billMoney;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    @Excel(name = "数量")
    private BigDecimal ipbvquantity;

    /**
     * 付款日期
     */
    @ApiModelProperty(value = "付款日期")
    @Excel(name = "付款日期")
    private LocalDateTime paymentDvouchDate;

    /**
     * 收款单号
     */
    @ApiModelProperty(value = "收款单号")
    @Excel(name = "收款单号")
    private String paymentReceiptCode;

    /**
     * 已付款金额
     */
    @ApiModelProperty(value = "已付款金额")
    @Excel(name = "已付款金额")
    private BigDecimal paymentMoney;

    /**
     * 已付款本币金额
     */
    @ApiModelProperty(value = "已付款本币金额")
    @Excel(name = "已付款本币金额")
    private BigDecimal paymentNatMoney;

    /**
     * 未付款
     */
    @ApiModelProperty(value = "未付款")
    @Excel(name = "未付款")
    private BigDecimal noPaymentMoney;

    /**
     * 客户编码
     */
    @ApiModelProperty(value = "客户编码")
    @Excel(name = "客户编码")
    private String ccusCode;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Excel(name = "客户名称")
    private String ccusName;

    /**
     * 销售发票本币金额
     */
    @ApiModelProperty(value = "销售发票本币金额")
    @Excel(name = "销售发票本币金额")
    private BigDecimal saleBillNatMoney;
    /**
     * 回款时间
     */
    @ApiModelProperty(value = "回款时间")
    @Excel(name = "回款时间")
    private LocalDateTime dvouchDate;

    /**
     * 收款单号
     */
    @ApiModelProperty(value = "收款单号")
    @Excel(name = "收款单号")
    private String receiptCode;

    /**
     * 回款金额
     */
    @ApiModelProperty(value = "回款金额")
    @Excel(name = "回款金额")
    private BigDecimal conlectionMoney;

    /**
     * 回款本币金额
     */
    @ApiModelProperty(value = "回款本币金额")
    @Excel(name = "回款本币金额")
    private BigDecimal conlectionNatMoney;

    /**
     * 未回款金额
     */
    @ApiModelProperty(value = "未回款金额")
    @Excel(name = "未回款金额")
    private BigDecimal noConlectionMoney;

    /**
     *  累计回款金额
     */
    @ApiModelProperty(value = "累计回款金额")
    @Excel(name = "累计回款金额")
    private BigDecimal countMoney;

    /**
     * 对应销售订单出货时间
     */
    @ApiModelProperty(value = "对应销售订单出货时间")
    @Excel(name = "销售订单出货时间")
    private LocalDateTime saleOutDate;

    /**
     * 应收款时间
     */
    @ApiModelProperty(value = "应收款时间")
    @Excel(name = "应收款时间")
    private LocalDateTime arDate;

    /**
     * 账期，单位天
     */
    @ApiModelProperty(value = "账期：天")
    @Excel(name = "账期：天")
    private Integer backDay;

    /**
     * 预警枚举名称
     */
    @ApiModelProperty(value = "预警枚举名称")
    @TableField(exist=false)
    private String warningName;

    /**
     * 预警枚举名称
     */
    @ApiModelProperty(value = "回款预警")
    @Excel(name = "回款预警")
    @TableField(exist=false)
    private String warningDesc;

    public LocalDateTime getArDate() {
        if(saleOutDate == null) {
            return null;
        }
        return saleOutDate.plusDays(backDay);
    }

    public void initWarningInfo() {
        getWarning().put(warningName, Warning.createWarning(WarningEnum.valueOf(warningName).getTypeName(), WarningEnum.valueOf(warningName).getWarningBean()));
    }

    public String getWarningDesc() {
        return warningName != null ? WarningEnum.valueOf(warningName).getDesc() : null;
    }
}
