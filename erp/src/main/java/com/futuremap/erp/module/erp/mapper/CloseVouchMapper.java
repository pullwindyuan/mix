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
import com.futuremap.erp.module.orderprocess.entity.CloseOrder;
import com.futuremap.erp.module.orderprocess.entity.PurchBill;
import com.futuremap.erp.module.orderprocess.entity.SaleBill;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Administrator
 * @title CloseVouchMapper
 * @description TODO
 * @date 2021/5/25 20:30
 */
@Mapper
//@DS("erp002")
public interface CloseVouchMapper {
    @Select("EXEC AR_mappingReport @Csocode=#{saleOrder}")
    List<CloseOrder>  getCloseListBySaleOrder(String saleOrder);

    @Select("EXEC AR_mappingReport @startDate=#{startDate},@endDate=#{endDate}")
    List<CloseOrder>  getCloseListByDay(String startDate,String endDate);

    //采购订单对应出口销售订单 根据销售订单号
    @Select("select \n" +
            "   cpoid ,zpurpotail.Csocode ,zpurpotail.Irowno ,zpurpoheader.cvenname ,dpodate ,zpurpoheader.cexch_name ap_cexch_name,imoney ,inatmoney ,\n" +
            "   v_ex_orderlist.ccusabbname ccus_abbname,v_ex_orderlist.ccusname  ccus_name,v_ex_orderlist.ffobmoney isum ,v_ex_orderlist.fnatfobmoney inatsum\n" +
            "FROM\n" +
            "zpurpoheader WITH (nolock)\n" +
            "INNER JOIN zpurpotail WITH (nolock) ON zpurpoheader.poid = zpurpotail.poid\n" +
            "INNER JOIN v_ex_orderlist on v_ex_orderlist.Ccode = zpurpotail.csocode AND v_ex_orderlist.irowno = zpurpotail.irowno\n"+
            "WHERE\n" +
            " zpurpotail.Csocode =#{saleOrder}")
    List<CloseOrder>  getExCloseListBySaleOrder(String saleOrder);
    //采购订单对应内销销售订单 根据销售订单号
    @Select("select \n" +
            "   cpoid ,zpurpotail.Csocode ,zpurpotail.Irowno ,zpurpoheader.cvenname ,dpodate ,zpurpoheader.cexch_name ap_cexch_name,zpurpotail.imoney ,zpurpotail.inatmoney ,\n" +
            "   SaleOrderQ.ccusabbname ccus_abbname,SaleOrderQ.ccusname ccus_name,SaleOrderSQ.isum ,SaleOrderSQ.inatsum\n" +
            "FROM\n" +
            "zpurpoheader WITH (nolock)\n" +
            "INNER JOIN zpurpotail WITH (nolock) ON zpurpoheader.poid = zpurpotail.poid\n" +
            "LEFT JOIN SaleOrderQ on SaleOrderQ.Csocode = zpurpotail.csocode\n" +
            "INNER JOIN SaleOrderSQ ON SaleOrderQ.id = SaleOrderSQ.id and SaleOrderSQ.irowno = zpurpotail.irowno\n" +
            "WHERE\n" +
            " zpurpotail.Csocode =#{saleOrder}")
    List<CloseOrder>  getInnerCloseListBySaleOrder(String saleOrder);

