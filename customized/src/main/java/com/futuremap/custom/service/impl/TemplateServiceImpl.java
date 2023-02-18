package com.futuremap.custom.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.futuremap.custom.dto.template.BasicTemplate;
import com.futuremap.custom.dto.template.TemplateQuery;
import com.futuremap.custom.dto.template.UserTemplateQuery;
import com.futuremap.custom.service.ICommentSellService;
import com.futuremap.custom.service.ITemplateService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-01-27
 */
@Service
public class TemplateServiceImpl  implements ITemplateService {
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	ICommentSellService commentSellService;
	
	
	private static String COLLECT_NAME = "cfg_template";
	private static String COLLECT_NAME1 = "user_template";
	private static String COLLECT_USER_SELL = "user_sell";

	@Override
	public BasicTemplate findOne(TemplateQuery templateQuery) {
		return mongoTemplate.findOne(new Query(Criteria.where("name").is(templateQuery.getName())), BasicTemplate.class,
				COLLECT_NAME);
	}

	/**
	 * @param map
	 * @return
	 */
	@Override
	public BasicTemplate insert(BasicTemplate template) {
		template.setVersion(System.currentTimeMillis() + "");
		//建用户业务销售数据表
		commentSellService.createTable(template);
		return mongoTemplate.save(template, COLLECT_NAME1);
	}


	@Override
	public List<BasicTemplate> searchUserTemplate(UserTemplateQuery userTemplateQuery) {
		return mongoTemplate.find(new Query(Criteria.where("uid").is(userTemplateQuery.getCompanyId()))
				.with( Sort.by(Direction.DESC, "version")), BasicTemplate.class, COLLECT_NAME1);
	}

	@Override
	public List<Map> getUserSell() {
		return mongoTemplate.findAll(Map.class, COLLECT_USER_SELL);
	}
	
	
}

