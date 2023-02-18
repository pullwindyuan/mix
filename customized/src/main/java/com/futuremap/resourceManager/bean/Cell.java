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
 * 细胞功能，是最小化的能够完整表达某一特定事物的数据集和可视化元素组成的一个整体单元
 * 比如一个趋势图表、一个排名图表、一个雷达图就是一个细胞，一个登录注册页面也是一个细
 * 胞。细胞由若干功能和元素组成.
 * 同时我们约定kv中同时存储了Element信息和Function信息。为了能
 * 够快速的区分这两种类型的KeyValue，我们约定除了了通过type字段
 * 区分外，在大f分组的key上我们使用element和function来分组，在分组下的key只上也可以通过
 * 前缀区分：e-开头的是元素，f-开头的是功能。并且后面的部分是从0到n的数字。
 * @author dell
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class Cell extends Dictionary implements Serializable {
    private static final long serialVersionUID = 1L;
    public Cell() {
        super();
    }

    public Cell(String id,
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
