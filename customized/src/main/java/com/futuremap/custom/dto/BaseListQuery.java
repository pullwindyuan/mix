package com.futuremap.custom.dto;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel("列表查询基类")
public class BaseListQuery {
	
	private Integer createdBy;
	
	private Timestamp startDate;

	private Timestamp endDate;
	
	@ApiModelProperty(value="当前页", required = true)
	Integer curPage;
	
	@ApiModelProperty(value="一页数量", required = true)
	Integer pageSize;
}
