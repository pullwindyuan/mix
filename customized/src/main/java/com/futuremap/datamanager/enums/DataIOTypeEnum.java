package com.futuremap.datamanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据格式描述枚举，为了规范描述，采用枚举来定义
 * @author dell
 */
@Getter
@AllArgsConstructor
public enum DataIOTypeEnum {
    /**
     *
     */
    FIELD(0, "字段", "表字段"),
    VALUE(1, "值", "直接值"),
    OTHER(99, "其他", "其他类型");


    private final Integer code;
    private final String name;
    private final String desc;
}
