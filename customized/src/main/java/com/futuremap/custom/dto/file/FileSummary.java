/**
 * 
 */
package com.futuremap.custom.dto.file;
/**
* @author 作者 E-mail:
* @version 创建时间：2021年8月9日 下午3:30:17
* 类说明
*/
/**
 * @author futuremap
 *
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 
 */
@Data
@ApiModel("文件返回")
public class FileSummary {

	@ApiModelProperty("文件名称")
	String fname;
	
	
	@ApiModelProperty("hash")
	String hash;
	
	@ApiModelProperty("key")
	String key;
	
	@ApiModelProperty("hash")
	Long fsize;
	
	@ApiModelProperty("downloadUrl")
	String downloadUrl;
	
}