    //委外采购订单对应出口销售订单 根据销售订单号
    @Select("select\n" +
            "  om_mohead.ccode,modetailsid  autoid,csocode ,om_mobody.irowno , cvenabbname cven_abbname,om_mohead.cvenname cven_name,om_mobody.cinvcode ,om_mobody.cinvname ,om_mobody.cinvstd ,\n" +
            "   om_mohead.ddate dpodate,cinvm_unit,iquantity,om_mohead.cexch_name,itaxprice  ,isum ,inatsum ,Darrivedate darrive_date,ireceivedqty,\n" +
            "   v_ex_orderlist.ccusabbname ccus_abbname,v_ex_orderlist.ccusname  ccus_name,v_ex_orderlist.ffobmoney isum ,v_ex_orderlist.fnatfobmoney inatsum\n" +
            "FROM\n" +
            "om_mohead WITH (nolock)\n" +
            "INNER JOIN om_mobody WITH (nolock) ON om_mohead.moid = om_mobody.moid\n" +
            "INNER JOIN v_ex_orderlist on v_ex_orderlist.Ccode = om_mobody.csocode AND v_ex_orderlist.irowno = om_mobody.irowno\n" +
            "WHERE\n" +
            " om_mohead.ccode=#{saleOrder}\n"+
            "AND iProductType = 0\n" )
    List<CloseOrder>  getOutSourceExCloseListBySaleOrder(String saleOrder);
    //委外采购订单对应内销销售订单 根据销售订单号
    @Select("select\n" +
            "  om_mohead.ccode,modetailsid  autoid,om_mobody.csocode ,om_mobody.irowno , om_mohead.cvenabbname cven_abbname,om_mohead.cvenname cven_name,om_mobody.cinvcode ,om_mobody.cinvname ,om_mobody.cinvstd ,\n" +
            "  om_mohead.ddate dpodate,om_mobody.cinvm_unit,om_mobody.iquantity,om_mohead.cexch_name,itaxprice  ,om_mobody.isum ,om_mobody.inatsum ,Darrivedate darrive_date,ireceivedqty,\n" +
            "  SaleOrderQ.ccusabbname ccus_abbname,SaleOrderQ.ccusname ccus_name,SaleOrderSQ.isum ,SaleOrderSQ.inatsum\n" +
            "FROM\n" +
            "om_mohead WITH (nolock)\n" +
            "INNER JOIN om_mobody WITH (nolock) ON om_mohead.moid = om_mobody.moid\n" +
            "LEFT JOIN SaleOrderQ on SaleOrderQ.Csocode = om_mobody.csocode\n" +
            "INNER JOIN SaleOrderSQ ON SaleOrderQ.id = SaleOrderSQ.id and SaleOrderSQ.irowno = om_mobody.irowno\n" +
            "WHERE\n" +
            " om_mohead.ccode=#{saleOrder}\n" +
            "AND iProductType = 0" )
    List<CloseOrder>  getOutSourceInnerExCloseListBySaleOrder(String saleOrder);






    //采购订单对应出口销售订单 根据时间
    @Select("select \n" +
            "   cpoid ,zpurpotail.id autoid,zpurpotail.Csocode ,zpurpotail.Irowno ,zpurpoheader.cvenname ,dpodate ,zpurpotail.cinvcode,zpurpoheader.cexch_name ap_cexch_name,isum imoney ,inatsum inatmoney,\n" +
            "   v_ex_orderlist.ccusabbname ccus_abbname,v_ex_orderlist.ccusname  ccus_name,v_ex_orderlist.ffobmoney isum ,v_ex_orderlist.fnatfobmoney inatsum\n" +
            "FROM\n" +
            "zpurpoheader WITH (nolock)\n" +
            "INNER JOIN zpurpotail WITH (nolock) ON zpurpoheader.poid = zpurpotail.poid\n" +
            "INNER JOIN v_ex_orderlist on v_ex_orderlist.Ccode = zpurpotail.csocode AND v_ex_orderlist.irowno = zpurpotail.irowno\n"+
            "WHERE\n" +
            " zpurpoheader.cmaketime  >=#{startDate} and zpurpoheader.cmaketime  <#{endDate}")
    List<CloseOrder>  getExCloseListByDay(String startDate,String endDate);



    //采购订单对应内销销售订单 根据时间
    @Select("select \n" +
            "   cpoid ,zpurpotail.id autoid,zpurpotail.Csocode ,zpurpotail.Irowno ,zpurpoheader.cvenname ,dpodate ,zpurpotail.cinvcode,zpurpoheader.cexch_name ap_cexch_name,zpurpotail.isum imoney,zpurpotail.inatsum inatmoney,\n" +
            "   SaleOrderQ.ccusabbname ccus_abbname,SaleOrderQ.ccusname ccus_name,SaleOrderSQ.isum ,SaleOrderSQ.inatsum\n" +
            "FROM\n" +
            "zpurpoheader WITH (nolock)\n" +
            "INNER JOIN zpurpotail WITH (nolock) ON zpurpoheader.poid = zpurpotail.poid\n" +
            "LEFT JOIN SaleOrderQ on SaleOrderQ.Csocode = zpurpotail.csocode\n" +
            "INNER JOIN SaleOrderSQ ON SaleOrderQ.id = SaleOrderSQ.id and SaleOrderSQ.irowno = zpurpotail.irowno\n" +
            "WHERE\n" +
            " zpurpoheader.cmaketime  >=#{startDate} and zpurpoheader.cmaketime  <#{endDate}")
    List<CloseOrder>  getInnerCloseListByDay(String startDate,String endDate);



