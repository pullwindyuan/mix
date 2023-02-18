/**
 * 
 */
package com.futuremap.custom.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年2月8日 上午9:38:18
* 类说明
*/
/**
 * @author futuremap
 *
 */
@Data
@ApiModel("数值范围查询")
public class IntegerRange {
	
	@ApiModelProperty(value="开始")
	Integer start;
	
	@ApiModelProperty(value="结束")
	Integer end;
}
