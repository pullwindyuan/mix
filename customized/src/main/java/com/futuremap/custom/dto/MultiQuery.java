package com.futuremap.custom.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel("多条件查询")
public class MultiQuery extends BaseListQuery {

	@ApiModelProperty(value = "行业")
	List<String> industry;

	@ApiModelProperty(value = "企业类型")
	List<String> econKind;
	
	@ApiModelProperty(value = "企业状态")
	List<String> status;

	@ApiModelProperty(value = "时间范围")
	List<DateRange> startDateRange;

	@ApiModelProperty(value = "注册资本")
	List<IntegerRange> registCapiValue;
	
	@ApiModelProperty(value = "省份数组-  湖南省")
	List<String> province;
	
	@ApiModelProperty(value = "省份数组-  永州市")
	List<String> city;
	
	
	@ApiModelProperty(value = "区 县 数组-  冷水滩区")
	List<String> county;
	
	@ApiModelProperty(value = "公司名称")
	String companyName;

}
