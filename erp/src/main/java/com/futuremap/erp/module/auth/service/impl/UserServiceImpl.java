package com.futuremap.erp.module.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.common.Constants;
import com.futuremap.erp.module.auth.dto.UserDto;
import com.futuremap.erp.module.auth.entity.*;
import com.futuremap.erp.module.auth.mapper.RoleMapper;
import com.futuremap.erp.module.auth.mapper.UserMapper;
import com.futuremap.erp.module.auth.mapper.UserRoleMapper;
import com.futuremap.erp.module.auth.service.IUserService;
import com.futuremap.erp.utils.BeanCopierUtils;
import com.futuremap.erp.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 授权用户表 服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    RoleMapper roleMapper;

    @Override
    public User selectByEmail(String username) {
        return baseMapper.selectByEmail(username);
    }

    @Override
    public User selectByPhone(String phone) {
        return baseMapper.selectByPhone(phone);
    }

//    @Override
//    public List<Role> getAuthorityList(Integer id) {
//        return new ArrayList<>();
//    }

    @Override
    public List<Column> getColumnList(Integer userId) {
        return baseMapper.getColumnsByUserId(userId);
    }
    @Override
    public List<Resource> getResourceList(Integer userId) {
        return baseMapper.getResourcesByUserId(userId);
    }
    @Override
    public List<Role> getRoleList(Integer userId) {
        return baseMapper.getRolesByUserId(userId);
    }

    @Override
    public List<UserDto> findList(UserDto user) {
        if(UserUtils.hasRole(Constants.ROLE_SALEMAN)||UserUtils.hasRole(Constants.ROLE_PURCHASER)){
            user = new UserDto();
            user.setPhone(UserUtils.getCurrentUserName());
        }
        List<UserDto> userDtoList = new ArrayList<>();
        List<User> list = baseMapper.findList(user);
        list.stream().forEach(e->{
            UserDto userDto = new UserDto();
            BeanCopierUtils.copyProperties(e,userDto);
            List<Role> roles = baseMapper.getRolesByUserId(e.getId());
            userDto.setRoles(roles);
            userDtoList.add(userDto);
        });
        return userDtoList;
    }

    @Override
    public Boolean delete(Integer id) {
        //删除用户角色对应关系
        userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id",id));
        //删除用户软删除
        return baseMapper.delete(id);
    }




    @Override
    public void saveUser(UserDto userdto) {
        String password = userdto.getPassword();
        //TODO 解密处理
//        数据库入库加密
        User user = new User();
        BeanCopierUtils.copyProperties(userdto,user);
        user.setPassword(new BCryptPasswordEncoder().encode(userdto.getPassword()));
       //保存用户
        save(user);
        List<Role> roles = userdto.getRoles();
        userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id",userdto.getId()));
        roles.stream().forEach(e->{
            //添加用户与角色关系
            UserRole userRole  = new UserRole();
            userRole.setRoleId(e.getId());
            userRole.setUserId(user.getId());
            userRoleMapper.insert(userRole);
        });
    }

    @Override
    public void updateUser(UserDto userdto) {
        User user = new User();
        BeanCopierUtils.copyProperties(userdto,user);
        List<Role> roles = userdto.getRoles();
        userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id",userdto.getId()));
        roles.stream().forEach(e->{
            //添加用户与角色关系
            UserRole userRole  = new UserRole();
            userRole.setRoleId(e.getId());
            userRole.setUserId(user.getId());
            userRoleMapper.insert(userRole);
        });
        //更新用户
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id",user.getId());
        updateWrapper.set("phone",user.getPhone());
        updateWrapper.set("code",user.getCode());
        updateWrapper.set("email",user.getEmail());
        updateWrapper.set("name",user.getName());
        update(null,updateWrapper);



    }


    public User selectByPhone(String phone, String[] select) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("phone", phone).select(select));
    }
    /**
     * @param userId
     * @return
     */
    public User findById(Integer userId) {
        return baseMapper.selectById(userId);
    }

    /**
     * @param userId
     * @return
     */
    public User findById(Integer userId,String[] select) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("id", userId).select(select));
    }
    /**
     * @param userId
     * @return
     */
    @Override
    public User findOne(Integer userId) {
        return baseMapper.findOne(userId);
    }

    @Override
    public UserDto getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserPhone = authentication.getName();
            UserDto userParam = new UserDto();
            userParam.setPhone(currentUserPhone);
            List<User> list = baseMapper.findList(userParam);
            if(list!=null&list.size()>0){
                User user = list.get(0);
                UserDto userDto = new UserDto();
                BeanCopierUtils.copyProperties(user,userDto);
                List<Role> roles = baseMapper.getRolesByUserId(user.getId());
                userDto.setRoles(roles);
                return userDto;
            }
        }
        return null;
    }


}
