package com.futuremap.datamanager.bean.BO;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 运算器
 * @author Pullwind
 */
@Data
public class DataIoBO implements Serializable {
    /**
     * 数据IO类型，FIELD表示来源于表字段，VALUE表示是直接可用的值
     */
    String type;
    /**
     * IO数据的映射关系，key表示条件信息，value嵌套的map的key表示IO对应的输入字段名称或者
     * （当type为FIELD是表示字段名，当type为VALUE是表示直接值），map的value表示字段名称或者直接值
     */
    Map<String, Map<String, JSONArray>> map;
}
