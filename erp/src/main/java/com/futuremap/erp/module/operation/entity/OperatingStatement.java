package com.futuremap.erp.module.operation.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
@ApiModel(value="OperatingStatement对象", description="")
public class OperatingStatement implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 年月
     */
    @ApiModelProperty(value = "年月")
    private String yearmonth;

    /**
     * 年度
     */
    @ApiModelProperty(value = "年度")
    private Integer year;

    /**
     * 月度
     */
    @ApiModelProperty(value = "月度")
    private Integer month;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @TableField(exist=false)
    private String companyName;

    /**
     * 币种名称
     */
    @ApiModelProperty(value = "币种名称")
    private String cexchName;

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
     * 出口订单标记
     */
    @ApiModelProperty(value = "出口订单标记")
    private Integer exflag;

    /**
     * 层级
     */
    @ApiModelProperty(value = "层级")
    private Integer levelType;

    /**
     * 排序编号
     */
    @ApiModelProperty(value = "排序编号")
    private Integer sort;
    /**
     * 父编码
     */
    @ApiModelProperty(value = "父编码")
    private String parentCode;
   /**
     * 类型编码
     */
    @ApiModelProperty(value = "类型编码")
    private String classCode;

    /**
     * 科目名称
     */
    @ApiModelProperty(value = "科目名称")
    private String name;
}
