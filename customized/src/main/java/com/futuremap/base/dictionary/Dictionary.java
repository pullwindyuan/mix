package com.futuremap.base.dictionary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * 分组字典实体类，通过parent和gd、kv一起组织成双向可搜素的树形结构，
 * 为了让结构更加简单，我们做出如下约定：
 * 1、id的生成规则：这里的ID基本都是根据业务走的，比如菜单、功能、场
 * 景等不同的业务都有自己的ID规则。我们按前缀加数字的方式来组合形成，
 * 有了这样的全局前缀就能使用更少的ID位数来保证全局不冲突
 * @author pullwind
 */
@Data
@Accessors(chain = true)
public class Dictionary implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
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
    private String type;
    private String desc;
    private String name;
    private String value;
    private Integer level;
    private String parentId;
    private String version;

    //分组字典集合
    private TreeMap<String, Dictionary> gd;
    //键值集合
    private TreeMap<String, Map<String, KeyValue>> kv;

    public Dictionary() {
        super();
    }

   public Dictionary(String id,
                     String type,
                     String desc,
                     String name,
                     String value,
                     //分组字典集合
                     TreeMap<String, Dictionary> gd,
                     //键值集合
                     TreeMap<String, Map<String, KeyValue>> kv
                     ) {

       this.setId(id);
       this.setType(type);
       this.setDesc(desc);
       this.setName(name);
       this.setValue(value);
       this.setGd(gd);
       this.setKv(kv);
       this.setParentId(parentId);
    }
}
