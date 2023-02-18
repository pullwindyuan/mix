package com.futuremap.base.dictionary;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * key-value数据的基本存储实体类
 * @author dell
 */
@Data
@Accessors(chain = true)
public class KeyValue implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "值", required = true)
    private  Object value;
    @ApiModelProperty(value = "规则描述", required = true)
    private String rule;
    @ApiModelProperty(value = "名称", required = true)
    private String name;
    @ApiModelProperty(value = "详情", required = true)
    private String desc;
    @ApiModelProperty(value = "类型", required = true)
    private String type;
}
