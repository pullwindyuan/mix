/**
 * 
 */
package com.futuremap.custom.dto.template;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年8月10日 上午11:13:11
* 类说明
*/
@Data
@ApiModel("模板查询")
public class BasicTemplate {

	@ApiModelProperty(value="用户关联id",required = true)
	protected String uid;
	
	@ApiModelProperty(value="Id",required = true)
	protected String id;
	
	@ApiModelProperty(value="字段可定制规则描述；",required = true)
	protected String desc;
	
	@ApiModelProperty(value="这里取值可以是KV、GD、GD_KV", required = true)
	protected String type;
	
	@ApiModelProperty(value="版本", required = true)
	protected String version;
	
	@ApiModelProperty(value="销售数据表格模板定制规则", required = true)
	protected String name;
	
	@ApiModelProperty(value="KV", required = false)
	protected JSONObject kv;
	
	@ApiModelProperty(value="GD", required = false)
	protected JSONObject gd;
}
