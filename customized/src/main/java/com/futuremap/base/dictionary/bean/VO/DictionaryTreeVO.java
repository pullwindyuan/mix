package com.futuremap.base.dictionary.bean.VO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 分组字典树实体类
 * @author pullwind
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictionaryTreeVO extends DictionaryVO implements Serializable {
    private static final long serialVersionUID = 1L;
    //分组字典集合，树的所有节点都保存在这里，可以加速查询
    //private TreeMap<String, DictionaryBeanDTO<T>> dictionaryBeanDTOMap;
}
