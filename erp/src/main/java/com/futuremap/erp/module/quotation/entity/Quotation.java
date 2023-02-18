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
 *
 *  @Excel(name = "materail cost")
 *     private BigDecimal materialCost;
 *     @Excel(name = "labor cost")
 *     private BigDecimal laborCost;
 *     @Excel(name = "exporting cost")
 *     private BigDecimal exportingCost;
 *     @Excel(name = "exchange rate")
 *     private BigDecimal exchangeRate;
 *     @Excel(name="Sales")
 *     private BigDecimal unitPrice;
 *     @Excel(name="Item no.")
 *     private String cinvcode;
 *     private BigDecimal profit;
 *     @Excel(name="TC")
 *     private BigDecimal cost;
 *     @Excel(name="VAL")
 *     private BigDecimal salePrice;
 *     @Excel(name="admin expense")
 *     private BigDecimal mgrCost;
 *     @Excel(name="factory expenses")
 *     private BigDecimal manufactureCost;
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Quotation对象", description="")
public class Quotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 存货编码
     */
    @ApiModelProperty(value = "存货编码")
    @Excel(name="Item no.")
    private String cinvcode;

    /**
     * 存货名称
     */
    @ApiModelProperty(value = "存货名称")
    private String cinvname;

    /**
     * 业务员名称
     */
    @ApiModelProperty(value = "业务员名称")
    private String salemanName;

    /**
     * 业务员简称
     */
    @ApiModelProperty(value = "业务员简称")
    private String salemanAbbrname;

    /**
     * 提点
     */
    @ApiModelProperty(value = "提点")
    private BigDecimal commissionPercent;

    /**
     * 汇率
     */
    @ApiModelProperty(value = "汇率")
    @Excel(name = "exchange rate")
    private BigDecimal exchangeRate;
    /**
     * autoid
     */
    @ApiModelProperty(value = "autoid")
    private String autoid;
    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    @Excel(name="Sales")
    private BigDecimal unitPrice;
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    @Excel(name="QTY")
    private BigDecimal fquantity;

    /**
     * 船务费用
     */
    @ApiModelProperty(value = "船务费用")
    @Excel(name = "exporting cost")
    private BigDecimal exportingCost;

    /**
     * 人工费用
     */
    @ApiModelProperty(value = "人工费用")
    @Excel(name = "labor cost")
    private BigDecimal laborCost;

    /**
     * 材料费用
     */
    @ApiModelProperty(value = "材料费用")
    @Excel(name = "materail cost")
    private BigDecimal materialCost;

    /**
     * 制造费用
     */
    @ApiModelProperty(value = "制造费用")
    @Excel(name="factory expenses")
    private BigDecimal manufactureCost;

    /**
     * 管理费用
     */
    @ApiModelProperty(value = "管理费用")
    @Excel(name="admin expense")
    private BigDecimal mgrCost;
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private LocalDateTime ddate;
    /**
     * 成本
     */
    @ApiModelProperty(value = "成本")
    @Excel(name="TC")
    private BigDecimal cost;

    /**
     * 售价
     */
    @ApiModelProperty(value = "售价")
    @Excel(name="VAL")
    private BigDecimal salePrice;


}
