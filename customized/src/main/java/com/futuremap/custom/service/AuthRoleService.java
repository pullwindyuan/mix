package com.futuremap.custom.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.futuremap.custom.entity.AuthRole;
import com.futuremap.custom.entity.Role;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-01-27
 */
public interface AuthRoleService extends IService<Role> {
	public List<Role> findByIdIn(List<String> ids);
}
