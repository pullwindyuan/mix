package com.futuremap.erp.common.page;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 为了使用个性化的分页返回数据而扩展
 * @author dell
 */
@ApiModel(value="带扩展字段的分页数据", description="extra字段可以是任意的JSON数据结构,便于扩展数据")
public interface IPageWithExtra<T> extends IPage<T> {

    /**
     * 分页记录列表
     *
     * @return 分页对象记录列表
     */
    @ApiModelProperty(value = "扩展字段，任意JSON数据结构")
    JSONObject getExtra();

    /**
     * 设置分页记录列表
     * @param extra 附加数据
     * @return 分页对象
     */
    IPageWithExtra<T> setExtra(JSONObject extra);
}
