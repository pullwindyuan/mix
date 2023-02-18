package com.futuremap.custom.dictionary;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 规则描述实体类，value可以是任意数据结构
 * @author dell
 */
@Data
@Accessors(chain = true)
public class RuleBean implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 对应RuleEnum中的code值
     */
    private Integer code;
    private Object value;
    private String desc;
}
