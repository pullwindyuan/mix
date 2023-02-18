/**
 * 
 */
package com.futuremap.custom.dto.resource;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年8月10日 上午11:13:11
* 类说明
*/
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel("列表修改基类")
public class ResourceTemplateUpdate extends ResourceTemplateCreate{
	
	@ApiModelProperty("id")
	@NotBlank
	private String id;
}
