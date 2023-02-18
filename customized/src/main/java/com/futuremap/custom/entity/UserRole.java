package com.futuremap.custom.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
@TableName("auth_user_role")
public class UserRole extends BaseEntity{
	/**
	 * role_id
	 */
	private String roleId;

	/**
	 * user_id
	 */
	private String userId;
}
