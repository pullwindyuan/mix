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
package com.futuremap.erp.module.orderprocess.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 * @title RecordoutQuery
 * @description TODO
 * @date 2021/6/1 14:33
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RecordoutQuery extends RecordoutList{
    private String startDate;
    private String endDate;
}
