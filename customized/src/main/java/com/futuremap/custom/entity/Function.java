package com.futuremap.custom.entity;


import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("cfg_function")
public class Function extends BaseEntity {
	/**
	 * 父id
	 */
	private String parentId;

	private Integer level;

	/**
	 * 功能名称
	 */
	private String name;

	/**
	 * 方法类型
	 */
	private String methodType;

	/**
	 * 方法名称
	 */
	private String methodName;

	/**
	 * url
	 */
	private String url;
}
