package com.futuremap.datamanager.bean.DTO;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author dell
 */
@Data
public class QueryDTO {
    @ApiModelProperty(value = "查询条件树对应的ID", required = true)
    String queryId;
    @ApiModelProperty(value = "排名", required = false)
    Integer top;
    @ApiModelProperty(value = "查询条件参数集合：k1-(k2-v)形式，k1为查询条件分组，对应的值为本分组下的所有查询条件参数（k1-v）的集合。k2为查询条件树节点的name值，v为用户选择的或者输入的值", required = false)
    JSONObject queryMap;
    @ApiModelProperty(value = "查询分组字段列表", required = false)
    List<String> groupList;
    @ApiModelProperty(value = "查询聚合参数集合：k-v形式，k为聚合字段名称，v为希望集合后输出的字段名称", required = false)
    JSONObject aggregateMap;
}
