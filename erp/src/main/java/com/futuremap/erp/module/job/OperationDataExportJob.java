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
import com.futuremap.erp.module.comp.entity.CompanyInfo;
import com.futuremap.erp.module.comp.service.impl.CompanyInfoServiceImpl;
import com.futuremap.erp.module.constants.OperationConstants;
import com.futuremap.erp.module.constants.OrderProcessConstants;
import com.futuremap.erp.module.operation.entity.OperatingStatement;
import com.futuremap.erp.module.operation.service.impl.OperatingStatementServiceImpl;
import com.futuremap.erp.utils.BigDecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.futuremap.erp.module.constants.OperationConstants.COMPANY_9999;

/**
 * @author Administrator
 * @title OperationDataExportJob
 * @description TODO
 * @date 2021/6/1 15:09
 */
@Component
@Slf4j
public class OperationDataExportJob {

    @Autowired
    OperatingStatementServiceImpl operatingStatementService;

    @Autowired
    CompanyInfoServiceImpl companyInfoService;
    //@Scheduled(cron="")
    public List<OperatingStatement> startOperationDataExportJob(){
        //月初1号统计上月
//        LocalDate currentDate = LocalDate.now();
        String startDate ="2021-05-01";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(startDate, fmt);

        return export(currentDate);
    }
    //@Scheduled(cron="")
    public void startHistoryOperationDataExportJob(){
        String startDate ="2021-01-01";
        String endDate = "2021-09-01";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(startDate, fmt);
        LocalDate lastDate = LocalDate.parse(endDate, fmt);
        while (currentDate.compareTo(lastDate)<=0){
            export(currentDate);
            currentDate = currentDate.plusMonths(1);
        }

    }
    private List<OperatingStatement> export(LocalDate currentDate) {
        List<CompanyInfo> companyInfoList = companyInfoService.list();//new QueryWrapper<CompanyInfo>().notIn("id",1)

        LocalDate date = currentDate.minusMonths(1);
//        LocalDate date = LocalDate.parse(beginDate, fmt);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter fmtYM = DateTimeFormatter.ofPattern("yyyyMM");

        int year = date.getYear();
        int month = date.getMonth().getValue();
        String yearMonth = date.format(fmtYM);
        List<OperatingStatement> resultList = new ArrayList<>();

        Map<String,Map<String, OperatingStatement>> stockMap = new HashMap<>();
        Map<String, List<OperatingStatement>> OperatingStatementListMap = new HashMap<>();

        companyInfoList.stream().forEach(comp-> {
                try {
                    String ds = comp.getDatasource();
                    String companyCode = comp.getCompanyCode();
                    DynamicDataSourceContextHolder.push(ds);
                    Map<String, List<OperatingStatement>> stringListMap = operatingStatementService.operationDataExport(currentDate,companyCode);
                    List<OperatingStatement> operatingStatements = stringListMap.get("other");
                    OperatingStatementListMap.put(companyCode, stringListMap.get("sum"));
                    operatingStatements.stream().forEach(e->{
                        e.setCompanyCode(companyCode);
                        resultList.add(e);
                    });
                    List<OperatingStatement> stockStatements = stringListMap.get("stock");
                    Map<String, OperatingStatement> collectMap = stockStatements.stream().collect(Collectors.toMap(OperatingStatement::getName, Function.identity(), (k1, k2) -> k1));
                    stockMap.put(companyCode,collectMap);
                } catch (Exception e) {
                    log.error(comp.getCompanyName()+"经营数据统计异常",e);
                }
        });

        //切换默认master
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        operatingStatementService.saveBatch(resultList);
       // 保存多公司汇总数据
        List<OperatingStatement> countList = operatingStatementService.countCompany(currentDate);
        List<OperatingStatement> sumResultList = new ArrayList<>();
        final BigDecimal[] incomeSum = {BigDecimal.ZERO};
        countList.stream().forEach(e->{
            e.setCompanyCode(OperationConstants.COMPANY_SUM_CODE);
            if(!e.getName().equals("存货周转率")&&!e.getName().equals("十一、毛利率")){
                sumResultList.add(e);
            }
            if(e.getClassCode().equals(OperationConstants.SALESREVENUE_109)){
                incomeSum[0] = e.getIsum();
            }
        });

        Iterator<Map.Entry<String, Map<String, OperatingStatement>>> iterator = stockMap.entrySet().iterator();
        BigDecimal costSum = BigDecimal.ZERO;
        BigDecimal beginStockSum = BigDecimal.ZERO;
        BigDecimal endStockSum = BigDecimal.ZERO;
        BigDecimal profitSum = BigDecimal.ZERO;
        while (iterator.hasNext()){
            Map.Entry<String, Map<String, OperatingStatement>> next = iterator.next();
            Map<String, OperatingStatement> value = next.getValue();

            costSum = BigDecimalUtil.add(costSum,value.get("营业成本").getIsum());
            beginStockSum = BigDecimalUtil.add(beginStockSum,value.get("期初库存").getIsum());
            endStockSum = BigDecimalUtil.add(endStockSum,value.get("期末库存").getIsum());
            profitSum = BigDecimalUtil.add(profitSum,value.get("利润").getIsum());

        }
        //存货平均余额
        BigDecimal avgStock = BigDecimalUtil.devide2(beginStockSum.add(endStockSum), new BigDecimal(2));

        //营业成本/存货平均余额
        BigDecimal itr = BigDecimalUtil.devide4(costSum, avgStock);
        OperatingStatement stockOperatingStatements = operatingStatementService.getOperatingStatement(year, month,yearMonth, OperationConstants.STOCK_126,OperationConstants.STOCK_TURNOVER_127, itr,OperationConstants.RMB_ZH, 2,null,"存货周转率");
        //添加存货周转率
        stockOperatingStatements.setCompanyCode(OperationConstants.COMPANY_SUM_CODE);
        sumResultList.add(stockOperatingStatements);

        BigDecimal grossProfit = BigDecimalUtil.devide4(profitSum, incomeSum[0]);
        BigDecimal persentMultiply = BigDecimalUtil.multiply(grossProfit, new BigDecimal(100));
        //添加毛利率
        OperatingStatement operatingStatements6 = operatingStatementService.getOperatingStatement(year, month,yearMonth,OperationConstants.GROSS_PROFIT_130,OperationConstants.GROSS_PROFIT_130, persentMultiply,OperationConstants.RMB_ZH,2,null, "十一、毛利率");
        operatingStatements6.setCompanyCode(OperationConstants.COMPANY_SUM_CODE);
        sumResultList.add(operatingStatements6);

        operatingStatementService.saveBatch(sumResultList);

        DynamicDataSourceContextHolder.clear();
        return resultList;
    }



}
