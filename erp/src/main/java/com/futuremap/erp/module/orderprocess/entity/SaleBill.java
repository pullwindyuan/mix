package com.futuremap.erp.module.orderprocess.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.futuremap.erp.common.warning.Warning;
import com.futuremap.erp.common.warning.WarningEnum;
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
 * @since 2021-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SaleBill对象", description="")
public class SaleBill extends WarningInfos implements Serializable {

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
     * 销售订单号
     */
    @ApiModelProperty(value = "销售订单号")
    private String csocode;

    /**
     * 订单行号
     */
    @ApiModelProperty(value = "订单行号")
    private Integer irowno;

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

    /**
     * 业务员编码
     */
    @ApiModelProperty(value = "业务员编码")
    @Excel(name = "业务员编码")
    private String cpersoncode;

    /**
     * 业务员名称
     */
    @ApiModelProperty(value = "业务员名称")
    @Excel(name = "业务员名称")
    private String cpersonname;

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
     * 回款时间
     */
    @ApiModelProperty(value = "回款时间")
    private LocalDateTime dvouchDate;

    /**
     * 收款单号
     */
    @ApiModelProperty(value = "收款单号")
    private String receiptCode;

    /**
     * 回款金额
     */
    @ApiModelProperty(value = "回款金额")
    private BigDecimal conlectionMoney;

    /**
     * 回款本币金额
     */
    @ApiModelProperty(value = "回款本币金额")
    private BigDecimal conlectionNatMoney;

    /**
     * 未回款金额
     */
    @ApiModelProperty(value = "未回款金额")
    private BigDecimal noConlectionMoney;
}
