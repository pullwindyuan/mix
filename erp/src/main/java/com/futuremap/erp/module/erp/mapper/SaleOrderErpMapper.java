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

import com.futuremap.erp.module.comp.entity.CompanyDataTableInfo;
import com.futuremap.erp.module.saleorder.entity.SaleOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 * @title SaleOrderMapper
 * @description TODO
 * @date 2021/5/28 10:44
 */
@Mapper
//@DS("erp002")
public interface SaleOrderErpMapper {

    /**
     * 获取销售订单列表
     * @return
     */
   @Select("select ddate,dshippingdate dpredatebt,ccode  csocode,irowno,cpersoncode,cpersonname,cdepcode,cdepname,cinvcode,cinvname,cinvstd,ccomunitname cinvm_unit," +
           "ccuscode ccus_code,ccusabbname ccus_abbname,ccusname ccus_name,cexch_name,  fquantity iquantity,ftaxprice  itaxunitprice, ffobmoney isum ," +
           "fnatfobmoney inatsum,dcompletedate dcomplete_date ,dlineclosedate," +
           "(case when dlineclosedate IS NOT NULL then -1 else 0 end) as status\n" +
           "from v_ex_orderlist \n" +
           "where ddate >= #{startDate} AND ddate <#{endDate} AND   ccuscode not in('HD154', 'W0001', 'HD151','EASI001','9999') AND ccode is not null")
   List<SaleOrder> getExSaleOrderList(@Param("startDate")String startDate, @Param("endDate")String endDate);


   @Select("select ddate ,dpredatebt,csocode,irowno,cpersoncode,cpersonname,cdepcode,cdepname,cinvcode,cinvname,cinvstd,cinvm_unit,ccuscode ccus_code," +
           "ccusabbname ccus_abbname,ccusname ccus_name,cexch_name,iquantity ,itaxunitprice,isum ,inatsum ,dpremodate dcomplete_date,dbclosedate," +
           "(case when dbclosedate IS NOT NULL then -1 else 0 end) as status\n" +
           "from SaleOrderQ\n" +
           "INNER JOIN SaleOrderSQ ON SaleOrderQ.id = SaleOrderSQ.id\n" +
           "where ddate >= #{startDate} AND ddate <#{endDate} AND   ccuscode not in('HD154', 'W0001', 'HD151','EASI001','9999') AND csocode is not null")
   List<SaleOrder> getSaleOrderList(@Param("startDate")String startDate, @Param("endDate")String endDate);


