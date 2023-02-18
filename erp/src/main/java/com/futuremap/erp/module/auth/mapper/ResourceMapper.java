package com.futuremap.erp.module.auth.mapper;

import com.futuremap.erp.module.auth.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    * 功能表 Mapper 接口
    * </p>
*
* @author futuremap
* @since 2021-06-22
*/
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {
    List<Resource> findList(@Param("resource")Resource resource);
    List<Resource> findListByResourceIds(@Param("list")List<Integer> list);
}
