package com.futuremap.custom.service;

import com.futuremap.custom.dto.resource.ResourceTemplateCreate;
import com.futuremap.custom.dto.resource.ResourceTemplateQuery;
import com.futuremap.custom.dto.resource.ResourceTemplateUpdate;
import com.futuremap.custom.entity.ResourceRef;
import com.futuremap.custom.entity.ResourceTemplate;

import java.util.List;

import javax.validation.Valid;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-08-10
 */
public interface ResourceTemplateService extends IService<ResourceTemplate> {

	/**
	 * @param resourceTemplateCreate
	 */
	void saveResource(ResourceTemplateCreate resourceTemplateCreate);

	/**
	 * @param id
	 */
	void delete(String id);

	/**
	 * @param resourceTemplateUpdate
	 */
	void updateResource(ResourceTemplateUpdate resourceTemplateUpdate);

	/**
	 * @param query
	 * @return
	 */
	List<ResourceTemplate> list(ResourceTemplateQuery query);

	/**
	 * @param createdBy
	 */
	void deleteAll(String createdBy);

	/**
	 * @param list
	 */
	void addRefCount(List<ResourceRef> list);

}
