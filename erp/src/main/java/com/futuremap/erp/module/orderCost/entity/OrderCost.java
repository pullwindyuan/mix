package com.futuremap.erp.module.orderCost.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OrderCost对象", description = "订单成本")
public class OrderCost {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 月份
     */
    @ApiModelProperty(value = "月份")
    @Excel(name = "月份")
    private String dmonth;

    /**
     * 子公司代号
     */
    @ApiModelProperty(value = "子公司代号")
    @Excel(name = "帐套")
    private String companyLabel;
    /**
     * 子公司帐套
     */
    @ApiModelProperty(value = "子公司帐套")
    private String companyCode;
    /**
     * 组别
     */
    @ApiModelProperty(value = "组别")
    @Excel(name = "组别")
    private String cdepname;


    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String ccusCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Excel(name = "客户")
    private String ccusName;


    /**
     * 发票号
     */
    @ApiModelProperty(value = "发票号")
    @Excel(name = "发票号")
    private String invoiceId;
    /**
     * autoid
     */
    @ApiModelProperty(value = "autoid")
    private String autoid;
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
    @Excel(name = "订单行号")
    private String orderNumber;
    /**
     * 行号
     */
    @ApiModelProperty(value = "行号")
    private String irowno;

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
     * 发票上的数量
     */
    @ApiModelProperty(value = "发票上的数量")
    @Excel(name = "数量")
    private BigDecimal fquantity;
    /**
     * 收入金额
     */
    @ApiModelProperty(value = "收入金额")
    @Excel(name = "收入金额")
    private BigDecimal income;

    /**
     * 采购材料成本
     */
    @ApiModelProperty(value = "采购材料成本")
    @Excel(name = "采购材料成本")
    private BigDecimal materialCost;

    /**
     * 核销金额
     */
    @ApiModelProperty(value = "核销金额")
    private BigDecimal hexiaoCost;
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
     * 运营费用
     */
    @ApiModelProperty(value = "运营费用")
    @Excel(name = "运营费用")
    private BigDecimal runCost;


    /**
     * 成本小计
     */
    @ApiModelProperty(value = "成本小计")
    @Excel(name = "成本小计")
    private BigDecimal costSum;
    /**
     * 毛利小计
     */
    @ApiModelProperty(value = "毛利小计")
    @Excel(name = "毛利小计")
    private BigDecimal profitSum;
    /**
     * 毛利率小计
     */
    @ApiModelProperty(value = "毛利率小计")
    @Excel(name = "毛利率小计")
    private BigDecimal profitSumRadio;

    /**
     * 合计成本
     */
    @ApiModelProperty(value = "合计成本")
    @Excel(name = "合计成本")
    private BigDecimal costTotal;
    /**
     * 利润
     */
    @ApiModelProperty(value = "利润")
    @Excel(name = "利润")
    private BigDecimal profit;
    /**
     * 利润率
     */
    @ApiModelProperty(value = "利润率")
    @Excel(name = "利润率")
    private BigDecimal profitRadio;

    /**
     * 发票复核时间
     */
    @ApiModelProperty(value = "发票复核时间")
    private LocalDateTime dverifysystime;
    /**
     * 应收审核日期
     */
    @ApiModelProperty(value = "应收审核日期")
    private String darverifierdate;
    /**
     * 订单发货日期
     */
    @ApiModelProperty(value = "订单发货日期")
    private String deliverDay;
    /**
     * 应收账款日期
     */
    @ApiModelProperty(value = "应收账款日期")
    private String planReturnDay;
    /**
     * 发票制单时间
     */
    @ApiModelProperty(value = "发票制单时间")
    private LocalDateTime dcreatesystime;
    /**
     * 订单来源，0：出口，1：国内
     */
    @ApiModelProperty(value = "订单来源，0：出口，1：国内")
    private Integer orderSrc;

    /**
     * 订单类型，0：ERP默认，1：委外，2：挪货
     */
    @ApiModelProperty(value = "订单类型，0：ERP默认，1：委外，2：挪货")
    private Integer orderType;
    /**
     * 采购原币含税单价
     */
    @ApiModelProperty(value = "采购原币含税单价")
    private BigDecimal taxPrice;


    /**
     * 业务员编号
     */
    @ApiModelProperty(value = "业务员编号")
    private String cpersonCode;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "业务员名称")
    @Excel(name = "业务员")
    private String cpersonName;


    /**
     * 供应商编号
     */
    @ApiModelProperty(value = "供应商编号")
    private String cvenCode;
    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private String cvenName;


    /**
     * 父级标示
     */
    @ApiModelProperty(value = "父级标示")
    private String cparentCode;
    /**
     * 子级标示
     */
    @ApiModelProperty(value = "子级标示")
    private String cchildCode;

    public void setTaxPrice(BigDecimal taxPrice) {
        taxPrice = taxPrice.setScale(4, BigDecimal.ROUND_HALF_UP);
        this.taxPrice = taxPrice;
    }

    public void setFquantity(BigDecimal fquantity) {
        fquantity = fquantity.setScale(4, BigDecimal.ROUND_HALF_UP);
        this.fquantity = fquantity;
    }

    private BigDecimal format4point(BigDecimal val) {
        val = val.setScale(2, BigDecimal.ROUND_HALF_UP);
        return val;
    }

    public void setIncome(BigDecimal income) {
        income = format4point(income);
        this.income = income;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        materialCost = format4point(materialCost);
        this.materialCost = materialCost;
    }

    public void setManufactureCost(BigDecimal manufactureCost) {
        manufactureCost = format4point(manufactureCost);
        this.manufactureCost = manufactureCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        laborCost = format4point(laborCost);
        this.laborCost = laborCost;
    }

    public void setMgrCost(BigDecimal mgrCost) {
        mgrCost = format4point(mgrCost);
        this.mgrCost = mgrCost;
    }

    public void setRunCost(BigDecimal runCost) {
        runCost = format4point(runCost);
        this.runCost = runCost;
    }

    public void setCostSum(BigDecimal costSum) {
        costSum = format4point(costSum);
        this.costSum = costSum;
    }

    public void setProfitSum(BigDecimal profitSum) {
        profitSum = format4point(profitSum);
        this.profitSum = profitSum;
    }

    public void setProfitSumRadio(BigDecimal profitSumRadio) {

        this.profitSumRadio = profitSumRadio.setScale(4, BigDecimal.ROUND_HALF_UP);
        ;
    }

    public void setCostTotal(BigDecimal costTotal) {
        costTotal = format4point(costTotal);
        this.costTotal = costTotal;
    }

    public void setProfit(BigDecimal profit) {
        profit = format4point(profit);
        this.profit = profit;
    }

    public void setProfitRadio(BigDecimal profitRadio) {
        this.profitRadio = profitRadio.setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    public void setDmonth(String dmonth) {
        try {
            String dmonth_new = dmonth.substring(0, 7).replace("-", "");
            this.dmonth = dmonth_new;
        } catch (Exception e) {
            this.dmonth = dmonth;
        }

    }
}
