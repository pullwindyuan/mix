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
 * 功能：由多个View组成，不可嵌套，并且一个功能只能对应一个接口，
 * 加上这些约定是为了让功能的定义不能过于开放，否者技术复杂度会
 * 无谓增加。功能当中有一种特殊的存在：那就是通用工具类功能，比
 * 如搜索框，条件筛选栏，日历控件，多级联动选择等通用功能，之所
 * 以把这类独立定义是应为他是辅助存在，不能独立形成功能.这类组件
 * 形态相对固定，能够穷举，并且可以有独立的封装，所以独立定义可
 * 以非常并与集成和降低开发难度。也是按照约定大于配置的理念来处
 * 理通用辅助工具的方式。如果需要嵌套需要使用Menu。
 * 同时我们约定kv中同时存储了DataBinder和信息和View信息。为了能
 * 够快速的区分这两种类型的KeyValue，我们约定除了了通过type字段
 * 区分外， * 够快速的区分这两种类型的KeyValue，我们约定除了了通过type字段
 *  * 区分外，在大f分组的key上我们使用dataBinder和view来分组，在分组下的
 *  key上也可以通过前缀区分：dbr-开头的是绑定器，v-
 * 开头的是视图。并且后面的部分是从0到n的数字。
 * @author dell
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class Function extends Dictionary implements Serializable {
    private static final long serialVersionUID = 1L;
    public Function() {
        super();
    }

    public Function(String id,
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
