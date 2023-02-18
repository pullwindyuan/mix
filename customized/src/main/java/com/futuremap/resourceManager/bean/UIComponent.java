package com.futuremap.resourceManager.bean;

import com.futuremap.base.dictionary.Dictionary;
import com.futuremap.base.dictionary.KeyValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Pullwind
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UIComponent extends Dictionary implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 若干element，数量多少取决于View本身的复杂度和需要表达的数据量
     * 原始数据来源于父类的kv
     */
    private TreeMap<String, String> elementMap;
    public UIComponent() {
        super();
    }

    public UIComponent(String id,
                       String type,
                       String desc,
                       String name,
                       String value,
                       //分组字典集合
                       TreeMap<String, Dictionary> gd,
                       //键值集合
                       TreeMap<String, Map<String, KeyValue>> kv
        ) {
            super(id,
                    type,
                    desc,
                    name,
                    value,
                    gd,
                kv);
    }
}
