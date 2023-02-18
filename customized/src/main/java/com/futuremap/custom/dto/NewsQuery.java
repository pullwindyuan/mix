/**
 * 
 */
package com.futuremap.custom.dto;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年2月4日 上午9:08:50
* 类说明
*/
/**
 * @author futuremap
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel("舆情查询基类")
public class NewsQuery extends BaseListQuery{
	
	
	@ApiModelProperty(value="情绪 积极-positive 消极-negative 中立-none", required = true)
	String impact;
	
	@ApiModelProperty(value="类别码", required = true)
	String tags;

}
