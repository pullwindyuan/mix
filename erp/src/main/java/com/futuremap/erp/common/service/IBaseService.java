package com.futuremap.erp.common.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;

/**
 * @author Administrator
 * @title IBaseService
 * @description TODO
 * @date 2021/5/25 17:31
 */
public interface IBaseService<T> extends IService<T> {

    Integer insertBatch(Collection<T> entityList);
}
