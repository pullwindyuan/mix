package com.futuremap.custom.service.impl;

import com.futuremap.custom.dto.resource.ResourceTemplateCreate;
import com.futuremap.custom.dto.resource.ResourceTemplateQuery;
import com.futuremap.custom.dto.resource.ResourceTemplateUpdate;
import com.futuremap.custom.entity.ResourceObject;
import com.futuremap.custom.entity.ResourceRef;
import com.futuremap.custom.entity.ResourceTemplate;
import com.futuremap.custom.mapper.ResourceTemplateMapper;
import com.futuremap.custom.service.ResourceTemplateService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class ResourceTemplateServiceImpl extends ServiceImpl<ResourceTemplateMapper, ResourceTemplate> implements ResourceTemplateService {

	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public void saveResource(ResourceTemplateCreate resourceTemplateCreate) {
		ResourceTemplate resourceTemplate = modelMapper.map(resourceTemplateCreate, ResourceTemplate.class);
		this.save(resourceTemplate);
	}

	@Override
	public void updateResource(ResourceTemplateUpdate resourceTemplateUpdate) {
		ResourceTemplate resourceObject = this.getById(resourceTemplateUpdate.getId());
		modelMapper.map(resourceTemplateUpdate, resourceObject);
		this.saveOrUpdate(resourceObject);
	}

	@Override
	public void delete(String id) {
		this.removeById(id);
	}

	/**
	 * @return
	 */
	public void deleteAll(String createdBy) {
		this.remove(new QueryWrapper<ResourceTemplate>().eq("created_by", createdBy));
	}

	/**
	 * @param query
	 * @return
	 */
	public List<ResourceTemplate> list(ResourceTemplateQuery query) {
		return super.list(new QueryWrapper<ResourceTemplate>()
				.eq(StringUtils.isNoneEmpty(query.getCreatedBy()), "created_by", query.getCreatedBy())
				.like(StringUtils.isNoneEmpty(query.getName()), "title", query.getName())
				.orderByDesc(StringUtils.isNotBlank(query.getOrderBy()), query.getOrderBy())
		        .orderByDesc(StringUtils.isBlank(query.getOrderBy()), "created_at"));
	
	}
	
	private List<ResourceTemplate> findByIdIn(List<String> ids) {
		return super.list(new QueryWrapper<ResourceTemplate>()
				.in("id", ids));
	}
	/**
	 * @param query
	 * @return
	 */
	public void addRefCount(List<ResourceRef> ref) {
		Map<String, Long> countMap = ref.stream()
				.collect(Collectors.groupingBy(ResourceRef::getTemplateId, Collectors.counting()));
		List<ResourceTemplate>  templates = findByIdIn(new ArrayList<>(countMap.keySet()));
		templates.forEach(e-> {
			Long count = e.getRefCount();
			e.setRefCount(countMap.getOrDefault(e.getId(), 0L) + count);
		});
		this.saveOrUpdateBatch(templates);
	}

}
