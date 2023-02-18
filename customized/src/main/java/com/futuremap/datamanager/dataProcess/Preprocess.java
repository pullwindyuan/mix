package com.futuremap.datamanager.dataProcess;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.dictionary.bean.VO.DictionaryTreeVO;
import com.futuremap.base.dictionary.bean.VO.DictionaryVO;
import com.futuremap.base.dictionary.bean.VO.DictionaryForestVO;
import com.futuremap.datamanager.enums.DataProcessDimensionSourceEnum;
import com.futuremap.datamanager.enums.DataRelationEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 对数据进行预处理，处理过程中可能增加或者减少数据条目，增加或者减少数据的字段等。
 * 具体处理行为包括对字段进行拆分、多字段聚合、多字段进行公式运算形成新字段、对条
 * 数据按照一定的分组条件进行合成为单条数据的（有可能保留原数据的多维度统计信息），
 * 比如多条数据合成后，很多字段的信息会丢失，这些都是的数据嗯可以保留其统计信息。
 * 还有一种操作就是表拆分：把数据按照一定的分类规则分别放置到不同的表中。所有这些
 * 处理都是属于一个中间数据，为今后可能的业务提供支撑，提高形同相应速度，典型的空
 * 间换时间策略
 * @author Pullwind
 */
@Service
@Slf4j
public class Preprocess {
    @Autowired
    private MongoTemplate mongoTemplate;

    private  static Map<String, DictionaryForestVO> gDictionaryForestMap = new HashMap<>() ;
    // Map<母模板ID+分组名称+组内逻辑排序+字段值, 分组字典对象>，记录已经处理过的节点
    private  static Map<String, Object> dataFieldDictionaryMap = new HashMap<>();
    
