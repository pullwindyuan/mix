/**
 * 
 */
package com.futuremap.custom.entity.file;
/**
* @author 作者 E-mail:
* @version 创建时间：2021年8月9日 下午3:40:43
* 类说明
*/
/**
 * @author futuremap
 *
 */

import lombok.Data;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author 
 */
@Data
@TableName("file_info")
public class FileInfo implements Serializable {
	
	@ApiModelProperty("文件ID")
	@TableId(type = IdType.ASSIGN_UUID)
    private long id;

	@ApiModelProperty("文件名称")
    private String fileName;

	@ApiModelProperty("文件目录")
    private String identifier;

	@ApiModelProperty("文件大小")
    private Long totalSize;

	@ApiModelProperty("文件类型")
    private String type;

	@ApiModelProperty("下载路径")
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
