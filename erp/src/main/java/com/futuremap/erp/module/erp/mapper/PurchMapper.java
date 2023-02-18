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
package com.futuremap.erp.module.erp.mapper;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.erp.module.orderprocess.entity.PurchOrder;
import com.futuremap.erp.module.orderprocess.entity.RecordinList;
import com.futuremap.erp.module.orderprocess.entity.RecordoutList;
import com.futuremap.erp.module.orderprocess.entity.SaleoutList;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @title PurchorderMapper
 * @description TODO
 * @author Administrator
 * @date 2021/5/25 18:00
 */
@Mapper
//@DS("erp002")
public interface PurchMapper {

   /**
    * 根据销售订单获取采购订单信息
    * @return
    * @param csocode
    */
   @Select("select \n" +
           "  cpoid ,zpurpotail.id autoid,zpurpotail.Csocode ,zpurpotail.Irowno ,zpurpoheader.cvenabbname cven_abbname,zpurpoheader.cvenname cven_name,zpurpotail.cinvcode ,zpurpotail.cinvname ,zpurpotail.cinvstd ,zpurpoheader.dpodate ,zpurpotail.cinvm_unit,zpurpotail.iquantity,zpurpoheader.cexch_name,zpurpotail.itaxprice  ,zpurpotail.isum ,zpurpotail.inatsum ,zpurpotail.Darrivedate darrive_date,zpurpotail.ireceivedqty,\n" +
           "  pu_arr.iquantity puarr_iquantity,pu_arrrefuse.Frefusequantity refuse_iquantity\n" +
           "FROM\n" +
           "zpurpoheader WITH (nolock)\n" +
           "INNER JOIN zpurpotail WITH (nolock) ON zpurpoheader.poid = zpurpotail.poid\n" +
           "left join\n" +
           "(\n" +
           "select\n" +
           "csocode,irowno,iquantity \n" +
           "FROM\n" +
           "pu_arrhead\n" +
           "INNER  JOIN \n" +
           "pu_arrbody  on pu_arrbody.id =pu_arrhead.id  \n" +
           "WHERE\n" +
           "1 = 1 \n" +
           "and ibilltype=1\n" +
           "and Csocode =#{csocode}\n" +
           ") pu_arr on pu_arr.csocode = zpurpotail.Csocode and pu_arr.irowno = zpurpotail.irowno\n" +
           " left join\n" +
           " (\n" +
           "   select \n" +
           "  csocode,irowno,Frefusequantity\n" +
           "FROM\n" +
           "pu_arrhead\n" +
           "  INNER  JOIN \n" +
           " pu_arrbody  on pu_arrbody.id =pu_arrhead.id  \n" +
           "WHERE\n" +
           "1 = 1 \n" +
           "and Frefusequantity is not null and Frefusequantity <>0\n" +
           "and ibilltype =2 \n" +
           "and Csocode =#{csocode}\n" +
           " ) pu_arrrefuse on pu_arrrefuse.csocode = zpurpotail.Csocode and pu_arrrefuse.irowno = zpurpotail.irowno\n" +
           "WHERE\n" +
           " zpurpotail.Csocode =#{csocode}\n" +
           "order by zpurpotail.csocode ,zpurpotail.irowno ;")
   List<PurchOrder>  getPurchOrderInfoByCsocode(String csocode);
   /**
    * 原来mingo的取数表和erp对不上，需要使用新的查询语句
    * 根据销售订单获取采购订单信息
    * @return
    * @param
    */
//   @Select("select \n" +
//           "  cpoid ,zpurpotail.id autoid,zpurpotail.Csocode ,zpurpotail.Irowno ,zpurpoheader.cvenabbname cven_abbname,zpurpoheader.cvenname cven_name,zpurpotail.cinvcode ,zpurpotail.cinvname ,zpurpotail.cinvstd ,zpurpoheader.dpodate ,zpurpotail.cinvm_unit,zpurpotail.iquantity,zpurpoheader.cexch_name,zpurpotail.itaxprice  ,zpurpotail.isum ,zpurpotail.inatsum ,zpurpotail.Darrivedate darrive_date,zpurpotail.ireceivedqty,\n" +
//           "  pu_arr.iquantity puarr_iquantity,pu_arrrefuse.Frefusequantity refuse_iquantity\n" +
//           "FROM\n" +
//           "zpurpoheader WITH (nolock)\n" +
//           "INNER JOIN zpurpotail WITH (nolock) ON zpurpoheader.poid = zpurpotail.poid\n" +
//           "left join\n" +
//           "(\n" +
//           "select\n" +
//           "csocode,irowno,iquantity \n" +
//           "FROM\n" +
//           "pu_arrhead\n" +
//           "INNER  JOIN \n" +
//           "pu_arrbody  on pu_arrbody.id =pu_arrhead.id  \n" +
//           "WHERE\n" +
//           "1 = 1 \n" +
//           "and ibilltype=1\n" +
//           ") pu_arr on pu_arr.csocode = zpurpotail.Csocode and pu_arr.irowno = zpurpotail.irowno\n" +
//           " left join\n" +
//           " (\n" +
//           "   select \n" +
//           "  csocode,irowno,Frefusequantity\n" +
//           "FROM\n" +
//           "pu_arrhead\n" +
//           "  INNER  JOIN \n" +
//           " pu_arrbody  on pu_arrbody.id =pu_arrhead.id  \n" +
//           "WHERE\n" +
//           "1 = 1 \n" +
//           "and Frefusequantity is not null and Frefusequantity <>0\n" +
//           "and ibilltype =2 \n" +
//           " ) pu_arrrefuse on pu_arrrefuse.csocode = zpurpotail.Csocode and pu_arrrefuse.irowno = zpurpotail.irowno\n" +
//           "WHERE\n" +
//           "zpurpotail.Csocode is not null\n" +
//           "and zpurpoheader.cmaketime   >=#{startDate}\n" +
//           "and zpurpoheader.cmaketime   < #{endDate}\n" +
//           "order by zpurpotail.csocode ,zpurpotail.irowno ;")
   @Select("select pu_arr.cpersoncode,pu_arr.cpersonname,PO_Pomain.cpoid,PO_Pomain.dpodate,PO_Pomain.cexch_name,\n" +
           "PO_Podetails.id autoid,PO_Podetails.Csocode,PO_Podetails.Irowno,PO_Podetails.cinvcode,PO_Podetails.iquantity,PO_Podetails.itaxprice,\n" +
           "PO_Podetails.isum ,PO_Podetails.inatsum ,PO_Podetails.Darrivedate darrive_date,PO_Podetails.ireceivedqty,\n" +
           "v.cvenabbname cven_abbname,v.cvenname cven_name,\n" +
           "i.cinvname ,i.cinvstd,\n" +
           "u.cUnitName cinvm_unit,\n" +
           "pu_arr.iquantity puarr_iquantity,\n" +
           "pu_arrrefuse.Frefusequantity refuse_iquantity\n" +
           "FROM PO_Pomain WITH (nolock)\n" +
           "INNER JOIN PO_Podetails WITH (nolock) ON PO_Pomain.poid = PO_Podetails.poid \n" +
           "left join(select cInvCode,cInvName,cInvStd from Inventory) i on i.cInvCode = PO_Podetails.cInvCode\n" +
           "left join(select cUnitName,cUnitCode from UnitStructure) u on u.cUnitCode = PO_Podetails.cUnitID \n" +
           "--(ibilltype  0到货单,1退货单,2拒收单)\n" +
           "left join(select cpersoncode,cpersonname,csocode,irowno,iquantity FROM pu_arrhead INNER  JOIN pu_arrbody  on pu_arrbody.id =pu_arrhead.id WHERE 1 = 1 and ibilltype=1) pu_arr on pu_arr.csocode = PO_Podetails.Csocode and pu_arr.irowno = PO_Podetails.irowno\n" +
           "left join(select csocode,irowno,Frefusequantity FROM pu_arrhead INNER  JOIN pu_arrbody  on pu_arrbody.id =pu_arrhead.id WHERE 1 = 1 and Frefusequantity is not null and Frefusequantity <>0 and ibilltype =2) pu_arrrefuse on pu_arrrefuse.csocode = PO_Podetails.Csocode and pu_arrrefuse.irowno = PO_Podetails.irowno\n" +
           "left join(select cVenCode,cVenName,cVenAbbName,cVCCode from Vendor) v on v.cVenCode = PO_Pomain.cVenCode\n" +
           "WHERE PO_Podetails.Csocode is not null\n" +
           "--and zpurpoheader.cmaketime   >='2021-04-01' and zpurpoheader.cmaketime   < '2021-05-1'\n" +
           "and PO_Pomain.dpodate   >=#{startDate} and PO_Pomain.dpodate   < #{endDate}\n" +
           "order by PO_Podetails.csocode ,PO_Podetails.irowno")
   List<PurchOrder>  getPurchOrderInfoByDay(String startDate,String endDate);

