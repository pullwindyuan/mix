package com.futuremap.custom.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.futuremap.custom.dto.template.TemplateQuery;
import com.alibaba.fastjson.JSONArray;
import com.futuremap.custom.dto.template.BasicTemplate;
import com.futuremap.custom.dto.template.UserTemplateQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-01-27
 */
public interface ITemplateService  {

	/**
	 * @param templateQuery
	 * @return
	 */
	BasicTemplate findOne(TemplateQuery templateQuery);

	/**
	 * @param map
	 * @return
	 */
	BasicTemplate insert(BasicTemplate template);

	/**
	 * @param userTemplateQuery
	 * @return
	 */
	List<BasicTemplate> searchUserTemplate(UserTemplateQuery userTemplateQuery);

	/**
	 * @return
	 */
	List<Map> getUserSell();
	
}
