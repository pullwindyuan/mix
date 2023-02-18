package com.futuremap.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.futuremap.custom.entity.AuthRole;
import com.futuremap.custom.entity.Role;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author futuremap
 * @since 2021-01-27
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * @param ids
	 * @return
	 */
	List<Role> selectByIdIn(@Param("ids") List<String> ids);

}
