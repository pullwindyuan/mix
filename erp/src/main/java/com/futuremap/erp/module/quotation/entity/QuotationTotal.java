package com.futuremap.erp.module.quotation.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
@ApiModel(value = "QuotationTotal对象", description = "")
public class QuotationTotal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * autoid
     */
    @ApiModelProperty(value = "autoid")
    private String autoid;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称 ")
    @Excel(name = "账套")
    private String companyName;



    /**
     * 本币金额
     */
    @ApiModelProperty(value = "本币金额")
    private BigDecimal inatsum;
    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    @Excel(name = "订单号")
    private String csocode;

    /**
     * 订单行号
     */
    @ApiModelProperty(value = "订单行号")
    @Excel(name = "订单行号")
    private Integer irowno;

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private LocalDateTime ddate;
    /**
     * 日期
     */
    @Excel(name = "日期")
    private String _ddate;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    private String companyCode;


    /**
     * 存货编码
     */
    @ApiModelProperty(value = "存货编码")
    @Excel(name = "存货编码")
    private String cinvcode;

    /**
     * 存货名称
     */
    @ApiModelProperty(value = "存货名称")
    @Excel(name = "存货名称")
    private String cinvname;

    /**
     * 规格
     */
    @ApiModelProperty(value = "规格")
    private String cinvstd;

    /**
     * 计量
     */
    @ApiModelProperty(value = "计量")
    private String cinvmUnit;

    /**
     * 采购材料成本
     */
    @ApiModelProperty(value = "(实际)材料成本")
    @Excel(name = "(实际)材料成本")
    private BigDecimal materialCost;

    /**
     * 制造费用
     */
    @ApiModelProperty(value = "(实际)制造费用")
    @Excel(name = "(实际)制造费用")
    private BigDecimal manufactureCost;

    /**
     * 人工费用
     */
    @ApiModelProperty(value = "(实际)人工费用")
    @Excel(name = "(实际)人工费用")
    private BigDecimal laborCost;

    /**
     * 管理费用
     */
    @ApiModelProperty(value = "(实际)管理费用")
    @Excel(name = "(实际)管理费用")
    private BigDecimal mgrCost;

    /**
     * 船务费用
     */
    @ApiModelProperty(value = "(实际)船务费用")
    @Excel(name = "(实际)船务费用")
    private BigDecimal shippingCost;

    /**
     * (实际)退税前利润
     */
    @ApiModelProperty(value = "(实际)退税前利润")
    @Excel(name = "(实际)退税前利润")
    private BigDecimal profit;

    /**
     * 报价材料费用
     */
    @ApiModelProperty(value = "(报价)材料费用")
    @Excel(name = "(报价)材料费用")
    private BigDecimal quotationMaterialCost;

    /**
     * 报价制造费用
     */
    @ApiModelProperty(value = "(报价)制造费用")
    @Excel(name = "(报价)制造费用")
    private BigDecimal quotationManufactureCost;

    /**
     * 报价人工费用
     */
    @ApiModelProperty(value = "(报价)人工费用")
    @Excel(name = "(报价)人工费用")
    private BigDecimal quotationLaborCost;

    /**
     * 报价管理费用
     */
    @ApiModelProperty(value = "(报价)管理费用")
    @Excel(name = "(报价)管理费用")
    private BigDecimal quotationMgrCost;

    /**
     * 船务费用
     */
    @ApiModelProperty(value = "(报价)船务费用")
    @Excel(name = "(报价)船务费用")
    private BigDecimal quotationShippingCost;

    /**
     * 报价退税前利润
     */
    @ApiModelProperty(value = "(报价)退税前利润")
    @Excel(name = "(报价)退税前利润")
    private BigDecimal quotationProfit;

    /**
     * 报价单价
     */
    @ApiModelProperty(value = "报价单价")
    @Excel(name = "报价单价")
    private BigDecimal quotationUnitprice;

    /**
     * 报价成本
     */
    @ApiModelProperty(value = "报价成本")
    @Excel(name = "报价成本")
    private BigDecimal quotationCost;

    /**
     * 实际成本
     */
    @ApiModelProperty(value = "实际成本")
    @Excel(name = "实际成本")
    private BigDecimal actualCost;
    /**
     * 汇率
     */
    @ApiModelProperty(value = "汇率")
    private BigDecimal exchangeRate;
    /**
     * 差异
     */
    @ApiModelProperty(value = "差异")
    @Excel(name = "差异")
    private BigDecimal diff;

    /**
     * 差异率
     */
    @ApiModelProperty(value = "差异率")
    @Excel(name = "差异率")
    private BigDecimal diffRadio;


}
