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
public class AuthRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 角色名
     */
    private String roleName;


    /**
     * 描述
     */
    private String detail;


}
