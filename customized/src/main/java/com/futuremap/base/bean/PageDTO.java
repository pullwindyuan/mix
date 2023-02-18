package com.futuremap.base.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 不支持多重排序，一次只能使用一个维度进行排序
 * @author Pullwind
 */
@Data
public class PageDTO implements Serializable {
    @ApiModelProperty(value = "分页页码:从0开始", notes = "\"dateTime\": \"2021-12-13 00:00:00\"", required = true)
    private Integer page;
    @ApiModelProperty(value = "分页大小", notes = "\"dateTime\": \"2021-12-13 00:00:00\"", required = true)
    private Integer size;
//    @ApiModelProperty(value = "排序：DESC(降序) 或者 ASC(升序) ", required = true)
//    private String sortDirection;
//    @ApiModelProperty(value = "排序字段名称", required = true)
//    private String sortProperty;
}
