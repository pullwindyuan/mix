package com.futuremap.custom.entity;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author futuremap
 * @since 2021-01-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AuthUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户状态 0--正常 1--禁用
     */
    private Integer userStatus;

    /**
     * 部门id
     */
    private String department;

    /**
     * 职位ID
     */
    private String position;

    private String role;

    /**
     * 头像
     */
    private String avatar;

   


}
