/*
 * Copyright © 2017-2021 http://www.futuremap.com.cn/ All rights reserved.
 *
 * Statement: This document's code after sufficiently has not permitted does not have
 * any way dissemination and the change, once discovered violates the stipulation, will
 * receive the criminal sanction.
 * Address: Building A, block 1F,  Tian'an Yungu Industrial Park phase I,
 *          Xuegang Road, Bantian street, Longgang District, Shenzhen
 * Tel: 0755-22674916
 */
package com.futuremap.erp.module.auth.dto;

import com.futuremap.erp.module.auth.entity.Role;
import com.futuremap.erp.module.auth.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Administrator
 * @title UserDto
 * @description TODO
 * @date 2021/6/23 14:12
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserDto extends User {

    private List<Role> Roles;

}
