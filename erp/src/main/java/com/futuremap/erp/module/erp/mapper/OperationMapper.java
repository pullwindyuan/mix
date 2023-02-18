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
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Administrator
 * @title operationMapper
 * @description TODO
 * @date 2021/5/27 16:13
 */
@Mapper
//@DS("erp002")
public interface OperationMapper {
    /**
     * 出口销售订单汇总
     * @param startDate
     * @param endDate
     * @return
     * 注意：原来mingo的代码没有排除子公司，现在加上了，内销都排除了，外销也要排除才对
     * 在订单全过程表中也都是排除了的，所以必须保持一直的规则
     */
    @Select("select cexch_name ,SUM(fnatfobmoney) isum,SUM(ffobmoney) inatsum \n" +
            "from v_ex_orderlist \n" +
            "WHERE\n" +
            "1 = 1\n" +
            "AND ((ddate >= #{startDate})AND (ddate <#{endDate}))\n" +
            "and ccuscode  not  in( 'EASI001', '9999', 'W0001', 'HD154', 'HD151')\n" +
            "GROUP by\n" +
            "cexch_name;")
    List<JSONObject> getExOrderCountData(@Param("startDate")String startDate, @Param("endDate")String endDate);

    /**
     * 内销销售订单汇总
     * @param startDate
     * @param endDate
     * @return
     * --不能统计inatsum字段，否则会和原始数据不一致，需要统计inatmoney字段
     */
    @Select("<script>" +
            "select cexch_name,SUM(isum) isum,SUM(inatmoney) inatsum\n" +
            "from SaleOrderQ\n" +
            "INNER JOIN SaleOrderSQ ON SaleOrderQ.id = SaleOrderSQ.id\n" +
            "WHERE <![CDATA[dDate >= #{startDate} AND dDate < #{endDate}]]>\n" +
            "<if test='departCondition != null'>" +
            "and cdepcode ${departCondition}" +
            "</if> \n" +
            "AND cCusCode not in('HD154', 'W0001', 'HD151','EASI001','9999')\n" +
            "AND isnull(cchildcode, N'') = N''\n" +
            "AND isnull(cchildcode, N'') = N''\n" +
            "group by cexch_name\n" +
            "</script>")
    List<JSONObject> getSaleOrderCountData(@Param("startDate")String startDate,
                                           @Param("endDate")String endDate,
                                           @Param("departCondition")String departCondition);

    /**
     * 内销实际发货汇总
     * @param startDate
     * @param endDate
     * @return
     */
//    @Select(" select Sales_FHD_T.cexch_name,SUM(isum) isum,SUM(inatsum) inatsum\n" +
//            "           FROM\n" +
//            "           dispatchlist\n" +
//            "           LEFT JOIN \n" +
//            "           Sales_FHD_T on Sales_FHD_T.dlid = dispatchlist.dlid\n" +
//            "           INNER JOIN Sales_FHD_W ON Sales_FHD_T.dlid = Sales_FHD_W.dlid \n" +
//            "            WHERE\n" +
//            "             isnull(dispatchlist.bReturnFlag, 0) = 0\n" +
//            "            AND (\n" +
//            "            (Sales_FHD_T.dDate >=  #{startDate})\n" +
//            "            AND (Sales_FHD_T.dDate < #{endDate})\n" +
//            "            )\n" +
//            "            AND  Sales_FHD_T.cCusCode not in('HD154', 'W0001', 'HD151','EASI001','9999')\n" +
//            "            group by Sales_FHD_T.cexch_name;")
    @Select("<script>" +
            "select SUM(isum) isum,SUM(inatsum) inatsum from DispatchLists a\n" +
            "inner join DispatchList b on a.DLID=b.DLID\n" +
            "where  <![CDATA[(b.dDate>=#{startDate} and  b.dDate<#{endDate})]]> " +
            "<if test='departCondition != null'>" +
            "and b.cdepcode ${departCondition}" +
            "</if> \n" +
            "and b.cCusCode not in( 'EASI001', '9999', 'W0001', 'HD154', 'HD151') " +
            "and bReturnFlag=0 " +
            "</script>")
    List<JSONObject> getSaleFhdCountData(@Param("startDate")String startDate,
                                         @Param("endDate")String endDate,
                                           @Param("departCondition")String departCondition);

    /**
     * 出口实际发货汇总
     * @param startDate
     * @param endDate
     * @return
     * 注意：汇率不能直接取记录中的数据，而是要到外币汇率配置表中取到对应时间的起始汇率才行，不然即不能和原erp数据对上，也不符合实际财务操作
     * 要使用iquantity*ftaxprice*nflat而不是iquantity*fnattaxprice来统计本币总额
     * 后面stone找到了新的表可以直接取到计算好的结果
     */
//    @Select("select\n" +
//            "   cexch_code,SUM(iquantity*ftaxprice) isum,SUM(iquantity*ftaxprice*nflat) inatsum\n" +
//            "FROM\n" +
//            "KCSaleOutList WITH (nolock)" +
//            "INNER join\n" +
//            "  v_ex_orderlist on v_ex_orderlist.ccode = kcsaleoutlist.csocode and  v_ex_orderlist.irowno= kcsaleoutlist.iorderseq\n" +
//            "inner join exch on exch.cexch_name = v_ex_orderlist.cexch_name and itype = 2 and iYear = ${year} and iperiod = ${month}\n" +
//            "WHERE\n" +
//            "kcsaleoutlist.DDATE >= #{startDate}\n" +
//            "AND kcsaleoutlist.DDATE < #{endDate}\n" +
//            "AND kcsaleoutlist.cVouchType = N'32'\n" +
//            "AND isnull(bIsSTQc, 0) <> 1\n" +
//            "AND ISNULL(bPuFirst, 0) <> 1\n" +
//            "AND ISNULL(bIAFirst, 0) <> 1\n" +
//            "AND ISNULL(bOMFirst, 0) <> 1\n" +
//            "AND 1 = 1\n" +
//            "AND cexch_code is not null\n" +
//            "group by\n" +
//            "cexch_code;")
    @Select("select cexch_name,sum(fnatsummoney) isum, sum(fsummoney) inatsum \n" +
            "from ex_consignment \n" +
            "where ddate>=#{startDate} and ddate<#{endDate}" +
            "group by cexch_name")
    List<JSONObject> getExOutCountData(@Param("startDate")String startDate, @Param("endDate")String endDate);

