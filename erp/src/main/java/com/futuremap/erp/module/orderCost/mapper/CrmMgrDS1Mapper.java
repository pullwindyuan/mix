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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.module.orderCost.entity.CrmMgr;
import com.futuremap.erp.module.orderCost.entity.CrmMgrQuery;
import com.futuremap.erp.module.orderCost.entity.OrderCost;
import com.futuremap.erp.module.orderCost.entity.SaleKpi;
import com.futuremap.erp.module.quotation.entity.QuotationTotal;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface CrmMgrDS1Mapper extends BaseMapper<CrmMgr> {



    @Select("SELECT ddate,ccus_abbname AS customerName,cdepname AS accountGroup,cdepcode AS groupType,ccus_code AS customerCode FROM sale_order WHERE csocode=#{orderDetailCode} AND irowno=#{orderNumber} AND cinvcode=#{cinvcode} limit 1")
    CrmMgr getCmp(@Param("orderDetailCode") String orderDetailCode, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode);

    CrmMgr getInnerCrmMgrOne(@Param("orderDetailCode") String orderDetailCode, @Param("orderNumber") String orderNumber, @Param("productCode") String productCode);
    CrmMgr getOuterCrmMgrOne(@Param("orderDetailCode") String orderDetailCode, @Param("orderNumber") String orderNumber, @Param("productCode") String productCode);

    List<CrmMgr> getOuterCrmMgrListByTime(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<CrmMgr> getInnerCrmMgrListByTime(@Param("startDate") String startDate, @Param("endDate") String endDate);
    List<CrmMgr> getOuterCrmMgrListByTime0001(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<CrmMgr> getInnerCrmMgrListByTime0001(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<CrmMgr> getCrmMgrList(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Select("select * from crm_mgr where company_label=#{companyLabel} and dmonth=#{dmonth}")
    List<CrmMgr> getCrmMgrListByTime(@Param("companyLabel") String companyLabel, @Param("dmonth") String dmonth);


    @Select(" SELECT SUM(conlection_money) AS return_amount,MAX(dvouch_date) AS return_date FROM sale_bill WHERE csocode=#{orderDetilCode} AND irowno=#{orderNumber} and cinvcode=#{cinvcode}")
    CrmMgr getAmountAndDate(@Param("orderDetilCode") String orderDetilCode, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode);

    public IPage<CrmMgr> findList(Page<CrmMgr> page, CrmMgrQuery crmMgrQuery);

//    @Update("UPDATE `crm_mgr` b SET company_label=(SELECT a.`datasource` FROM company_info a WHERE a.`company_code`=b.company_code) WHERE b.company_label IS NULL")
//    void updateCompanyInfo();

    @Update("UPDATE `crm_mgr` b SET company_name=(SELECT a.company_name FROM company_info a WHERE a.datasource=b.company_label), company_code=(SELECT a.company_code FROM company_info a WHERE a.datasource=b.company_label) WHERE b.company_code IS NULL or b.company_name IS NULL")
    void updateCompanyInfo();

    String getDeliverDay(@Param("orderDetilCode") String orderDetilCode, @Param("orderNumber") String orderNumber, @Param("cInvCode") String cInvCode, @Param("customerCode") String customerCode);

    String getDeliverDay_erp10(@Param("orderDetilCode") String orderDetilCode, @Param("orderNumber") String orderNumber, @Param("cInvCode") String cInvCode, @Param("customerCode") String customerCode);

    String getDeliverDay_erp3(@Param("orderDetilCode") String orderDetilCode, @Param("orderNumber") String orderNumber, @Param("cInvCode") String cInvCode, @Param("customerCode") String customerCode);

    @Select("SELECT * FROM quotation_total WHERE csocode=#{csocode} AND irowno=#{irowno} AND cinvcode=#{cinvcode} limit 1")
    QuotationTotal getQuotationTotal(@Param("csocode") String csocode, @Param("irowno") String irowno, @Param("cinvcode") String cinvcode);


    @Delete("DELETE FROM `crm_mgr` WHERE order_detail_code=#{orderDetailCode} AND order_number=#{orderNumber} AND product_code=#{cinvcode} ")
    void delOld(@Param("orderDetailCode") String orderDetailCode, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode);


    CrmMgr getFromBill(@Param("invoiceId") String invoiceId, @Param("orderDetailCode") String orderDetailCode, @Param("orderNumber") String orderNumber, @Param("productCode") String productCode);

    @Select("SELECT cdepname FROM sale_order WHERE csocode=#{orderDetailCode} AND irowno=#{orderNumber} AND cinvcode=#{productCode} AND company_code=#{companyCode}  limit 1")
    String getGroupType(@Param("companyCode") String companyCode, @Param("orderDetailCode") String orderDetailCode, @Param("orderNumber") String orderNumber, @Param("productCode") String productCode);
}
