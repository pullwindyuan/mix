package com.futuremap.base.dictionary.bean.VO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.futuremap.base.dictionary.KeyValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * 分组字典实体类，通过parent和gd、kv一起组织成双向可搜素的树形结构，
 * @author pullwind
 */
@Data
public class DictionaryVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 由于我们是采用引用的方式，所以一个数据字典数据会被多个树引用
     * 所以这个treeId在序列化的时候是不需要的，也就是在持久化的时候
     * 不会保存该字段。该字段只出现在反序列化后的实际的树中的实例化
     * 对象
     */
    @JsonIgnore
    private String treeId;
    /**
     * 用于数据隔离的字段，表示本数据归属
     * 我们在之前保存的数据表格配置模板中的uid就可以使用这个
     * 更加通用字段含义来代替，避免语义过于局限从而限制了数据结构
     */
    private String ownerId ;
    /**
     * 外部ID是用于在特定场景下关联外部信息，起到数据隔离的作用，
     * 比如可以是用户的ID，也可以是模板ID，还客户是一个组织的ID等
     */
    private String externalId;
    @ApiModelProperty(value = "字典ID", required = true)
    private String id;
    @ApiModelProperty(value = "字典类型", required = true)
    private String type;
    @ApiModelProperty(value = "字典描述", required = true)
    private String desc;
    @ApiModelProperty(value = "字典名称", required = true)
    private String name;
    @ApiModelProperty(value = "字典总值", required = true)
    private String value;
    @ApiModelProperty(value = "字典所在数的高度", required = true)
    private Integer level;
    @ApiModelProperty(value = "字典版本", required = true)
    private String version;
    //分组字典集合
    @ApiModelProperty(value = "子字典集合", required = true)
    private TreeMap<String, DictionaryVO> gd;
    //键值集合
    @ApiModelProperty(value = "字典键值对集合，也是叶子节点", required = true)
    private TreeMap<String, KeyValue> kv;
    private String parentId;
}
