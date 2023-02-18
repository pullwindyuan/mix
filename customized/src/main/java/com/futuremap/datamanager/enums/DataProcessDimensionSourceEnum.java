package com.futuremap.datamanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据处理分析维度数据来源枚举，为了规范描述，采用枚举来定义
 * @author dell
 */
@Getter
@AllArgsConstructor
public enum DataProcessDimensionSourceEnum {
    /**
     *
     */
    CALENDAR_YEAR(0, "从日历中按年选择", "来源日历年"),
    CALENDAR_MONTH(1, "从日历中按月选择", "来源日历月"),
    CALENDAR_DAY(2, "从日历中按天选择", "来源日历天"),
    CALENDAR_DATETIME(3, "从日历中按时间", "来源日历时间"),
    YEAR(4, "年份", "年"),
    MONTH(5, "月", "每年的月"),
    DAY(6, "日", "每月的天"),
    TIME(7, "一天当中的时间", "每天的24小时时间"),
    EXTRACT_FROM_DATA(8, "从数据中提取", "从数据中提取分析维度"),
    MANUAL_INPUT(9, "手动自由输入", "分析维度由用户自定义输入，比如数字、文字等匹配条件");

    private final Integer code;
    private final String name;
    private final String desc;
}