   @Select(
           "select\n" +
           "    om_mobody.moid cpoid,modetailsid  autoid,csocode ,irowno , cvenabbname cven_abbname,cvenname cven_name,cinvcode ,cinvname ,cinvstd ,ddate dpodate,cinvm_unit,iquantity,cexch_name,itaxprice  ,isum ,inatsum ,Darrivedate darrive_date,ireceivedqty\n" +
           "FROM\n" +
           "om_mohead WITH (nolock)\n" +
           "INNER JOIN om_mobody WITH (nolock) ON om_mohead.moid = om_mobody.moid\n" +
           "WHERE\n" +
           "Csocode =#{csocode}\n" +
           "AND iProductType = 0\n" +
           "ORDER BY\n" +
           "irowno ASC,\n" +
           "cvenabbname ASC,\n" +
           "cmaker ASC,\n" +
           "dstartdate ASC")
   List<PurchOrder>  getOutsourcePurchOrderInfoByCsocode(String csocode);

   /**
    * 委外采购单本质上就是委外订单
    * @param startDate
    * @param endDate
    * @return
    */
   @Select(
           "select\n" +
                   "    om_mohead.cpersoncode,om_mohead.cpersonname,om_mohead.ccode cpoid,modetailsid  autoid,csocode ,irowno , cvenabbname cven_abbname,cvenname cven_name,cinvcode ,cinvname ,cinvstd ,ddate dpodate,cinvm_unit,iquantity,cexch_name,itaxprice  ,isum ,inatsum ,Darrivedate darrive_date,ireceivedqty\n" +
                   "FROM\n" +
                   "om_mohead WITH (nolock)\n" +
                   "INNER JOIN om_mobody WITH (nolock) ON om_mohead.moid = om_mobody.moid\n" +
                   "WHERE\n" +
                   " dcreatetime   >=#{startDate}\n" +
                   "and dcreatetime   < #{endDate}\n" +
                   "AND om_mobody.Csocode is not null\n" +
                   "AND iProductType = 0\n" +
                   "ORDER BY\n" +
                   "irowno ASC,\n" +
                   "cvenabbname ASC,\n" +
                   "cmaker ASC,\n" +
                   "dstartdate ASC")
//   @Select("select select ccode cpoid,ddate dpodate,cexch_name,\n" +
//           "av.id autoid,av.csocode,av.Irowno,av.cinvcode,fQuantity iquantity,itaxprice,\n" +
//           "--isum ,inatsum ,\n" +
//           "Darrivedate darrive_date,ireceivedqty,\n" +
//           "v.cvenabbname cven_abbname,v.cvenname cven_name,\n" +
//           "inv.cinvname ,inv.cinvstd,\n" +
//           "u.cUnitName cinvm_unit,\n" +
//           "pu_arr.iquantity puarr_iquantity,\n" +
//           "pu_arrrefuse.Frefusequantity refuse_iquantity " +
//           "from PU_AppVouch a\n" +
//           "right join PU_AppVouchs av on av.ID = a.ID\n" +
//           "left join(select cInvCode,cInvName,cInvStd from Inventory) inv on inv.cInvCode = av.cinvcode\n" +
//           "left join(select cVenCode,cVenName,cVenAbbName,cVCCode from Vendor) v on v.cVenCode = av.cVenCode\n" +
//           "left join(select cUnitName,cUnitCode from UnitStructure) u on u.cUnitCode = av.cUnitID \n" +
//           "left join(select csocode,irowno,iquantity FROM pu_arrhead INNER  JOIN pu_arrbody  on pu_arrbody.id =pu_arrhead.id WHERE 1 = 1 and ibilltype=1) pu_arr on pu_arr.csocode = av.Csocode and pu_arr.irowno = av.irowno\n" +
//           "left join(select csocode,irowno,Frefusequantity FROM pu_arrhead INNER  JOIN pu_arrbody  on pu_arrbody.id =pu_arrhead.id WHERE 1 = 1 and Frefusequantity is not null and Frefusequantity <>0 and ibilltype =2) pu_arrrefuse on pu_arrrefuse.csocode = av.Csocode and pu_arrrefuse.irowno = av.irowno\n" +
//           "WHERE\n" +
//           " ddate   >=#{startDate}\n" +
//           "and ddate   < #{endDate}")
   List<PurchOrder> getOutsourcePurchOrderInfoByDay(String startDate, String endDate);

