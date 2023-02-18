package com.futuremap.custom.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 规则描述枚举，为了规范规则的描述，采用枚举来定义
 * @author dell
 */
@Getter
@AllArgsConstructor
public enum RuleEnum {
    ENUM(0, "", "枚举验证"),
    ENUM_VALUE_EQUAL(2, "", "枚举值相等验证"),
    REG_EXP(1, "", "正则表达式"),
    EMPTY(999, "", "空");


    private Integer code;
    private String group;
    private String desc;
}