    //委外采购订单对应出口销售订单 根据时间
    @Select("select\n" +
            "  om_mohead.ccode,modetailsid  autoid,csocode ,om_mobody.irowno , cvenabbname cven_abbname,om_mohead.cvenname cven_name,om_mobody.cinvcode ,om_mobody.cinvname ,om_mobody.cinvstd ,\n" +
            "   om_mohead.ddate dpodate,om_mobody.cinvcode,cinvm_unit,iquantity,om_mohead.cexch_name,itaxprice  ,isum imoney,inatsum inatmoney,Darrivedate darrive_date,ireceivedqty,\n" +
            "   v_ex_orderlist.ccusabbname ccus_abbname,v_ex_orderlist.ccusname  ccus_name,v_ex_orderlist.ffobmoney isum ,v_ex_orderlist.fnatfobmoney inatsum\n" +
            "FROM\n" +
            "om_mohead WITH (nolock)\n" +
            "INNER JOIN om_mobody WITH (nolock) ON om_mohead.moid = om_mobody.moid\n" +
            "INNER JOIN v_ex_orderlist on v_ex_orderlist.Ccode = om_mobody.csocode AND v_ex_orderlist.irowno = om_mobody.irowno\n" +
            "WHERE\n" +
            " (om_mohead.dcreatetime  >= #{startDate})AND (om_mohead.dcreatetime  < #{endDate})\n" +
            "AND iProductType = 0\n" )
    List<CloseOrder>  getOutSourceExCloseListByDay(String startDate,String endDate);


    //委外采购订单对应内销销售订单  根据时间
    @Select("select\n" +
            "  om_mohead.ccode,modetailsid  autoid,om_mobody.csocode ,om_mobody.irowno , om_mohead.cvenabbname cven_abbname,om_mohead.cvenname cven_name,om_mobody.cinvcode ,om_mobody.cinvname ,om_mobody.cinvstd ,\n" +
            "  om_mohead.ddate dpodate,om_mobody.cinvcode,om_mobody.cinvm_unit,om_mobody.iquantity,om_mohead.cexch_name,itaxprice  ,om_mobody.isum imoney,om_mobody.inatsum inatmoney,Darrivedate darrive_date,ireceivedqty,\n" +
            "  SaleOrderQ.ccusabbname ccus_abbname,SaleOrderQ.ccusname ccus_name,SaleOrderSQ.isum ,SaleOrderSQ.inatsum\n" +
            "FROM\n" +
            "om_mohead WITH (nolock)\n" +
            "INNER JOIN om_mobody WITH (nolock) ON om_mohead.moid = om_mobody.moid\n" +
            "LEFT JOIN SaleOrderQ on SaleOrderQ.Csocode = om_mobody.csocode\n" +
            "INNER JOIN SaleOrderSQ ON SaleOrderQ.id = SaleOrderSQ.id and SaleOrderSQ.irowno = om_mobody.irowno\n" +
            "WHERE\n" +
            " (om_mohead.dcreatetime  >= #{startDate})AND (om_mohead.dcreatetime  < #{endDate})\n" +
            "AND iProductType = 0" )
    List<CloseOrder>  getOutSourceInnerExCloseListByDay(String startDate,String endDate);



//    @Select("select \n" +
//            "   dPBVDate,cordercode,cinvcode ,cdefine3 csocode ,\n" +
//            "  sum(iorisum) iorisum,sum(isum) isum,sum(ipbvquantity)  ipbvquantity\n" +
//            "FROM\n" +
//            "zpurBillHead WITH (nolock)\n" +
//            "INNER JOIN zpurbilltail WITH (nolock) ON zpurBillHead.pbvid = zpurbilltail.pbvid\n" +
//            "LEFT JOIN om_v_rds_postdegreeforsettle ON zpurbilltail.RdsId = om_v_rds_postdegreeforsettle.pautoid\n" +
//            "WHERE\n" +
//            "cordercode =#{purchOrderCode}" +
//            "AND cSource in ('委外','采购')\n"+
//            "group by" +
//            " cordercode,cinvcode , cdefine3, dPBVDate")
    @Select("select \n" +
            "   dPBVDate,cordercode,cinvcode ,cdefine3 csocode ,\n" +
            "  iorisum iorisum,isum isum,ipbvquantity  ipbvquantity\n" +
            "FROM\n" +
            "zpurBillHead WITH (nolock)\n" +
            "INNER JOIN zpurbilltail WITH (nolock) ON zpurBillHead.pbvid = zpurbilltail.pbvid\n" +
            "LEFT JOIN om_v_rds_postdegreeforsettle ON zpurbilltail.RdsId = om_v_rds_postdegreeforsettle.pautoid\n" +
            "WHERE\n" +
            "cordercode =#{purchOrderCode}" +
            "AND cSource in ('委外','采购')\n")
    List<JSONObject> getInvoicePaymentListByCode(String purchOrderCode);

