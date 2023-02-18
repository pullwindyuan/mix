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
package com.futuremap.erp.module.sys.dto;

import com.futuremap.erp.module.auth.dto.UserDto;
import lombok.Data;

/**
 * @author Administrator
 * @title TokenDto
 * @description TODO
 * @date 2021/6/19 17:17
 */
@Data
public class TokenDto {
    private String token;
    private String head;
    private Long expiration;
    private UserDto userDto;
}
