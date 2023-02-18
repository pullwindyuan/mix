package com.futuremap.erp.module.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.futuremap.erp.module.auth.dto.UserDto;
import com.futuremap.erp.module.auth.entity.Column;
import com.futuremap.erp.module.auth.entity.Resource;
import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.entity.User;

import java.util.List;

/**
 * <p>
 * 授权用户表 服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-19
 */
public interface IUserService extends IService<User> {
    /**
     * @param username
     * @return
     */
    User selectByEmail(String username);

    User selectByPhone(String phone);


    List<Column> getColumnList(Integer id);

    List<Resource> getResourceList(Integer userId);

    List<Role> getRoleList(Integer userId);

    List<UserDto> findList(UserDto user);

    Boolean delete(Integer id);

    void saveUser(UserDto userdto);

    void updateUser(UserDto userdto);

    User findOne(Integer userId);

    UserDto getCurrentUser();
}
