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
import com.futuremap.erp.module.orderCost.entity.Bom;
import com.futuremap.erp.module.orderCost.entity.Cven;
import com.futuremap.erp.module.orderCost.entity.OrderCost;
import com.futuremap.erp.module.orderCost.entity.OrderCostQuery;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 从SQL SERVER查询数据出来
 * 主要通过登录ERP系统，定位功能相关位置，监控SQL，拿到所需要的查询数据
 * 根据 stone 提供 3月30日报表问题记录 .docx 文档定位ERP相关位置以及相关参考SQL
 */

@Mapper
//@DS("erp002")
public interface OrderCostDS2Mapper extends BaseMapper<OrderCost> {

    /**
     * 获取某月订单号和收入
     * 订单号：ERP-供应链-出口管理-出口发票列表-取（产口细节）列
     * 收入：ERP-供应链-出口管理-出口发票列表-取（本币金额）列
     *
     * @return
     */
    List<OrderCost> getOrderAndIncomeList(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<OrderCost> getOrderAndIncomeList0(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<OrderCost> getOrderAndIncomeList3(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<OrderCost> getOCbyType1(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<OrderCost> getOCbyType2(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Select("select irowno,ddate,autoid,cinvname from v_ex_orderlist where ccode=#{ccode} and cinvcode=#{cinvcode} and  ccuscode not in('HD154', 'W0001', 'HD151','EASI001','9999')")
    List<Map<String, Object>> getOuterSaleOrderNumber(@Param("ccode") String ccode, @Param("cinvcode") String cinvcode);

    @Select("select irowno,ddate,autoid,cinvname from v_ex_invoicelist where iverifystate=2 and cdemandcode=#{ccode} and cinvcode=#{cinvcode} and  ccuscode not in('HD154', 'W0001', 'HD151','EASI001','9999')")
    List<Map<String, Object>> getOuterSaleOrderNumber0001(@Param("ccode") String ccode, @Param("cinvcode") String cinvcode);

    @Select("select irowno,ddate,autoid,cinvname from SaleOrderQ INNER JOIN SaleOrderSQ ON SaleOrderQ.id = SaleOrderSQ.id where csocode=#{csocode} and cinvcode=#{cinvcode} AND ccuscode not in('HD154', 'W0001', 'HD151','EASI001','9999')")
    List<Map<String, Object>> getInnerSaleOrderNumber(@Param("csocode") String csocode, @Param("cinvcode") String cinvcode);

    @Select(" SELECT irowno,ddate,autoid,cinvname FROM SaleBillVouchZT inner join SaleBillVouchZW on SaleBillVouchZT.sbvid=SaleBillVouchZW.sbvid " +
            "WHERE cdemandcode=#{csocode} and cinvcode=#{cinvcode} and (cVouchType = 26 OR cVouchType = 27) and SaleBillVouchZT.ccusCode not in('HD154', 'W0001', 'HD151','EASI001','9999')")
    List<Map<String, Object>> getInnerSaleOrderNumber0001(@Param("csocode") String csocode, @Param("cinvcode") String cinvcode);

    String getDeliverDay(@Param("orderDetilCode") String orderDetilCode, @Param("orderNumber") String orderNumber, @Param("cInvCode") String cInvCode, @Param("customerCode") String customerCode);

    String getDeliverDay_erp10(@Param("orderDetilCode") String orderDetilCode, @Param("orderNumber") String orderNumber, @Param("cInvCode") String cInvCode, @Param("customerCode") String customerCode);

    String getDeliverDay_erp3(@Param("orderDetilCode") String orderDetilCode, @Param("orderNumber") String orderNumber, @Param("cInvCode") String cInvCode, @Param("customerCode") String customerCode);


    String getDeliverDay_all(@Param("orderDetailCode") String orderDetilCode, @Param("orderNumber") String orderNumber, @Param("productCode") String productCode, @Param("invoiceId") String invoiceId);

    String getReturnDay(@Param("invoiceId") String invoiceId);


    @Select("select top 1 ccusname from Customer  where cCusCode =#{cCusCode}")
    String getCcusname(@Param("cCusCode") String cCusCode);

    @Select("select top 1 cpersoncode  from  ex_order where ccode =#{ccode}")
    String getCpersoncodeOuter(@Param("ccode") String ccode);

    @Select("select top 1 cPersonCode  from SO_SOMain where cSOCode =#{cSOCode} ")
    String getCpersoncodeInner(@Param("cSOCode") String cSOCode);

    @Select("select top 1 cPersonName from Person  where cPersonCode=#{cPersonCode}")
    String getCpersonName(@Param("cPersonCode") String cPersonCode);


    /**
     * 获取某月订单号
     *
     * @return
     */
    List<OrderCost> getOrderCodeList(@Param("startDate") String startDate, @Param("endDate") String endDate);


    /**
     * 获取某月材料成本
     * 材料成本：ERP-采购管理-采购订货-采购订单列表-取（原币金额）列
     *
     * @return
     */

    BigDecimal getMaterialCost_1(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode);
    BigDecimal getMaterialCost_Hexiao(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode);
    BigDecimal getMaterialCost_HexiaoX(@Param("orderCode") String orderCode, @Param("cinvcode") String cinvcode);

    BigDecimal getMaterialCost_1X(@Param("orderCode") String orderCode, @Param("cinvcode") String cinvcode);

    BigDecimal getMaterialCost_1000(@Param("cinvcode") String cinvcode);

    Map<String, Object> getMaterialCost_1001(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode);

    Map<String, Object> getMaterialCost_1001X(@Param("orderCode") String orderCode, @Param("cinvcode") String cinvcode);

    BigDecimal getMaterialCost_0001(@Param("orderCode") String orderCode, @Param("cinvcode") String cinvcode);

    BigDecimal getMaterialCost_0002(@Param("orderCode") String orderCode, @Param("cinvcode") String cinvcode);

    Map<String,Object> getMaterialCost_2(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode);

    List<Cven> getCvenList(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber);

    List<Cven> getCvenListLike(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber);

    @Update("UPDATE order_cost SET tax_price=#{tax_price} ,material_cost=#{material_cost},create_time=now() WHERE id=#{id}")
    void updateUMC(@Param("tax_price") BigDecimal tax_price, @Param("material_cost") BigDecimal material_cost, @Param("id") Integer id);

    @Select("SELECT * FROM `order_cost`  WHERE dmonth=#{dmonth} AND order_type!=3  AND (material_cost <=0 OR material_cost IS NULL) ")
    List<OrderCost> getOmaterial(@Param("dmonth") String dmonth);


    @Select("select  sum(iNatMoney/iQuantity) as mc from OM_MODetails where  csocode=#{orderCode} and irowno =#{orderNumber}  ")
    BigDecimal getMaterialCost_ww(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber);

    @Select("select  sum(iNatMoney/iQuantity) as mc from OM_MODetails where  csocode=#{orderCode}   ")
    BigDecimal getMaterialCost_wwX(@Param("orderCode") String orderCode);

    @Select("select iNatUnitPrice  from PO_Podetails where csoordercode=#{orderCode} and irowno=#{orderNumber} and cinvcode=#{cinvcode}")
    BigDecimal getiNatUnitPrice(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("cinvcode") String cinvcode);


    @Select("SELECT top 1 b.inatunitprice FROM zpurpotail b where b.cinvcode=#{cinvcode}  order by b.darrivedate  desc")
    BigDecimal getMaterialCost_unit10(@Param("cinvcode") String cinvcode);


    @Select("select sum(iAOutPrice)/ sum(iAOutQuantity)  from  IA_Subsidiary  where iYear = #{iYear} and cInvCode = #{cinvcode}")
    BigDecimal getMaterialCost_unit10_plus(@Param("cinvcode") String cinvcode, @Param("iYear") String iYear);


    BigDecimal getItemCostByType(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("cAmoType") String cAmoType, @Param("dmonth") String dmonth);

    BigDecimal getItemCostByType0001(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("cAmoType") String cAmoType, @Param("dmonth") String dmonth);

    BigDecimal getManufactureCostByItem(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("cAmoType") String cAmoType, @Param("dmonth") String dmonth);

    BigDecimal getManufactureCostByItem0001(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("cAmoType") String cAmoType);

    String getBomId(@Param("cinvcode") String cinvcode);

    @Select("select a.BomId, b.InvCode,a.BaseQtyN from bom_opcomponent a inner join bas_part b on a.ComponentId=b.PartId where a.bomid =#{bomId}")
    List<Bom> getBomList(@Param("bomId") String bomId);


    List<Bom> getBomLists(Map<String, Object> param);


    /**
     * 某月出口发票总金额
     * strone提供sql
     *
     * @return
     */
    BigDecimal getExport_amount(Map<String, Object> param);

    /**
     * 某月国内发票总金额
     * strone提供sql
     *
     * @return
     */
    BigDecimal getDomestic_amount2(Map<String, Object> param);


    /**
     * 某月人工费用总金额
     * 人工总费用：ERP-管理会计-成本管理-数据录入-人工费用
     *
     * @return
     */
    @Select("SELECT sum(ca_dirma.idirectpay)+sum(ca_dirma.iindirectpay) as laborCost FROM ca_dirma WHERE   iperiod = #{dmonth}")
    BigDecimal getLabor_cost(@Param("dmonth") String dmonth);


    /**
     * 获取某月某个项目的总费用
     * 管理费 5501
     * 营业费 5502
     * 制造费 4105
     * strone提供sql
     *
     * @return
     */
    @Select("select md as itemCost from GL_accsum where ccode=#{itemCode} and iYPeriod =#{dmonth}")
    BigDecimal getItemCost(@Param("dmonth") String dmonth, @Param("itemCode") String itemCode);

    /**
     * @return
     */
    @Select("select  sum(fnatmoney) as income from ex_invoice as a inner join ex_invoicedetail as b on a.id = b.id  where idemandseq= #{idemandseq} and irowno=#{irowno} and  cordercode =#{cordercode} and ccode = #{ccode} and cinvcode=#{cinvcode} and autoid=#{autoid}")
    BigDecimal getOrderIncome_outer(@Param("cordercode") String cordercode, @Param("idemandseq") String idemandseq, @Param("cinvcode") String cinvcode, @Param("ccode") String ccode, @Param("irowno") String irowno, @Param("autoid") String autoid);

    /**
     * @return
     */
    @Select("select  sum(fnatmoney) as income from ex_invoice as a inner join ex_invoicedetail as b on a.id = b.id  where autoid=#{autoid} and idemandseq= #{idemandseq}  and  cordercode =#{cordercode} and ccode = #{ccode} and cinvcode=#{cinvcode} and irowno is null")
    BigDecimal getOrderIncome_outer1(@Param("cordercode") String cordercode, @Param("idemandseq") String idemandseq, @Param("cinvcode") String cinvcode, @Param("ccode") String ccode, @Param("autoid") String autoid);

    /**
     * @return
     */
    @Select("select iNatSum from SaleBillVouchs  where autoid=#{autoid} and  cordercode = #{cordercode} AND iorderrowno =#{iorderrowno} AND cinvcode=#{cinvcode} and irowno=#{irowno} and sbvid=#{sbvid}")
    BigDecimal getOrderIncome_inner(@Param("cordercode") String cordercode, @Param("iorderrowno") String iorderrowno, @Param("cinvcode") String cinvcode, @Param("irowno") String irowno, @Param("sbvid") String sbvid, @Param("autoid") String autoid);

    @Select("select iNatSum from SaleBillVouchs  where autoid=#{autoid} and cordercode = #{cordercode} AND iorderrowno =#{iorderrowno} AND cinvcode=#{cinvcode}  and sbvid=#{sbvid} and irowno is null")
    BigDecimal getOrderIncome_inner1(@Param("cordercode") String cordercode, @Param("iorderrowno") String iorderrowno, @Param("cinvcode") String cinvcode, @Param("sbvid") String sbvid, @Param("autoid") String autoid);


    @Select("select sum(iQuantity) as qn  from SaleBillVouchs where cSoCode = #{cordercode} and  idemandseq =#{irowno}")
    BigDecimal getiQuantity(@Param("cordercode") String cordercode, @Param("irowno") String irowno);

    /**
     * @return
     */
    BigDecimal getOrderIncome_Co10(@Param("cordercode") String cordercode, @Param("cinvcode") String cinvcode, @Param("csbvcode") String csbvcode, @Param("irowno") String irowno, @Param("iorderrowno") String iorderrowno, @Param("autoid") String autoid);

    BigDecimal getOrderIncome_Co10_1(@Param("cordercode") String cordercode, @Param("cinvcode") String cinvcode, @Param("csbvcode") String csbvcode, @Param("iorderrowno") String iorderrowno, @Param("autoid") String autoid);


    @Select("select cMOCode as orderDetailCode,iMOSubSN as orderNumber,iYearPeriod as dmonth from ca_rptsumList where SoCode = #{orderCode} and SoSeq=#{orderNumber} and cDepName=#{cDepName} ")
    List<OrderCost> getOtherManufactureOrdersInfo(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("cDepName") String cDepName);


    @Select("select top 1 MoCode as orderDetailCode ,SortSeq as orderNumber,substring(CONVERT(varchar(100),CloseDate,112),0,7) as dmonth  from mom_orderdetail  as a \n" +
            "inner join  mom_order as b on a.MoId =b.MoId \n" +
            "WHERE  SoCode=#{orderCode}  and SoSeq = #{orderNumber} and InvCode=#{InvCode}  and MDeptCode=#{deptCode}")
    OrderCost getManufactureOrdersInfo(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("InvCode") String InvCode, @Param("deptCode") String deptCode);

    @Select("select top 1 MoCode as orderDetailCode ,SortSeq as orderNumber,substring(CONVERT(varchar(100),CloseDate,112),0,7) as dmonth  from mom_orderdetail  as a \n" +
            "inner join  mom_order as b on a.MoId =b.MoId \n" +
            "WHERE  SoCode=#{orderCode}  and  InvCode=#{InvCode}  and MDeptCode=#{deptCode}")
    OrderCost getManufactureOrdersInfo0001(@Param("orderCode") String orderCode, @Param("InvCode") String InvCode, @Param("deptCode") String deptCode);

    @Select("select bomid as invoiceId, InvCode as cinvcode, MoCode as orderDetailCode ,SortSeq as orderNumber,substring(CONVERT(varchar(100),CloseDate,112),0,7) as dmonth  from mom_orderdetail  as a \n" +
            "inner join  mom_order as b on a.MoId =b.MoId \n" +
            "WHERE  SoCode=#{orderCode}  and SoSeq = #{orderNumber} and MDeptCode=#{deptCode}")
    List<OrderCost> getManufactureOrdersInfos(@Param("orderCode") String orderCode, @Param("orderNumber") String orderNumber, @Param("deptCode") String deptCode);

    @Select("select bomid as invoiceId, InvCode as cinvcode, MoCode as orderDetailCode ,SortSeq as orderNumber,substring(CONVERT(varchar(100),CloseDate,112),0,7) as dmonth  from mom_orderdetail  as a \n" +
            "inner join  mom_order as b on a.MoId =b.MoId \n" +
            "WHERE  SoCode=#{orderCode}   and MDeptCode=#{deptCode}")
    List<OrderCost> getManufactureOrdersInfos0001(@Param("orderCode") String orderCode, @Param("deptCode") String deptCode);


}
