/**
 * 
 */
package com.futuremap.custom.dto.template;

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
@ApiModel("模板查询基类")
public class TemplateQuery {

	@ApiModelProperty(value="模板名称", example = "销售模板 sell",required = true)
	@NotNull
	protected String name;

	
}