   /**
    * @param startDate
    * @param endDate
    * @return
    */
   @Select("select \n" +
           "  dnmaketime ,dnmodifytime ,ddate,csocode ,isoseq ,ccode,cinvcode,cordercode,cinvname,cinvstd,cinvm_unit,iquantity \n" +
           "FROM\n" +
           "zpurrkdlist \n" +
           "WHERE" +
           "(dnmaketime >= #{startDate})\n" +
           "AND (dnmaketime < #{endDate})\n"
           )
   List<JSONObject>  getPurchOrderRecordeInByDay(String startDate, String endDate);


   /**
    *
    * @param csocode
    * @return
    */
   @Select("select \n" +
           "   csocode,isoseq irowno,autoid ,ccode ,cinvcode,cinvname,cinvstd,cinvm_unit ,cdepcode ,cdepname ,ccode ,ddate,iquantity \n" +
           "FROM\n" +
           "RecordOutList WITH (nolock)\n" +
           "WHERE\n" +
           " cBusType <> N'假退料'\n" +
           "AND isnull(bIsSTQc, 0) <> 1\n" +
           "AND ISNULL(bPuFirst, 0) <> 1\n" +
           "AND ISNULL(bIAFirst, 0) <> 1\n" +
           "AND ISNULL(bOMFirst, 0) <> 1\n" +
           "and csocode =#{csocode}\n" +
           "order  by  csocode,isoseq,cdepcode;")
   List<RecordoutList>  getWorkshopOutByCsocode(String csocode);

