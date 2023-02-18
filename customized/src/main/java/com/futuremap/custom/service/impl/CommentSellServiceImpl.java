/**
 * 
 */
package com.futuremap.custom.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.custom.dto.template.BasicTemplate;
import com.futuremap.custom.service.ICommentSellService;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年10月13日 下午2:22:21
* 类说明
*/
/**
 * @author futuremap
 *
 */
@Service
public class CommentSellServiceImpl  implements ICommentSellService {

	String id;
	String groupName;
	String group;
	Integer index;
	Integer logicIndex;
	
	private static String MYSQL = "mysql";
	
	@Override
	public void createTable(BasicTemplate template) {
		String tabName = template.getVersion();
		
		JSONObject columns = template.getKv();
		
		columns.keySet().forEach(key-> {
			JSONObject column = columns.getJSONObject(key);
			JSONObject rule = columns.getJSONObject(key);
			JSONObject mysql = rule.getJSONObject(MYSQL);
		});
		
	}
	
	
	
/*
	 "rule" : {
                "isRequired" : NumberInt(0), 
                "regex" : "正则表达式", 
                "mysql" : {
                    "dataType" : "varchar", 
                    "length" : "255", 
                    "isRequired" : NumberInt(1)
                }
            }, 
    */
	
}