    //采购付款处理
//    @Select("select \n" +
//            "   dPBVDate,cordercode,cinvcode ,cdefine3 csocode,iposid autoid,\n" +
//            "  sum(iorisum) isum,sum(isum) inatsum,sum(ipbvquantity)  ipbvquantity\n" +
//            "FROM\n" +
//            "zpurBillHead WITH (nolock)\n" +
//            "INNER JOIN zpurbilltail WITH (nolock) ON zpurBillHead.pbvid = zpurbilltail.pbvid\n" +
//            "LEFT JOIN om_v_rds_postdegreeforsettle ON zpurbilltail.RdsId = om_v_rds_postdegreeforsettle.pautoid\n" +
//            "WHERE\n" +
//            "(cmaketime>= #{startDate})AND (cmaketime< #{endDate})" +
//            "AND cSource in ('委外','采购')\n"+
//            "group by" +
//            " cordercode,cinvcode , cdefine3, dPBVDate,iposid")
    @Select("select \n" +
            "   cpbvcode billCode, dPBVDate ddate,cordercode ,zpurBillHead.cvencode,zpurBillHead.cvenname,zpurbilltail.cinvcode ,zpurpoheader.dpodate, csocode  ,irowno ,zpurbilltail.id autoid,\n" +
            "    zpurBillHead.cexch_name,zpurbilltail.iorisum billMoney, zpurbilltail.isum billNatMoney,  ipbvquantity\n" +
            "FROM\n" +
            "zpurBillHead \n" +
            "INNER JOIN zpurbilltail  ON zpurBillHead.pbvid = zpurbilltail.pbvid\n" +
            "LEFT JOIN om_v_rds_postdegreeforsettle ON zpurbilltail.RdsId = om_v_rds_postdegreeforsettle.pautoid\n" +
            "left join zpurpoheader on zpurpoheader.cpoid=cordercode \n" +
            "INNER JOIN zpurpotail  ON zpurpoheader.poid = zpurpotail.poid and zpurpotail.id = zpurbilltail.iposid\n"+
            "WHERE\n" +
            "(zpurBillHead.cmaketime>= #{startDate}) AND (zpurBillHead.cmaketime< #{endDate})\n" +
            "AND zpurBillHead.cSource in ('采购')\n")
    List<PurchBill> getInvoicePaymentListByDay(String startDate, String endDate);

