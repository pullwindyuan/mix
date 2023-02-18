package com.futuremap.custom.dictionary;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 规则描述工厂类
 * @author dell
 */
@Data
@Accessors(chain = true)
public class RuleFactory  implements Serializable {
    private static final long serialVersionUID = 1L;
}
