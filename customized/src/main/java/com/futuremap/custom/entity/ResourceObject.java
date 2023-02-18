package com.futuremap.custom.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author futuremap
 * @since 2021-08-10
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel("资源表")
@TableName("resource_object")
public class ResourceObject extends ResourceTemplate implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3970090899096161832L;

	/**
	 * 动态数据
	 */
	private String dynamicData;

	/**
	 * 静态数据
	 */
	private String staticData;

	/**
	 * 应用的模板ID
	 */
	private String templateId;

}
