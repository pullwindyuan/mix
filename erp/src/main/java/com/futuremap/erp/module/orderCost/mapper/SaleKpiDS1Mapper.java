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
import com.futuremap.erp.module.orderCost.entity.OrderCost;
import com.futuremap.erp.module.orderCost.entity.OrderCostQuery;
import com.futuremap.erp.module.orderCost.entity.SaleKpi;
import com.futuremap.erp.module.orderCost.entity.SaleKpiQuery;
import com.futuremap.erp.module.quotation.entity.Quotation;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;


@Mapper
public interface SaleKpiDS1Mapper extends BaseMapper<SaleKpi> {

    SaleKpi getSaleKpiOne(@Param("ccode") String ccode, @Param("irowno") String irowno, @Param("cinvcode") String cinvcode);
    SaleKpi getSaleKpiByTr(@Param("ccode") String ccode, @Param("irowno") String irowno, @Param("cinvcode") String cinvcode);
    List<SaleKpi> getSaleKpiListByTime(@Param("startDate") String startDate, @Param("endDate") String endDate);
    List<SaleKpi> getSaleKpiListByTime0001(@Param("startDate") String startDate, @Param("endDate") String endDate);
    @Select("select * from sale_kpi where company_label=#{companyLabel} and dmonth=#{dmonth}")
    List<SaleKpi> getSaleKpiList(@Param("companyLabel") String companyLabel, @Param("dmonth") String dmonth);
    @Update("update sale_kpi set update_flag=1 where update_flag=0")
    void  updateFlag();

    @Select("SELECT profit_radio FROM order_cost WHERE order_detail_code=#{orderDetailCode} AND order_number=#{orderNumber} and cinvcode=#{cinvcode} limit 1")
    BigDecimal getProfit(@Param("orderDetailCode") String orderDetailCode,@Param("orderNumber") String orderNumber,@Param("cinvcode") String cinvcode);

    @Select("SELECT ddate,ccus_abbname AS customerName FROM sale_order WHERE csocode=#{orderDetailCode} AND irowno=#{orderNumber} AND cinvcode=#{cinvcode} limit 1")
    SaleKpi getSKp(@Param("orderDetailCode") String orderDetailCode,@Param("orderNumber") String orderNumber,@Param("cinvcode") String cinvcode);

    @Select("SELECT saleman_name,commission_percent,exchange_rate FROM quotation WHERE csocode=#{csocode} AND irowno=#{irowno} AND cinvcode=#{cinvcode} limit 1")
    Quotation getQuotationProp(@Param("csocode") String csocode,@Param("irowno") String irowno,@Param("cinvcode") String cinvcode);


    @Select("SELECT quotation_profit FROM quotation_total WHERE csocode=#{csocode} AND irowno=#{irowno} AND cinvcode=#{cinvcode} limit 1")
    BigDecimal getQuotationProfit(@Param("csocode") String csocode,@Param("irowno") String irowno,@Param("cinvcode") String cinvcode);

//    @Update("UPDATE `sale_kpi` b SET company_label=(SELECT a.`datasource` FROM company_info a WHERE a.`company_code`=b.company_code) WHERE b.company_label IS NULL")
//    void updateCompanyInfo();

    @Update("UPDATE `sale_kpi` b SET company_name=(SELECT a.company_name FROM company_info a WHERE a.datasource=b.company_label), company_code=(SELECT a.company_code FROM company_info a WHERE a.datasource=b.company_label) WHERE b.company_code IS NULL or b.company_name IS NULL")
    void updateCompanyInfo();

    IPage<SaleKpi> findList(IPage<SaleKpi> page, SaleKpiQuery saleKpiQuery);

    @Select("select top 1 fnatgatheringmoney from ex_invoicedetail where cordercode=#{orderDetailCode} and irowno=#{orderNumber} and cInvCode=#{cinvcode} ")
    BigDecimal getExReturnAmount(@Param("orderDetailCode") String orderDetailCode,@Param("orderNumber") String orderNumber,@Param("cinvcode") String cinvcode);

    @Select("select top 1 iMoneySum from SaleBillVouchs where cordercode=#{orderDetailCode} and irowno=#{orderNumber} and cInvCode=#{cinvcode} ")
    BigDecimal getReturnAmount(@Param("orderDetailCode") String orderDetailCode,@Param("orderNumber") String orderNumber,@Param("cinvcode") String cinvcode);


    @Delete("DELETE FROM sale_kpi WHERE order_detail_code=#{orderDetailCode} AND order_number=#{orderNumber} AND product_code=#{cinvcode} ")
    void delOld(@Param("orderDetailCode") String orderDetailCode,@Param("orderNumber") String orderNumber,@Param("cinvcode") String cinvcode);

}
