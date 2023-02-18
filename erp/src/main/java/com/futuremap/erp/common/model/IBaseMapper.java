package com.futuremap.erp.common.model;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

/**
 * @author Administrator
 * @title IBaseMapper
 * @description TODO
 * @date 2021/7/2 17:32
 */
public interface IBaseMapper<T> extends BaseMapper<T> {
    /**
     * 批量插入 仅适用于mysql
     *
     * @param entityList 实体列表
     * @return 影响行数
     */
    Integer insertBatchSomeColumn(Collection<T> entityList);
}
