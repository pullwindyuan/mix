package com.futuremap.custom.entity;


import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@TableName("auth_role")
public class Role extends BaseEntity{

	 /**
	    * role_desc
	    */
	    private String roleDesc;

	    /**
	    * 角色名
	    */
	    private String roleName;

	    private String detail;
}
