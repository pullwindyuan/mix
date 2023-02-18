/**
 * 
 */
package com.futuremap.custom.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年1月19日 下午2:24:59
* 类说明
*/
/**
 * @author futuremap
 *
 */
@Data
@ApiModel("关健字请求分页")
public class KeyworkSearch {


    @ApiModelProperty(value="关键词(模糊)", required = true)
    @NotBlank(message="关键词 必填")
    private String keyword;
    
    @ApiModelProperty(value="当前页码", required = true)
    @NotNull(message="当前页码 必填")
	Long curPage;
	
	@ApiModelProperty(value="一页数量", required = true)
	@NotNull(message="一页数量 必填")
	Long pageSize;
}