    /**
     * 内销实收退货额
     * @param startDate
     * @param endDate
     * @return
     */
    @Select("SELECT\n" +
            "   Sales_FHD_T.cexch_name,  SUM(isum) isum,SUM(inatsum) inatsum\n" +
            "FROM\n" +
            "dispatchlist\n" +
            "LEFT JOIN \n" +
            "Sales_FHD_T on Sales_FHD_T.dlid = dispatchlist.dlid\n" +
            "INNER JOIN Sales_FHD_W  ON Sales_FHD_T.dlid = Sales_FHD_W.dlid \n" +
            "WHERE\n" +
            "dispatchlist.DLID > 0\n" +
            "AND (\n" +
            "dispatchlist.cVouchType = N'05'\n" +
            "AND isnull(dispatchlist.bReturnFlag, 0) = 1\n" +
            "AND isnull(dispatchlist.bfirst, 0) = 0\n" +
            "AND (\n" +
            "(dispatchlist.dDate >= #{startDate})\n" +
            "AND (dispatchlist.dDate < #{endDate}))\n" +
            ")\n" +
            "group by Sales_FHD_T.cexch_name;")
    List<JSONObject> getDispatchCountData(@Param("startDate")String startDate, @Param("endDate")String endDate);

    /**
     * 调用存储过程（会创建临时表）
     * @param tempTable
     * @param startMonth
     */
    @Select("exec GL_Ledger @tblname=#{tempTable},@KmCode=${kmCode},@beginPeriod=#{startMonth},@endPeriod=#{startMonth}," +
            "@bVouch=0,@bequal=0,@sum=0,@bMJ=0,@swhere=N'',@sAuth=N'',@ReportID=#{reportId},@ReportType=0,@iUnite=0")
   void getLedgerReport(@Param("tempTable")String tempTable,@Param("startMonth")String startMonth,@Param("reportId")String reportId,String kmCode);


    /**
     * 删除临时表
     * "IF exists(select name from tempdb..sysobjects where name=#{tempTable} and xtype='U') \n" +
     */
    @Select("drop table tempdb..${tempTable}")
    void  dropTempTable(@Param("tempTable")String tempTable);


    /**
     * 产品销售收入  明细表主营业收入科目    贷方
     * 弃用原来的使用调用存储过程再查询临时表的方式
     *
     * 主表gl_accvouch 中部门字段没有值，研究发现在SaleBillVouch中有部门字段，通过SaleBillVouchs表与gl_accvouch 表通过
     * SaleBillVouchs = gl_accvouch关联，再通过SaleBillVouch.cSOCode=SaleBillVouchs.csocode关联就能得到部门字段，从而
     * 区分内外销
     * @param codeCondition
     * @param incomingCodeCondition
     * @param yearMonth
     * @return
     */
//   @Select("select cexch_name,SUM(mc_f) isum, SUM(mc) inatsum from tempdb..${tempTable}\n" +
//           "where iyear is not null and imonth is not null and iday is not null and cexch_name is not null\n" +
//           "AND (cdigest not like '%珠海智拓%'  and cdigest not like 'MEX%' and cdigest not like 'ESSEN%' " +
//           " and cdigest not like '%珠海横琴%' and cdigest not like '%珠海宜心%' )\n" +
//           "group by  cexch_name;")

    /**
     * 这里不能使用ccode_equal like '1131%' 这个条件，否者会和erp后台数据对不上
     * 这里还有一个需要注意的：兑汇损益需要排除，ERP里的统计结果没有排除兑汇损益项
     * 欧元不知道要不要区分开
     * 由于缺乏客户信息，所以后期就不考虑使用客户来区分内外销，直接使用账套区分，所以
     * 我们需要强烈建议宜兴需要对自己的业务规范做出一定的调整，流程不规范，再好的软件
     * 也白搭
     * select a.cexch_name,SUM(mc) as mc, SUM(mc_f) as mc_f
     * from gl_accvouch a
     * left join(select cDepCode,SaleBillVouchs.cClue,sum(SaleBillVouchs.iQuantity) iQuantity from SaleBillVouchs
     * right join(select cSOCode,cDepCode,SUM(iTaxRate) iTaxRate from SaleBillVouch
     * where cDepCode like '0105%'
     * group by cSOCode,cDepCode)sv
     * on sv.cSOCode=SaleBillVouchs.csocode group by cClue,cDepCode) aa
     * on aa.cClue = a.coutno_id
     * where a.ccode like '5101%' and  iYPeriod >= '202104' and iYPeriod <= '202104'
     * --AND (cdigest not like '%珠海智拓%'  and cdigest not like 'MEX%' and cdigest not like 'ESSEN%'
     * --and cdigest not like '%珠海横琴%' and cdigest not like '%珠海宜心%' )
     * group by a.cexch_name
     * @param departCondition
     * @param incomingCodeCondition
     * @param yearMonth
     * @return
     */
   @Select("<script>" +
           "select \n" +
           "a.cexch_name,SUM(mc) as mc, SUM(mc_f) as mc_f\n" +
           "from gl_accvouch a\n" +
           "\t\tleft join(select cDepCode,SaleBillVouchs.cClue,sum(SaleBillVouchs.iQuantity) iQuantity from SaleBillVouchs\n" +
           "\t\t\t\tright join(select cSOCode,cDepCode,SUM(iTaxRate) iTaxRate from SaleBillVouch \n" +
           "\t\t\t\tgroup by cSOCode,cDepCode)sv \n" +
           "\t\t\t\ton sv.cSOCode=SaleBillVouchs.csocode group by cClue,cDepCode) aa \n" +
           "\t\ton aa.cClue = a.coutno_id\n" +
           "where ccode ${incomingCode} and  iYPeriod = #{yearMonth} \n" +
           "\t\t\t\t<if test='departCondition != null' >and cDepCode ${departCondition}</if>\n" +
//           "and c.cCusCode not in( 'EASI001', '9999', 'W0001', 'HD154', 'HD151')" +
           "AND (cdigest not like '%珠海智拓%'  and cdigest not like 'MEX%' and cdigest not like 'ESSEN%' " +
           " and cdigest not like '%珠海横琴%' and cdigest not like '%珠海宜心%' )\n" +
           "group by a.cexch_name" +
           "</script>")
    List<JSONObject> getOperationRevenueCountData(
            @Param("departCondition")String departCondition,
            @Param("incomingCode")String incomingCodeCondition,
            @Param("yearMonth")String yearMonth
            );

