/**
 * 
 */
package com.futuremap.erp.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年1月28日 上午11:43:22
* 类说明
 * @author futuremap
 *
 * 处理新增和更新的基础数据填充，配合BaseEntity和MyBatisPlusConfig使用
 */
@Component
public class MetaHandler implements MetaObjectHandler {

    /**
     * 新增数据执行
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
    	Integer userId = 0;
    	if(RequestContextHolder.getRequestAttributes() != null) {
    		userId = (Integer) RequestContextHolder.getRequestAttributes().getAttribute("userId", 0);
    	}
    	
        this.setFieldValByName("createdBy", userId, metaObject);
        this.setFieldValByName("updatedBy", userId, metaObject);
    }

    /**
     * 更新数据执行
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
    	Integer userId = 0;
    	if(RequestContextHolder.getRequestAttributes() != null) {
    		userId = (Integer) RequestContextHolder.getRequestAttributes().getAttribute("userId", 0);
    	}
    	
        this.setFieldValByName("updateBy", userId, metaObject);
    }

}