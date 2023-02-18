package com.futuremap.erp.module.orderprocess.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.futuremap.erp.common.warning.Warning;
import com.futuremap.erp.common.warning.WarningInfos;
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
 * @since 2021-07-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PurchBill对象", description="")
public class PurchBill extends WarningInfos implements Serializable {

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


}
