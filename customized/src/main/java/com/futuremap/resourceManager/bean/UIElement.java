package com.futuremap.resourceManager.bean;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.dictionary.KeyValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * 元素
 * @author Pullwind
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UIElement extends Resource implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题", required = true)
    private String title;

    @ApiModelProperty(value = "内容", required = true)
    private String contents;

    @ApiModelProperty(value = "提示", required = true)
    private String hints;

    @ApiModelProperty(value = "图标", required = true)
    private String icon;

    @ApiModelProperty(value = "背景", required = true)
    private String background;

    @ApiModelProperty(value = "文字颜色", required = true)
    private String textColor;

    @ApiModelProperty(value = "样式", required = true)
    private String style;

    @ApiModelProperty(value = "图标和文字的布局: 上下、左右、默认隐藏文字，鼠标悬浮显示", required = true)
    private String layout;

    public UIElement() {
        super();
    }
    public UIElement(TreeMap<String, KeyValue> keyValueTreeMap) {
        super(keyValueTreeMap);
        this.setUri((String) keyValueTreeMap.get("title").getValue());
        this.setUri((String) keyValueTreeMap.get("contents").getValue());
        this.setUri((String) keyValueTreeMap.get("hints").getValue());
        this.setUri((String) keyValueTreeMap.get("icon").getValue());
        this.setUri((String) keyValueTreeMap.get("background").getValue());
        this.setUri((String) keyValueTreeMap.get("style").getValue());
        this.setUri((String) keyValueTreeMap.get("textColor").getValue());
        this.setUri((String) keyValueTreeMap.get("layout").getValue());
    }

    public UIElement(JSONObject keyValueMap) {
        super(keyValueMap);
        this.setUri(keyValueMap.getJSONObject("title").getString("value"));
        this.setUri(keyValueMap.getJSONObject("contents").getString("value"));
        this.setUri(keyValueMap.getJSONObject("hints").getString("value"));
        this.setUri(keyValueMap.getJSONObject("icon").getString("value"));
        this.setUri(keyValueMap.getJSONObject("background").getString("value"));
        this.setUri(keyValueMap.getJSONObject("style").getString("value"));
        this.setUri(keyValueMap.getJSONObject("textColor").getString("value"));
        this.setUri(keyValueMap.getJSONObject("layout").getString("value"));
    }
}
