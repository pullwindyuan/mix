/**
 * 
 */
package com.futuremap.custom.dto;

import java.util.List;

import com.futuremap.custom.dto.function.FunctionSummary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年2月1日 下午7:41:41
* 类说明
*/
/**
 * @author futuremap
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("柱状分布")
public class ColumnChart {
	@ApiModelProperty(value="平均")
	String avg;

	@ApiModelProperty(value="数据")
	List<Columnar> columns;
	
}