    private JSONObject findDataStructureTemplateById(String id) {
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

    public void dictionaryTreeDTOForEachNode(String id, TreeMap<String, DictionaryVO> gd) {
        if(gd == null || gd.size() == 0) {
            return;
        }
        gd.forEach((k, item)->{
            DictionaryVO temp = (DictionaryVO) item;
            String tempKey = id + "-" + temp.getType()  +"-" + temp.getLevel()  +"-" + temp.getValue();
            dataFieldDictionaryMap.put(tempKey, temp);
            dictionaryTreeDTOForEachNode(id, item.getGd());
        });
    }

    public DictionaryForestVO getForestByTemplateId(String templateId) {
        JSONObject dataTemplate = findDataStructureTemplateById(templateId);
        JSONObject KV = dataTemplate.getJSONObject("kv");
        DictionaryForestVO dictionaryForestVO;
        String objStr = mongoTemplate.findOne(new Query(Criteria.where("id").is(templateId)), String.class, "user_data_tree");
        JSONObject object = JSONObject.parseObject(objStr);

        if (object == null) {
            dataFieldDictionaryMap.clear();;
            dictionaryForestVO = new DictionaryForestVO();
            dictionaryForestVO.setId(templateId);
            dictionaryForestVO.setDictionaryTreeMap(new TreeMap<>());
        } else {
            dictionaryForestVO = JSONObject.toJavaObject(object, DictionaryForestVO.class);
        }
        dictionaryForestVO.setExtra(KV);
        return dictionaryForestVO;
    }

    public DictionaryForestVO extractForestByTemplateId(String templateId) {
        List<JSONObject> dataList;
        int limit = 20;
        long skip = 0;
        DictionaryForestVO dictionaryForestVO = null;
        Query query = new Query(Criteria.where("templateId").is(templateId)).skip(skip).limit(limit);
        String source = getSourceByTemplateId(templateId);
        if(source == null) {
            return null;
        }
        do {
            dataList = mongoTemplate.find( query, JSONObject.class, source);
            skip += limit;
            query.skip(skip);
            dictionaryForestVO = extractForest(dataList, templateId, dictionaryForestVO);
        }while (dataList.size() > 0);
        return dictionaryForestVO;
    }

    public DictionaryForestVO extractForest(List<JSONObject> dataList) {
        return extractForest(dataList, null);
    }


    public DictionaryForestVO extractForest(List<JSONObject> dataList, DictionaryForestVO dictionaryForestVO) {
        String templateId = dataList.get(0).getString("templateId");
        return  extractForest(dataList, templateId, dictionaryForestVO);
    }

    public DictionaryForestVO extractForest(List<JSONObject> dataList, String templateId, DictionaryForestVO dictionaryForestVO) {
        Boolean isExists = false;
        AtomicReference<Boolean> isChanged = new AtomicReference<>(false);
        JSONObject dataTemplate = findDataStructureTemplateById(templateId);
        JSONObject KV = dataTemplate.getJSONObject("kv");
        JSONObject queryMap = KV.getJSONObject("queryMap");
        // Map<分组名称, TreeMap<组内逻辑排序, Map<字段值, 分组字典对象>>>
        Map<String, TreeMap<Integer, Map<Object, Object>>> dataFieldGroupMap = new HashMap<>();
        // Map<字段名称, JSONObject (child / group, 值)>
        JSONObject dataFieldChild = KV.getJSONObject("dataFieldChild");
        if(dataFieldChild == null) {
            dataFieldChild = new JSONObject();
        }

        Map<String, DictionaryTreeVO> dictionaryTreeMap = new HashMap<>();

        if(dictionaryForestVO == null) {
            String objStr = mongoTemplate.findOne(new Query(Criteria.where("id").is(templateId)), String.class, "user_data_tree");
            JSONObject object = JSONObject.parseObject(objStr);

            if (object == null) {
                dataFieldDictionaryMap.clear();;
                dictionaryForestVO = new DictionaryForestVO();
                dictionaryForestVO.setId(templateId);
                dictionaryForestVO.setDictionaryTreeMap(new TreeMap<>());
            } else {
                isExists = true;
                dictionaryForestVO = JSONObject.toJavaObject(object, DictionaryForestVO.class);
            }
            dictionaryForestVO.setExtra(KV);
        }

        dictionaryForestVO.getDictionaryTreeMap().forEach((k, e)->{
            DictionaryTreeVO dictionaryTree = (DictionaryTreeVO) e;
            dictionaryTreeMap.put(dictionaryTree.getName(), dictionaryTree);
            //初始化已经存在的数据，防止重复处理
            dictionaryTree.getGd().forEach((key, item)->{
                dictionaryTreeDTOForEachNode(dictionaryTree.getExternalId(), dictionaryTree.getGd());
            });
        });

        DictionaryForestVO finalDictionaryForest = dictionaryForestVO;
        JSONObject finalDataFieldChild = dataFieldChild;
        dataList.forEach(item->{
            // Map<分组名称+组内逻辑排序, 分组字典对象>
            Map<String, Object> dataFieldDictionaryMapTemp = new HashMap<>();
            item.forEach((k, v)->{
                //用字段的key值对应数据映射配置中的key
                JSONObject kvItem = KV.getJSONObject(k);
                //能从配置获取到对应key的数据就说明这是实际数据（因为表中还存了自动ID和模板ID）
                if(kvItem != null) {
                    //根据group和logicIndex值可以从数据的字段中找到树形结构

                    int logicIndex = kvItem.getIntValue("logicIndex");
                    JSONObject relation = kvItem.getJSONObject("relation");
                    String relationValue = relation.getString("value");
                    String relationRule = relation.getString("rule");
                    String group = relation.getString("name");

                    //TODO  : 理想的情况是：FIX属性不应该放到group中，这样就导致group中描述的属性不再单一，而是混杂了业务属性（组织、人员、分类等）和规则类属性（FIX表示的是限制规则）
                    //TODO  : 合理的做法是FIX这个中表面是不可删除列的属性放到rule这个分类下，为了暂时不动之前已经写好的代码，这里来做一个暂时的特殊处理。今后需要进行调整。
                    //FIXED : 2021-11-15 目前已经把分组信息放到relation当中的name属性，不依赖group属性
                    if(DataRelationEnum.NONE.name().equals(relationValue)) {
                        return;
                    }

                    //columnInfoKVList.add(logicIndex, kvItem);
                    TreeMap<Integer, Map<Object, Object>> dataField = dataFieldGroupMap.get(group);

                    if(dataField == null) {
                        //首次检查到该字段, 保存到map
                        dataField = new TreeMap<>((o, t1) -> { return o - t1; } );
                        dataFieldGroupMap.put(group, dataField);
                    }
                    //字段信息存储到MAP
                    Map<Object, Object> children = dataField.get(logicIndex);
                    if(children == null) {
                        children = new HashMap<>();
                        dataField.put(logicIndex, children);
                    }

                    DictionaryVO dictionaryBean = (DictionaryVO) children.get(v);
                    if(dictionaryBean == null) {
                        dictionaryBean = new DictionaryVO();
                        //时间格式和数字类型的需要转换
                        if(!(v instanceof String)) {
                            //return;
                        }
                        if(DataProcessDimensionSourceEnum.EXTRACT_FROM_DATA.name().equals(relationRule)) {
                            dictionaryBean.setValue((String) v);
                        }else {
                            dictionaryBean.setValue(relationRule);
                        }

                        //value存储是数据实际存储的时候对应的字段名称
                        dictionaryBean.setName((String) k);
                        dictionaryBean.setType(group);
                        dictionaryBean.setLevel(logicIndex);

//                        TreeMap<String, KVBean> kvBeanTreeMap = new TreeMap<>();
//                        KVBean processKV = JSONObject.toJavaObject(process, KVBean.class);
//                        kvBeanTreeMap.put("process", processKV);
//                        KVBean formatKV = JSONObject.toJavaObject(format, KVBean.class);
//                        kvBeanTreeMap.put("format", formatKV);
//
//                        dictionaryBean.setKv(kvBeanTreeMap);
                        children.put(v, dictionaryBean);
                    }
                    dataFieldDictionaryMapTemp.put(group + logicIndex, dictionaryBean);
                }
            });

            dataFieldDictionaryMapTemp.forEach((k, v)->{
                DictionaryVO dbd = (DictionaryVO) v;
                int level = dbd.getLevel() - 1;
                String group = dbd.getType();
                //String tempKey = group  +"-" + dbd.getLevel()  +"-" + dbd.getName();
                String gTempKey = templateId +"-" + group  +"-" + dbd.getLevel()  +"-" + dbd.getValue();
                DictionaryTreeVO dictionaryTreeVO = dictionaryTreeMap.get(group);
                //TreeMap<String, DictionaryBeanDTO> dictionaryBeanDTOMap;
                if(dictionaryTreeVO == null) {
                    //首次检查到该字典树, 创建字典树
                    dictionaryTreeVO = new DictionaryTreeVO();
                    dictionaryTreeVO.setExternalId(templateId);
//                    dictionaryTreeVO.setName(group);
//                    dictionaryTreeVO.setId(group);
                    //dictionaryBeanDTOMap = new TreeMap<>();
                    //dictionaryTreeDTO.setDictionaryBeanDTOMap(dictionaryBeanDTOMap);
                    //创建根节点，因为从书中提取的树形结构存在一个隐藏的根节点：全部
                    dictionaryTreeVO.setName("全部");
                    dictionaryTreeVO.setValue("ALL");
                    dictionaryTreeVO.setType(group);
                    dictionaryTreeVO.setLevel(0);
                    TreeMap<String, DictionaryVO> rootGd = new TreeMap<>();
                    dictionaryTreeVO.setGd(rootGd);
                    dictionaryTreeMap.put(group, dictionaryTreeVO);
                    String groupOrder = queryMap.getJSONObject(group).getString("value");
                    finalDictionaryForest.getDictionaryTreeMap().put(groupOrder, dictionaryTreeVO);
                    isChanged.set(true);
                }else {
//                    dictionaryBeanDTOMap = dictionaryTreeDTO.getDictionaryBeanDTOMap();
//                    if(dictionaryBeanDTOMap == null) {
//                        dictionaryBeanDTOMap = new TreeMap<>();
//                        dictionaryTreeDTO.setDictionaryBeanDTOMap(dictionaryBeanDTOMap);
//                    }
                }

                TreeMap<String, DictionaryVO> gd;
                //描述父子关系，仅限两级
                JSONObject childInfo = new JSONObject();
                childInfo.put("group", group);

                //子节点
                if(level > 0) {
                    DictionaryVO parentDbd = (DictionaryVO) dataFieldDictionaryMapTemp.get(group + level);
                    gd = parentDbd.getGd();
                    if(gd == null) {
                        isChanged.set(true);
                        gd = new TreeMap<>();
                        parentDbd.setGd(gd);
                    }
                    childInfo.put("child", dbd.getName());
                    finalDataFieldChild.put(parentDbd.getName(), childInfo);
                }else {//根节点
                    gd = dictionaryTreeVO.getGd();
                    DictionaryVO childDbd = (DictionaryVO) dataFieldDictionaryMapTemp.get(group + (dbd.getLevel() + 1));
                    if(childDbd != null) {
                        childInfo.put("child", childDbd.getName());
                    }
                    queryMap.getJSONObject(group).put("root", dbd.getName());
                    finalDataFieldChild.put(dbd.getName(), childInfo);
                }

                if(dataFieldDictionaryMap.get(gTempKey) == null) {
                    dataFieldDictionaryMap.put(gTempKey, dbd);
//                    DictionaryBeanDTO temp = BeanUtil.copyProperties(dbd, DictionaryBeanDTO.class);
//                    temp.setGd(null);
                    //dictionaryBeanDTOMap.put(tempKey, temp);
                    isChanged.set(true);
                    gd.put(dbd.getValue(), dbd);
                }
            });
        });

        KV.put("dataFieldChild", dataFieldChild);
        Update userTemplatUpdate = Update.update("kv", KV);
        mongoTemplate.findAndModify(new Query(Criteria.where("externalId").is(templateId)), userTemplatUpdate, JSONObject.class, "user_template");

        JSONObject savaObj = JSONObject.parseObject(JSONObject.toJSONString(dictionaryForestVO));
        if(isExists && isChanged.get()) {
            Update update = Update.update("dictionaryTreeMap", savaObj.getJSONObject("dictionaryTreeMap"));
            mongoTemplate.findAndModify(new Query(Criteria.where("id").is(templateId)), update, JSONObject.class, "user_data_tree");
            //mongoTemplate.remove(new Query(Criteria.where("id").is(id)), "user_data_tree");
        }else {
            mongoTemplate.save(savaObj, "user_data_tree");
        }

        //gDictionaryForestMap.put(id, dictionaryForestDTO);
        return dictionaryForestVO;
    }
}