    @Select("<script>" +
            "select \n" +
            "SUM(mc) as mc, SUM(mc_f) as mc_f\n" +
            "from gl_accvouch a\n" +
//            "inner join (\n" +
//            "select cVouchID,cPZid,cDwCode from Ar_Detail ad \n" +
//            "group by cDwCode,cVouchID,cPZid )d on a.coutid = (case when d.cVouchID = a.coutid then d.cVouchID \n" +
//            "else (d.cVouchID + ' ' +  CAST(a.inid AS VARCHAR(10))) end) \n" +
//            "and d.cPZid = a.coutno_id\n" +
//            "inner join(\n" +
//            "select cCusCode,cCusName,cCusDepart\n" +
//            "from Customer\n" +
//            "where 1=1\n" +
//            "<if test='departCondition != null' >and cCusDepart ${departCondition}</if> \n" +
//            ")c on c.cCusCode = cDwCode \n" +
            "where ccode ${incomingCode} and  iYPeriod = #{yearMonth} \n" +
            "AND (cdigest not like '%珠海智拓%'  and cdigest not like 'MEX%' and cdigest not like 'ESSEN%' " +
            " and cdigest not like '%珠海横琴%' and cdigest not like '%珠海宜心%' )\n" +
//            "and c.cCusCode not in( 'EASI001', '9999', 'W0001', 'HD154', 'HD151')" +
            "</script>")
    List<JSONObject> getOperationAllRevenueCountData(
            @Param("departCondition")String departCondition,
            @Param("incomingCode")String incomingCodeCondition,
            @Param("yearMonth")String yearMonth
    );

    /**
     * 这里不能使用ccode_equal like '1131%' 这个条件，否者会和erp后台数据对不上
     * @param departCondition
     * @param incomingCodeCondition
     * @param yearMonth
     * @return
     */
    @Select("<script>" +
            "select \n" +
            "SUM(mc) as mc, SUM(mc_f) as mc_f\n" +
            "from gl_accvouch a\n" +
            "inner join (\n" +
            "select cVouchID,cPZid,cDwCode from Ar_Detail ad \n" +
            "group by cDwCode,cVouchID,cPZid\n" +
            ")d on a.coutid = (case when d.cVouchID = a.coutid then d.cVouchID \n" +
            "else (d.cVouchID + ' ' +  CAST(a.inid AS VARCHAR(10))) \n" +
            "end) \n" +
            "and d.cPZid = a.coutno_id\n" +
            "inner join(\n" +
            "select cCusCode,cCusName,cCusDepart\n" +
            "from Customer\n" +
            "where 1=1\n" +
            "<if test='departCondition != null' >and cCusDepart ${departCondition}</if> \n" +
            ")c on c.cCusCode = cDwCode \n" +
            "where ccode #{incomingCode} and  iYPeriod = #{yearMonth} \n" +
            "and c.cCusCode in( 'EASI001', '9999', 'W0001', 'HD154', 'HD151')" +
            "</script>")
    List<JSONObject> getOperationRevenueExceptCountData(
            //@Param("codeCondition")String codeCondition,
            @Param("departCondition")String departCondition,
            @Param("incomingCode")String incomingCodeCondition,
            @Param("yearMonth")String yearMonth
    );

    /**
     * 货款回收
     * @param tempTable
     * @return
     */
//    @Select("select \n" +
//            "cexch_name,SUM(md_f)  isum,SUM(md) inatsum\n" +
//            "from \n" +
//            "tempdb..${tempTable}  \n" +
//            "where \n" +
//            "iyear is not null and imonth is not null and iday is not null and cexch_name is not null\n" +
//            "AND cexch_name = '美元' \n" +
//            "group by  cexch_name;")
//    List<JSONObject> getExPaymentRecoveryCountData(@Param("tempTable")String tempTable);
    /**
     * 应收款记录中有客户ID，实际银行存款入账记录中没有，但是两个记录是一一对应的，通过ino_id可以关联
     * select cCusName,cCusDepart,ar.ccus_id,*
     * --cexch_name,SUM(mc),SUM(md)
     * from gl_accvouch ga
     * left join (
     * 	select ccus_id,ino_id from gl_accvouch
     * 	where ccode like '1131%'
     * 	and ccode_equal like '%1002%'
     * 	and  iYPeriod = '202105')ar
     * on ar.ino_id = ga.ino_id
     * left join(
     * 	select cCusCode,cCusName,cCusDepart
     * 	from Customer
     * )c on c.cCusCode = ar.ccus_id
     * where ccode like '1002%'
     * and ccode_equal like '%1131%'
     * and iYPeriod = '202105'
     * and cCusDepart like '0105%'
     * and ar.ccus_id not in( 'EASI001', '9999', 'W0001', 'HD154', 'HD151')
     * group by cexch_name
     * 货款回收  综合明细表银行货款科目  借方
     * @param codeCondition         用于币种科目筛选
     * @param settleCodeCondition   用于筛选结算科目
     * @param yearMonth             用于时间筛选
     * @param departCondition       用于区分内销和外销
     * @return
     */
//    @Select("select \n" +
//            "cexch_name,SUM(md_f)  isum,SUM(md) inatsum\n" +
//            "from \n" +
//            "tempdb..${tempTable}  \n" +
//            "where \n" +
//            "iyear is not null and imonth is not null and iday is not null and cexch_name is not null\n" +
//            "AND ( cdigest not like '%利息%' and cdigest not like '%退款%' and cdigest not like '%退税%' " +
//            "      and cdigest not like '%法院%' and cdigest not like '%租金%'  " +
//            "      and cdigest not like '%工会%' and cdigest not like '%结汇%' " +
//            "      and cdigest not like '%补贴%'  " +
//            "      and cdigest not like '%珠海智拓%'  and cdigest not like '%MEX%' and cdigest not like '%ESSEN%'" +
//            "      and cdigest not like '%珠海横琴%' and cdigest not like '%珠海宜心%' ) " +
//            "AND  cexch_name is not  null \n"+
//            "group by  cexch_name;")
    @Select("<script>" +
            "select cexch_name,SUM(md) md,SUM(md_f) md_f\n" +
            "from gl_accvouch ga\n" +
            "left join (\n" +
            "\tselect ccus_id,ino_id from gl_accvouch  \n" +
            "\twhere ccode ${codeCondition} \n" +
            "\tand ccode_equal ${settleCodeCondition}\n" +
            "\tand  iYPeriod =  #{yearMonth})ar \n" +
            "on ar.ino_id = ga.ino_id\n" +
            "left join(\n" +
            "\tselect cCusCode,cCusName,cCusDepart\n" +
            "\tfrom Customer\t\t\n" +
            ")c on c.cCusCode = ar.ccus_id \n" +
            "where ccode ${settleCodeCondition} \n" +
            "and ccode_equal ${codeCondition} \n" +
            "and iYPeriod = #{yearMonth} \n" +
            "<if test='departCondition != null'>\n" +
            "and cCusDepart ${departCondition}\n" +
            "</if>\n" +
            "AND (cdigest not like '%珠海智拓%'  and cdigest not like 'MEX%' and cdigest not like 'ESSEN%'\n" +
            "and cdigest not like '%珠海横琴%' and cdigest not like '%珠海宜心%')\n" +
            "group by cexch_name" +
            "</script>")
    List<JSONObject> getPaymentRecoveryCountData(@Param("codeCondition")String codeCondition,
                                                 @Param("settleCodeCondition")String settleCodeCondition,
                                                 @Param("yearMonth")String yearMonth,
                                                 @Param("departCondition")String departCondition);

