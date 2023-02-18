package com.futuremap.custom.service;

import com.futuremap.custom.dto.resource.ResourceObjCreate;
import com.futuremap.custom.dto.resource.ResourceObjUpdate;
import com.futuremap.custom.dto.resource.ResourceTemplateQuery;
import com.futuremap.custom.entity.ResourceObject;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-08-10
 */
public interface ResourceObjectService extends IService<ResourceObject> {

	/**
	 * @param resourceObjCreate
	 */
	void saveResource(ResourceObjCreate resourceObjCreate);

	/**
	 * @param id
	 */
	void delete(String id);

	/**
	 * @param resourceObjUpdate
	 */
	void updateResource(ResourceObjUpdate resourceObjUpdate);

	/**
	 * @param id
	 * @return
	 */
	ResourceObject get(@Valid String id);

	/**
	 * @param query
	 * @return
	 */
	List<ResourceObject> list(ResourceTemplateQuery query);

}
