package com.futuremap.custom.entity;

import java.sql.Timestamp;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class BaseEntity {
	
	
	@TableId(type = IdType.ASSIGN_UUID)
	protected String id;
	protected Integer delStatus;
	@TableField(value = "created_by", fill = FieldFill.INSERT) // 新增执行
	protected String createdBy;
	@TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
	protected String updatedBy;
	protected Timestamp updatedAt;
	protected Timestamp createdAt;


}