    @Select("<script>" +
            "select SUM(md) md\n" +
            "from gl_accvouch ga\n" +
            "left join (\n" +
            "\tselect ccus_id,ino_id from gl_accvouch  \n" +
            "\twhere ccode ${codeCondition} \n" +
            "\tand ccode_equal ${settleCodeCondition}\n" +
            "\tand  iYPeriod =  #{yearMonth})ar \n" +
            "on ar.ino_id = ga.ino_id\n" +
            "left join(\n" +
            "\tselect cCusCode,cCusName,cCusDepart\n" +
            "\tfrom Customer\t\t\n" +
            ")c on c.cCusCode = ar.ccus_id \n" +
            "where ccode  ${settleCodeCondition} \n" +
            "and ccode_equal ${codeCondition} \n" +
            "and iYPeriod = #{yearMonth} \n" +
            "<if test='departCondition != null'>\n" +
            "and cDeptCode ${departCondition}\n" +
            "</if>\n" +
            "and ar.ccus_id not in( 'EASI001', '9999', 'W0001', 'HD154', 'HD151')" +
            "</script>")
    List<JSONObject> getAllPaymentRecoveryCountData(@Param("codeCondition")String codeCondition,
                                                 @Param("settleCodeCondition")String settleCodeCondition,
                                                 @Param("yearMonth")String yearMonth,
                                                 @Param("departCondition")String departCondition);

    /**
     * 获取排除企业的收款总额
     * @param codeCondition         用于币种科目筛选
     * @param settleCodeCondition   用于筛选结算科目
     * @param yearMonth             用于时间筛选
     * @param except                用于排除集团下子公司
     * @return
     */
    @Select("<script>" +
            "select sum(iAmount) as mc, sum(iAmount_f) as mc_f\n" +
            "from gl_accvouch  a\n" +
            "right join (\n" +
            "select cVouchID,cPzID,cDeptCode,iAmount,iAmount_f from Ap_CloseBill \n" +
            "where cCode ${settleCodeCondition}\n" +
            ")c on a.coutno_id = c.cPzID and a.coutid = c.cVouchID " +
            "where ccode ${codeCondition} " +
            "and iYPeriod in <foreach collection = 'yearMonth' index='index' item='item' open='(' separator=',' close=')'> #{item}</foreach>\n" +
            "and ccus_id in(${except})" +
            "</script>")
    List<JSONObject> getPaymentRecoveryExceptCountData(@Param("codeCondition")String codeCondition,
                                                 @Param("settleCodeCondition")String settleCodeCondition,
                                                 @Param("yearMonth")List yearMonth,
                                                 @Param("except")String except);

    /**
     * 应收账款         明细表应收账款科目   期末余额
     * @param tempTable
     * @return
     */
//    @Select("select iyear,imonth,mark,cexch_name,cdigest,me isum,me_f inatsum  from   tempdb..${tempTable}\n" +
//            " where mark in (5,6) and cexch_name is not null;")
    /**
     * 按月统计应收人民币总和, 应客户要求过滤掉8个月来没有往来的客户
     * @param codeCondition
     * @param startYearMonth
     * @param endYearMonth
     * @return
     */
//    @Select("<script>" +
//            "select iYPeriod,SUM(case when cendd_c = '贷' then -me else me end) as me \n" +
//            "from GL_accass \n" +
//            "where ccode ${codeCondition} " +
//            "and ccus_id not in (${except}) " +
//            "and iYPeriod in <foreach collection = 'yearMonth' index='index' item='item' open='(' separator=',' close=')'> #{item}</foreach> " +
//            "group by iYPeriod;" +
//            "</script>")
    @Select("<script>" +
            "with sum_by_month as (select iYPeriod,ccus_id,\n" +
            "sum(case when cendd_c = '贷' then -me else me end) me_sum, \n" +
            "sum(case when cendd_c = '贷' then -me_f else me_f end) me_f_sum\n" +
            "from GL_accass  gla\n" +
            "where ccode ${codeCondition} " +
            "and ccus_id not in( 'EASI001', '9999', 'W0001', 'HD154', 'HD151') \n" +
            "and iYPeriod between ${startYearMonth} and ${endYearMonth} \n" +
            "group by iYPeriod,ccus_id),\n" +
            "me_avg_with as (select ccus_id,AVG(me_sum) me_avg,AVG(me_f_sum) me_f_avg from sum_by_month group by ccus_id),\n" +
            "count_with as (select ccus_id,me_sum,count(me_sum) me_num_count\n" +
            "from sum_by_month group by ccus_id,me_sum),\n" +
            "count_f_with as (select ccus_id,me_f_sum,count(me_f_sum) me_f_num_count\n" +
            "from sum_by_month group by ccus_id,me_f_sum)\n" +
            "select sum(me_sum) as me,sum(s1.me_f_sum) as me_f\n" +
            "from sum_by_month s1\n" +
            "where iYPeriod = ${endYearMonth} and\n" +
            "(exists(select me_f_sum from sum_by_month s2 \n" +
            "where s2.ccus_id = s1.ccus_id and s2.me_f_sum != 0 and s2.me_f_sum = s1.me_f_sum group by ccus_id,me_f_sum \n" +
            "<![CDATA[ having count(s2.me_f_sum) < ${activeCustomerMonths}]]>)\n" +
            "or\n" +
            "exists(select me_sum,me_f_sum from sum_by_month s2 \n" +
            "where s2.ccus_id = s1.ccus_id and s2.me_f_sum = 0 and s2.me_sum != 0 and s2.me_sum = s1.me_sum group by ccus_id,me_sum,me_f_sum\n" +
            "<![CDATA[having count(s2.me_sum) < ${activeCustomerMonths})]]>)" +
            "</script>")
    List<JSONObject> getAccountsReceivableTotalDataByMonth(@Param("codeCondition")String codeCondition,
                                                           @Param("startYearMonth")String startYearMonth,
                                                           @Param("endYearMonth")String endYearMonth,
                                                           @Param("activeCustomerMonths")Integer activeCustomerMonths);

