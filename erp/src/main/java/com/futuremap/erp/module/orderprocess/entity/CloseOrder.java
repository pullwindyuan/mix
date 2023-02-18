package com.futuremap.erp.module.orderprocess.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author futuremap
 * @since 2021-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CloseOrder对象", description="")
public class CloseOrder implements Serializable {

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
     * 采购订单号
     */
    @ApiModelProperty(value = "采购订单号")
    private String cpoid;

    /**
     * 销售订单号
     */
    @ApiModelProperty(value = "销售订单号")
    private String csocode;

    /**
     * 销售订单行号
     */
    @ApiModelProperty(value = "销售订单行号")
    private String irowno;
    /**
     * 采购订单自增号
     */
    @ApiModelProperty(value = "自增号")
    private String autoid;

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
     * 采购订单日志
     */
    @ApiModelProperty(value = "采购订单日志")
    private LocalDateTime dpodate;

    /**
     * 付款币种名称
     */
    @ApiModelProperty(value = "付款币种名称")
    private String apCexchName;

    /**
     * 采购订单原币金额
     */
    @ApiModelProperty(value = "采购订单原币金额")
    private BigDecimal imoney;

    /**
     * 采购订单本币金额
     */
    @ApiModelProperty(value = "采购订单本币金额")
    private BigDecimal inatmoney;

    /**
     * 应付金额
     */
    @ApiModelProperty(value = "应付金额")
    private BigDecimal payableMoney;

    /**
     * 已付金额
     */
    @ApiModelProperty(value = "已付金额")
    private BigDecimal paidMoney;

    /**
     * 未付金额
     */
    @ApiModelProperty(value = "未付金额")
    private BigDecimal noPay;


    /**
     * 客户简称
     */
    @ApiModelProperty(value = "客户简称")
    private String ccusAbbname;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String ccusName;

    /**
     * 销售订单原币金额
     */
    @ApiModelProperty(value = "销售订单原币金额")
    private BigDecimal isum;

    /**
     * 销售订单本币金额
     */
    @ApiModelProperty(value = "销售订单本币金额")
    private BigDecimal inatsum;

    /**
     * 回款日期
     */
    @ApiModelProperty(value = "回款日期")
    private LocalDateTime dvouchDate;

    /**
     * 回款币种名称
     */
    @ApiModelProperty(value = "回款币种名称")
    private String arCexchName;

    /**
     * 回款本币金额
     */
    @ApiModelProperty(value = "回款本币金额")
    private BigDecimal iamount;

    /**
     * 回款原币金额
     */
    @ApiModelProperty(value = "回款原币金额")
    private BigDecimal iamountFob;


}
