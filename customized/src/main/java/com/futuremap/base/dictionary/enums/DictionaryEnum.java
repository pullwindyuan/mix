package com.futuremap.base.dictionary.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字典分类描述枚举，为了规范描述，采用枚举来定义
 * @author dell
 */
@Getter
@AllArgsConstructor
public enum DictionaryEnum {
    KV(0, "键值字典", "仅支持键值"),
    GD(1, "分组字典", "仅支持分组"),
    GD_KV(2, "分组和键值字典", "同时支持分组和键值");


    private Integer code;
    private String name;
    private String desc;
}
