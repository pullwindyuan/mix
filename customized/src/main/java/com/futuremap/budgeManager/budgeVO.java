package com.futuremap.budgeManager;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class budgeVO {
    private String tradTypeId;
    private String sequence;
    private String product;
    private String plateId;
    private String plate;
    private JSONObject inlineFieldEdit;
    private String id;
    private JSONObject inlineChildren;
}
