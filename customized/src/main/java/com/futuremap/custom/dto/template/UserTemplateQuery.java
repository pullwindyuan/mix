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
@ApiModel("用户模板查询基类")
public class UserTemplateQuery {

	@ApiModelProperty(value="公司Id", example = "3463",required = true)
	@NotNull
	protected String companyId;

	protected String type;
}