    @Select("select \n" +
            "   cpbvcode billCode, dPBVDate ddate,cordercode ,zpurBillHead.cvencode,zpurBillHead.cvenname,zpurbilltail.cinvcode ,om_mohead.ddate dpodate, csocode  ,irowno ,zpurbilltail.id autoid,\n" +
            "    zpurBillHead.cexch_name,zpurbilltail.iorisum billMoney, zpurbilltail.isum billNatMoney,  ipbvquantity\n" +
            "FROM\n" +
            "zpurBillHead \n" +
            "INNER JOIN zpurbilltail  ON zpurBillHead.pbvid = zpurbilltail.pbvid\n" +
            "LEFT JOIN om_v_rds_postdegreeforsettle ON zpurbilltail.RdsId = om_v_rds_postdegreeforsettle.pautoid\n" +
            "left join  om_mohead  on om_mohead.ccode=cordercode\n" +
            "INNER JOIN om_mobody  ON om_mohead.moid = om_mobody.moid and om_mobody.modetailsid = iposid\n"+
            "WHERE\n" +
            "(zpurBillHead.cmaketime>= #{startDate}) AND (zpurBillHead.cmaketime< #{endDate})\n" +
            "AND zpurBillHead.cSource in ('委外')\n")
    List<PurchBill> getOutSourceInvoicePaymentListByDay(String startDate, String endDate);


//    @Select("select \n" +
//            "   dPBVDate,cordercode,cinvcode ,cdefine3 csocode,iposid autoid,\n" +
//            "  sum(iorisum) isum,sum(isum) inatsum,sum(ipbvquantity)  ipbvquantity\n" +
//            "FROM\n" +
//            "zpurBillHead WITH (nolock)\n" +
//            "INNER JOIN zpurbilltail WITH (nolock) ON zpurBillHead.pbvid = zpurbilltail.pbvid\n" +
//            "LEFT JOIN om_v_rds_postdegreeforsettle ON zpurbilltail.RdsId = om_v_rds_postdegreeforsettle.pautoid\n" +
//            "WHERE\n" +
//            " cpbvcode =#{cpbvcode}\n" +
////            "cpbvcode  = '12451066-1067'\n"+
//            "AND cSource in ('委外','采购')\n"+
//            "group by" +
//            " cordercode,cinvcode , cdefine3, dPBVDate,iposid")
    @Select("select \n" +
            " id autoid,cpbvcode billCode, dPBVDate ddate,cordercode,cinvcode ,cdefine3 csocode,iposid autoid," +
            "#{dvouchDate} ddate,#{receiptCode} receiptCode, #{companyCode} companyCode,\n" +
            "  iorisum isum,isum inatsum,ipbvquantity  ipbvquantity\n" +
            "FROM\n" +
            "zpurBillHead WITH (nolock)\n" +
            "INNER JOIN zpurbilltail WITH (nolock) ON zpurBillHead.pbvid = zpurbilltail.pbvid\n" +
//            "LEFT JOIN om_v_rds_postdegreeforsettle ON zpurbilltail.RdsId = om_v_rds_postdegreeforsettle.pautoid\n" +
            "WHERE\n" +
            " cpbvcode =#{cpbvcode}\n" +
//            "cpbvcode  = '12451066-1067'\n"+
            "AND cSource in ('委外','采购')\n"
           )
    List<JSONObject> getInvoicePaymentListByCpbvcode(String cpbvcode,String dvouchDate,String receiptCode,String companyCode);




