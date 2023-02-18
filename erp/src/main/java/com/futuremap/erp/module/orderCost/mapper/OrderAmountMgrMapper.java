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
package com.futuremap.erp.module.orderCost.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futuremap.erp.module.orderCost.entity.OrderAmountMgr;
import com.futuremap.erp.module.orderCost.entity.OrderAmountMgrQuery;
import com.futuremap.erp.module.orderCost.entity.SwitchOrder;
import com.futuremap.erp.module.orderCost.entity.SwitchOrderQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface OrderAmountMgrMapper extends BaseMapper<OrderAmountMgr> {

    @Select("SELECT back_day FROM order_amount_mgr WHERE customer_code=#{customer_code} limit 1")
    Integer getLimitBack(@Param("customer_code") String customer_code);


    IPage<OrderAmountMgr> findList(IPage<OrderAmountMgr> page, OrderAmountMgrQuery orderAmountMgrQuery);

}
