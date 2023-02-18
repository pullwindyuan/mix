package com.futuremap.erp.module.orderCost.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 订单成本表单，按月度汇总的项目
 */

@Data
@EqualsAndHashCode()
public class OrderCostTotal {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 月份
     */
    private String dmonth;

    private BigDecimal format4point(BigDecimal val) {
        val = val.setScale(4, BigDecimal.ROUND_HALF_UP);
        return val;
    }
    /**
     * 子公司代号
     */
    @ApiModelProperty(value = "子公司代号")
    private String companyLabel;
    /**
     * 出口营收总金额
     */

    private BigDecimal exportAmount;
    /**
     * 国内营收总金额
     */

    private BigDecimal domesticAmount;

    /**
     * 国内外营收总金额
     */

    private BigDecimal totalAmount;

    /**
     * 制造费用
     */

    private BigDecimal manufactureCost;
    /**
     * 人工费用
     */

    private BigDecimal laborCost;
    /**
     * 管理费用
     */

    private BigDecimal mgrCost;
    /**
     * 运营费用
     */

    private BigDecimal runCost;

    public void setExportAmount(BigDecimal exportAmount) {
        exportAmount = format4point(exportAmount);
        this.exportAmount = exportAmount;
    }

    public void setDomesticAmount(BigDecimal domesticAmount) {
        domesticAmount = format4point(domesticAmount);
        this.domesticAmount = domesticAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        totalAmount = format4point(totalAmount);
        this.totalAmount = totalAmount;
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


    public void setDmonth(String dmonth) {
        try {
            String dmonth_new = dmonth.substring(0, 7).replace("-", "");
            this.dmonth = dmonth_new;
        } catch (Exception e) {
            this.dmonth = dmonth;
        }
    }


}
