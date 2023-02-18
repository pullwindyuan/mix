package com.futuremap.base.dictionary;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.TreeMap;

/**
 * 分组字典森林实体类
 * @author pullwind
 */
@Data
public class DictionaryForest {
    private  String id;
    private JSONObject extra;
    private TreeMap<String, DictionaryTree> dictionaryTreeList;
}
