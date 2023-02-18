package com.futuremap.custom.entity;


import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@TableName("auth_role_function")
public class RoleFunction extends BaseEntity{

	    /**
	    * 父id
	    */
	    private String roleId;

	    /**
	    * 角色名
	    */
	    private String roleName;

	    /**
	    * 功能名称
	    */
	    private String functionId;

}