    @Select("SELECT\n" +
            "max(dVouchDate) AS dVouchDate2,\n" +
            "max(dRegDate) AS dRegDate2,\n" +
            "cCoVouchID AS cVouchID2,\n" +
            "cVouchID AS cVouchID,\n" +
            "sum(iDAmount_f - iCAmount_f) AS iAmount_f2,\n" +
            "sum(iDAmount - iCAmount) AS iAmount2,\n" +
            "sum(iDAmount_s - iCAmount_s) AS iAmount_s2\n" +
            "FROM\n" +
            "Ap_DetailVend a WITH (nolock)\n" +
            "WHERE\n" +
            "(iflag <= 2 OR iflag = 5)\n" +
            "AND cCoVouchType <> cProcStyle\n" +
            "AND iFlag <> 1\n" +
            "AND cProcStyle <> N'9C'\n" +
            "AND cProcStyle <> N'9M'\n" +
            "AND cProcStyle <> N'TP'\n" +
            "AND (cProcStyle <> N'XJ' OR iFlag = 5)\n" +
            "AND (\n" +
            "(\n" +
            "cProcStyle = N'BZ'\n" +
            "AND (\n" +
            "(cSign = N'Z' AND iCAmount < 0)\n" +
            "OR (cSign = N'F' AND iCAmount > 0)\n" +
            ")\n" +
            ")\n" +
            "OR cProcStyle <> N'BZ'\n" +
            ")\n" +
            "AND cFlag = N'AP'\n" +
            "AND (a.dVouchDate >= #{startDate})\n" +
            "AND (a.dVouchDate <= #{endDate})\n" +
            "AND cCoVouchID<>cVouchID\n" +
//            "AND  cVouchID='0000003656'"+
            "GROUP BY\n" +
            "cDwCode,\n" +
            "cCancelno,\n" +
            "cFlag,\n" +
            "cCoVouchType,\n" +
            "cCoVouchID,\n" +
            "cVouchType,\n" +
            "cVouchID\n" +
            "HAVING\n" +
            "sum(iCAmount_f - iDAmount_f) <> 0\n" +
            "OR sum(iCAmount - iDAmount) <> 0")
    List<JSONObject> getApDetailListByDay(String startDate,String endDate);











    //出口销售回款处理

    /**
     * 使用ddate而不是dcreatesystime,制单时间不严谨，可能出错
     * @param startDate
     * @param endDate
     * @return
     */
    @Select("select \n" +
//            "  cdemandcode csocode ,irowno  ,cinvcode,ftaxmoney billMoney ,fnattaxmoney billNatMoney ,ddate\n" +
            "   cdemandcode csocode ,irowno  ,ccode billCode,ddate, cinvcode,cordercuscode ccusCode,cordercusname ccusName,cexch_name,ftaxmoney billMoney,fnattaxmoney billNatMoney\n" +
            "FROM\n" +
            "v_ex_invoicelist\n" +
            "WHERE\n" +
            "(ddate   >= #{startDate})\n" +
            "AND (ddate   < #{endDate})"+
            "AND cdemandcode is not null"
            )
    List<SaleBill> getExInvoiceListByDay(String startDate, String endDate);

//    @Select("select \n" +
//            "  cdemandcode csocode ,irowno  ,cinvcode,sum(ftaxmoney) isum ,sum(fnattaxmoney) inatsum ,#{dvouchDate} ddate\n" +
//            "FROM\n" +
//            "v_ex_invoicelist\n" +
//            "WHERE\n" +
//            "ccode=#{ccode} \n"+
//             "group by cdemandcode,irowno,cinvcode")
    @Select("select \n" +
            "  ccode billCode,cdemandcode csocode ,irowno  ,cinvcode,ftaxmoney isum ,fnattaxmoney inatsum ,#{dvouchDate} ddate,#{receiptCode} receiptCode\n" +
            "FROM\n" +
            "v_ex_invoicelist\n" +
            "WHERE\n" +
            "ccode=#{ccode} \n"
             )
    List<JSONObject> getExInvoiceListByCcode(String ccode,String dvouchDate,String receiptCode);

