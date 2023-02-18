package com.futuremap.erp.module.operation.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author futuremap
 * @since 2021-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CcodeMapping对象", description="")
public class CcodeMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 子公司编码
     */
    @ApiModelProperty(value = "子公司编码")
    private String companyCode;

    /**
     * 科目编码
     */
    @ApiModelProperty(value = "科目编码")
    private String ccode;

    /**
     * 科目名称
     */
    @ApiModelProperty(value = "科目名称")
    private String ccodeName;

    /**
     * 科目类别
     */
    @ApiModelProperty(value = "科目类别")
    private String ccodeClass;


}