   /**
    * 不能加上AND Csocode is not null这个条件，这样凭空就会少数据，由于使用的是视图查询，在需求跟踪号为空的情况下表里任然能够取得相应部门的
    * 领料时间和数量。这样才能同原erp中的查询数据一致。
    * 时间筛选条件不能使用dnmaketime时间，要使用出库时间ddate
    * @param startDate
    * @param endDate
    * @return
    */
   @Select("select \n" +
           "   csocode,isoseq irowno,autoid ,ccode ,cinvcode,cinvname,cinvstd,cinvm_unit ,cdepcode ,cdepname ,ccode ,ddate,iquantity \n" +
           "FROM\n" +
           "RecordOutList WITH (nolock)\n" +
           "WHERE\n" +
           " cBusType <> N'假退料'\n" +
           //"AND Csocode is not null\n" +
           "AND isnull(bIsSTQc, 0) <> 1\n" +
           "AND ISNULL(bPuFirst, 0) <> 1\n" +
           "AND ISNULL(bIAFirst, 0) <> 1\n" +
           "AND ISNULL(bOMFirst, 0) <> 1\n" +
           "and ddate    >=#{startDate}\n" +
           "and ddate    < #{endDate}\n" +
           "order  by  csocode,isoseq,cdepcode;")
   List<RecordoutList>  getWorkshopOutByDay(String startDate,String endDate);

//   @Select("select \n" +
//           "   csocode,isoseq irowno,autoid,ccode ,cinvcode,cinvname,cinvstd,cinvm_unit ,cdepcode ,cdepname ,ccode ,ddate,iquantity \n" +
//           "FROM\n" +
//           "RecordOutList WITH (nolock)\n" +
//           "WHERE\n" +
//           " cBusType <> N'假退料'\n" +
//           "AND isnull(bIsSTQc, 0) <> 1\n" +
//           "AND ISNULL(bPuFirst, 0) <> 1\n" +
//           "AND ISNULL(bIAFirst, 0) <> 1\n" +
//           "AND ISNULL(bOMFirst, 0) <> 1\n" +
//           "and csocode =#{csocode}\n" +
//           "AND cdepcode='020301'\n" +
//           "order  by  csocode,isoseq,cdepcode;")
//   List<RecordoutList>  getNeedleWorkshopByCsocode(String csocode);
//
//
//   @Select("select \n" +
//           "   csocode,isoseq irowno,autoid,ccode ,cinvcode,cinvname,cinvstd,cinvm_unit ,cdepcode ,cdepname ,ccode ,ddate,iquantity \n" +
//           "FROM\n" +
//           "\tRecordOutList WITH (nolock)\n" +
//           "WHERE\n" +
//           "cBusType <> N'假退料'\n" +
//           "AND isnull(bIsSTQc, 0) <> 1\n" +
//           "AND ISNULL(bPuFirst, 0) <> 1\n" +
//           "AND ISNULL(bIAFirst, 0) <> 1\n" +
//           "AND ISNULL(bOMFirst, 0) <> 1\n" +
//           "and csocode =#{csocode}\n" +
//           "AND cdepcode='020302'\n" +
//           "order  by  csocode,isoseq,cdepcode;")
//   List<RecordoutList>  getCuttingWorkshopByCsocode(String csocode);

//   @Select("select \n" +
//           "   csocode,isoseq irowno,autoid,ccode ,cinvcode,cinvname,cinvstd,cinvm_unit ,cdepcode ,cdepname ,ccode ,ddate,iquantity \n" +
//           "FROM\n" +
//           "\tRecordOutList WITH (nolock)\n" +
//           "WHERE\n" +
//           " cBusType <> N'假退料'\n" +
//           "AND isnull(bIsSTQc, 0) <> 1\n" +
//           "AND ISNULL(bPuFirst, 0) <> 1\n" +
//           "AND ISNULL(bIAFirst, 0) <> 1\n" +
//           "AND ISNULL(bOMFirst, 0) <> 1\n" +
//           "and csocode =#{csocode}\n" +
//           "AND cdepcode='020303'\n" +
//           "order  by  csocode,isoseq,cdepcode;")
//   List<RecordoutList>  getPackagingWorkshopByCsocode(String csocode);