    /**
     * 使用ddate而不是dcreatesystime,制单时间不严谨，会出错，比如4月的数据用dcreatesystime作为时间标准查出来是95条，用ddate是155条
     * 而原erp查询出来也是155条，说明dcreatesystime不对
     * @param startDate
     * @param endDate
     * @return
     */
    @Select("select\n" +
//            "  cordercode csocode ,iorderrowno irowno,cinvcode,isum,inatsum ,ddate\n" +
            "  cordercode csocode ,iorderrowno   irowno,csbvcode   billCode,ddate,cinvcode,ccuscode ccusCode,ccusname ccusName,cexch_name,isum billMoney,inatsum billNatMoney\n" +
            "FROM\n" +
            "SaleBillVouchZT\n" +
            "INNER JOIN SaleBillVouchZW ON SaleBillVouchZT.sbvid = SaleBillVouchZW.sbvid\n" +
            "WHERE\n" +
            "(cVouchType = 26\n" +
            "OR cVouchType = 27)\n" +
            "AND( ddate    >= #{startDate}\n" +
            "AND ddate    < #{endDate})\n" +
            "AND cordercode is not null")
    List<SaleBill> getInnerInvoiceListByDay(String startDate,String endDate);


//    @Select("select\n" +
//            "  cordercode csocode ,iorderrowno irowno,cinvcode,sum(isum) isum,sum(inatsum) inatsum ,#{dvouchDate} ddate\n" +
//            "FROM\n" +
//            "SaleBillVouchZT\n" +
//            "INNER JOIN SaleBillVouchZW ON SaleBillVouchZT.sbvid = SaleBillVouchZW.sbvid\n" +
//            "WHERE\n" +
//            "(cVouchType = 26\n" +
//            "OR cVouchType = 27)\n" +
//            "and csbvcode =#{csbvcode}"+
//            "AND isnull(cchildcode, N'') = N''" +
//            "group by cordercode,iorderrowno,cinvcode")
    @Select("select\n" +
            "  csbvcode billCode,cordercode csocode ,iorderrowno irowno,cinvcode,isum isum,inatsum inatsum ,#{dvouchDate} ddate,#{receiptCode} receiptCode\n" +
            "FROM\n" +
            "SaleBillVouchZT\n" +
            "INNER JOIN SaleBillVouchZW ON SaleBillVouchZT.sbvid = SaleBillVouchZW.sbvid\n" +
            "WHERE\n" +
            "(cVouchType = 26\n" +
            "OR cVouchType = 27)\n" +
            "and csbvcode =#{csbvcode}"+
            "AND isnull(cchildcode, N'') = N''" )
    List<JSONObject> getInnerInvoiceListByCsbvcode(String csbvcode,String dvouchDate,String receiptCode);

   //应收款核销记录
    @Select("SELECT\n" +
            "max(dVouchDate) AS dVouchDate2,\n" +
            "max(dRegDate) AS dRegDate2,\n" +
            "cCoVouchID AS cVouchID2,\n" +
            "cVouchID AS cVouchID,\n" +
            "sum(iCAmount_f - iDAmount_f) AS iAmount_f2,\n" +
            "sum(iCAmount - iDAmount) AS iAmount2,\n" +
            "sum(iCAmount_s - iDAmount_s) AS iAmount_s2\n" +
            "FROM\n" +
            "Ar_DetailCust_V a WITH (nolock)\n" +
            "WHERE\n" +
            "(iflag <= 2 OR iflag = 5)\n" +
            "AND cCoVouchType <> cProcStyle\n" +
            "AND iFlag <> 1\n" +
            "AND cProcStyle <> N'9C'\n" +
            "AND cProcStyle <> N'9M'\n" +
            "AND cProcStyle <> N'TP'\n" +
            "AND (cProcStyle <> N'XJ' OR iFlag = 5)\n" +
            "AND (\n" +
            "(\n" +
            "cProcStyle = N'BZ'\n" +
            "AND (\n" +
            "(cSign = N'Z' AND iDAmount < 0)\n" +
            "OR (cSign = N'F' AND iDAmount > 0)\n" +
            ")\n" +
            ")\n" +
            "OR cProcStyle <> N'BZ'\n" +
            ")\n" +
            "AND (\n" +
            "cFlag = N'AR'\n" +
            "OR a.cBusType = N'代理进口'\n" +
            ")\n" +
            "AND dVouchDate >=#{startDate} and dVouchDate<#{endDate}\n" +
            "AND cCoVouchID<>cVouchID\n" +
            "GROUP BY\n" +
            "cDwCode,\n" +
            "cCancelno,\n" +
            "cFlag,\n" +
            "cCoVouchType,\n" +
            "cCoVouchID,\n" +
            "cVouchType,\n" +
            "cVouchID\n" +
            "HAVING\n" +
            "sum(iCAmount_f - iDAmount_f) <> 0\n" +
            "OR sum(iCAmount - iDAmount) <> 0\n")
    List<JSONObject> getArdetailListByDay(String startDate,String endDate);








}
