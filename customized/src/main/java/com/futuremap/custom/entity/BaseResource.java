/**
 * 
 */
package com.futuremap.custom.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年8月17日 上午9:11:40
* 类说明
*/
/**
 * @author futuremap
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BaseResource extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 209613299900192561L;

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

}
