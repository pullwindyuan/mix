package com.futuremap.base.dictionary;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * 分组字典树实体类，本质上DictionaryTree也是继承自Dictionary，不
 * 同点在于多了root单独的root节点和nodeMap，不难发现除了多出了
 * root外其他均一致。所以我们还是采用直接继承Dictionary，当然gd
 * 的用法就和Dictionary作为单独节点的时候是有区别的：单独节点gd
 * 值保存器孩子节点。而作为DictionaryTree的时候保存的是整棵树的
 * 所有节点。为了更好的处理这个业务不同点，还是扩展了nodeMap
 * 成员，父类的gd将闲置不用。
 * 父类的kv可以描述本字段数数据的对应可视化方案，比如使用列表还
 * 是宫格等
 * @author pullwind
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class DictionaryTree extends Dictionary implements Serializable {
    private static final long serialVersionUID = 1L;
    //分组字典集合，树的所有节点都保存在这里，可以加速查询
    @JsonIgnore
    private TreeMap<String, Dictionary> nodeMap;
}
