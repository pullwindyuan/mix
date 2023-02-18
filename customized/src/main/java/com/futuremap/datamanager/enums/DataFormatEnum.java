package com.futuremap.datamanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据格式描述枚举，为了规范描述，采用枚举来定义
 * @author dell
 */
@Getter
@AllArgsConstructor
public enum DataFormatEnum {
    /**
     *
     */
    NUMERAL(0, "数字", "数字格式"),
    STRING(1, "文本", "文本格式"),
    DATETIME(2, "时间", "时间日期格式"),
    INTEGER(2, "整数", "整数"),
    LONG(2, "长整型", "长整型"),
    DOUBLE(2, "双精度浮点型", "双精度浮点型");


    private final Integer code;
    private final String name;
    private final String desc;
}
