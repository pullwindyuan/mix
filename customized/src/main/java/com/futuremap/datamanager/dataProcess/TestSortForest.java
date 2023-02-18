package com.futuremap.datamanager.dataProcess;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.dictionary.bean.VO.DictionaryForestVO;
import com.futuremap.config.MqCustomConfig;
import com.futuremap.datamanager.enums.DataProcessEnum;
import com.futuremap.mqService.Mq4Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dell
 */
@Service
public class TestSortForest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Mq4Base mq4Base;

    @Autowired
    private Extract extract;

    private  static Map<String, DictionaryForestVO> gDictionaryForestMap = new HashMap<>() ;
    // Map<母模板ID+分组名称+组内逻辑排序+字段值, 分组字典对象>，记录已经处理过的节点
    private  static Map<String, Object> dataFieldDictionaryMap = new HashMap<>();

    public DictionaryForestVO testExtractForestFromTable() {
        List<JSONObject> dataList;
        int limit = 20;
        long skip = 0;
        DictionaryForestVO dictionaryForestVO = null;
        String templateId = "617fba4abc59492bd781eff2";
        Query query = new Query(Criteria.where("templateId").is(templateId)).skip(skip).limit(limit);
        String source = extract.getSourceByTemplateId(templateId);
        do {
            dataList = mongoTemplate.find( query, JSONObject.class, source);
            if(dataList.size() == 0) {
                break;
            }
            skip += limit;
            query.skip(skip);
            JSONObject msg = new JSONObject();
            msg.put("action", DataProcessEnum.EXTRACT_FOREST.name());
            msg.put("data", dataList);
            mq4Base.sendMsg(MqCustomConfig.QUEUE_DATA_PROCESS, msg.toJSONString());
        }while (true);
        return dictionaryForestVO;
    }
}
