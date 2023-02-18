package com.futuremap.erp.module.saleorder.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.futuremap.erp.common.warning.Warning;
import com.futuremap.erp.common.warning.WarningEnum;
import com.futuremap.erp.common.warning.WarningInfos;
import com.futuremap.erp.module.auth.service.impl.RoleColumnServiceImpl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;

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
@ApiModel(value="SaleOrder对象", description="")
public class SaleOrderDto extends WarningInfos implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 单据日期
     */
    @ApiModelProperty(value = "单据日期")
    @Excel(name = "单据日期")
    private LocalDateTime ddate;

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
     * 公司账套编码
     */
    @ApiModelProperty(value = "公司账套编码")
    @Excel(name = "账套编码")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Excel(name = "公司名称")
    private String companyName;

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
     * 部门编码
     */
    @ApiModelProperty(value = "部门编码")
    @Excel(name = "部门编码")
    private String cdepcode;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    @Excel(name = "部门名称")
    private String cdepname;

    /**
     * 存货编号
     */
    @ApiModelProperty(value = "存货编号")
    @Excel(name = "存货编号")
    private String cinvcode;

    /**
     * 存货名称
     */
    @ApiModelProperty(value = "存货名称")
    @Excel(name = "存货名称")
    private String cinvname;

    /**
     * 规格型号
     */
    @ApiModelProperty(value = "规格型号")
    @Excel(name = "规格型号")
    private String cinvstd;

    /**
     * 计量单位
     */
    @ApiModelProperty(value = "计量单位")
    @Excel(name = "计量单位")
    private String cinvmUnit;

    /**
     * 客户编码
     */
    @ApiModelProperty(value = "客户编码")
    @Excel(name = "客户编码")
    private String ccusCode;

    /**
     * 客户简称
     */
    @ApiModelProperty(value = "客户简称")
    @Excel(name = "客户简称")
    private String ccusAbbname;

    /**
     * 客户全称
     */
    @ApiModelProperty(value = "客户全称")
    @Excel(name = "客户全称")
    private String ccusName;

    /**
     * 币种名称
     */
    @ApiModelProperty(value = "币种名称")
    @Excel(name = "币种名称")
    private String cexchName;
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    @Excel(name = "数量")
    private BigDecimal iquantity;

    /**
     * 已入库数量
     */
    @ApiModelProperty(value = "已入库数量")
    @Excel(name = "已入库数量")
    private BigDecimal iInQuantitySum;

    /**
     * 已出库数量
     */
    @ApiModelProperty(value = "已出库数量")
    @Excel(name = "已出库数量")
    private BigDecimal iOutQuantitySum;

    /**
     * 未入库数量
     */
    @ApiModelProperty(value = "未入库数量")
    @Excel(name = "未入库数量")
    private BigDecimal iNotInQuantitySum;

    /**
     * 未出库数量
     */
    @ApiModelProperty(value = "未出库数量")
    @Excel(name = "未出库数量")
    private BigDecimal iNotOutQuantitySum;

    /**
     * 已经挪货数量
     */
    @ApiModelProperty(value = "已经挪货数量")
    @Excel(name = "已经挪货数量")
    private BigDecimal iSwitchQuantitySum;

    /**
     * 未挪完数量
     */
    @ApiModelProperty(value = "未挪完数量")
    @TableField(exist=false)
    @Excel(name = "未挪完数量")
    private BigDecimal iNotSwitchQuantitySum;

    /**
     * 含税单价
     */
    @ApiModelProperty(value = "含税单价")
    @Excel(name = "含税单价")
    private BigDecimal itaxunitprice;

    /**
     * 原币金额
     */
    @ApiModelProperty(value = "原币金额")
    @Excel(name = "原币金额")
    private BigDecimal isum;

    /**
     * 本币金额
     */
    @ApiModelProperty(value = "本币金额")
    @Excel(name = "本币金额")
    private BigDecimal inatsum;

    /**
     * 预计完工时间
     */
    @ApiModelProperty(value = "预计完工时间")
    @Excel(name = "预计完工时间")
    private LocalDateTime dcompleteDate;

    /**
     * 预计发货时间
     */
    @ApiModelProperty(value = "预计发货时间")
    @Excel(name = "预计发货时间")
    private LocalDateTime dpredatebt;

      /**
     * 异常扣费
     */
    @ApiModelProperty(value = "异常扣费")
    @Excel(name = "异常扣费")
    private BigDecimal exceptionCost;

    /**
     * 出口订单标记
     */
    @ApiModelProperty(value = "出口订单标记")
    @Excel(name = "出口订单标记")
    private Integer exflag;

    /**
     * 订单状态 0初始化1采购下单2材料入库3成品入库4销售出库5收付款
     */
    @ApiModelProperty(value = "订单状态 0初始化1采购下单2材料入库3成品入库4销售出库5收付款")
    @Excel(name = "订单状态")
    private Integer status;

    /**
     * 对应销售订单出货时间
     */
    @ApiModelProperty(value = "出货时间")
    @Excel(name = "出货时间")
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
     * 挪货预警枚举名称
     */
    @ApiModelProperty(value = "挪货预警枚举名称")
    private String swWarningName;

    /**
     * 收款预警枚举名称
     */
    @ApiModelProperty(value = "收款预警枚举名称")
    private String arWarningName;

    /**
     * 发货预警枚举名称
     */
    @ApiModelProperty(value = "发货预警枚举名称")
    private String btWarningName;

    /**
     * 挪货预警枚举名称
     */
    @ApiModelProperty(value = "挪货预警")
    @Excel(name = "挪货预警")
    private String swWarningDesc;

    /**
     * 收款预警枚举名称
     */
    @ApiModelProperty(value = "收款预警")
    @Excel(name = "收款预警")
    private String arWarningDesc;

    /**
     * 发货预警枚举名称
     */
    @ApiModelProperty(value = "发货预警")
    @Excel(name = "发货预警")
    private String btWarningDesc;


    public LocalDateTime getArDate() {
        if(saleOutDate == null) {
            return null;
        }
        return saleOutDate.plusDays(backDay);
    }

    public void initWarningInfo() {

        //挪货
        getWarning().put(swWarningName, Warning.createWarning(WarningEnum.valueOf(swWarningName).getTypeName(), WarningEnum.valueOf(swWarningName).getWarningBean()));
        //出货

        //btWarningName = WarningEnum.SALE_NORMAL.getTypeName();
        WarningEnum btWarning =  WarningEnum.SALE_NORMAL;

        if(LocalDateTime.now().isBefore(dpredatebt)) {
            //已过出货时间的
            //未发货的
            if(saleOutDate == null) {
                //btWarningName = WarningEnum.SALE_NOT_START.getTypeName();
                btWarning = WarningEnum.SALE_NOT_START;
            }else if(iNotOutQuantitySum != null && iNotOutQuantitySum.intValue() == 0) {
                //btWarningName = WarningEnum.SALE_COMPLETED_BUT_DELAY.name();
                btWarning = WarningEnum.SALE_COMPLETED_BUT_DELAY;
            }else if(iNotOutQuantitySum != null && iNotOutQuantitySum.intValue() > 0) {
                //部分发货已逾期
                //btWarningName = WarningEnum.SALE_DELAY.getTypeName();
                btWarning = WarningEnum.SALE_DELAY;
            }
            //已发货的


            //btWarningName = WarningEnum.SALE_DELAY.getTypeName();
            btWarning =  WarningEnum.SALE_DELAY;
        }else if(LocalDateTime.now().isEqual(dpredatebt)){
            //刚到出货时间的
            //未发货的
            if(saleOutDate == null) {
               // btWarningName = WarningEnum.SALE_NOT_START.getTypeName();
                btWarning = WarningEnum.SALE_NOT_START;
            }else if(iNotOutQuantitySum != null && iNotOutQuantitySum.intValue() == 0) {
               // btWarningName = WarningEnum.SALE_COMPLETED.getTypeName();
                btWarning = WarningEnum.SALE_COMPLETED;
            }else if(iNotOutQuantitySum != null && iNotOutQuantitySum.intValue() > 0) {
                //部分发货已逾期
                //btWarningName = WarningEnum.SALE_DELAY.getTypeName();
                btWarning = WarningEnum.SALE_DELAY;
            }
        }else {
            //未到发货时间
            if(saleOutDate == null) {
                //btWarningName = WarningEnum.EMPTY.getTypeName();
                btWarning = WarningEnum.EMPTY;
            }else if(iNotOutQuantitySum != null && iNotOutQuantitySum.intValue() == 0) {
                //btWarningName = WarningEnum.SALE_COMPLETED.getTypeName();
                btWarning = WarningEnum.SALE_COMPLETED;
            }else if(iNotOutQuantitySum != null && iNotOutQuantitySum.intValue() > 0) {
                //部分发货
                //btWarningName = WarningEnum.SALE_START.getTypeName();
                btWarning = WarningEnum.SALE_START;
            }
        }
        getWarning().put(btWarning.name(), Warning.createWarning(btWarningName, btWarning.getWarningBean()));
        //收款
        getWarning().put(arWarningName, Warning.createWarning(WarningEnum.valueOf(arWarningName).getTypeName(), WarningEnum.valueOf(arWarningName).getWarningBean()));
    }

    public String getSwWarningDesc() {
        return swWarningName != null ? WarningEnum.valueOf(swWarningName).getDesc() : null;
    }

    public String getArWarningDesc() {

        return arWarningName != null ? WarningEnum.valueOf(arWarningName).getDesc() : null;
    }

    public String getBtWarningDesc() {
        return btWarningName != null ? WarningEnum.valueOf(btWarningName).getDesc() : null;
    }

    public BigDecimal getiInQuantitySum() {
        return iInQuantitySum != null ? iInQuantitySum : null;
    }

    public BigDecimal getiOutQuantitySum() {
        return iOutQuantitySum != null ? iOutQuantitySum : null;
    }

    public BigDecimal getiNotInQuantitySum() {
        return iNotInQuantitySum != null ? iNotInQuantitySum : null;
    }

    public BigDecimal getiNotOutQuantitySum() {
        return iNotOutQuantitySum != null ? iNotOutQuantitySum : null;
    }

    public BigDecimal getiSwitchQuantitySum() {
        return  iSwitchQuantitySum != null ? iSwitchQuantitySum : null;
    }

    public BigDecimal getiNotSwitchQuantitySum() {
        return iNotSwitchQuantitySum != null ? iNotSwitchQuantitySum : null;
    }
}
