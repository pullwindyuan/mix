package com.futuremap.erp.module.saleorder.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.futuremap.erp.common.warning.WarningInfos;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class SaleOrderQuery extends WarningInfos implements Serializable {

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
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称,用于反向搜索对应的销售订单")
    private String cvenName;

    /**
     * 订单状态 0初始化1采购下单2材料入库3成品入库4销售出库5收付款
     */
    @ApiModelProperty(value = "订单状态 0初始化1采购下单2材料入库3成品入库4销售出库5收付款")
    @Excel(name = "订单状态")
    private Integer status;

    private String startDate;

    private String endDate;
}
