package com.futuremap.resourceManager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 资源描述枚举，为了规范描述，采用枚举来定义
 * @author dell
 */
@Getter
@AllArgsConstructor
public enum ResourceTypeEnum {
    /**
     *
     */
    MENU(0, "菜单", "菜单"),
    /**
     *
     */
    SCENE(1, "场景", "场景"),
    /**
     *
     */
    CELL(2, "细胞", "细胞"),
    /**
     *
     */
    UTIL(3, "工具", "工具"),
    /**
     *
     */
    FUNCTION(4, "功能", "功能"),
    /**
     *
     */
    VIEW(5, "可视化", "可视化"),
    /**
     *
     */
    ELEMENT(6, "可视化元素", "可视化元素"),
    /**
     *
     */
    SHORTCUT(7, "快捷方式", "快捷方式");

    private final Integer code;
    private final String name;
    private final String desc;
}
