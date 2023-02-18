package com.futuremap.erp.common.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class BaseEntity {

	@TableId(type = IdType.AUTO)
	protected Integer id;
	
	@ApiModelProperty(value = "删除状态（0--未删除1--已删除）")
	@TableLogic(value = "0", delval = "1")
	@JsonIgnore
	public Integer delStatus;
	@TableField(value = "created_by", fill = FieldFill.INSERT) // 新增执行
	@JsonIgnore
	protected Integer createdBy;
	@TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
	@JsonIgnore
	protected Integer updatedBy;
	@JsonIgnore
	protected Timestamp updatedAt;
	@JsonIgnore
	protected Timestamp createdAt;

}