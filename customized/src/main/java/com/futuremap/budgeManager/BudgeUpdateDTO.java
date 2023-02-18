package com.futuremap.budgeManager;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author dell
 */
@Data
public class BudgeUpdateDTO {
    private String id;
    private JSONObject inlineChildren;
}
