package com.futuremap.custom.dictionary;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * 分组字典实体类，通过parent和gdl、kvl一起组织成双向可搜素的树形结构，
 * @author pullwind
 */
@Data
@Accessors(chain = true)
public class DictionaryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String type;
    private String desc;
    private String name;
    private String gds;
    private ArrayList gdl;
    private Map gdm;
    private String kvs;
    private ArrayList kvl;
    private Map kvm;
    private String parentId;
    private DictionaryBean parent;
}