   //产成品入库
   @Select("select \n" +
           "   csocode,isoseq irowno,autoid,ccode ,cinvcode,cinvname,cinvstd,cinvm_unit ,cdepcode ,cdepname,cmocode ,imoseq , ddate,Iquantity \n" +
           "FROM\n" +
           "RecordInList WITH (nolock)\n" +
           "WHERE\n" +
           " cbustype <> N'假退料'\n" +
           "AND cdepcode = '020303'\n" +
           "AND isnull(bIsSTQc, 0) <> 1\n" +
           "AND ISNULL(bPuFirst, 0) <> 1\n" +
           "AND ISNULL(bIAFirst, 0) <> 1\n" +
           "AND ISNULL(bOMFirst, 0) <> 1\n" +
           "and csocode =#{csocode}\n" +
           "order  by  csocode,isoseq;")
   List<RecordinList>  getFinishedProductsByCsocode(String csocode);



   //产成品入库

   /**
    * RecordInList表中按照原来的代码查询结果只有135条，原erp中查询出来的有1542条，差距巨大，原因是AND cdepcode = '020303'这个条件过滤掉
    * 大部分数据呢。020303这个代码是表示包装车间，也就是说只有经过包装车间入库的才算成品，其他车间的都是半成品
    * 从RecordInList视图查询的时候时间筛选条件不能使用dnmaketime时间，要使用入库时间ddate
    * AND Csocode is not null这个条件也需要去掉
    * @param startDate
    * @param endDate
    * @return
    */
   @Select("select \n" +
           "   csocode,isoseq irowno,autoid,ccode ,cinvcode,cinvname,cinvstd,cinvm_unit ,cdepcode ,cdepname,cmocode ,imoseq , ddate,Iquantity \n" +
           "FROM\n" +
           "RecordInList WITH (nolock)\n" +
           "WHERE\n" +
           " cbustype <> N'假退料'\n" +
           //"AND Csocode is not null\n" +
           "AND cdepcode = '020303'\n" +
           "AND isnull(bIsSTQc, 0) <> 1\n" +
           "AND ISNULL(bPuFirst, 0) <> 1\n" +
           "AND ISNULL(bIAFirst, 0) <> 1\n" +
           "AND ISNULL(bOMFirst, 0) <> 1\n" +
           "and ddate >=#{startDate}\n" +
           "and ddate < #{endDate}\n" +
           "order  by  csocode,isoseq;")
   List<RecordinList>  getFinishedProductsByDay(String startDate,String endDate);

