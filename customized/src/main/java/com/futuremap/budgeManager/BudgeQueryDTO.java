package com.futuremap.budgeManager;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dell
 */
@Data
public class BudgeQueryDTO {
    @ApiModelProperty(value = "预算年度", required = true)
    String year;
    @ApiModelProperty(value = "预算板块ID", required = true)
    String plateId;
    @ApiModelProperty(value = "贸易类型ID", required = true)
    String tradTypeId;
}
