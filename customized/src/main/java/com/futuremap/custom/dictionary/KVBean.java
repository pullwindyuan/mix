package com.futuremap.custom.dictionary;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * key-value数据的基本存储实体类
 * @author dell
 */
@Data
@Accessors(chain = true)
public class KVBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String value;
    private String rule;
    private String name;
    private String desc;
}
