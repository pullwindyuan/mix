package com.futuremap.datamanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据分析查询维度组合方式枚举，为了规范描述，采用枚举来定义
 * @author dell
 */
@Getter
@AllArgsConstructor
public enum AnalyzeQueryCriteriaRelationEnum {
    /**
     *
     */
    INDEPENDENCE_ONLY(0, "仅独立", "分析的时候不与其他条件组合查询匹配数据"),
    UNION_ONLY(1, "仅联合", "分析的时候必须需与其他条件组合来匹配数据"),
    INDEPENDENCE_UNION(2, "可独立可联合", "分析的时候既可以独立查询也允许与其他条件组合来匹配数据"),
    QUERY_ONLY(99, "仅查询", "仅用于查询匹配数据，不参与分析，如果仅存在本条件进行查询，需要与其他维度进行遍历分组进行分析，也就是永远不会针对本字段进行分组");

    private final Integer code;
    private final String name;
    private final String desc;
}
