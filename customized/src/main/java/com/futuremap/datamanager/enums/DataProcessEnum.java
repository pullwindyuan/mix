package com.futuremap.datamanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据处理方式描述枚举，为了规范描述，采用枚举来定义
 * @author dell
 */
@Getter
@AllArgsConstructor
public enum DataProcessEnum {
    /**
     *
     */
    AVG(0, "均值", "对数据聚合求平均值"),
    SUM(1, "求和", "对数据聚合求和"),
    SORT(2, "求和", "对数据进行分类"),
    ORDER(3, "排序", "对数据进行排序"),
    EXTRACT_FOREST(4, "提取多个树形结构", "对数据中包含的树形结构进行提取，一般为数据中的分类信息的枚举、多级联动的数据结构等"),
    GROUP(5, "分组聚合", "对数据进行分组聚合，数据条数可能减少"),
    RATIO(6, "比例", "对数据进行分组聚合后再计算出各自所占比例，本质上包含了分类、求和等前置数据处理"),
    COUNT(7, "计数", "对数据条目数进行统计"),
    FIELD_SPLIT(8, "字段拆分", "对数据的指定指定字段按照指定规则拆分成多个字段"),
    FIELD_ARITHMETIC(9, "字段运算", "对数据指定单个或者多个字段按照指定规则进行运算，可能增加字段也可能替换其中的原字段"),
    AGGREGATE(99, "聚合", "聚合");


    private final Integer code;
    private final String name;
    private final String desc;
}
