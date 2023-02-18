package com.futuremap.base.dictionary.bean.VO;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.TreeMap;

/**
 * 分组字典森林实体类
 * @author pullwind
 */
@Data
public class DictionaryForestVO {
    @ApiModelProperty(value = "字典森林ID", required = true)
    private  String id;
    @ApiModelProperty(value = "附加信息", required = true)
    private JSONObject extra;
    @ApiModelProperty(value = "字典树的集合", required = true)
    private TreeMap<String, DictionaryTreeVO> dictionaryTreeMap;
}