    /**
     --排除8个月内没有往来的客户
     --建立临时表，用于将同时间同客户的金额累计到一起，因为会存在不同的ccode，所以就有多条记录，如果不汇总的话在对比平均值的时候会出错
     with sum_by_month as (select iYPeriod,ccus_id,c.cCusName,
     sum(case when cendd_c = '贷' then -me else me end) me_sum,
     sum(case when cendd_c = '贷' then -me_f else me_f end) me_f_sum
     from GL_accass  gla
     left join Customer c on gla.ccus_id = c.cCusCode
     where
     ccode like '1131%' and c.cCusDepart like '0105%'
     and ccus_id not in( 'EASI001', '9999', 'W0001', 'HD154', 'HD151')
     and iYPeriod between 202011 and 202106
     group by iYPeriod,ccus_id,c.cCusName),
     --建立按客户的所选时间段中的金额平均值用于判断是否属于活跃客户：连续8个月的金额没有发生变化，每月客户金额等于均值的视为不活跃
     me_avg_with as (select ccus_id,AVG(me_sum) me_avg,AVG(me_f_sum) me_f_avg from sum_by_month group by ccus_id),
     --建立按输入时间范围统计每个客户个月的金额重复的次数,可用于判断是否是活跃用户
     count_with as (select ccus_id,me_sum,count(me_sum) me_num_count
     from sum_by_month group by ccus_id,me_sum),
     count_f_with as (select ccus_id,me_f_sum,count(me_f_sum) me_f_num_count
     from sum_by_month group by ccus_id,me_f_sum)
     --正式查询
     select iYPeriod,s1.ccus_id,cCusName,s1.me_sum as me,s1.me_f_sum as me_f
     from sum_by_month s1
     --与count_*with表联表查询判断
     --left join count_with cw on cw.ccus_id = s1.ccus_id and cw.me_sum = s1.me_sum
     --left join count_f_with cfw on cfw.ccus_id = s1.ccus_id and cfw.me_f_sum = s1.me_f_sum
     --where
     --me_num_count < 8 and me_f_num_count < 8 and s1.me_f_sum != 0 --外销
     --or me_num_count < 8 and s1.me_sum != 0 and s1.me_f_sum = 0 --内销

     --用exists判断存在外销的情况下是否有超过8个月的金额没有变化
     where
     exists(select me_f_sum from sum_by_month s2
     where s2.ccus_id = s1.ccus_id and s2.me_f_sum != 0 and s2.me_f_sum = s1.me_f_sum group by ccus_id,me_f_sum having count(s2.me_f_sum)<8)
     or
     --判断只存在内销的情况下是否有超过8个月的金额没有变化
     exists(select me_sum,me_f_sum from sum_by_month s2
     where s2.ccus_id = s1.ccus_id and s2.me_f_sum = 0 and s2.me_sum != 0 and s2.me_sum = s1.me_sum group by ccus_id,me_sum,me_f_sum
     having count(s2.me_sum)<8)
     * @param codeCondition
     * @param startYearMonth
     * @param endYearMonth
     * @return
     */
//    @Select("<script>" +
//            "select iYPeriod,SUM(case when cendd_c = '贷' then -me else me end) as me \n" +
//            "from GL_accass a\n" +
//            "right join(\n" +
//            "select cCusCode,cCusDepart,cCusName \n" +
//            "from Customer \n" +
//            ")c on a.ccus_id = c.cCusCode \n" +
//            "where ccode ${codeCondition} " +
//            "and ccus_id in (${except}) \n" +
//            "and iYPeriod in <foreach collection = 'yearMonth' index='index' item='item' open='(' separator=',' close=')'> #{item}</foreach> " +
//            "group by iYPeriod;" +
//            "</script>")
    @Select("<script>" +
            "with sum_by_month as (select iYPeriod,ccus_id,\n" +
            "sum(case when cendd_c = '贷' then -me else me end) me_sum, \n" +
            "sum(case when cendd_c = '贷' then -me_f else me_f end) me_f_sum\n" +
            "from GL_accass  gla\n" +
            "where ccode ${codeCondition} " +
            "and ccus_id in( 'EASI001', '9999', 'W0001', 'HD154', 'HD151') \n" +
            "and iYPeriod between #{startYearMonth} and #{endYearMonth} \n" +
            "group by iYPeriod,ccus_id),\n" +
            "me_avg_with as (select ccus_id,AVG(me_sum) me_avg,AVG(me_f_sum) me_f_avg from sum_by_month group by ccus_id),\n" +
            "count_with as (select ccus_id,me_sum,count(me_sum) me_num_count\n" +
            "from sum_by_month group by ccus_id,me_sum),\n" +
            "count_f_with as (select ccus_id,me_f_sum,count(me_f_sum) me_f_num_count\n" +
            "from sum_by_month group by ccus_id,me_f_sum)\n" +
            "select sum(me_sum) as me,sum(s1.me_f_sum) as me_f\n" +
            "from sum_by_month s1\n" +
            "where  iYPeriod = ${endYearMonth} and\n" +
            "(exists(select me_f_sum from sum_by_month s2 \n" +
            "where s2.ccus_id = s1.ccus_id and s2.me_f_sum != 0 and s2.me_f_sum = s1.me_f_sum group by ccus_id,me_f_sum " +
            "<![CDATA[ having count(s2.me_f_sum) < ${activeCustomerMonths}]]>)\n" +
            "or\n" +
            "exists(select me_sum,me_f_sum from sum_by_month s2 \n" +
            "where s2.ccus_id = s1.ccus_id and s2.me_f_sum = 0 and s2.me_sum != 0 and s2.me_sum = s1.me_sum group by ccus_id,me_sum,me_f_sum\n" +
            "<![CDATA[ having count(s2.me_sum) < ${activeCustomerMonths}]]>))" +
            "</script>")
    List<JSONObject> getAccountsReceivableExceptDataByMonth(@Param("codeCondition")String codeCondition,
                                                            @Param("startYearMonth")String startYearMonth,
                                                            @Param("endYearMonth")String endYearMonth,
                                                            @Param("activeCustomerMonths")Integer activeCustomerMonths);

