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
 * CRM
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CRM对象", description = "CRM")
public class CrmMgr {


    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单来源，0：出口，1：国内
     */
    @ApiModelProperty(value = "订单来源，0：出口，1：国内")
    private Integer orderSrc;

    /**
     * 发票号
     */
    @ApiModelProperty(value = "发票号")
    private String invoiceId;

    /**
     * 子公司名
     */
    @ApiModelProperty(value = "子公司名")
    @Excel(name = "账套")
    private String companyName;

    /**
     * 子公司代码
     */
    @ApiModelProperty(value = "子公司代码")
    private String companyCode;
    /**
     * 月份
     */
    @ApiModelProperty(value = "月份")
    private String dmonth;
    /**
     * 子公司数据源
     */
    @ApiModelProperty(value = "子公司数据源")
    private String companyLabel;
    /**
     * 组别
     */
    @ApiModelProperty(value = "组别")
    @Excel(name = "组别")
    private String cdepName;
    /**
     * 组别编码
     */
    @ApiModelProperty(value = "组别编码")
    private String cdepCode;
    /**
     * 订单日期
     */
    @ApiModelProperty(value = "订单日期")
    private LocalDateTime ddate;
    /**
     * 订单日期
     */
    @Excel(name = "订单日期")
    private String _ddate;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "主订单号")
    private String orderCode;

    /**
     * 子订单号
     */
    @ApiModelProperty(value = "订单号")
    @Excel(name = "订单号")
    private String orderDetailCode;

    /**
     * 订单行号
     */
    @ApiModelProperty(value = "订单行号")
    @Excel(name = "订单行号")
    private String orderNumber;


    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Excel(name = "客户名称")
    private String customerName;

    /**
     * 客户编码
     */
    @ApiModelProperty(value = "客户编码")
    private String customerCode;


    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    @Excel(name = "产品名称")
    private String productName;
    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    @Excel(name = "产品编码")
    private String productCode;
    /**
     * 发货日期
     */
    @ApiModelProperty(value = "发货日期")
    private String deliverDay;

    private BigDecimal format4point(BigDecimal val) {
        val = val.setScale(4, BigDecimal.ROUND_HALF_UP);
        return val;
    }

    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额")
    @Excel(name = "订单金额")
    private BigDecimal orderValue;

    /**
     * 回款金额
     */
    @ApiModelProperty(value = "回款金额")
    @Excel(name = "回款金额")
    private BigDecimal returnAmount;
    /**
     * 应回款日期
     */
    @ApiModelProperty(value = "应回款日期")
    private LocalDateTime returnPlanDate;
    /**
     * 应回款日期
     */
    @Excel(name = "应回款日期")
    private String _returnPlanDate;
    /**
     * 回款收到日期
     */
    @ApiModelProperty(value = "回款收到日期")
    private LocalDateTime returnDate;
    /**
     * 回款收到日期
     */
    @Excel(name = "回款收到日期")
    private String _returnDate;

    /**
     * 利润率
     */
    @ApiModelProperty(value = "利润率")
    @Excel(name = "利润率")
    private BigDecimal profitRate;


    /**
     * 采购材料成本
     */
    @ApiModelProperty(value = "采购材料成本")
    @Excel(name = "采购材料成本")
    private BigDecimal materialCost;

    /**
     * 制造费用
     */
    @ApiModelProperty(value = "制造费用")
    @Excel(name = "制造费用")
    private BigDecimal manufactureCost;

    /**
     * 人工费用
     */
    @ApiModelProperty(value = "人工费用")
    @Excel(name = "人工费用")
    private BigDecimal laborCost;

    /**
     * 管理费用
     */
    @ApiModelProperty(value = "管理费用")
    @Excel(name = "管理费用")
    private BigDecimal mgrCost;

    /**
     * 船务费用
     */
    @ApiModelProperty(value = "船务费用")
    @Excel(name = "船务费用")
    private BigDecimal shippingCost;

    /**
     * 利润
     */
    @ApiModelProperty(value = "利润")
    @Excel(name = "利润")
    private BigDecimal profit;
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private BigDecimal fquantity;


}
