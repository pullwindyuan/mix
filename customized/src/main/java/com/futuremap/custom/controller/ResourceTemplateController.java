package com.futuremap.custom.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.futuremap.custom.annotation.CustomResponse;
import com.futuremap.custom.dto.resource.ResourceTemplateCreate;
import com.futuremap.custom.dto.resource.ResourceTemplateQuery;
import com.futuremap.custom.dto.resource.ResourceTemplateUpdate;
import com.futuremap.custom.entity.ResourceTemplate;
import com.futuremap.custom.service.ResourceTemplateService;
import com.futuremap.custom.service.impl.ResourceTemplateServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author futuremap
 * @since 2021-08-10
 */
@RestController
@RequestMapping("/resource-template")
@ApiOperation("资源模板相关")
@Api(tags = "资源模板相关", description = "资源模板相关")
public class ResourceTemplateController {
	

	
	
	@Autowired
	ResourceTemplateService resourceTemplateService;

	/**
	 * 新增
	 * 
	 **/
	@PostMapping()
	@CustomResponse
	@ApiOperation("新增资源模板")
	public void insert(@Valid @RequestBody ResourceTemplateCreate resourceTemplateCreate) {
		resourceTemplateService.saveResource(resourceTemplateCreate);
	}

	/**
	 * 刪除
	 * 
	 **/
	@DeleteMapping("/{id}")
	@CustomResponse
	@ApiOperation("删除资源模板")
	public void delete(@Valid @PathVariable String id) {
		resourceTemplateService.delete(id);
	}

	/**
	 * 更新
	 * 
	 **/
	@PutMapping()
	@CustomResponse
	@ApiOperation("修改资源模板")
	public void update(@Valid @RequestBody ResourceTemplateUpdate resourceTemplateUpdate) {
		resourceTemplateService.updateResource(resourceTemplateUpdate);
	}

	/**
	 * 更新
	 * 
	 **/
	@PostMapping("search")
	@CustomResponse
	@ApiOperation("查询资源模板")
	public List<ResourceTemplate> list(@RequestBody ResourceTemplateQuery query) {
		return resourceTemplateService.list(query);
	}
	

	/**
	 * 更新
	 * 
	 **/
	@DeleteMapping("all/{createdBy}")
	@CustomResponse
	@ApiOperation("清空")
	public void deleteAll(@Valid @PathVariable String createdBy) {
		 resourceTemplateService.deleteAll(createdBy);
	}

	

}
