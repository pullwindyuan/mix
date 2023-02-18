package com.futuremap.custom.entity;

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
public class AuthRoleFunction extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父ID
     */
    private String roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 功能名称
     */
    private String functionName;

    /**
     * 功能ID
     */
    private Integer functionId;

   


}
