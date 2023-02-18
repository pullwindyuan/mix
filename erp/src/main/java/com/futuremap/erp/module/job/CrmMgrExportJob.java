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
import com.futuremap.erp.module.constants.OrderProcessConstants;
import com.futuremap.erp.module.orderCost.service.impl.CrmMgrServiceImpl;
import com.futuremap.erp.module.orderCost.service.impl.SaleKpiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CrmMgrExportJob {
    @Autowired
    CrmMgrServiceImpl crmMgrServiceImpl;

    @Autowired
    CompanyInfoServiceImpl companyInfoService;

    public void startCrmMgrExportFromErpJob() {
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        List<CompanyInfo> companyInfoList = companyInfoService.list(new QueryWrapper<CompanyInfo>().notIn("id", 25));
        List<String> months = new ArrayList<>();
        months.add("202105");
        months.add("202106");
        months.add("202107");

        companyInfoList.stream().forEach(comp -> {
            String ds = comp.getDatasource();
            DynamicDataSourceContextHolder.push(ds);
            for (String month : months) {
                crmMgrServiceImpl.buildCrmMgrList(ds, month,new ArrayList<>());
            }
        });
    }


}
