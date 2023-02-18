package com.futuremap.erp.module.orderCost.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * KPI
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SaleKpi对象", description = "销售绩效")
public class SaleKpi {


    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 上级ID
     */
    @ApiModelProperty(value = "上级ID")
    private Integer parentId;
    /**
     * 更新标志
     */
    @ApiModelProperty(value = "更新标志")
    private Integer updateFlag;
    /**
     * 子公司数据源
     */
    @ApiModelProperty(value = "子公司数据源")
    @Excel(name = "账套")
    private String companyLabel;


    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    @Excel(name = "订单号")
    private String orderDetailCode;

    /**
     * 订单行号
     */
    @ApiModelProperty(value = "订单行号")
    private String orderNumber;
    /**
     * 下单日期
     */
    @ApiModelProperty(value = "下单日期")
    private LocalDateTime ddate;
    /**
     * 下单日期
     */
    @Excel(name = "下单日期")
    private String _ddate;

    /**
     * 子公司名
     */
    @ApiModelProperty(value = "子公司名")
    private String companyName;

    /**
     * 子公司代码
     */
    @ApiModelProperty(value = "子公司代码")
    private String companyCode;


    /**
     * 业务员
     */
    @ApiModelProperty(value = "业务员")
    @Excel(name = "业务员")
    private String saleName;
    /**
     * 业务员编码
     */
    @ApiModelProperty(value = "业务员编码")
    private String saleCode;

    /**
     * 客户名
     */
    @ApiModelProperty(value = "客户名")
    @Excel(name = "客户名")
    private String customerName;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;
    /**
     * 部门编码
     */
    @ApiModelProperty(value = "部门编码")
    private String cdepCode;
    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    @Excel(name = "产品编码")
    private String productCode;
    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    @Excel(name = "产品名称")
    private String productName;
    /**
     * 提点
     */
    @ApiModelProperty(value = "提点")
    @Excel(name = "提点")
    private BigDecimal profitRate;
    /**
     * 下单当月汇率
     */
    @ApiModelProperty(value = "下单当月汇率")
    @Excel(name = "下单当月汇率")
    private BigDecimal exchangeRate;


    /**
     * 部门名
     */
    @ApiModelProperty(value = "部门名")
    private String cdepName;


    /**
     * 月份
     */
    @ApiModelProperty(value = "月份")
    private String dmonth;

    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额")
    @Excel(name = "订单金额")
    private BigDecimal orderValue;
    /**
     * 报批表毛利
     */
    @ApiModelProperty(value = "报批表毛利")
    @Excel(name = "报批表毛利")
    private BigDecimal definedProfit;
    /**
     * 净利润
     */
    @ApiModelProperty(value = "净利润")
    @Excel(name = "净利润")
    private BigDecimal retainedProfit;
    /**
     * 本月到账金额
     */
    @ApiModelProperty(value = "本月到账金额")
    @Excel(name = "本月到账金额")
    private BigDecimal currentAmount;


    /**
     * 提成金额
     */
    @ApiModelProperty(value = "提成金额")
    @Excel(name = "提成金额")
    private BigDecimal profitAmount;
    /**
     * 提成占净利比
     */
    @ApiModelProperty(value = "提成占净利比")
    @Excel(name = "提成占净利比")
    private BigDecimal outAmountRate;
    /**
     * 占提成总额比例
     */
    @ApiModelProperty(value = "占提成总额比例")
    @Excel(name = "占提成总额比例")
    private BigDecimal outTotalRate;


    private BigDecimal format4point(BigDecimal val) {
        val = val.setScale(4, BigDecimal.ROUND_HALF_UP);
        return val;
    }


}