   //委外到货入库
   @Select("SELECT\n" +
           "  csocode, irowno,autoid,ccode ,cinvcode,cinvname,cinvstd,cinvm_unit ,cdepcode ,cdepname, ddate,Iquantity\n" +
           "FROM\n" +
           "pu_ArrHead WITH (nolock)\n" +
           "INNER JOIN pu_ArrBody WITH (nolock) ON pu_ArrHead.id = pu_ArrBody.id\n" +
           "WHERE\n" +
           " csocode =#{csocode}" +
           "AND cbustype = N'委外加工'")
   List<RecordinList>  getOutSourceFinishedProductsByCsocode(String csocode);
   //委外到货入库 根据时间
   /**
    * 原来这里取数是错误的
    * 委外入库数据出入很大，从原erp的委外入库明细表中2月入库36条，而我们原来的代码只查出了4条。原来的代码是查询的委外到货明细而不是委外入库，
    * 所以明显不对。
    * 应该从zpurrkdlist视图获取数据，并去除cwhcode 为 10的委外半成品仓（and cwhcode != '10' ）
    * @param startDate
    * @param endDate
    * @return
    */
//   @Select("SELECT\n" +
//           "  csocode, irowno,autoid,ccode ,cinvcode,cinvname,cinvstd,cinvm_unit ,cdepcode ,cdepname, ddate,Iquantity\n" +
//           "FROM\n" +
//           "pu_ArrHead WITH (nolock)\n" +
//           "INNER JOIN pu_ArrBody WITH (nolock) ON pu_ArrHead.id = pu_ArrBody.id\n" +
//           "WHERE\n" +
//           " cmaketime   >=#{startDate}\n" +
//           "and cmaketime   < #{endDate}\n" +
//           "AND pu_ArrBody.Csocode is not null\n" +
//           "AND cbustype = N'委外加工'")
   @Select("select autoid,ccode ,cinvcode,cinvname,cinvstd,cinvm_unit ,cdepcode ,cdepname, ddate,Iquantity\n" +
           "FROM zpurrkdlist WITH (nolock)\n" +
           "WHERE\n" +
           "ddate   >=#{startDate}\n" +
           "and ddate   < #{endDate}\n" +
           "and cbustype = N'委外加工'\n" +
           "and cwhcode != '10'")
   List<RecordinList>  getOutSourceFinishedProductsByDay(String startDate,String endDate);



   @Select("select  OM_MODetails.csocode ,OM_MODetails.irowno,id autoid,ccode,IA_Subsidiary.cInvCode,dvouDate ddate,iAInQuantity Iquantity \n" +
           "   from  \n" +
           "   IA_Subsidiary \n" +
           "   left join OM_MOMain on IA_Subsidiary.cOrderCode=ccode\n" +
           "   inner join  OM_MODetails  on OM_MOMain.MOID=OM_MODetails.MOID and OM_MODetails.cInvCode=IA_Subsidiary.cInvCode\n" +
           "   where  \n" +
           "   dCreateDate>=#{startDate} and dCreateDate<#{endDate}" +
           "   AND cvoutype='01' \n" +
           "   AND IA_Subsidiary.cBusType = N'委外加工'")
   List<RecordinList>  getStockFinishedProductsByDay(String startDate, String endDate);





