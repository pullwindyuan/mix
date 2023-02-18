package com.futuremap.resourceManager.bean;

import com.futuremap.base.dictionary.Dictionary;
import com.futuremap.base.dictionary.KeyValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * 场景：场景是由多个Function、Cell组成，不可嵌套，如果需要嵌套需要使用Menu。
 * 所以也可以认为它是特殊的菜单
 * 同时我们约定kv中同时存储了Cell和信息和Function信息。为了能
 * 够快速的区分这两种类型的KeyValue，我们约定除了了通过type字段
 * 区分外， * 够快速的区分这两种类型的KeyValue，我们约定除了了通过type字段
 *  * 区分外，在大f分组的key上我们使用cell和function来分组，在分组下的key
 *  上也可以通过前缀区分：c-开头的是细胞，f-
 * 开头的是功能。并且后面的部分是从0到n的数字。
 * @author dell
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class Scene extends Dictionary implements Serializable {
    private static final long serialVersionUID = 1L;
    public Scene() {
        super();
    }

    public Scene(String id,
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
