/**
 * 
 */
package com.futuremap.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.futuremap.custom.entity.BaseEntity;

import lombok.extern.slf4j.Slf4j;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年1月28日 上午11:43:22
* 类说明
*/
/**
 * @author futuremap
 *
 * 处理新增和更新的基础数据填充，配合BaseEntity和MyBatisPlusConfig使用
 */
@Component
@Slf4j
public class MetaHandler implements MetaObjectHandler {


    /**
     * 新增数据执行
     * @param metaObject
     */
    @Override
	public void insertFill(MetaObject metaObject) {
		
		Object createdByObj =  metaObject.getValue("createdBy");
		if (createdByObj != null && StringUtils.isNotEmpty(createdByObj.toString())) {
			this.setFieldValByName("createdBy", createdByObj.toString(), metaObject);
		}else {
			String userId = (String) RequestContextHolder.getRequestAttributes().getAttribute("userId", 0);
			this.setFieldValByName("createdBy", userId, metaObject);
		}
		Object updatedByObj = metaObject.getValue("updatedBy");
		if (updatedByObj != null && StringUtils.isNotEmpty(updatedByObj.toString())) {
			this.setFieldValByName("updatedBy", updatedByObj.toString(), metaObject);
		}else {
			String userId = (String) RequestContextHolder.getRequestAttributes().getAttribute("userId", 0);
			this.setFieldValByName("updatedBy", userId, metaObject);
		}
	}

    /**
     * 更新数据执行
     * @param metaObject
     */
    @Override
	public void updateFill(MetaObject metaObject) {
		// 先取上下文中用户id
		String userId = null;
		try {
			userId = (String) RequestContextHolder.getRequestAttributes().getAttribute("userId", 0);
			this.setFieldValByName("updatedBy", userId, metaObject);
		} catch (Exception e) {

			Object updatedByObj = metaObject.getValue("et");
			if (updatedByObj != null) {
				BaseEntity baseEntity = (BaseEntity) updatedByObj;
				userId = baseEntity.getUpdatedBy();
				this.setFieldValByName("updatedBy", userId, metaObject);
			}

		}

	}

}