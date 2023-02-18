package com.futuremap.test;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class ApiDTO {
    private String path;
    private JSONObject params;
}
