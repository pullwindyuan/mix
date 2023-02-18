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
import com.futuremap.erp.module.orderCost.entity.OrderCost;
import com.futuremap.erp.module.orderCost.entity.OrderCostTotal;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface OrderCostTotalDS1Mapper extends BaseMapper<OrderCostTotal> {

    @Delete("DELETE FROM order_cost_total WHERE dmonth=#{dmonth} and company_label=#{companyLabel}")
    void delOrderCostTotal(@Param("dmonth") String dmonth, @Param("companyLabel") String companyLabel);

}