    @Select("DROP TABLE IF EXISTS sale_order_process; \n" +
            "CREATE TABLE sale_order_process AS\n" +
            "SELECT\n" +
            "\t`so`.`id` AS `id`,\n" +
            "\t`so`.`ddate` AS `ddate`,\n" +
            "\t`so`.`csocode` AS `csocode`,\n" +
            "\t`so`.`irowno` AS `irowno`,\n" +
            "\t`so`.`company_code` AS `company_code`,\n" +
            "\t`so`.`company_name` AS `company_name`,\n" +
            "\t`so`.`cpersoncode` AS `cpersoncode`,\n" +
            "\t`so`.`cpersonname` AS `cpersonname`,\n" +
            "\t`so`.`cdepcode` AS `cdepcode`,\n" +
            "\t`so`.`cdepname` AS `cdepname`,\n" +
            "\t`so`.`cinvcode` AS `cinvcode`,\n" +
            "\t`so`.`cinvname` AS `cinvname`,\n" +
            "\t`so`.`cinvstd` AS `cinvstd`,\n" +
            "\t`so`.`cinvm_unit` AS `cinvm_unit`,\n" +
            "\t`so`.`ccus_code` AS `ccus_code`,\n" +
            "\t`so`.`ccus_abbname` AS `ccus_abbname`,\n" +
            "\t`so`.`ccus_name` AS `ccus_name`,\n" +
            "\t`so`.`cexch_name` AS `cexch_name`,\n" +
            "\t`so`.`iquantity` AS `iquantity`,\n" +
            "\t`so`.`itaxunitprice` AS `itaxunitprice`,\n" +
            "\t`so`.`isum` AS `isum`,\n" +
            "\t`so`.`inatsum` AS `inatsum`,\n" +
            "\t`so`.`dpredatebt` AS `dpredatebt`,\n" +
            "\t`so`.`dcomplete_date` AS `dcomplete_date`,\n" +
            "\t`so`.`exception_cost` AS `exception_cost`,\n" +
            "\t`so`.`exflag` AS `exflag`,\n" +
            "\t`so`.`status` AS `status`,\n" +
            "\t`sot`.`ddate` AS `saleOutDate`,\n" +
            "IF\n" +
            "\t(( `oam`.`back_day` <> NULL ), `oam`.`back_day`, 90 ) AS `back_day`,\n" +
            "\t`ri`.`iInQuantity` AS `iInQuantitySum`,\n" +
            "\t`sot`.`iOutQuantity` AS `iOutQuantitySum`,\n" +
            "\t`sw`.`iSwitchQuantity` AS `iSwitchQuantitySum`,\n" +
            "IF\n" +
            "\t(( `ri`.`iInQuantity` IS NOT NULL ),( `so`.`iquantity` - `ri`.`iInQuantity` ), `so`.`iquantity` ) AS `iNotInQuantitySum`,\n" +
            "IF\n" +
            "\t((\n" +
            "\t\t\t`sot`.`iOutQuantity` IS NOT NULL \n" +
            "\t\t\t),(\n" +
            "\t\t\t`so`.`iquantity` - `sot`.`iOutQuantity` \n" +
            "\t\t\t),\n" +
            "\tIF\n" +
            "\t(( `sw`.`iSwitchQuantity` IS NOT NULL ), NULL, `so`.`iquantity` )) AS `iNotOutQuantitySum`,\n" +
            "IF\n" +
            "\t((\n" +
            "\t\t\t`sw`.`iSwitchQuantity` IS NULL \n" +
            "\t\t\t),\n" +
            "\t\tNULL,(\n" +
            "\t\t\t`so`.`iquantity` - `sw`.`iSwitchQuantity` \n" +
            "\t\t)) AS `iNotSwitchQuantitySum`,\n" +
            "IF\n" +
            "\t((\n" +
            "\t\t\t`sw`.`iSwitchQuantity` IS NULL \n" +
            "\t\t\t),\n" +
            "\t\t'SWITCH_NORMAL',\n" +
            "\tIF\n" +
            "\t\t(((\n" +
            "\t\t\t\t\ttimestampdiff(\n" +
            "\t\t\t\t\t\tMONTH,\n" +
            "\t\t\t\t\t\tdate_format( now(), '%Y-%m-%d %H:%i:%S' ),\n" +
            "\t\t\t\t\tdate_format( `so`.`ddate`, '%Y-%m-%d %H:%i:%S' )) > 6 \n" +
            "\t\t\t\t\t) \n" +
            "\t\t\t\tAND (( `so`.`iquantity` - `sw`.`iSwitchQuantity` ) > 0 )),\n" +
            "\t\t\t'SWITCH_DELAY',\n" +
            "\t\tIF\n" +
            "\t\t\t(((\n" +
            "\t\t\t\t\t\ttimestampdiff(\n" +
            "\t\t\t\t\t\t\tMONTH,\n" +
            "\t\t\t\t\t\t\tdate_format( now(), '%Y-%m-%d %H:%i:%S' ),\n" +
            "\t\t\t\t\t\tdate_format( `so`.`ddate`, '%Y-%m-%d %H:%i:%S' )) <= 6 ) AND (( `so`.`iquantity` - `sw`.`iSwitchQuantity` ) > 0 \n" +
            "\t\t\t\t\t\t)),\n" +
            "\t\t\t\t'SWITCH_IN_PROGRESS',\n" +
            "\t\t\tIF\n" +
            "\t\t\t\t(((\n" +
            "\t\t\t\t\t\t\ttimestampdiff(\n" +
            "\t\t\t\t\t\t\t\tMONTH,\n" +
            "\t\t\t\t\t\t\t\tdate_format( now(), '%Y-%m-%d %H:%i:%S' ),\n" +
            "\t\t\t\t\t\t\tdate_format( `so`.`ddate`, '%Y-%m-%d %H:%i:%S' )) > 6 \n" +
            "\t\t\t\t\t\t\t) \n" +
            "\t\t\t\t\t\tAND (( `so`.`iquantity` - `sw`.`iSwitchQuantity` ) = 0 )),\n" +
            "\t\t\t\t\t'SWITCH_COMPLETED_BUT_DELAY',\n" +
            "\t\t\t\tIF\n" +
            "\t\t\t\t\t(((\n" +
            "\t\t\t\t\t\t\t\ttimestampdiff(\n" +
            "\t\t\t\t\t\t\t\t\tMONTH,\n" +
            "\t\t\t\t\t\t\t\t\tdate_format( now(), '%Y-%m-%d %H:%i:%S' ),\n" +
            "\t\t\t\t\t\t\t\tdate_format( `so`.`ddate`, '%Y-%m-%d %H:%i:%S' )) <= 6 \n" +
            "\t\t\t\t\t\t\t\t) \n" +
            "\t\t\t\t\t\t\tAND (( `so`.`iquantity` - `sw`.`iSwitchQuantity` ) = 0 )),\n" +
            "\t\t\t\t\t\t'SWITCH_COMPLETED',\n" +
            "\t\t\t\t\t\t'SWITCH_DELAY' \n" +
            "\t\t\t\t\t))))) AS `swWarningName`,\n" +
            "\t`t`.`saleBillNatMoney` AS `saleBillNatMoney`,\n" +
            "\t`t`.`saleBillMoney` AS `saleBillMoney`,\n" +
            "\t`t`.`dvouch_date` AS `dvouch_date`,\n" +
            "\t`t`.`conlection_money` AS `conlection_money`,\n" +
            "\t`t`.`conlection_nat_money` AS `conlection_nat_money`,\n" +
            "\t`t`.`no_conlection_money` AS `no_conlection_money`,\n" +
            "IF\n" +
            "\t((\n" +
            "\t\t\ttimestampdiff(\n" +
            "\t\t\t\tDAY,\n" +
            "\t\t\t\tdate_format( now(), '%Y-%m-%d %H:%i:%S' ),\n" +
            "\t\t\tdate_format( `so`.`ddate`, '%Y-%m-%d %H:%i:%S' )) >= `oam`.`back_day` \n" +
            "\t\t\t),\n" +
            "\t\t'RECEIVE_DELAY',\n" +
            "\tIF\n" +
            "\t\t(((\n" +
            "\t\t\t\t\ttimestampdiff(\n" +
            "\t\t\t\t\t\tDAY,\n" +
            "\t\t\t\t\t\tdate_format( now(), '%Y-%m-%d %H:%i:%S' ),\n" +
            "\t\t\t\t\tdate_format( `so`.`ddate`, '%Y-%m-%d %H:%i:%S' )) >= 30 \n" +
            "\t\t\t\t\t) \n" +
            "\t\t\t\tAND ( timestampdiff( DAY, date_format( now(), '%Y-%m-%d %H:%i:%S' ), date_format( `so`.`ddate`, '%Y-%m-%d %H:%i:%S' )) < `oam`.`back_day` )),\n" +
            "\t\t\t'RECEIVE_WARNING',\n" +
            "\t\tIF\n" +
            "\t\t(( 0 = `t`.`no_conlection_money` ), 'RECEIVE_COMPLETED', 'RECEIVE_NORMAL' ))) AS `arWarningName` \n" +
            "FROM\n" +
            "\t(((((\n" +
            "\t\t\t\t\t\t`sale_order` `so`\n" +
            "\t\t\t\t\t\tLEFT JOIN (\n" +
            "\t\t\t\t\t\tSELECT\n" +
            "\t\t\t\t\t\t\t`saleout_list`.`csocode` AS `csocode`,\n" +
            "\t\t\t\t\t\t\t`saleout_list`.`irowno` AS `irowno`,\n" +
            "\t\t\t\t\t\t\t`saleout_list`.`ddate` AS `ddate`,\n" +
            "\t\t\t\t\t\t\tsum( `saleout_list`.`iquantity` ) AS `iOutQuantity` \n" +
            "\t\t\t\t\t\tFROM\n" +
            "\t\t\t\t\t\t\t`saleout_list` \n" +
            "\t\t\t\t\t\tGROUP BY\n" +
            "\t\t\t\t\t\t\t`saleout_list`.`csocode`,\n" +
            "\t\t\t\t\t\t\t`saleout_list`.`irowno` \n" +
            "\t\t\t\t\t\t\t) `sot` ON (((\n" +
            "\t\t\t\t\t\t\t\t\t`sot`.`csocode` = `so`.`csocode` \n" +
            "\t\t\t\t\t\t\t\t\t) \n" +
            "\t\t\t\t\t\t\tAND ( `sot`.`irowno` = `so`.`irowno` ))))\n" +
            "\t\t\t\t\tLEFT JOIN (\n" +
            "\t\t\t\t\tSELECT\n" +
            "\t\t\t\t\t\t`inl`.`csocode` AS `csocode`,\n" +
            "\t\t\t\t\t\t`inl`.`irowno` AS `irowno`,\n" +
            "\t\t\t\t\t\tsum( `inl`.`iquantity` ) AS `iInQuantity` \n" +
            "\t\t\t\t\tFROM\n" +
            "\t\t\t\t\t\t`recordin_list` `inl` \n" +
            "\t\t\t\t\tWHERE\n" +
            "\t\t\t\t\t\t( `inl`.`cdepcode` = '020303' ) \n" +
            "\t\t\t\t\tGROUP BY\n" +
            "\t\t\t\t\t\t`inl`.`csocode`,\n" +
            "\t\t\t\t\t\t`inl`.`irowno` \n" +
            "\t\t\t\t\t\t) `ri` ON (((\n" +
            "\t\t\t\t\t\t\t\t`ri`.`csocode` = `so`.`csocode` \n" +
            "\t\t\t\t\t\t\t\t) \n" +
            "\t\t\t\t\t\tAND ( `ri`.`irowno` = `so`.`irowno` ))))\n" +
            "\t\t\t\tLEFT JOIN (\n" +
            "\t\t\t\tSELECT\n" +
            "\t\t\t\t\t`switch`.`order_switch` AS `order_switch`,\n" +
            "\t\t\t\t\t`switch`.`order_number_switch` AS `order_number_switch`,\n" +
            "\t\t\t\t\tsum( `switch`.`order_count` ) AS `iSwitchQuantity` \n" +
            "\t\t\t\tFROM\n" +
            "\t\t\t\t\t`switch_order` `switch` \n" +
            "\t\t\t\tGROUP BY\n" +
            "\t\t\t\t\t`switch`.`order_switch`,\n" +
            "\t\t\t\t\t`switch`.`order_number_switch` \n" +
            "\t\t\t\t\t) `sw` ON (((\n" +
            "\t\t\t\t\t\t\t`sw`.`order_switch` = `so`.`csocode` \n" +
            "\t\t\t\t\t\t\t) \n" +
            "\t\t\t\t\tAND ( `sw`.`order_number_switch` = `so`.`irowno` ))))\n" +
            "\t\t\tLEFT JOIN ( SELECT `oa`.`customer_code` AS `customer_code`, `oa`.`back_day` AS `back_day` FROM `order_amount_mgr` `oa` ) `oam` ON ((\n" +
            "\t\t\t\t\t`oam`.`customer_code` = `so`.`ccus_code` \n" +
            "\t\t\t\t)))\n" +
            "\t\tLEFT JOIN (\n" +
            "\t\tSELECT\n" +
            "\t\t\t`s`.`csocode` AS `csocode`,\n" +
            "\t\t\t`s`.`irowno` AS `irowno`,\n" +
            "\t\t\t`s`.`ccus_code` AS `ccus_code`,\n" +
            "\t\t\t`s`.`ccus_name` AS `ccus_name`,\n" +
            "\t\t\tsum( `s`.`bill_nat_money` ) AS `saleBillNatMoney`,\n" +
            "\t\t\tsum( `s`.`bill_money` ) AS `saleBillMoney`,\n" +
            "\t\t\tmax( `s`.`dvouch_date` ) AS `dvouch_date`,\n" +
            "\t\t\t`s`.`receipt_code` AS `receipt_code`,\n" +
            "\t\t\tsum( `s`.`conlection_money` ) AS `conlection_money`,\n" +
            "\t\t\tsum( `s`.`conlection_nat_money` ) AS `conlection_nat_money`,\n" +
            "\t\t\tsum((\n" +
            "\t\t\t\t`s`.`bill_nat_money` - ifnull( `s`.`conlection_nat_money`, 0 ))) AS `no_conlection_money` \n" +
            "\t\tFROM\n" +
            "\t\t\t`sale_bill` `s` \n" +
            "\t\tGROUP BY\n" +
            "\t\t\t`s`.`csocode`,\n" +
            "\t\t\t`s`.`irowno` \n" +
            "\t\t\t) `t` ON (((\n" +
            "\t\t\t\t\t`t`.`csocode` = `so`.`csocode` \n" +
            "\t\t\t\t\t) \n" +
            "\t\t\tAND ( `t`.`irowno` = `so`.`irowno` )))) \n" +
            "ORDER BY\n" +
            "\t`so`.`ddate` DESC,\n" +
            "\t`so`.`csocode`,\n" +
            "\t`so`.`irowno`" )
    void createSaleOrderDetaileView();

    @Select("select #{datasource} as datasource, #{companyName} as company_name, #{companyCode} as company_code, sys.objects.name from sys.objects join sys.sysindexes " +
            "on sys.objects.object_id=sys.sysindexes.id\n" +
            "where indid<=2 and type='U'\n" +
            "and (sys.objects.name not like '%temp%' and sys.objects.name not like '%Tmp%' and sys.objects.name not like '%bak%' \n" +
            "and sys.objects.name not like '%TMPUF%' and sys.objects.name not like '%TMPMODID%' and sys.objects.name not like 'SFD%')\n" +
            "group by sys.objects.name\n" +
            "having SUM(rows)>0\n" +
            "order by sys.objects.name")
    List<CompanyDataTableInfo>  getAllTableName(@Param("datasource")String datasource, @Param("tableName")String tableName, @Param("companyName")String companyName, @Param("companyCode")String companyCode);

    @Select("CREATE TABLE #{tableName} AS\n" +
            "#{companyDataTableInfoList}")
    void createAllData(@Param("companyDataTableInfoList") List<CompanyDataTableInfo>companyDataTableInfoList, @Param("tableName")String tableName);
}
