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

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futuremap.erp.module.orderCost.entity.OrderCost;
import com.futuremap.erp.module.orderCost.entity.OrderCostQuery;
import com.futuremap.erp.module.orderCost.entity.SwitchOrder;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Mapper
public interface OrderCostDS1Mapper extends BaseMapper<OrderCost> {

    @Delete("DELETE FROM order_cost WHERE id NOT IN (SELECT id FROM (SELECT MIN(id) AS id FROM order_cost GROUP BY order_code) AS b) AND dmonth=#{dmonth}")
    void delRepeatOrder(@Param("dmonth") String dmonth);

    @Update("UPDATE order_cost SET material_cost=#{material_cost} WHERE order_code=#{order_code} AND dmonth=#{dmonth}")
    void updateMaterialCost(@Param("material_cost") BigDecimal material_cost, @Param("order_code") String order_code, @Param("dmonth") String dmonth);

    @Select("SELECT * FROM order_cost WHERE dmonth=#{dmonth} and company_label=#{companyLabel}")
    List<OrderCost> getOrdersByTime(@Param("dmonth") String dmonth, @Param("companyLabel") String companyLabel);


    @Select("SELECT * FROM order_cost WHERE order_type!=#{orderType} AND hexiao_cost>0 AND dmonth=#{dmonth} and company_label=#{companyLabel}")
    List<OrderCost> getOrdersByType(@Param("dmonth") String dmonth, @Param("companyLabel") String companyLabel, @Param("orderType") Integer orderType);


    @Update("UPDATE order_cost SET order_type=#{orderType} WHERE dmonth=#{dmonth} AND company_label=#{companyLabel} AND order_detail_code=#{order_detail_code} AND cinvcode=#{cinvcode}")
    void updateByType(@Param("dmonth") String dmonth, @Param("companyLabel") String companyLabel, @Param("order_detail_code") String order_detail_code, @Param("cinvcode") String cinvcode, @Param("orderType") Integer orderType);


    @Select("SELECT * FROM order_cost WHERE cchild_code IS NOT NULL AND dmonth=#{dmonth} AND company_label=#{companyLabel} ")
    List<OrderCost> getClist(@Param("dmonth") String dmonth, @Param("companyLabel") String companyLabel);

    @Select("SELECT * FROM order_cost WHERE cparent_code=#{cparentCode}")
    OrderCost getFone(@Param("cparentCode") String cparentCode);


    @Select("SELECT id, income,material_cost,order_detail_code,order_number,cinvcode,fquantity FROM order_cost WHERE (material_cost<=0 or material_cost is null) and dmonth=#{dmonth} and company_label=#{companyLabel}")
    List<OrderCost> getOrderCostList6(@Param("dmonth") String dmonth, @Param("companyLabel") String companyLabel);

    @Update("UPDATE  order_cost SET order_type=#{orderType} WHERE company_label=#{companyLabel} AND dmonth=#{dmonth}  AND order_detail_code LIKE '%002'")
    void updateOtype2(@Param("dmonth") String dmonth, @Param("companyLabel") String companyLabel, @Param("orderType") Integer orderType);

    @Update("UPDATE  order_cost SET order_type=#{orderType} WHERE company_label=#{companyLabel} AND dmonth=#{dmonth}  AND order_detail_code LIKE '%003'")
    void updateOtype3(@Param("dmonth") String dmonth, @Param("companyLabel") String companyLabel, @Param("orderType") Integer orderType);

    @Select("SELECT * FROM order_cost WHERE order_type=#{orderType} AND fquantity>0 and id!=#{id} AND order_number=#{orderNumber} AND cinvcode=#{cinvcode} AND (order_detail_code=#{odc1} OR  order_detail_code=#{odc2}) ORDER BY darverifierdate DESC LIMIT 1")
    OrderCost getformatOC(@Param("odc1") String odc1, @Param("odc2") String odc2, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode, @Param("id") Integer id, @Param("orderType") Integer orderType);


    IPage<OrderCost> findList(IPage<OrderCost> page, OrderCostQuery orderCostQuery);

    List<OrderCost> findLists(OrderCostQuery orderCostQuery);

    @Select("select * from order_cost")
    List<OrderCost> getAllData();

    @Delete("delete from order_cost where dmonth=#{dmonth} and company_label=#{companyLabel}")
    void delOldData(@Param("dmonth") String dmonth, @Param("companyLabel") String companyLabel);

    @Select("SELECT company_code FROM company_info WHERE datasource!=#{companyLabel}")
    List<String> getCompanyCodeList(@Param("companyLabel") String companyLabel);


    void updateOwnd(Map<String, Object> param);


    @Select("SELECT SUM(total_amount) AS ta FROM order_cost_total WHERE dmonth=#{dmonth}")
    BigDecimal getAllAmount(@Param("dmonth") String dmonth);

