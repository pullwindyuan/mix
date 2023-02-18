package com.futuremap.datamanager.dataProcess;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.dictionary.bean.VO.DictionaryForestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 从数据样本中提取特征数据等
 * @author Pullwind
 */
@Service
@Slf4j
public class TemplateService {
    @Autowired
    private MongoTemplate mongoTemplate;

    private  static Map<String, DictionaryForestVO> gDictionaryForestMap = new HashMap<>() ;
    // Map<母模板ID+分组名称+组内逻辑排序+字段值, 分组字典对象>，记录已经处理过的节点
    private  static Map<String, Object> dataFieldDictionaryMap = new HashMap<>();
    
    public JSONObject findDataStructureTemplateById(String id) {
        return mongoTemplate.findById(id, JSONObject.class, "user_template");
    }

    public String getSourceByTemplateId(String id) {
        JSONObject template =  mongoTemplate.findById(id, JSONObject.class, "user_template");
        if(template != null) {
            JSONObject KV = template.getJSONObject("kv");
            return KV.getJSONObject("source").getString("value");
        }else {
            return null;
        }
    }

    public String getSourceFromTemplate(JSONObject template) {
        if(template != null) {
            JSONObject KV = template.getJSONObject("kv");
            return KV.getJSONObject("source").getString("value");
        }else {
            return null;
        }
    }
}
