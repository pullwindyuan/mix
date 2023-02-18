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
@TableName("cfg_company")
public class ComCompanyConfig extends BaseEntity {

	@ApiModelProperty("优先支持-priority")
	private int priority;
	@ApiModelProperty("一般支持general")
	private int general;
	@ApiModelProperty("限制支持 limit")
	private int limitStatus;
	@ApiModelProperty("禁止支持 prohibit)")
	private int prohibit;

	@ApiModelProperty(" 非新模式范围exclude)")
	private int exclude;

	@ApiModelProperty("省份")
	private String province;

	@ApiModelProperty("城市")
	private String city;

	@ApiModelProperty("县 区")
	private String county;

	@ApiModelProperty("纬度")
	private String lng;

	@ApiModelProperty("经度")
	private String lat;

}
