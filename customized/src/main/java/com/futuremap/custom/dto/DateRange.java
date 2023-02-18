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
@ApiModel("时间范围查询")
public class DateRange {
	
	@ApiModelProperty(value="时间-开始")
	Date start;
	
	@ApiModelProperty(value="时间-结束")
	Date end;
}
