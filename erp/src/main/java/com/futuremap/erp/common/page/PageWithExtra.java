package com.futuremap.erp.common.page;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 为了支持更加个性化的定制返回数据而扩展的分页
 * @author dell
 */
@ApiModel(value="带扩展字段的分页数据", description="extra字段可以是任意的JSON数据结构,便于扩展数据")
public class PageWithExtra<T> extends Page<T> implements IPageWithExtra<T>{
    /**
     * 扩展数据封装
     */
    private JSONObject extra = new JSONObject();

    public PageWithExtra() {
        super();
    }

    @ApiModelProperty(value = "扩展字段，任意JSON数据结构")
    @Override
    public JSONObject getExtra() {
        return this.extra;
    }

    @Override
    public IPageWithExtra<T> setExtra(JSONObject extra) {
        this.extra = extra;
        return this;
    }
}
