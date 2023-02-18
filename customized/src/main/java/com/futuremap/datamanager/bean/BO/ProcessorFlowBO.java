package com.futuremap.datamanager.bean.BO;

import lombok.Data;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * 数据处理链
 * @author Pullwind
 */
@Data
public class ProcessorFlowBO implements Serializable {
    /**
     * 对应的数据模板ID
     */
    String templateId;
    /**
     * 处理器链映射
     */
    TreeMap<Integer, ProcessorBO> flowMap;
}