    @Select("SELECT sum(run_cost) FROM order_cost_total WHERE dmonth=#{dmonth}")
    BigDecimal getRunCost(@Param("dmonth") String dmonth);

    @Select("SELECT sum(mgr_cost) FROM order_cost_total WHERE  dmonth=#{dmonth}")
    BigDecimal getMgrCost(@Param("dmonth") String dmonth);

    @Update("UPDATE `order_cost` SET company_code=(SELECT a.company_code FROM company_info a WHERE a.`datasource`=company_label) WHERE company_code IS NULL")
    void updateCompanyInfo();

    @Select("SELECT order_switch as orderDetailCode,order_number_switch as orderNumber FROM switch_order WHERE order_detail_code=#{orderCode}  AND product_code=#{cinvcode} limit 1")
    OrderCost getOC(@Param("orderCode") String orderCode, @Param("cinvcode") String cinvcode);

    @Select("SELECT * FROM switch_order WHERE order_detail_code=#{order_detail_code}  AND order_number=#{order_number} and ddmonth=#{ddmonth} limit 1")
    SwitchOrder getSwitchOrder(@Param("order_detail_code") String order_detail_code, @Param("order_number") String order_number, @Param("ddmonth") String ddmonth);

    @Select("SELECT DISTINCT(invoice_id) as invs FROM order_cost WHERE plan_return_day IS NULL AND company_label=#{company_label} AND dmonth=#{ddmonth}")
    List<String> getNullRDs(@Param("company_label") String company_label, @Param("ddmonth") String ddmonth);

    @Update("UPDATE order_cost SET return_day=#{plan_return_day} WHERE plan_return_day IS NULL AND company_label=#{company_label} AND dmonth=#{ddmonth} AND invoice_id=#{invoice_id}")
    void updateRDs(@Param("company_label") String company_label, @Param("ddmonth") String ddmonth, @Param("invoice_id") String invoice_id, @Param("plan_return_day") String plan_return_day);


    @Select("SELECT * FROM order_cost WHERE order_detail_code=#{orderCode} AND order_number=#{orderNumber} AND cinvcode=#{cinvcode}")
    OrderCost getOrderCost(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode);

//    @Select("SELECT * FROM order_cost WHERE order_detail_code=#{orderCode} AND order_number=#{orderNumber} AND cinvcode=#{cinvcode} and autoid=#{autoid}")
//    OrderCost getOrderCostUnit(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode, @Param("autoid") String autoid);

    @Select("SELECT * FROM order_cost WHERE order_detail_code=#{orderCode} AND order_number=#{orderNumber} AND cinvcode=#{cinvcode} limit 1")
    OrderCost getOrderCostUnit(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode);

    @Select("SELECT * FROM order_cost WHERE order_detail_code=#{orderCode} AND cinvcode=#{cinvcode} limit 1")
    OrderCost getOrderCostNew(@Param("orderCode") String orderCode, @Param("cinvcode") String cinvcode);

    @Update("UPDATE order_cost SET cparent_code=#{cparentCode} WHERE order_detail_code=#{orderDetailCode} AND order_number=#{orderNumber} and cinvcode=#{cinvcode} AND irowno=#{irowno}  and autoid=#{autoid}")
    void updateF(@Param("orderDetailCode") String orderDetailCode, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode, @Param("irowno") String irowno, @Param("cparentCode") String cparentCode, @Param("autoid") String autoid);

    @Update("UPDATE order_cost SET cchild_code=#{cchildCode} WHERE order_detail_code=#{orderDetailCode} AND order_number=#{orderNumber} AND cinvcode=#{cinvcode} AND irowno=#{irowno} and autoid=#{autoid} ")
    void updateC(@Param("orderDetailCode") String orderDetailCode, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode, @Param("irowno") String irowno, @Param("cchildCode") String cchildCode, @Param("autoid") String autoid);


    @Select("SELECT * FROM order_cost WHERE (material_cost<=0 or material_cost is null) and order_detail_code=#{orderCode} AND order_number=#{orderNumber} AND cinvcode=#{cinvcode}")
    OrderCost getOrderCost1(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode);

    @Select("SELECT ccus_code,ccus_name FROM order_cost WHERE ccus_code IS NOT NULL AND ccus_name IS NOT NULL  GROUP BY ccus_code,ccus_name")
    List<Map<String, Object>> getCCus();

    @Select("SELECT cperson_code,cperson_name FROM order_cost WHERE cperson_code IS NOT NULL AND cperson_name IS NOT NULL  GROUP BY cperson_code,cperson_name")
    List<Map<String, Object>> getCperson();

    @Select("SELECT cven_code,cven_name FROM order_cost WHERE cven_code IS NOT NULL AND cven_name IS NOT NULL  GROUP BY cven_code,cven_name")
    List<Map<String, Object>> getCven();

}
