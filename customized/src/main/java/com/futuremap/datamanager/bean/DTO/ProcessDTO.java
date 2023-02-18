package com.futuremap.datamanager.bean.DTO;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author dell
 */
@Data
public class ProcessDTO {
    @ApiModelProperty(value = "查询条件树对应的ID", required = true)
    String queryId;
    @ApiModelProperty(value = "查询匹配参数集合：k-v形式，k为查询条件字段名称，v为匹配值", required = false)
    Map<String, String> queryMap;
    @ApiModelProperty(value = "查询分组字段列表", required = false)
    List<String> groupList;
    @ApiModelProperty(value = "需要输出的字段", required = false)
    List<String> projectList;
    @ApiModelProperty(value = "查询聚合参数集合：外层k-v用于区分是何种聚合操作，内层k-v形式，k为聚合字段名称，v为希望集合后输出的字段名称", required = false)
    Map<String, Map<String, String>> aggregateMap;
}
