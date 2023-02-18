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
package com.futuremap.erp.module.operation.dto;

import com.futuremap.erp.module.operation.entity.OperatingStatement;
import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 * @title OperationStatemnetDto
 * @description TODO
 * @date 2021/6/7 20:32
 */
@Data
public class OperationStatemnetDto {

    private String tag;
    private List<OperatingStatement> peratingStatements;
}
