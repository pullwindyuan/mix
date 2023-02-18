package com.futuremap.budgeManager;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dell
 */
@Data
public class BudgeDTO {
    private String year;
    private String sequence;
    private String parentSequence;
    private String product;
    private String id;
    private String parentId;
    private String plate;
    private String tradTypeId;
    private String tradType;
    private JSONObject inlineFieldEdit;
    private JSONObject inlineChildren;
}
