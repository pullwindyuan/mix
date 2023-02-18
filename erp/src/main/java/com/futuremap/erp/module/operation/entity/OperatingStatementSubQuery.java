package com.futuremap.erp.module.operation.entity;

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
public class OperatingStatementSubQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 年月
     */
    @ApiModelProperty(value = "年月")
    private String yearmonth;

    /**
     * 年度
     */
    @ApiModelProperty(value = "年度", required = true)
    private Integer year;

    /**
     * 月度
     */
    @ApiModelProperty(value = "月度")
    private Integer month;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码", required = true)
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @TableField(exist=false)
    private String companyName;

    /**
     * 层级
     */
    @ApiModelProperty(value = "层级")
    private Integer levelType;
   /**
     * 类型编码
     */
    @ApiModelProperty(value = "类型编码", required = true)
    private String classCode;
}
