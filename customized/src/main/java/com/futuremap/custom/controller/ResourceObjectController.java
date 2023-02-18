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
import com.futuremap.custom.dto.resource.ResourceObjCreate;
import com.futuremap.custom.dto.resource.ResourceObjUpdate;
import com.futuremap.custom.dto.resource.ResourceTemplateQuery;
import com.futuremap.custom.entity.ResourceObject;
import com.futuremap.custom.service.ResourceObjectService;
import com.futuremap.custom.service.impl.ResourceObjectServiceImpl;

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
@RequestMapping("/resource-obj")
@ApiOperation("资源对象相关")
@Api(tags = "资源对象相关", description = "资源对象相关")
public class ResourceObjectController {
	
	
	@Autowired
	ResourceObjectServiceImpl resourceObjectService;

	/**
	 * 新增
	 * 
	 **/
	@PostMapping()
	@ApiOperation("新增资源")
	@CustomResponse
	public void insert(@Valid @RequestBody ResourceObjCreate resourceObjCreate) {
		resourceObjectService.saveResource(resourceObjCreate);
	}
	
	/**
	 * 刪除
	 * 
	 **/
	@GetMapping("/{id}")
	@ApiOperation("查询资源详情")
	@CustomResponse
	public ResourceObject get(@Valid @PathVariable String id) {
		return resourceObjectService.get(id);
	}
	

	/**
	 * 刪除
	 * 
	 **/
	@DeleteMapping("/{id}")
	@ApiOperation("删除资源")
	@CustomResponse
	public void delete(@Valid @PathVariable String id) {
		resourceObjectService.delete(id);
	}

	/**
	 * 更新
	 * 
	 **/
	@PutMapping()
	@ApiOperation("修改资源")
	@CustomResponse
	public void update(@Valid @RequestBody ResourceObjUpdate resourceObjUpdate) {
		resourceObjectService.updateResource(resourceObjUpdate);
	}

	/**
	 * 更新
	 * @return 
	 * 
	 **/
	@PostMapping("search")
	@ApiOperation("资源列表")
	@CustomResponse
	public List<ResourceObject> list(@RequestBody ResourceTemplateQuery query) {
		return resourceObjectService.list(query);
	}
}
