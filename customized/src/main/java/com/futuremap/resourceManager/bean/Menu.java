package com.futuremap.resourceManager.bean;

import com.futuremap.base.dictionary.Dictionary;
import com.futuremap.base.dictionary.KeyValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;


/**
 * 菜单，可挂载任何类型的资源，层级无限。任何资源都过kv中的shortcut的信息
 * 来引用。
 * shortcut存储在kv中，一个KeyValue就对应一个资源(ID引用)。
 * 同时kv中还可以包含Element信息，用于表示本菜单在渲染的时候使用什么可视化
 * 方案
 *
 * 其实不难发现，Menu\Scene\Cell\Function\View都是直接继承了Dictionary，并且
 * 没有扩展任何属性。因此我们在实际应用的时候并不需要单独定义这些对象，直接
 * 使用Dictionary即可。通过type来区分不同l类型即可。不同类型的特殊约定通过各自
 * 的业务层也控制。这样就将持久层都统一为了一个对象：Dictionary。这样既体现了
 * 约定大于配置的优越性，同时也通过Dictionary的kv来实现了对不同实际对象的描述
 * Menu的ID的前缀是m-
 * @author Pullwind
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Menu extends Dictionary implements Serializable {
    private static final long serialVersionUID = 1L;

    public Menu() {
        super();
    }

    public Menu(String id,
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
