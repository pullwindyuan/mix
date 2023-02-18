package com.futuremap.erp.module.orderprocess.entity;

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
 * @since 2021-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SaleoutList对象", description="")
public class SaleoutList extends WarningInfos implements Serializable {

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
     * 销售订单行号
     */
    @ApiModelProperty(value = "销售订单行号")
    private String irowno;

    /**
     * erp自增id
     */
    @ApiModelProperty(value = "erp自增id")
    private String autoid;

    /**
     * 存货编码
     */
    @ApiModelProperty(value = "存货编码")
    private String cinvcode;

    /**
     * 存货名称
     */
    @ApiModelProperty(value = "存货名称")
    private String cinvname;

    /**
     * 存货规格
     */
    @ApiModelProperty(value = "存货规格")
    private String cinvstd;

    /**
     * 主计量
     */
    @ApiModelProperty(value = "主计量")
    private String cinvmUnit;

    /**
     * 出库单号
     */
    @ApiModelProperty(value = "出库单号")
    private String ccode;

    /**
     * 出库日期
     */
    @ApiModelProperty(value = "出库日期")
    private LocalDateTime ddate;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private BigDecimal iquantity;

    /**
     * 数量
     */
    @ApiModelProperty(value = "累计数量")
    @TableField(exist=false)
    private BigDecimal iCountQuantity;


    /**
     * 出库数量
     */
    @ApiModelProperty(value = "出库数量")
    private BigDecimal foutquantity;

    /**
     * 含税单价
     */
    @ApiModelProperty(value = "含税单价")
    private BigDecimal itaxunitprice;

    /**
     * 原币金额
     */
    @ApiModelProperty(value = "原币金额")
    private BigDecimal isum;

    /**
     * 本币金额
     */
    @ApiModelProperty(value = "本币金额")
    private BigDecimal inatsum;

    /**
     * 累计本币金额
     */
    @ApiModelProperty(value = "累计本币金额")
    @TableField(exist=false)
    private BigDecimal iCountNatsum;

}