   //内销销货出柜
   @Select("select \n" +
           " Sales_FHD_W.csocode ,Sales_FHD_W.idemandseq irowno,autoid,cdlcode ccode,cinvcode,cinvname,cinvstd,cinvm_unit,CONVERT(datetime, ddate)  ddate ,iquantity,foutquantity ,itaxunitprice ,isum ,inatsum \n" +
           "FROM\n" +
           "Sales_FHD_T\n" +
           "INNER JOIN Sales_FHD_W ON Sales_FHD_T.dlid = Sales_FHD_W.dlid\n" +
           "where\n" +
           " Sales_FHD_W.csocode =#{csocode}")
   List<SaleoutList>  getProductDeliveryByCsocode(String csocode);

   /**
    * 使用ddate而不是dcreatesystime
    * @param startDate
    * @param endDate
    * @return
    */
   @Select("select \n" +
           " Sales_FHD_W.csocode ,Sales_FHD_W.idemandseq irowno,autoid,cdlcode ccode,cinvcode,cinvname,cinvstd,cinvm_unit,CONVERT(datetime, ddate)  ddate ,iquantity,foutquantity ,itaxunitprice ,isum ,inatsum \n" +
           "FROM\n" +
           "Sales_FHD_T\n" +
           "INNER JOIN Sales_FHD_W ON Sales_FHD_T.dlid = Sales_FHD_W.dlid\n" +
           "where\n" +
           " ddate   >=#{startDate}" +
           "and ddate   <#{endDate}\n" +
           "AND Sales_FHD_W.Csocode is not null" )
   List<SaleoutList>  getProductDeliveryByDay(String startDate,String endDate);
   //出口销货出柜
   @Select("select \n" +
           "cordercode csocode ,irowno,autoid,ccode,cinvcode,cinvname,cinvstd,ccomunitname cinvm_unit,CONVERT(datetime, ddate)  ddate ,foutqty  iquantity, fnum foutquantity ,ftaxprice itaxunitprice,ftaxmoney isum ,fnattaxmoney inatsum  \n" +
           "FROM\n" +
           "v_ex_consignmentlist_foruser\n" +
           "WHERE" +
           " cordercode =#{csocode}")
   List<SaleoutList>  getExProductDeliveryByCsocode(String csocode);


   /**
    * 和原erp数据对不上，2021年4月原erp是189条，而我们的程序查出来是是215条，原因是时间判断的字段要使用ddate而不是dcreatesystime
    * @param startDate
    * @param endDate
    * @return
    */
   @Select("select \n" +
           "cordercode csocode ,irowno,autoid,ccode,cinvcode,cinvname,cinvstd,ccomunitname cinvm_unit,CONVERT(datetime, ddate)  ddate ,foutqty  iquantity, fnum foutquantity ,ftaxprice itaxunitprice,ftaxmoney isum ,fnattaxmoney inatsum  \n" +
           "FROM\n" +
           "v_ex_consignmentlist_foruser\n" +
           "WHERE" +
           " ddate >=#{startDate}" +
           "and ddate  <#{endDate}"+
           "AND cordercode is not null" )
   List<SaleoutList>  getExProductDeliveryByDay(String startDate,String endDate);

