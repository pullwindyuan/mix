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
package com.futuremap.erp.module.job;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.futuremap.erp.module.comp.entity.CompanyInfo;
import com.futuremap.erp.module.comp.service.impl.CompanyInfoServiceImpl;
import com.futuremap.erp.module.orderCost.service.impl.OrderCostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class OrderCostExportJob {
    @Autowired
    OrderCostServiceImpl orderCostService;
    @Autowired
    CompanyInfoServiceImpl companyInfoService;

    public void testVV() {
        orderCostService.updateFatherChildOrders("2020-10-01", "2020-10-25", "202010", "erp010");
    }

//    @Scheduled(cron = "0 55 1 3 * ? ") 每个月3号的1：55执行一次
    public void startOrderCostExportFromErpJob() {
        List<CompanyInfo> companyInfoList = companyInfoService.list(new QueryWrapper<CompanyInfo>().notIn("id", 25));
        List<String> months = new ArrayList<>();


//        months.add("201901");
//        months.add("201902");
//        months.add("201903");
//        months.add("201904");
//        months.add("201905");
//        months.add("201906");
//        months.add("201907");
//        months.add("201908");
//        months.add("201909");
//        months.add("201910");
//        months.add("201911");
//        months.add("201912");
//
//        months.add("202001");
//        months.add("202002");
//        months.add("202003");
//        months.add("202004");
//        months.add("202005");
//        months.add("202006");
//        months.add("202007");
//        months.add("202008");
//        months.add("202009");
//        months.add("202010");
//        months.add("202011");
//        months.add("202012");
//        months.add("202101");
//        months.add("202102");
//        months.add("202103");
//        months.add("202104");
        months.add("202105");
        months.add("202106");
        months.add("202107");

//        orderCostService.process0material("202106");


        companyInfoList.stream().forEach(comp -> {

            String ds = comp.getDatasource();

            DynamicDataSourceContextHolder.push(ds);


            for (String month : months) {
                orderCostService.calcMonthBill(ds, month);

            }


        });


        companyInfoList.stream().forEach(comp -> {

            String ds = comp.getDatasource();

            DynamicDataSourceContextHolder.push(ds);
            for (String month : months) {
                orderCostService.buildOrderCostList(ds, month);
            }


        });


        //清空当前线程数据源
        DynamicDataSourceContextHolder.clear();
    }


}
