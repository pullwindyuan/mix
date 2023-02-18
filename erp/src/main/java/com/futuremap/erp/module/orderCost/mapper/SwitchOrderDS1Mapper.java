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
import com.futuremap.erp.module.orderCost.entity.SaleKpi;
import com.futuremap.erp.module.orderCost.entity.SaleKpiQuery;
import com.futuremap.erp.module.orderCost.entity.SwitchOrder;
import com.futuremap.erp.module.orderCost.entity.SwitchOrderQuery;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SwitchOrderDS1Mapper extends BaseMapper<SwitchOrder> {

    @Delete("DELETE FROM switch_order WHERE ddmonth=#{ddmonth} AND order_detail_code=#{order_detail_code} AND product_code=#{product_code} AND order_switch=#{order_switch}")
    void delData(@Param("ddmonth") String ddmonth,@Param("order_detail_code") String order_detail_code,@Param("product_code") String product_code,@Param("order_switch") String order_switch);

    IPage<SwitchOrder> findList(IPage<SwitchOrder> page, SwitchOrderQuery switchOrderQuery);

    List<SwitchOrder> findListBySwitchCode(SwitchOrderQuery switchOrderQuery);
}
