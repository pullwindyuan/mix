package com.futuremap.datamanager.bean.BO;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据处理器
 * @author Pullwind
 */
@Data
public class ProcessorBO implements Serializable {
    /**
     * 处理器名称
     */
    String name;
    /**
     * 处理规则
     */
    ProcessorRuleBO rule;
    /**
     * 处理器详情
     */
    String desc;
    /**
     * 处理器类型
     */
    String type;
    /**
     * 数据源，如果配置中没有指定数据源，就取模板中的指定的数据源
     */
    String source;
    /**
     * 持久化存储名称
     */
    String persistent;
}
