package com.futuremap.resourceManager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 资源描述枚举，为了规范描述，采用枚举来定义
 * @author dell
 */
@Getter
@AllArgsConstructor
public enum UtilTypeEnum {
    /**
     *
     */
    CALENDAR(0, "日历", "数字格式"),
    SEARCH(1, "搜索", "文本格式"),
    FILTER(2, "过滤", "时间日期格式"),
    SHORTCUT(3, "文本", "文本格式");

    private final Integer code;
    private final String name;
    private final String desc;
}
