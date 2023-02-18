package com.futuremap.custom.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.futuremap.custom.dto.template.TemplateQuery;
import com.alibaba.fastjson.JSONArray;
import com.futuremap.custom.annotation.CustomResponse;
import com.futuremap.custom.dto.template.BasicTemplate;
import com.futuremap.custom.dto.template.UserTemplateQuery;
import com.futuremap.custom.service.ITemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/template")
@Api(tags = "模板接口", description = "等")
public class TemplateController {
	
	@Autowired
	ITemplateService testService;

	@PostMapping("/config")
    @ApiOperation("配置模板查询")
	@CustomResponse
    public BasicTemplate findOne(@RequestBody @Valid TemplateQuery templateQuery) {
		return testService.findOne(templateQuery);
    }
	
	@PostMapping("/user")
    @ApiOperation("插入用户后编辑模板")
	@CustomResponse
	public BasicTemplate getTemplate(@RequestBody BasicTemplate template) {
		return testService.insert(template);
	}
	
	@PostMapping("/user/search")
    @ApiOperation("查询用户当前最新模板")
	@CustomResponse
	public List<BasicTemplate> searchUserTemplate(@RequestBody @Valid UserTemplateQuery userTemplateQuery) {
		return testService.searchUserTemplate(userTemplateQuery);
	}

	@GetMapping("/user/sell/search")
    @ApiOperation("查询用户当前最新模板")
	@CustomResponse
	public List<Map> getUserSell() {
		return testService.getUserSell();
	}
	
}
