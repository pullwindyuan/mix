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
import com.futuremap.erp.module.comp.entity.CompanyDataTableInfo;
import com.futuremap.erp.module.comp.entity.CompanyInfo;
import com.futuremap.erp.module.comp.service.impl.CompanyInfoServiceImpl;
import com.futuremap.erp.module.constants.OrderProcessConstants;
import com.futuremap.erp.module.erp.mapper.SaleOrderErpMapper;
import com.futuremap.erp.module.saleorder.entity.SaleOrder;
import com.futuremap.erp.module.saleorder.service.impl.SaleOrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单全过程数据导入
 * @author Administrator
 * @title SaleOrderExportFromErpJob
 * @description TODO
 * @date 2021/5/28 11:35
 */
@Component
@Slf4j
public class SaleOrderExportJob {
    @Autowired
    SaleOrderErpMapper saleOrderErpMapper;
    @Autowired
    CompanyInfoServiceImpl companyInfoService;
    @Autowired
    SaleOrderServiceImpl saleOrderService;
    //每天跑批
    public  void startSaleOrderExportFromErpJob(){
//        String endDate ="2021-02-01";
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate currentDate = LocalDate.parse(endDate, fmt);
//        LocalDate startLocalDate = currentDate.minusMonths(1);
//        String startDate = startLocalDate.format(fmt);
        List<CompanyInfo> companyInfoList = companyInfoService.list();//new QueryWrapper<CompanyInfo>().notIn("id",1,22,23,24)
        companyInfoList.stream().forEach(comp->{
            try {
                String ds = comp.getDatasource();
                DynamicDataSourceContextHolder.push(ds);
                List<SaleOrder> saleOrderList = new ArrayList<>();
                //内销
                List<SaleOrder> saleOrderErpList = saleOrderErpMapper.getSaleOrderList("2021-01-01", "2021-10-01");
                saleOrderErpList.stream().forEach(e->{
                    e.setCompanyCode(comp.getCompanyCode());
                    e.setCompanyName(comp.getCompanyName());
                    e.setExflag(0);
                    e.setStatus(0);
                    saleOrderList.add(e);
                });
                //出口
                List<SaleOrder> exSaleOrderErpList = saleOrderErpMapper.getExSaleOrderList("2021-01-01", "2021-10-01");
                exSaleOrderErpList.stream().forEach(e->{
                    e.setCompanyCode(comp.getCompanyCode());
                    e.setCompanyName(comp.getCompanyName());
                    e.setExflag(1);
                    e.setStatus(0);
                    saleOrderList.add(e);
                });
                //切换默认master
                DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                if(saleOrderList.size()>0){
                    saleOrderService.saveBatch(saleOrderList);
                    saleOrderList.clear();
                }


            } catch (Exception e) {
                log.error(comp.getCompanyName()+"处理销售订单异常",e);
            }
        });
        //清空当前线程数据源
        DynamicDataSourceContextHolder.clear();

    }

    public  void createSaleOrderDetaileView() {
        //切换默认master
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        saleOrderErpMapper.createSaleOrderDetaileView();
        //清空当前线程数据源
        DynamicDataSourceContextHolder.clear();
    }

    public  void createAllData() {
        //切换默认master
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        List<CompanyInfo> companyInfoList = companyInfoService.list();
        companyInfoList.stream().forEach(c->{
            String ds = c.getDatasource();
            DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
            List<CompanyDataTableInfo> companyDataTableInfoList =  saleOrderErpMapper.getAllTableName(ds,  ds + "_erp_table_name", c.getCompanyName(),  c.getCompanyCode());
            DynamicDataSourceContextHolder.push(ds);
            saleOrderErpMapper.createAllData(companyDataTableInfoList, ds + "_erp_table_name");
        });
        //清空当前线程数据源
        DynamicDataSourceContextHolder.clear();
    }
}
