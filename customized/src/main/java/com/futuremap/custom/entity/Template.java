package com.futuremap.custom.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
@TableName("cfg_template")
public class Template extends BaseEntity {

	private String type;

	private String name;

	private String en;
	
	private String dateType;

	private int isRequired;

	private int sort;

	private String pattern;

	private String tips;

}