    /**
     * 按月统计内销或者外销应收人民币总和
     * 宜心为例：
     * 人民币:     field = md;     code = 1131%
     * 美元:      field = md_f;   code = 11310201
     * 欧元:      field = md_f;   code = 11310202
     * 美元:      field = md_f;   code = 11310203
     * select iYPeriod,SUM(me) as me
     * from GL_AccMultiAss  a
     * 	join(
     * 		select cCusCode,cCusName,cCusDepart
     * 		from Customer
     * 		where cCusDepart = '0112' and cCusCode not in( 'EASI001', '9999', 'W0001', 'HD154', 'HD151')
     * 		)c on a.ccus_id = c.cCusCode
     * where iYPeriod = '202106' and ccode like '1131%' group by iYPeriod
     * @param codeCondition
     * @param startYearMonth
     * @param endYearMonth
     * @param departCondition
     * @return
     */
//    @Select({"<script>" +
//            "select iYPeriod,SUM(case when cendd_c = '贷' then -me else me end) as me \n" +
//            "from GL_accass a \n" +
//            "<if test='departCondition != null' >\n" +
//            "right join(\n" +
//            "select cCusCode,cCusDepart \n" +
//            "from Customer \n" +
//            "where cCusDepart ${departCondition} \n" +
//            ")c on a.ccus_id = c.cCusCode \n" +
//            "</if> \n" +
//            "where ccode ${codeCondition} " +
//            "and ccus_id not in (${except}) \n" +
//            "and iYPeriod in <foreach collection = 'yearMonth' index='index' item='item' open='(' separator=',' close=')'> #{item}</foreach> " +
//            "group by iYPeriod;" +
//            "</script>"})
    @Select("<script>" +
            "with sum_by_month as (select cexch_name,iYPeriod,ccus_id,\n" +
            "sum(case when cendd_c = '贷' then -me else me end) me_sum, \n" +
            "sum(case when cendd_c = '贷' then -me_f else me_f end) me_f_sum\n" +
            "from GL_accass  gla\n" +
            "<if test='departCondition != null' >\n" +
            "right join Customer c on gla.ccus_id = c.cCusCode \n" +
            "</if> \n" +
            "where ccode ${codeCondition} " +
            "<if test='departCondition != null' >\n" +
            "and cCusDepart ${departCondition} \n" +
            "</if> \n" +
            "and ccus_id not in( 'EASI001', '9999', 'W0001', 'HD154', 'HD151') \n" +
            "and iYPeriod between #{startYearMonth} and #{endYearMonth} \n" +
            "group by cexch_name,iYPeriod,ccus_id),\n" +
            "me_avg_with as (select ccus_id,AVG(me_sum) me_avg,AVG(me_f_sum) me_f_avg from sum_by_month group by ccus_id),\n" +
            "count_with as (select ccus_id,me_sum,count(me_sum) me_num_count\n" +
            "from sum_by_month group by ccus_id,me_sum),\n" +
            "count_f_with as (select ccus_id,me_f_sum,count(me_f_sum) me_f_num_count\n" +
            "from sum_by_month group by ccus_id,me_f_sum)\n" +
            "select cexch_name,sum(me_sum) as me,sum(s1.me_f_sum) as me_f\n" +
            "from sum_by_month s1\n" +
            "where  iYPeriod = ${endYearMonth} and\n" +
            "(exists(select me_f_sum from sum_by_month s2 \n" +
            "where s2.ccus_id = s1.ccus_id and s2.me_f_sum != 0 and s2.me_f_sum = s1.me_f_sum group by ccus_id,me_f_sum " +
            "<![CDATA[ having count(s2.me_f_sum) < ${activeCustomerMonths}]]>)\n" +
            "or\n" +
            "exists(select me_sum,me_f_sum from sum_by_month s2 \n" +
            "where s2.ccus_id = s1.ccus_id and s2.me_f_sum = 0 and s2.me_sum != 0 and s2.me_sum = s1.me_sum group by ccus_id,me_sum,me_f_sum\n" +
            "<![CDATA[ having count(s2.me_sum) < ${activeCustomerMonths}]]>))" +
            "group by cexch_name" +
            "</script>")
    List<JSONObject> getAccountsReceivableTotalDataByMonthAndDepart(@Param("codeCondition")String codeCondition,
                                                    @Param("startYearMonth")String startYearMonth,
                                                    @Param("endYearMonth")String endYearMonth,
                                                    @Param("activeCustomerMonths")Integer activeCustomerMonths,
                                                    @Param("departCondition")String departCondition);

    /**
     * 按月按客户按归属部门统计应收人民币总和，用于数据分析和外销内销的统计
     * 宜心为例：
     * 人民币:     field = md;     code = 1131%
     * 美元:      field = md_f;   code = 11310201
     * 欧元:      field = md_f;   code = 11310202
     * 美元:      field = md_f;   code = 11310203
     * 按客户按部门分月统计应收款示例
     * select ccus_id,c.cCusName,c.cCusDepart,c.cDepName,iYPeriod, SUM(me) as me
     * from GL_AccMultiAss  a
     * 	join(
     * 		select cCusCode,cCusName,cCusDepart, d.cDepName
     * 		from Customer t
     * 			join	(
     * 				select cDepCode,cDepName
     * 				from Department
     * 			)d on t.cCusDepart = d.cDepCode
     * 		where cCusCode not in( 'EASI001', '9999', 'W0001', 'HD154', 'HD151')
     * 			and cCusDepart like '0105%'
     * 		)c on a.ccus_id = c.cCusCode
     * where ccode like '1131%' group by iYPeriod,a.ccus_id,c.cCusName,c.cCusDepart,c.cDepName
     * @param codeCondition
     * @param yearMonth
     * @param except
     * @return
     */
    @Select({"<script>" +
            "select ccus_id,c.cCusName,c.cCusDepart,c.cDepName,iYPeriod,SUM(case when cendd_c = '贷' then -me else me end) as me \n" +
            "from GL_accass  a \n" +
            "right join(select cCusCode,cCusName,cCusDepart,d.cDepName \n" +
            "from Customer t \n" +
            "<if test='departCondition != null' >\n" +
            "left join(select cDepCode,cDepName from Department)d on t.cCusDepart = d.cDepCode \n" +
            "and cCusDepart ${departCondition}\n" +
            "</if> \n" +
            ")c on a.ccus_id = c.cCusCode \n" +
            "where ccode ${codeCondition} " +
            "and ccus_id not in (${except}) \n" +
            "and iYPeriod in <foreach collection = 'yearMonth' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach> " +
            "group by iYPeriod,ccus_id,c.cCusName,c.cCusDepart,c.cDepName;" +
            "</script>"})
    List<JSONObject> getAccountsReceivableTotalData(@Param("codeCondition")String codeCondition,
                                                    @Param("yearMonth")List yearMonth,
                                                    @Param("departCondition")String departCondition,
                                                    @Param("except")String except);

    /**
     * 采购汇总
     * @param startDate
     * @param endDate
     * @return
     */
    @Select(" select\n" +
            " cexch_name,SUM(imoney) isum, SUM(inatmoney) inatsum\n" +
            " FROM\n" +
            "zpurpoheader WITH (nolock)\n" +
            "INNER JOIN zpurpotail WITH (nolock) ON zpurpoheader.poid = zpurpotail.poid\n" +
            "WHERE\n" +
            "dPODate >= #{startDate}\n" +
            "AND dPODate <#{endDate}\n" +
            "group by cexch_name;")
    List<JSONObject> getPurchCountData(@Param("startDate")String startDate, @Param("endDate")String endDate);

