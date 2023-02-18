/**
 * 
 */
package com.futuremap.custom.dto.file;
/**
* @author 作者 E-mail:
* @version 创建时间：2021年8月9日 下午3:29:45
* 类说明
*/
/**
 * @author futuremap
 *
 */

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 
 */
@Data
@ApiModel("合并文件请求")
public class FileInfoCreate {
	
	@ApiModelProperty("文件名称")
	@NotBlank
    private String fileName;

	@ApiModelProperty("文件目录")
	@NotBlank
    private String identifier;

	@ApiModelProperty("文件大小")
    private Long totalSize;

	@ApiModelProperty("文件类型")
	@NotBlank
    private String type;

	//@ApiModelProperty("文件路径")
	//@NotBlank
    //private String location;

 

}
