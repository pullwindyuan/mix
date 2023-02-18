package com.futuremap.custom.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author futuremap
 * @since 2021-08-10
 */
@Data
//@TableName("resource_template")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ResourceTemplate extends BaseEntity {

	/**
	 * 所有者
	 */
	@ApiModelProperty("所有者")
	private String owner;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
	private String type;

	/**
	 * 标题
	 */
	@ApiModelProperty("标题")
	private String title;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 父模板id
	 */
	@ApiModelProperty("父模板id")
	private String parentId;

	/**
	 * 子资源模板集合，用结构化形式可以嵌套存储
	 */
	@ApiModelProperty("子资源模板集合，用结构化形式可以嵌套存储")
	private String children;

	/**
	 * 外部资源标识id
	 */
	@ApiModelProperty("外部资源标识id")
	private String externalId;

	/**
	 * 扩展
	 */
	@ApiModelProperty("扩展")
	private String extra;

	/**
	 * 布局
	 */
	@ApiModelProperty("布局")
	private String layout;
	
	/**
	 * 引用次数
	 */
	@ApiModelProperty("引用次数")
	private Long refCount;

}
