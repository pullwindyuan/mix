package com.futuremap.datamanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据处理方式描述枚举，为了规范描述，采用枚举来定义
 * @author dell
 */
@Getter
@AllArgsConstructor
public enum DataRelationEnum {
    /**
     *
     */
    NONE(0, "无关联关系", "独立数据列"),
    TREE(1, "树形关系", "数据列之间组成树形关系");


    private final Integer code;
    private final String name;
    private final String desc;
}
