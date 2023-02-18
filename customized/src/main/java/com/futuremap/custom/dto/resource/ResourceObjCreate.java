/**
 * 
 */
package com.futuremap.custom.dto.resource;

import javax.validation.constraints.NotNull;

import org.springframework.web.context.request.RequestContextHolder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年8月10日 上午11:13:11
* 类说明
*/
@Data
@ApiModel("资源创建请求")
public class ResourceObjCreate {

	@ApiModelProperty(value = "id")
	private String id;
	
	/**
	 * 所有者
	 */
	@ApiModelProperty(value = "所有者" ,required = true)
	@NotNull
	protected String createdBy;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
	protected String type;

	/**
	 * 标题
	 */
	@ApiModelProperty("标题")
	protected String title;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	protected String name;

	/**
	 * 父级资源id
	 */
	@ApiModelProperty("父级资源id")
	protected String parentId;

	/**
	 * 子资源集合，用结构化形式可以嵌套存储
	 */
	@ApiModelProperty("子资源集合，用结构化形式可以嵌套存储")
	protected String children;

	/**
	 * 外部资源标识id
	 */
	@ApiModelProperty("外部资源标识id")
	protected String externalId;

	/**
	 * 扩展
	 */
	@ApiModelProperty("扩展")
	protected String extra;

	/**
	 * 布局
	 */
	@ApiModelProperty("布局")
	protected String layout;

	/**
	 * 动态数据
	 */
	@ApiModelProperty("动态数据")
	protected String dynamicData;

	/**
	 * 静态数据
	 */
	@ApiModelProperty("静态数据")
	protected String staticData;

	/**
	 * 应用的模板id
	 */
	@ApiModelProperty(value = "应用的模板id 多个用,分隔")
	protected String templateId;

	

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
		RequestContextHolder.getRequestAttributes().setAttribute("userId", createdBy,
				0);
	}
}
