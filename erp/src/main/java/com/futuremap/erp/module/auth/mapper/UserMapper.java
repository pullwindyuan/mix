package com.futuremap.erp.module.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.futuremap.erp.module.auth.dto.UserDto;
import com.futuremap.erp.module.auth.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* <p>
    * 授权用户表 Mapper 接口
    * </p>
*
* @author futuremap
* @since 2021-06-19
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * @param email
     * @return
     */

    @Select("select * from auth_user where email = #{email} and del_status = 0")
    User selectByEmail(@Param("email")String email);

    /**
     * @param phone
     * @return
     */
    @Select("select * from auth_user where phone = #{phone} and del_status = 0")
    User selectByPhone( @Param("phone") String phone);

    /**
     * @param phone
     * @param userId
     * @return
     */
    @Select("select * from auth_user where phone = #{phone} and id != #{userId}")
    List<UserRole> selectByPhoneAndIdNot(@Param("phone") String phone, @Param("userId") Integer userId);

    /**
     * @param phone
     * @param userId
     * @return
     */
    @Select("select count(*) from auth_user where phone = #{phone} and id != #{userId}")
    int countByPhoneAndIdNot(@Param("phone")String phone, @Param("userId") Integer userId);

    /**
     * @param phone
     * @param
     * @return
     */
    @Select("select count(*) from auth_user where phone = #{phone} and del_status=#{delStatus}")
    int countByPhoneAndDelStatus(@Param("phone") String phone, @Param("delStatus") int delStatus);

    /**
     * @param openId
     */
    @Select("select * from auth_user where open_id = #{openId} and del_status = 0")
    int countByOpenId(@Param("openId") String openId);

    /**
     * @param unionId
     * @param
     * @return
     */
    @Select("select count(*) from auth_user where unionId = #{unionId} and del_status=#{delStatus}")
    int countByUnionIdAndDelStatus(@Param("unionId") String unionId, @Param("delStatus") int delStatus);

    /**
     * @param unionId
     * @param
     * @return
     */
    @Select("select count(*) from auth_user where unionId = #{unionId} and del_status=#{delStatus} limit 1")
    User findFirstByUnionIdAndDelStatus(@Param("unionId") String unionId, @Param("delStatus") int delStatus);

    /**
     * @param phone
     * @param
     * @return
     */
    @Select("select * from auth_user where phone = #{phone} and del_status=#{delStatus} limit 1")
    User findByPhoneAndDelStatus(@Param("phone") String phone, @Param("delStatus") int delStatus);


    List<Column> getColumnsByUserId(@Param("userId")Integer userId);

    List<Resource> getResourcesByUserId(@Param("userId")Integer userId);

    List<Role> getRolesByUserId(@Param("userId") Integer userId);


    List<User> findList(@Param("user")UserDto user);

    Boolean delete(@Param("userId")Integer userId);

    User findOne(@Param("userId")Integer userId);
}
