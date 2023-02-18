/**
 * 
 */
package com.futuremap.custom.dto.resource;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年8月10日 上午11:13:11
* 类说明
*/
@Data
@ApiModel("列表查询基类")
public class ResourceTemplateCreate {

	/**
	 * 所有者
	 */
	@ApiModelProperty(value="所有者", example = "" )
	@NotNull
	protected String createdBy;

	/**
	 * 类型
	 */
	@ApiModelProperty(value="类型", example = "" )
	protected String type;

	/**
	 * 标题
	 */
	@ApiModelProperty(value="标题", example = "" )
	protected String title;

	/**
	 * 名称
	 */
	@ApiModelProperty(value="名称", example = "" )
	protected String name;

	/**
	 * 父模板id
	 */
	@ApiModelProperty(value="父模板id", example = "" )
	protected String parentId;

	/**
	 * 子资源模板集合，用结构化形式可以嵌套存储
	 */
	@ApiModelProperty(value="子资源模板集合，用结构化形式可以嵌套存储", example = "" )
	protected String children;

	/**
	 * 外部资源标识id
	 */
	@ApiModelProperty(value="外部资源标识id", example = "" )
	protected String externalId;

	/**
	 * 扩展
	 */
	@ApiModelProperty(value="扩展", example = "" )
	protected String extra;

	/**
	 * 布局
	 */
	@ApiModelProperty(value="布局", example = "" )
	protected String layout;

}
