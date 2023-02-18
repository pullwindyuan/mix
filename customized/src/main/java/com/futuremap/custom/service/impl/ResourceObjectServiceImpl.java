package com.futuremap.custom.service.impl;

import com.futuremap.custom.dto.resource.ResourceObjCreate;
import com.futuremap.custom.dto.resource.ResourceObjUpdate;
import com.futuremap.custom.dto.resource.ResourceTemplateQuery;
import com.futuremap.custom.entity.ResourceObject;
import com.futuremap.custom.entity.ResourceTemplate;
import com.futuremap.custom.mapper.ResourceObjectMapper;
import com.futuremap.custom.service.ResourceObjectService;
import com.google.common.base.Objects;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-08-10
 */
@Service
public class ResourceObjectServiceImpl extends ServiceImpl<ResourceObjectMapper, ResourceObject> implements ResourceObjectService {
	

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ResourceRefServiceImpl resourceRefService;
	
	@Override
	public void saveResource(ResourceObjCreate resourceObjCreate) {
		ResourceObject resourceObject = modelMapper.map(resourceObjCreate, ResourceObject.class);
		resourceObject.setTemplateId("");
		this.save(resourceObject);
		//记录模板引用次数
		if (StringUtils.isNoneEmpty(resourceObjCreate.getTemplateId())) {
			resourceObjCreate.setId(resourceObject.getId());
			resourceRefService.record(resourceObjCreate);
		}
	}

	@Override
	public void updateResource(ResourceObjUpdate resourceObjUpdate) {
		ResourceObject resourceObject = this.getById(resourceObjUpdate.getId());
		modelMapper.map(resourceObjUpdate, resourceObject);
		this.saveOrUpdate(resourceObject);
	}

	@Override
	public void delete(String id) {
		this.removeById(id);
	}


	/**
	 * @param query
	 * @return
	 */
	public List<ResourceObject> list(ResourceTemplateQuery query) {
		return super.list(new QueryWrapper<ResourceObject>().select("id","name")
				.eq(StringUtils.isNoneEmpty(query.getCreatedBy()), "created_by", query.getCreatedBy())
				.like(StringUtils.isNoneEmpty(query.getName()), "name", query.getName())
				.orderByDesc(StringUtils.isNoneEmpty(query.getOrderBy()), query.getOrderBy()));
	}

	/**
	 * @param id
	 */
	public ResourceObject get(String id) {
		return super.getOne(new QueryWrapper<ResourceObject>().eq("id", id));
	}

}
