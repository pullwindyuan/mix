/**
 * 
 */
package com.futuremap.custom.dto.resource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年8月10日 上午11:13:11
* 类说明
*/
@Data
@ApiModel("素材查询基类")
public class ResourceTemplateQuery {

	@ApiModelProperty(value="排序 created_at 时间 ref_count 引用次数", example = "created_at, ref_count" )
	protected String orderBy;

	@ApiModelProperty(value="素材名", example = "" )
	protected String name;
	
	@ApiModelProperty(value="创建人", example = "" )
	protected String createdBy;

}