    /**
     * 委外采购
     * @param startDate
     * @param endDate
     * @return
     */
    @Select(" select\n" +
            " om_mohead.cexch_name,SUM(imoney) isum, SUM(inatmoney) inatsum\n" +
            " FROM\n" +
            "om_mohead WITH (nolock)\n" +
            "INNER JOIN om_mobody WITH (nolock) ON om_mohead.moid = om_mobody.moid\n" +
            "WHERE\n" +
            "om_mohead.dDate >=#{startDate} AND om_mohead.dDate <#{endDate}\n" +
            "AND iProductType = 0\n" +
            "group by om_mohead.cexch_name")
    List<JSONObject> getOutSourcePurchCountData(@Param("startDate")String startDate, @Param("endDate")String endDate);


    /**
     * 内销采购
     * @param startDate
     * @param endDate
     * @return
     */
    @Select(" select\n" +
            "     zpurpoheader.cexch_name,SUM(zpurpotail.imoney) isum, SUM(zpurpotail.inatmoney) inatsum\n" +
            " FROM\n" +
            "zpurpoheader WITH (nolock)\n" +
            "INNER JOIN zpurpotail WITH (nolock) ON zpurpoheader.poid = zpurpotail.poid\n" +
            "RIGHT JOIN SaleOrderQ on SaleOrderQ.Csocode = zpurpotail.csocode\n" +
            "WHERE\n" +
            "dPODate >= #{startDate}\n" +
            "AND dPODate <#{endDate}\n" +
            "group by zpurpoheader.cexch_name;")

    JSONObject getInPurchCountData(@Param("startDate")String startDate, @Param("endDate")String endDate);



    /**
     * 委外内销采购
     * @param startDate
     * @param endDate
     * @return
     */
    @Select(" select\n" +
            " om_mohead.cexch_name,SUM(om_mobody.imoney) isum, SUM(om_mobody.inatmoney) inatsum\n" +
            " FROM\n" +
            "om_mohead WITH (nolock)\n" +
            "INNER JOIN om_mobody WITH (nolock) ON om_mohead.moid = om_mobody.moid\n" +
            "RIGHT JOIN SaleOrderQ on SaleOrderQ.Csocode = om_mobody.csocode\n" +
            "WHERE\n" +
            "om_mohead.dDate >= #{startDate}\n" +
            "AND om_mohead.dDate <#{endDate}\n" +
            "group by om_mohead.cexch_name;")
    JSONObject getOutSourceInPurchCountData(@Param("startDate")String startDate, @Param("endDate")String endDate);

    /**
     * 发生额及余额报表
     * @param tempTable
     * @param startMonth
     */
    @Select("exec Gl_Balance @tblname=#{tempTable},@iPeriod=#{startMonth},@iePeriod=#{startMonth},@kmcode1=N''," +
            "@kmcode2=N'',@jici1=1,@jici2=1,@bmoji=0,@wheremoney=N' where 1 = 1 ',@swhere=N'',@bVouch=0,@bshow=1,@swheother=N''")
    void getBalanceReport(@Param("tempTable")String tempTable,@Param("startMonth")String startMonth);

    /**
     * 期末库存RMB
     * @param startMonth
     * @param ccode
     * @return
     */
//    @Select("select  SUM(a.isum) isum,SUM(a.inatsum) inatsum from ( select cexch_name,SUM(eme_f) isum,SUM(eme) inatsum from tempdb..${tempTable} \n" +
//            "where\n" +
//            "  ccode in (${ccode})\n"+
//            " group by\n" +
//            "   cexch_name) a;"
//    )
    @Select("select SUM(me_f) isum,SUM(me) inatsum from GL_accsum\n" +
            "    where\n" +
            "    ccode in (${ccode})\n" +
            "and cendd_c = '借'\n" +
            "    and iYPeriod = #{startMonth}")
    JSONObject getEndPeriodStockCountData(//@Param("tempTable")String tempTable,
                                          @Param("startMonth")String startMonth,
                                          @Param("ccode") String ccode);
    /**
     * 期初库存RMB
     * @param startMonth
     * @param ccode
     * @return
     */
//    @Select("select  SUM(a.isum) isum,SUM(a.inatsum) inatsum from (select cexch_name,SUM(me_f) isum,SUM(me) inatsum from tempdb..${tempTable} \n" +
//            "where\n" +
//            "  ccode in (${ccode})\n"+
//            " group by\n" +
//            "   cexch_name) a;"
//    )
    @Select("select SUM(mb_f) isum,SUM(mb)\n" +
            "from GL_accsum \n" +
            "where \n" +
            "ccode in (${ccode})\n" +
            "and cbegind_c = '借'\n" +
            "and iYPeriod = #{startMonth}")
    JSONObject getBeginPeriodStockCountData(//@Param("tempTable")String tempTable,
                                            @Param("startMonth")String startMonth,
                                            @Param("ccode") String ccode);
//
//    /**
//     * 留存余额
//     * @param tempTable
//     * @return
//     */
//    @Select("select '留存余额',cexch_name,SUM(eme) isum,SUM(eme_f) inatsum from tempdb..${tempTable} \n" +
//            "where\n" +
//            "ccode in ('1001','1002')\n" +
//            "group by\n" +
//            "cexch_name;")
//    JSONObject getRetainedBalanceCountData(@Param("tempTable")String tempTable);

//    /**
//     * 总资产
//     * @param tempTable
//     * @return
//     */
//    @Select("select '总资产',cexch_name,SUM(eme) isum,SUM(eme_f) inatsum from tempdb..${tempTable} \n" +
//            "where\n" +
//            "  ccode in ('1501','1603')\n" +
//            " group by\n" +
//            "   cexch_name;")
//    JSONObject getTotalAssetsCountData(@Param("tempTable")String tempTable);
//
//    /**
//     * 固定资产 在建工程
//     * @param tempTable
//     * @return
//     */
//    @Select("select ccode_name,cexch_name,SUM(eme) isum,SUM(eme_f) inatsum from tempdb..${tempTable} \n" +
//            "where\n" +
//            "  ccode in ('1501','1603')\n" +
//            "  group by\n" +
//            "   cexch_name,ccode_name;")
//    List<JSONObject> getFixedAssetsCountData(@Param("tempTable")String tempTable);
//
//    /**
//     * 应收账款
//     * @param tempTable
//     * @return
//     */
//    @Select("select ccode_name,cexch_name,SUM(eme) isum,SUM(eme_f) inatsum from tempdb..${tempTable}  \n" +
//            "where\n" +
//            "  ccode in ('1131')\n" +
//            "  group by\n" +
//            "   cexch_name,ccode_name;")
//    JSONObject getAccountsPayableCountData(@Param("tempTable")String tempTable);



