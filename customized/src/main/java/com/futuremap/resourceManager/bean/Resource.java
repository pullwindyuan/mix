package com.futuremap.resourceManager.bean;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.dictionary.KeyValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * 资源基础类：资源是通用粘合剂，它是菜单与场景、细胞、功能等的挂载基座。同时又
 * 是UI可视化资源Element的基类。也就意味着Element可以单独挂载在Menu下，在简单
 * 应用中可以直接使用Element来代替功能、视图等比较重的资源。资源是可以重复使用
 * 的，所以ID显得尤为重要。ID不能太长，位数有6位足矣。我们按照分区的方式来定义：
 * 不同的类型的资源独占一个分区。比如000000分区表示A资源，010000表示B资源，
 * 020000表示C资源，直到990000。这意味着可以支持100种分类资源，每种支持10000个
 * 资源。
 * 我们之所以没有把所有的菜单等比较特殊的资源直接纳入资源的核心定义，是因为也是
 * 基于约定的原则减少系统复杂度。因为确定的东西可以单独描述。就像我们的医院也分
 * 妇幼医院、儿童医院、综合医院宇一个道理。
 *
 * 注意：在我们的概念中Resource是一般资源，比如一张图片，一段文本等。同时Menu\
 * Cell\Scene\Function\View也是资源，只是包装不同
 * @author Pullwind
 */
@Data
@Accessors(chain = true)
public class Resource implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id", required = true)
    private  String id;
    @ApiModelProperty(value = "URI", required = true)
    private  String uri;
    @ApiModelProperty(value = "执行动作", required = true)
    private String action;
    @ApiModelProperty(value = "名称", required = true)
    private String name;
    @ApiModelProperty(value = "详情", required = true)
    private String desc;
    @ApiModelProperty(value = "类型", required = true)
    private String type;
    @ApiModelProperty(value = "数据", required = true)
    private String data;
    @ApiModelProperty(value = "元数据：比如多个Cell都需要依赖统一的筛选条件来进行查询参数的输入，这个时候就需要用元数据的方式来描述这种数据获取的关系", required = true)
    private String metaData;

    public Resource() {
        super();
    }
    public Resource(TreeMap<String, KeyValue> keyValueTreeMap) {
        super();
        this.setUri((String) keyValueTreeMap.get("uri").getValue());
        this.setAction((String) keyValueTreeMap.get("action").getValue());
        this.setName((String) keyValueTreeMap.get("name").getValue());
        this.setDesc((String) keyValueTreeMap.get("desc").getValue());
        this.setType((String) keyValueTreeMap.get("type").getValue());
        this.setData((String) keyValueTreeMap.get("data").getValue());
        this.setMetaData((String) keyValueTreeMap.get("metaData").getValue());
    }

    public Resource(JSONObject keyValueMap) {
        super();
        this.setUri(keyValueMap.getJSONObject("uri").getString("value"));
        this.setAction(keyValueMap.getJSONObject("action").getString("value"));
        this.setName(keyValueMap.getJSONObject("name").getString("value"));
        this.setDesc(keyValueMap.getJSONObject("desc").getString("value"));
        this.setType(keyValueMap.getJSONObject("type").getString("value"));
        this.setData(keyValueMap.getJSONObject("data").getString("value"));
        this.setMetaData(keyValueMap.getJSONObject("metaData").getString("value"));
    }
}
