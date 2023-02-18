package com.futuremap.erp.module.auth.mapper;

import com.futuremap.erp.module.auth.entity.Column;
import com.futuremap.erp.module.auth.entity.Resource;
import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* <p>
    * 授权用户角色 Mapper 接口
    * </p>
*
* @author futuremap
* @since 2021-06-19
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {




    List<UserRole> findList(@Param("userRole") UserRole userRole);



}
