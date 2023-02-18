package com.futuremap.custom.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import com.futuremap.custom.dto.Columnar;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author futuremap
 * @since 2021-02-04
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("com_company")
public class ComCompany extends BaseEntity {

	private static final long serialVersionUID = 1L;


    @ApiModelProperty("指派人")
    private String userId;
    
    @ApiModelProperty("指派人")
    private String userName;

    @ApiModelProperty("公司id")
    private String companyId;

    @ApiModelProperty("公司名")
    private String companyName;

    @ApiModelProperty("状态 待营销-0 营销中-1 存量-2 黑名单-3")
    private int status;
    
    @ApiModelProperty("完成日期")
    private Date finished;

    @ApiModelProperty("公司所属行业")
    private String industry;

    @ApiModelProperty("公司注册资本")
    private String registCapi;

    @ApiModelProperty("成立日期")
    private Date startDate;



}
