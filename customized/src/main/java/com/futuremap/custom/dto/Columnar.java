/**
 * 
 */
package com.futuremap.custom.dto;

import java.util.List;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年2月1日 下午7:43:51
* 类说明
*/
/**
 * @author futuremap
 *
 */
@Data
@ApiModel("柱状统计")
public class Columnar {

	@ApiModelProperty(value="名称")
	String name;
	
	@ApiModelProperty(value="占比")
	String percent;
	
	@ApiModelProperty(value="数量")
	String count;
	
	@ApiModelProperty(value="子分类")
	List<Columnar> children;
}