    /**
     * 营业成本
     * 主营业务成本
     * select * from GL_accsum
     * where ccode like '5401%'
     * and iYPeriod = '202106'
     * 内销
     * select * from GL_accsum
     * where ccode = '540101'
     * and iYPeriod = '202106'
     * 外销
     * select * from GL_accsum
     * where ccode = '540102'
     * and iYPeriod = '202106'
     * @param yearMonth
     * @param ccode
     * @return
     */
//    @Select("select ccode_name,cexch_name,SUM(bmd_f) isum,SUM(bmd) inatsum from tempdb..${tempTable} \n" +
//            "where\n" +
//            "  ccode in (${ccode})\n" +
//            "  group by\n" +
//            "   cexch_name,ccode_name;")
    @Select("select SUM(md_f) isum,SUM(md) inatsum\n" +
            "from GL_accsum \n" +
            "where ccode in (${ccode})\n" +
            "and iYPeriod = #{yearMonth}")
    JSONObject getOperatingCostCountData(
            //@Param("tempTable")String tempTable,
            @Param("yearMonth")String yearMonth,
            @Param("ccode") String ccode);

    /**
     * 营业收入
     * @param tempTable
     * @return
     */
    @Select("select ccode_name,cexch_name,SUM(bmd_f) isum,SUM(bmd) inatsum from tempdb..${tempTable} \n" +
            "where ccode =${ccode}\n" +
            "group by\n" +
            "   cexch_name,ccode_name; ")
    JSONObject getOperatingIncomeCountData(@Param("tempTable")String tempTable,@Param("ccode") String ccode);

    /**
     *
     * 制造费用
     * @param yearMonth
     * @param ccode
     * @return
     */
//    @Select("select ccode_name,cexch_name,SUM(bmd_f) isum,SUM(bmd) inatsum from tempdb..${tempTable}  \n" +
//            "where\n" +
//            "  ccode in (${ccode})\n" +
//            "group by\n" +
//            "   cexch_name,ccode_name; ")
    @Select("select SUM(md_f) isum,SUM(md) inatsum\n" +
            "from GL_accsum \n" +
            "where ccode in (${ccode})\n" +
            "and iYPeriod = #{yearMonth}")
    JSONObject getManufacturingCostCountData(
            //@Param("tempTable")String tempTable,
            @Param("yearMonth")String yearMonth,
            @Param("ccode") String ccode);

    /**
     * 营业费用
     * @param yearMonth
     * @param ccode
     * @return
     */
//    @Select("select ccode_name,cexch_name,SUM(bmd_f) isum,SUM(bmd) inatsum from tempdb..${tempTable} \n" +
//            "where\n" +
//            "  ccode in (${ccode})\n" +
//            "group by\n" +
//            "   cexch_name,ccode_name; ")
    @Select("select SUM(md_f) isum,SUM(md) inatsum\n" +
            "from GL_accsum \n" +
            "where ccode in (${ccode})\n" +
            "and iYPeriod = #{yearMonth}")
    JSONObject getOperatingExpensesCountData(
            //@Param("tempTable")String tempTable,
            @Param("yearMonth")String yearMonth,
            @Param("ccode") String ccode);

    /**
     * 税收及附加
     * @param yearMonth
     * @param ccode
     * @return
     */
//    @Select("select ccode_name,cexch_name,SUM(bmd_f) isum,SUM(bmd) inatsum from tempdb..${tempTable} \n" +
//            "where\n" +
//            "  ccode in (${ccode})\n" +
//            "group by\n" +
//            "   cexch_name,ccode_name;")
    @Select("select SUM(md_f) isum,SUM(md) inatsum\n" +
            "from GL_accsum \n" +
            "where ccode in (${ccode})\n" +
            "and iYPeriod = #{yearMonth}")
    JSONObject getTaxesAndSurchargesCountData(
            //@Param("tempTable")String tempTable,
            @Param("yearMonth")String yearMonth,
            @Param("ccode") String ccode);

    /**
     * 管理费用和营业外收支
     * @param yearMonth
     * @param ccode
     * @return
     */
//    @Select("select '管理费用和营业外收支',cexch_name,SUM(bmd_f) isum,SUM(bmd) inatsum from tempdb..${tempTable} \n" +
//            "where\n" +
//            "  ccode in (${ccode})\n" +
//            "group by\n" +
//            "   cexch_name;  ")
    @Select("select SUM(md_f) isum,SUM(md) inatsum from GL_accsum \n" +
            "where \n" +
            "ccode in (${ccode})\n" +
            "and iYPeriod = #{yearMonth}")
    JSONObject getManagementExpensesCountData(
            //@Param("tempTable")String tempTable,
            @Param("yearMonth")String yearMonth,
            @Param("ccode") String ccode);

    /**
     * 财务费用
     * @param yearMonth
     * @param ccode
     * @return
     */
//    @Select("select ccode_name,cexch_name,SUM(bmd_f) isum,SUM(bmd) inatsum from tempdb..${tempTable} \n" +
//            "where\n" +
//            "  ccode in (${ccode})\n" +
//            "group by\n" +
//            "   cexch_name,ccode_name;")
    @Select("select SUM(md_f) isum,SUM(md) inatsum from GL_accsum \n" +
            "where ccode in (${ccode})\n" +
            "and iYPeriod = #{yearMonth}")
    JSONObject getFinancialExpensesCountData(
            //@Param("tempTable")String tempTable,
            @Param("yearMonth")String yearMonth,
            @Param("ccode") String ccode);



    /**
     * 所得税
     * @param yearMonth
     * @param ccode
     * @return
     */
//    @Select("select ccode_name,cexch_name,SUM(bmd_f) isum,SUM(bmd) inatsum from tempdb..${tempTable} \n" +
//            "where\n" +
//            "  ccode in (${ccode})\n" +
//            "group by\n" +
//            "   cexch_name,ccode_name;")
    @Select("select SUM(md_f) isum,SUM(md) inatsum from GL_accsum \n" +
            "where ccode in (${ccode})\n" +
            "and iYPeriod = #{yearMonth}")
    JSONObject getIncomeTaxCountData(
            //@Param("tempTable")String tempTable,
            @Param("yearMonth")String yearMonth,
            @Param("ccode") String ccode);

    /**
     * 退税款   明细表出口退款科目     取贷方
     * @param yearMonth
     * @param ccode
     * @return
     */
//    @Select("select \n" +
//            "cexch_name,SUM(md_f) isum, SUM(md) inatsum\n" +
//            "from \n" +
//            "tempdb..${tempTable}  \n" +
//            "where \n" +
//            "iyear is not null and imonth is not null and iday is not null and cexch_name is not null\n"+
//            "group by  cexch_name;")
    @Select("select SUM(mc_f) isum,SUM(mc) inatsum from GL_accsum \n" +
            "where ccode in (${ccode})\n" +
            "and iYPeriod = #{yearMonth}")
    JSONObject getTaxRefundCountData(
            //@Param("tempTable")String tempTable,
            @Param("yearMonth")String yearMonth,
            @Param("ccode") String ccode);
}