   @Select("DROP TABLE IF EXISTS purch_sale_bill_mapper;\n" +
           "CREATE TABLE purch_sale_bill_mapper AS " +
           "SELECT\n" +
           "p.`id`,\n" +
           "p.`company_code`,\n" +
           "p.`company_name`,\n" +
           "p.`csocode`,\n" +
           "p.`irowno`,\n" +
           "p.cordercode,\n" +
           "p.cinvcode,\n" +
           "p.`cvencode`,\n" +
           "p.`cvenname`,\n" +
           "p.`ddate`,\n" +
           "p.`bill_code`,\n" +
           "p.`cexch_name`,\n" +
           "p.bill_nat_money,\n" +
           "p.`bill_money`,\n" +
           "p.`payment_dvouch_date`,\n" +
           "p.`payment_receipt_code`,\n" +
           "IF\n" +
           "\t(\n" +
           "\t\tTIMESTAMPDIFF(\n" +
           "\t\t\tDAY,\n" +
           "\t\t\tDATE_FORMAT( p.ddate, '%Y-%m-%d %H:%i:%S' ),\n" +
           "\t\tDATE_FORMAT( NOW(), '%Y-%m-%d %H:%i:%S' )) > 90,\n" +
           "\t\tbill_nat_money - IFNULL( payment_nat_money, 0 ),\n" +
           "\t\t0 \n" +
           "\t) `payment_money`,\n" +
           "\t`payment_nat_money`,\n" +
           "\tbill_nat_money - IFNULL( payment_nat_money, 0 ) `no_payment_money`,\n" +
           "\tt.ccus_code,\n" +
           "\tt.ccus_name,\n" +
           "\tt.saleBillNatMoney,\n" +
           "\tt.saleBillMoney,\n" +
           "\tdvouch_date,\n" +
           "\tconlection_money,\n" +
           "\tconlection_nat_money,\n" +
           "\tno_conlection_money,\n" +
           "\tso.ddate saleOutDate,\n" +
           "IF\n" +
           "\t( oam.back_day != NULL, oam.back_day, 90 ) back_day,\n" +
           "IF\n" +
           "\t(\n" +
           "\t\tTIMESTAMPDIFF(\n" +
           "\t\t\tDAY,\n" +
           "\t\t\tDATE_FORMAT( NOW(), '%Y-%m-%d %H:%i:%S' ),\n" +
           "\t\tDATE_FORMAT( so.ddate, '%Y-%m-%d %H:%i:%S' )) >= back_day,\n" +
           "\t\t'RECEIVE_DELAY',\n" +
           "\tIF\n" +
           "\t\t(\n" +
           "\t\t\tTIMESTAMPDIFF(\n" +
           "\t\t\t\tDAY,\n" +
           "\t\t\t\tDATE_FORMAT( NOW(), '%Y-%m-%d %H:%i:%S' ),\n" +
           "\t\t\tDATE_FORMAT( so.ddate, '%Y-%m-%d %H:%i:%S' )) >= 30 \n" +
           "\t\t\tAND TIMESTAMPDIFF(\n" +
           "\t\t\t\tDAY,\n" +
           "\t\t\t\tDATE_FORMAT( NOW(), '%Y-%m-%d %H:%i:%S' ),\n" +
           "\t\t\tDATE_FORMAT( so.ddate, '%Y-%m-%d %H:%i:%S' )) < back_day,\n" +
           "\t\t\t'RECEIVE_WARNING',\n" +
           "\t\tIF\n" +
           "\t\t( 0 = no_conlection_money, 'RECEIVE_COMPLETED', 'RECEIVE_NORMAL' ))) warningName \n" +
           "FROM\n" +
           "\tpurch_bill p\n" +
           "\tLEFT JOIN (\n" +
           "\tSELECT\n" +
           "\t\tcsocode,\n" +
           "\t\tirowno,\n" +
           "\t\t`ccus_code`,\n" +
           "\t\t`ccus_name`,\n" +
           "\t\tsum( `bill_nat_money` ) saleBillNatMoney,\n" +
           "\t\tsum( `bill_money` ) saleBillMoney,\n" +
           "\t\tmax( `dvouch_date` ) dvouch_date,\n" +
           "\t\t`receipt_code`,\n" +
           "\t\tsum( `conlection_money` ) conlection_money,\n" +
           "\t\tsum( `conlection_nat_money` ) conlection_nat_money,\n" +
           "\t\tsum(\n" +
           "\t\tbill_nat_money - IFNULL( conlection_nat_money, 0 )) no_conlection_money \n" +
           "\tFROM\n" +
           "\t\tsale_bill s \n" +
           "\tGROUP BY\n" +
           "\t\ts.csocode,\n" +
           "\t\ts.irowno \n" +
           "\t) t ON p.csocode = t.csocode \n" +
           "\tAND p.irowno = t.irowno\n" +
           "\tLEFT JOIN ( SELECT oa.customer_code, back_day FROM order_amount_mgr oa ) oam ON oam.customer_code = t.ccus_code\n" +
           "\tLEFT JOIN ( SELECT sot.company_code, sot.csocode, sot.irowno, sot.ddate FROM saleout_list sot ) so ON so.csocode = p.csocode \n" +
           "\tAND so.irowno = p.irowno \n" +
           "ORDER BY\n" +
           "\tp.ddate DESC,\n" +
           "\tp.cordercode,\n" +
           "\tp.irowno" )
   void  createPurchBillToSaleBillMapper();
}
