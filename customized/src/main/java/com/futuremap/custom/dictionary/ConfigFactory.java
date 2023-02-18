package com.futuremap.custom.dictionary;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 配置描述字典工厂，比如自定义表格数据中的字段规则配置描述
 * @author dell
 */
@Data
@Accessors(chain = true)
public class ConfigFactory implements Serializable {
    private static final long serialVersionUID = 1L;
}
