///*
// * Copyright © 2017-2021 http://www.futuremap.com.cn/ All rights reserved.
// *
// * Statement: This document's code after sufficiently has not permitted does not have
// * any way dissemination and the change, once discovered violates the stipulation, will
// * receive the criminal sanction.
// * Address: Building A, block 1F,  Tian'an Yungu Industrial Park phase I,
// *          Xuegang Road, Bantian street, Longgang District, Shenzhen
// * Tel: 0755-22674916
// */
//package com.futuremap.erp.module.operation.service.impl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.futuremap.erp.module.constants.OperationConstants;
//import com.futuremap.erp.module.operation.entity.OperatingStatement;
//import com.futuremap.erp.module.operation.entity.ReportClass;
//import com.futuremap.erp.module.erp.mapper.OperationMapper;
//import com.futuremap.erp.utils.BigDecimalUtil;
//import com.futuremap.erp.utils.CurrencyUtil;
//import jodd.util.StringUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicBoolean;
//
///**
// * @author Administrator
// * @title OperationServiceImpl
// * @description TODO
// * @date 2021/6/9 20:46
// */
//@Service
//@Slf4j
//public class OperationServiceImpl {
//    @Resource
//    OperationMapper operationMapper;
//    @Autowired
//    ReportClassServiceImpl reportClassService;
//
//    @Autowired
//    OperatingStatementServiceImpl operatingStatementService;
//
//    public void operationDataExport(LocalDate currentDate) {
//        LocalDate date = currentDate.minusMonths(1);
////        LocalDate date = LocalDate.parse(beginDate, fmt);
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        DateTimeFormatter fmtYM = DateTimeFormatter.ofPattern("yyyyMM");
//
//        int year = date.getYear();
//        int month = date.getMonth().getValue();
//        String yearMonth = date.format(fmtYM);
//        String beginDate = date.format(fmt);
//        String endDate = currentDate.format(fmt);
//        List<OperatingStatement> operatingStatementArrayList = new ArrayList<>();
//        List<ReportClass> list = reportClassService.list();
//
//        //订单合计
//        List<OperatingStatement> operatingStatements = orderSum(beginDate, endDate, yearMonth);
//        operatingStatementArrayList.addAll(operatingStatements);
//        //实际发货合计
//        List<OperatingStatement> operatingStatements1 = saleFhdSum(beginDate, endDate,yearMonth);
//        operatingStatementArrayList.addAll(operatingStatements1);
//
//
//
//        String ledgerTempTable = "tmpuf_"+yearMonth+"_"+  LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
//        log.info("tmptable:"+ledgerTempTable);
//        operationMapper.getLedgerReport(ledgerTempTable,yearMonth, OperationConstants.GL_13,"5101");
//
//        //产品销售收入
//        List<OperatingStatement> operatingStatements02 = operationRevenueSum(year, month,yearMonth, ledgerTempTable);
//        operatingStatementArrayList.addAll(operatingStatements02);
//        //采购合计
//        List<OperatingStatement> operatingStatements03 = purchSum(beginDate, endDate, year, month,yearMonth);
//        operatingStatementArrayList.addAll(operatingStatements03);
//
//        String tempTable = "tmpuf_"+yearMonth+"_"+  LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
//        log.info("tmptable:"+tempTable);
//        operationMapper.getLedgerReport(tempTable,yearMonth, OperationConstants.GL_14,"1002");
//        //货款回收
//        List<OperatingStatement> operatingStatements2 = paymentRecoverySum(year, month,yearMonth, tempTable);
//        operatingStatementArrayList.addAll(operatingStatements2);
//        //应收账款
//        List<OperatingStatement> operatingStatements04 = accountsReceivablePurchSum(year, month,yearMonth,tempTable);
//        operatingStatementArrayList.addAll(operatingStatements04);
//
//
//        OperatingStatement operatingStatement05 = getOperatingStatement(year, month,yearMonth,OperationConstants.OUTPUT_VALUE_124, null, null, 1, 0, "七、宜心产值 RMB");
//        operatingStatementArrayList.add(operatingStatement05);
//        OperatingStatement operatingStatement06 = getOperatingStatement(year, month,yearMonth,OperationConstants.OUTPUT_VALUE_124, null, null, 2, 1, "宜心外发加工费用 RMB");
//        operatingStatementArrayList.add(operatingStatement06);
//
//        //库存
//        String balanceTempTable = "tmpuf_"+yearMonth+"_"+  LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
//        log.info("tmptable:"+balanceTempTable);
//        operationMapper.getBalanceReport(balanceTempTable,yearMonth);
//        List<OperatingStatement> operatingStatements3 = stockSum(year, month, yearMonth,balanceTempTable);
//        operatingStatementArrayList.addAll(operatingStatements3);
//
//        //营业成本
//        JSONObject operatingCostCountData = operationMapper.getOperatingCostCountData(balanceTempTable);
//        String cexchName = (String) operatingCostCountData.get(OperationConstants.CEXCH_NAME);
//        BigDecimal cost = (BigDecimal) operatingCostCountData.get(OperationConstants.ISUM);
//        OperatingStatement operatingStatements4 = getOperatingStatement(year, month,yearMonth,OperationConstants.SALE_COST_128, operatingCostCountData, "九 、产品销售成本");
//        operatingStatementArrayList.add(operatingStatements4);
//        //营业收入
//        JSONObject operatingIncomeCountData = operationMapper.getOperatingIncomeCountData(balanceTempTable);
//        BigDecimal income = (BigDecimal) operatingIncomeCountData.get(OperationConstants.ISUM);
//        //利润
//        BigDecimal profit = BigDecimalUtil.subtract(income,cost);
//
//
//        //制造费用
//        JSONObject manufacturingCostCountData = operationMapper.getManufacturingCostCountData(balanceTempTable);
//        OperatingStatement operatingStatements7 = getOperatingStatement(year, month,yearMonth,OperationConstants.MANUFACTURING_COST_129, manufacturingCostCountData, "十、制造费用");
//        operatingStatementArrayList.add(operatingStatements7);
//        // 毛利率
//        BigDecimal grossProfit = BigDecimalUtil.devide4(profit, income);
//        BigDecimal persentMultiply = BigDecimalUtil.multiply(grossProfit, new BigDecimal(100));
//        OperatingStatement operatingStatements6 = getOperatingStatement(year, month,yearMonth,OperationConstants.GROSS_PROFIT_130, persentMultiply,cexchName, "十一、毛利率");
//        operatingStatementArrayList.add(operatingStatements6);
//
//        //产品销售费用
//        JSONObject operatingExpensesCountData = operationMapper.getOperatingExpensesCountData(balanceTempTable);
//        OperatingStatement operatingStatements8 = getOperatingStatement(year, month,yearMonth,OperationConstants.SALE_EXPENSES_131, operatingExpensesCountData, "十二、产品销售费用");
//        operatingStatementArrayList.add(operatingStatements8);
//        //产品销售税金
//        JSONObject taxesAndSurchargesCountData = operationMapper.getTaxesAndSurchargesCountData(balanceTempTable);
//        OperatingStatement operatingStatements9 = getOperatingStatement(year, month,yearMonth, OperationConstants.SALE_TAX_132,taxesAndSurchargesCountData, "十三、产品销售税金");
//        operatingStatementArrayList.add(operatingStatements9);
//        //管理费用和营业外收支
//        JSONObject managementExpensesCountData = operationMapper.getManagementExpensesCountData(balanceTempTable);
//        OperatingStatement operatingStatements10 = getOperatingStatement(year, month,yearMonth,OperationConstants.MANAGEMENT_EXPENSES_133, managementExpensesCountData, "十四、管理费用和营业外收支");
//        operatingStatementArrayList.add(operatingStatements10);
//
//        //财务费用
//        JSONObject financialExpensesCountData = operationMapper.getFinancialExpensesCountData(balanceTempTable);
//        OperatingStatement operatingStatements11 = getOperatingStatement(year, month,yearMonth,OperationConstants.FINANCIAL_EXPENSES_134, financialExpensesCountData, "十五、财务费用");
//        operatingStatementArrayList.add(operatingStatements11);
//        //利润
//        OperatingStatement operatingStatements5 = getOperatingStatement(year, month,yearMonth,OperationConstants.PROFIT_135, profit,cexchName, "十六、利润");
//        operatingStatementArrayList.add(operatingStatements5);
//        //所得税
//        JSONObject incomeTaxCountData = operationMapper.getIncomeTaxCountData(balanceTempTable);
//        BigDecimal incomeTax = (BigDecimal) incomeTaxCountData.get(OperationConstants.ISUM);
//        OperatingStatement operatingStatements12 = getOperatingStatement(year, month,yearMonth,OperationConstants.INCOME_TAX_136, incomeTaxCountData, "十七、所得税");
//        operatingStatementArrayList.add(operatingStatements12);
//
//        //净利润
//        BigDecimal netProfit = BigDecimalUtil.subtract(profit, incomeTax);
//        OperatingStatement operatingStatements13 = getOperatingStatement(year, month,yearMonth,OperationConstants.NET_PROFIT_137, netProfit, cexchName, "十八、净利润");
//        operatingStatementArrayList.add(operatingStatements13);
//        //退税款
//        JSONObject taxRefundCountData = operationMapper.getTaxRefundCountData(tempTable);
//        OperatingStatement operatingStatements14 = getOperatingStatement(year, month,yearMonth,OperationConstants.TAX_REFUND_138, taxRefundCountData, "十九、退税款");
//        operatingStatementArrayList.add(operatingStatements14);
//
//
//        //删除临时表
//        operationMapper.dropTempTable(tempTable);
//        operationMapper.dropTempTable(ledgerTempTable);
//        operationMapper.dropTempTable(balanceTempTable);
//
//
//        operatingStatementService.saveBatch(operatingStatementArrayList);
//    }
//
//
//    private OperatingStatement getOperatingStatement(int year, int month,String yearMonth, String code,JSONObject countData,String name) {
//        OperatingStatement operatingStatement = new OperatingStatement();
//        String cexchName =countData==null?OperationConstants.RMB_ZH: (String) countData.get(OperationConstants.CEXCH_NAME);
//        BigDecimal isum =countData==null?BigDecimal.ZERO: (BigDecimal) countData.get(OperationConstants.ISUM);
//        BigDecimal inatsum =countData==null?BigDecimal.ZERO:(BigDecimal) countData.get(OperationConstants.INATSUM);
//        operatingStatement.setCexchName(cexchName);
//        operatingStatement.setIsum(isum);
//        operatingStatement.setInatsum(isum);
//        operatingStatement.setYear(year);
//        operatingStatement.setMonth(month);
//        operatingStatement.setYearmonth(yearMonth);
//        operatingStatement.setParentCode(code);
//        operatingStatement.setLevelType(OperationConstants.LEVEL_TYPE_1);
//        operatingStatement.setSort(0);
//        operatingStatement.setName(name);
//        return operatingStatement;
//    }
//
//    private OperatingStatement getOperatingStatement(int year, int month,String yearMonth,String code, BigDecimal value,String cexchName,String name) {
//        return  getOperatingStatement(year,month,yearMonth,code,value,cexchName,null,null,name);
//
//    }
//    private OperatingStatement getOperatingStatement(int year, int month,String yearMonth,String code, BigDecimal value,String cexchName,Integer levelType,Integer sort,String name) {
//        OperatingStatement operatingStatement = new OperatingStatement();
//        cexchName = StringUtil.isBlank(cexchName)?OperationConstants.RMB_ZH:cexchName;
//        levelType = levelType==null?OperationConstants.LEVEL_TYPE_1:levelType;
//        sort = sort==null?0:sort;
//        value = value==null?BigDecimal.ZERO:value;
//        operatingStatement.setCexchName(cexchName);
//        operatingStatement.setIsum(value);
//        operatingStatement.setInatsum(value);
//        operatingStatement.setYear(year);
//        operatingStatement.setMonth(month);
//        operatingStatement.setYearmonth(yearMonth);
//        operatingStatement.setParentCode(code);
//        operatingStatement.setLevelType(levelType);
//        operatingStatement.setSort(sort);
//        operatingStatement.setName(name);
//        return operatingStatement;
//    }
//
//    /**
//     * 库存
//     * @param year
//     * @param month
//     * @param balanceTempTable
//     * @return
//     */
//    private List<OperatingStatement> stockSum(int year, int month,String yearMonth, String balanceTempTable) {
//        List<OperatingStatement> operatingStatements = new ArrayList<>();
//        //期末库存
//        JSONObject stockCountData = operationMapper.getEndPeriodStockCountData(balanceTempTable);
//        String cexchName = (String) stockCountData.get(OperationConstants.CEXCH_NAME);
//        BigDecimal isum = (BigDecimal) stockCountData.get(OperationConstants.ISUM);
//        OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.STOCK_126,stockCountData,"八、库存 RMB");
//        operatingStatements.add(operatingStatement);
//        //存货周转率
//        //营业成本
//        JSONObject operatingCostCountData = operationMapper.getOperatingCostCountData(balanceTempTable);
//        BigDecimal operatingCost = (BigDecimal) operatingCostCountData.get(OperationConstants.ISUM);
//        //期初库存
//        JSONObject beginStockCountData = operationMapper.getBeginPeriodStockCountData(balanceTempTable);
//        BigDecimal beginStock = (BigDecimal) beginStockCountData.get(OperationConstants.ISUM);
//        BigDecimal avgStock = BigDecimalUtil.devide2(isum.add(beginStock), new BigDecimal(2));
//        //营业成本/存货平均余额
//        BigDecimal itr = BigDecimalUtil.devide4(operatingCost, avgStock);
//        OperatingStatement stockOperatingStatements = getOperatingStatement(year, month,yearMonth,OperationConstants.STOCK_126, itr,cexchName, "存货周转率");
//        operatingStatements.add(stockOperatingStatements);
//        return operatingStatements;
//    }
//
//    /**
//     * 应收账款
//     * @param year
//     * @param month
//     * @return
//     */
//    private List<OperatingStatement> accountsReceivablePurchSum( int year, int month,String yearMonth,String tempTable) {
//        List<OperatingStatement> operatingStatements = new ArrayList<>();
//        BigDecimal orderTotal = BigDecimal.ZERO;
//        final int[] sort = {1};
//
//        List<JSONObject> accountsReceivableCountData = operationMapper.getAccountsReceivableCountData(tempTable);
//        final BigDecimal[] exAccountsReceivableCountDataTotal = {BigDecimal.ZERO};
//        accountsReceivableCountData.stream().forEach(e->{
//            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
//            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
//            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
//            if(!cexchName.equals(OperationConstants.RMB_ZH)){
//                exAccountsReceivableCountDataTotal[0] = exAccountsReceivableCountDataTotal[0].add(inatsum);
//            }
//
//        });
//        //外销折算RMB
//        OperatingStatement operatingStatementEx = getOperatingStatement(year,month,yearMonth,OperationConstants.ACCOUNTS_RECEIVABLE_117,exAccountsReceivableCountDataTotal[0]
//                ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"1、外销 RMB");
//        operatingStatements.add(operatingStatementEx);
//        //内销
//        final BigDecimal[] accountsReceivableTotal = {BigDecimal.ZERO};
//        AtomicBoolean flag = new AtomicBoolean(false);
//        accountsReceivableCountData.stream().forEach(e->{
//            flag.set(true);
//            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
//            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
//            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
//            if(cexchName.equals(OperationConstants.RMB_ZH)){
//                OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.ACCOUNTS_RECEIVABLE_117,isum
//                        ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"2、内销 RMB");
//                accountsReceivableTotal[0] = accountsReceivableTotal[0].add(isum);
//                operatingStatements.add(operatingStatement);
//            }
//        });
//
//        if(!flag.get()){
//            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.ACCOUNTS_RECEIVABLE_117,BigDecimal.ZERO
//                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"2、内销 RMB");
//            operatingStatements.add(operatingStatement);
//        }
//
//
//        //应收汇总 折合RMB
//        orderTotal = exAccountsReceivableCountDataTotal[0].add(accountsReceivableTotal[0]);
//        OperatingStatement operatingStatementTotal = getOperatingStatement(year,month,yearMonth,OperationConstants.ACCOUNTS_RECEIVABLE_117,orderTotal,OperationConstants.RMB_ZH,"五、应收账款 折合RMB");
//        operatingStatements.add(operatingStatementTotal);
//
//        return operatingStatements;
//    }
//
//    /**
//     * 采购汇总
//     * @param beginDate
//     * @param endDate
//     * @param year
//     * @param month
//     * @return
//     */
//    private List<OperatingStatement> purchSum(String beginDate, String endDate, int year, int month,String yearMonth) {
//        List<OperatingStatement> operatingStatements = new ArrayList<>();
//        BigDecimal orderTotal = BigDecimal.ZERO;
//        final int[] sort = {1};
//
//        List<JSONObject> purchCountData = operationMapper.getPurchCountData(beginDate, endDate);
//        final BigDecimal[] exPurchTotal = {BigDecimal.ZERO};
//        purchCountData.stream().forEach(e->{
//            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
//            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
//            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
//            if(!cexchName.equals(OperationConstants.RMB)){
//                exPurchTotal[0] = exPurchTotal[0].add(inatsum);
//            }
//
//        });
//        //外销折算RMB
//        OperatingStatement operatingStatementEx =  getOperatingStatement(year,month,yearMonth,OperationConstants.PURCHASE_120,exPurchTotal[0]
//                ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"1、外币采购 RMB");
//        operatingStatements.add(operatingStatementEx);
//        //内销RMB
//        final BigDecimal[] purchTotal = {BigDecimal.ZERO};
//        AtomicBoolean flag = new AtomicBoolean(false);
//        purchCountData.stream().forEach(e->{
//            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
//            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
//            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
//            if(cexchName.equals(OperationConstants.RMB_ZH)){
//                flag.set(true);
//                OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.PURCHASE_120,isum
//                        ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"2、人民币采购 "+OperationConstants.RMB);
//                purchTotal[0] = purchTotal[0].add(isum);
//                operatingStatements.add(operatingStatement);
//            }
//        });
//        if(!flag.get()){
//            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.PURCHASE_120,BigDecimal.ZERO
//                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"2、人民币采购 RMB");
//            operatingStatements.add(operatingStatement);
//        }
//
//
//        JSONObject inPurchCountData = operationMapper.getInPurchCountData(beginDate, endDate);
//        BigDecimal isum = (BigDecimal) inPurchCountData.get("isum");
//        OperatingStatement purchOperatingStatements = getOperatingStatement(year, month, yearMonth,OperationConstants.PURCHASE_120,isum,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++, "3、其中：内销采购");
//        operatingStatements.add(purchOperatingStatements);
//        //采购汇总 折合RMB
//        orderTotal = exPurchTotal[0].add(purchTotal[0]);
//        OperatingStatement operatingStatementTotal = getOperatingStatement(year,month,yearMonth,OperationConstants.PURCHASE_120,orderTotal,OperationConstants.RMB_ZH,"六、采购 折合RMB");
//        operatingStatements.add(operatingStatementTotal);
//        return operatingStatements;
//    }
//    /**
//     * 产品销售收入汇总
//     * @param year
//     * @param month
//     * @param tempTable
//     * @return
//     */
//    private List<OperatingStatement> operationRevenueSum(int year, int month,String yearMonth, String tempTable) {
//        List<OperatingStatement> operatingStatements = new ArrayList<>();
//        BigDecimal orderTotal = BigDecimal.ZERO;
//        final int[] sort = {1};
//
//        List<JSONObject> operationRevenueCountData = operationMapper.getOperationRevenueCountData(tempTable);
//        final BigDecimal[] exOperationRevenueTotal = {BigDecimal.ZERO};
//        AtomicBoolean exflag = new AtomicBoolean(false);
//        //外销
//        operationRevenueCountData.stream().forEach(e->{
//            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
//            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
//            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
//            if(!cexchName.equals(OperationConstants.RMB_ZH)){
//                exflag.set(true);
//                OperatingStatement operatingStatement = new OperatingStatement();
//                operatingStatement.setCexchName(cexchName);
//                operatingStatement.setIsum(isum);
//                operatingStatement.setInatsum(inatsum);
//                operatingStatement.setYear(year);
//                operatingStatement.setMonth(month);
//                operatingStatement.setYearmonth(yearMonth);
//                operatingStatement.setParentCode(OperationConstants.SALESREVENUE_109);
//                operatingStatement.setLevelType(OperationConstants.LEVEL_TYPE_2);
//                operatingStatement.setSort(sort[0]++);
//                operatingStatement.setName("1、"+OperationConstants.EX_ZH+" "+ CurrencyUtil.convertCode(cexchName));
//                exOperationRevenueTotal[0] = exOperationRevenueTotal[0].add(inatsum);
//                operatingStatements.add(operatingStatement);
//            }
//
//        });
//
//        if(!exflag.get()){
//            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.SALESREVENUE_109,BigDecimal.ZERO
//                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"1、外销 USD");
//            operatingStatements.add(operatingStatement);
//        }
//
//
//
//        //外销折算RMB
//        OperatingStatement operatingStatementEx = getOperatingStatement(year,month,yearMonth,OperationConstants.SALESREVENUE_109,exOperationRevenueTotal[0]
//                ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,OperationConstants.EX_ZH+" 折算RMB金额");
//        operatingStatements.add(operatingStatementEx);
//        //内销
//        final BigDecimal[] operationRevenueTotal = {BigDecimal.ZERO};
//        AtomicBoolean flag = new AtomicBoolean(false);
//        operationRevenueCountData.stream().forEach(e->{
//            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
//            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
//            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
//            if(cexchName.equals(OperationConstants.RMB_ZH)){
//                flag.set(true);
//                OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.SALESREVENUE_109,isum
//                        ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"2、内销 RMB");
//                operationRevenueTotal[0] = operationRevenueTotal[0].add(isum);
//                operatingStatements.add(operatingStatement);
//            }
//        });
//        if(!flag.get()){
//            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.SALESREVENUE_109,BigDecimal.ZERO
//                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"2、内销 RMB");
//            operatingStatements.add(operatingStatement);
//        }
//        //产品销售收入 折合RMB
//        orderTotal = exOperationRevenueTotal[0].add(operationRevenueTotal[0]);
//        OperatingStatement operatingStatementTotal = getOperatingStatement(year,month,yearMonth,OperationConstants.SALESREVENUE_109,orderTotal,OperationConstants.RMB_ZH,"三、产品销售收入 折合RMB");
//        operatingStatements.add(operatingStatementTotal);
//
//        return  operatingStatements;
//    }
//    /**
//     * 本月货款回收额汇总
//     * @param year
//     * @param month
//     * @param tempTable
//     * @return
//     */
//    private List<OperatingStatement> paymentRecoverySum(int year, int month, String yearMonth,String tempTable) {
//        List<OperatingStatement> operatingStatements = new ArrayList<>();
//        BigDecimal orderTotal = BigDecimal.ZERO;
//        final int[] sort = {1};
//
////        List<JSONObject> exPaymentRecoveryCountData = operationMapper.getExPaymentRecoveryCountData(tempTable);
//        List<JSONObject> paymentRecoveryCountData = operationMapper.getPaymentRecoveryCountData(tempTable);
//        final BigDecimal[] exOperationRevenueTotal = {BigDecimal.ZERO};
//        AtomicBoolean exflag = new AtomicBoolean(false);
//        //外销
//        paymentRecoveryCountData.stream().forEach(e->{
//            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
//            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
//            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
//            if(!cexchName.equals(OperationConstants.RMB_ZH)){
//                exflag.set(true);
//                OperatingStatement operatingStatement = new OperatingStatement();
//                operatingStatement.setCexchName(cexchName);
//                operatingStatement.setIsum(isum);
//                operatingStatement.setInatsum(inatsum);
//                operatingStatement.setYear(year);
//                operatingStatement.setMonth(month);
//                operatingStatement.setYearmonth(yearMonth);
//                operatingStatement.setParentCode(OperationConstants.RECOVERY_AMOUNT_113);
//                operatingStatement.setLevelType(OperationConstants.LEVEL_TYPE_2);
//                operatingStatement.setSort(sort[0]++);
//                operatingStatement.setName("1、"+OperationConstants.EX_ZH+" "+CurrencyUtil.convertCode(cexchName));
//                exOperationRevenueTotal[0] = exOperationRevenueTotal[0].add(inatsum);
//                operatingStatements.add(operatingStatement);
//            }
//
//        });
//        if(!exflag.get()){
//            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.RECOVERY_AMOUNT_113,BigDecimal.ZERO
//                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"1、外销 USD");
//            operatingStatements.add(operatingStatement);
//        }
//
//        //外销折算RMB
//        OperatingStatement operatingStatementEx = getOperatingStatement(year,month,yearMonth,OperationConstants.RECOVERY_AMOUNT_113,exOperationRevenueTotal[0]
//                ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,OperationConstants.EX_ZH+" 折算RMB金额");
//        operatingStatements.add(operatingStatementEx);
//        //内销
//
//        final BigDecimal[] operationRevenueTotal = {BigDecimal.ZERO};
//        AtomicBoolean flag = new AtomicBoolean(false);
//        paymentRecoveryCountData.stream().forEach(e->{
//            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
//            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
//            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
//            if(cexchName.equals(OperationConstants.RMB_ZH)){
//                flag.set(true);
//                OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.RECOVERY_AMOUNT_113,isum
//                        ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"2、内销 RMB");
//                operationRevenueTotal[0] = operationRevenueTotal[0].add(isum);
//                operatingStatements.add(operatingStatement);
//            }
//        });
//        if(!flag.get()){
//            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.RECOVERY_AMOUNT_113,BigDecimal.ZERO
//                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"2、内销 RMB");
//            operatingStatements.add(operatingStatement);
//        }
//
//        //本月货款回收额 折合RMB
//        orderTotal = exOperationRevenueTotal[0].add(operationRevenueTotal[0]);
//        OperatingStatement operatingStatementTotal = getOperatingStatement(year,month,yearMonth,OperationConstants.RECOVERY_AMOUNT_113,orderTotal,OperationConstants.RMB_ZH,"四、本月货款回收额 折合RMB");
//        operatingStatements.add(operatingStatementTotal);
//
//        return  operatingStatements;
//    }
//
//    /**
//     * 实际发货退货汇总
//     * @param beginDate
//     * @param endDate
//     * @return
//     */
//    private List<OperatingStatement> saleFhdSum(String beginDate, String endDate,String yearMonth) {
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate date = LocalDate.parse(beginDate, fmt);
//        int year = date.getYear();
//        int month = date.getMonth().getValue();
//        List<OperatingStatement> operatingStatements = new ArrayList<>();
//        BigDecimal orderTotal = BigDecimal.ZERO;
//        final int[] sort = {1};
//
//        List<JSONObject> exOutCountData = operationMapper.getExOutCountData(beginDate, endDate);
//        final BigDecimal[] exOutTotal = {BigDecimal.ZERO};
//        //外销
//        exOutCountData.stream().forEach(e->{
//            String cexchCode = (String) e.get(OperationConstants.CEXCH_CODE);
//            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
//            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
////            if(!cexchName.equals(OperationConstants.RMB)){
//            OperatingStatement operatingStatement = new OperatingStatement();
//            operatingStatement.setCexchName(CurrencyUtil.convertName(cexchCode));
//            operatingStatement.setIsum(isum);
//            operatingStatement.setInatsum(inatsum);
//            operatingStatement.setYear(year);
//            operatingStatement.setMonth(month);
//            operatingStatement.setYearmonth(yearMonth);
//            operatingStatement.setParentCode(OperationConstants.DELIVERY_104);
//            operatingStatement.setLevelType(OperationConstants.LEVEL_TYPE_2);
//            operatingStatement.setSort(sort[0]++);
//            operatingStatement.setName("1、"+OperationConstants.EX_ZH+"实际发货 "+cexchCode);
//            exOutTotal[0] = exOutTotal[0].add(inatsum);
//            operatingStatements.add(operatingStatement);
////            }
//        });
//
//        if(exOutCountData==null||exOutCountData.size()<=0){
//            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.DELIVERY_104,BigDecimal.ZERO
//                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"1、外销实际发货 USD");
//            operatingStatements.add(operatingStatement);
//        }
//
//        //外销折算RMB
//        OperatingStatement operatingStatementEx =getOperatingStatement(year,month,yearMonth,OperationConstants.DELIVERY_104,exOutTotal[0]
//                ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,OperationConstants.EX_ZH+" 实际发货 折算RMB金额");
//        operatingStatements.add(operatingStatementEx);
//        //内销
//        List<JSONObject> saleFhdCountData = operationMapper.getSaleFhdCountData(beginDate, endDate);
//        final BigDecimal[] saleFhdTotal = {BigDecimal.ZERO};
//        saleFhdCountData.stream().forEach(e->{
//            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
//            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
//            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
////            if(cexchName.equals(OperationConstants.RMB)){
//            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.DELIVERY_104,isum
//                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"2、内销实际发货 RMB");
//            saleFhdTotal[0] =saleFhdTotal[0].add(isum);
//            operatingStatements.add(operatingStatement);
////            }
//        });
//        if(saleFhdCountData==null||saleFhdCountData.size()<=0){
//            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.DELIVERY_104,BigDecimal.ZERO
//                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"2、内销实际发货 RMB");
//            operatingStatements.add(operatingStatement);
//        }
//
//        //内销实收退货额
//        List<JSONObject> dispatchCountData = operationMapper.getDispatchCountData(beginDate, endDate);
//        final BigDecimal[] dispatchTotal = {BigDecimal.ZERO};
//        dispatchCountData.stream().forEach(e->{
//            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
//            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
//            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
////            if(cexchName.equals(OperationConstants.RMB)){
//            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.DELIVERY_104,isum
//                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"3、内销实际退货金额");
//            dispatchTotal[0] = dispatchTotal[0].add(isum);
//            operatingStatements.add(operatingStatement);
////            }
//        });
//        if(dispatchCountData==null||dispatchCountData.size()<=0){
//            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.DELIVERY_104,BigDecimal.ZERO
//                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"3、内销实际退货金额");
//            operatingStatements.add(operatingStatement);
//        }
//
//        //实际发货减退货折合RMB
//        orderTotal = exOutTotal[0].add(saleFhdTotal[0]).subtract(dispatchTotal[0]);
//        OperatingStatement operatingStatementTotal = getOperatingStatement(year,month,yearMonth,OperationConstants.DELIVERY_104,orderTotal,OperationConstants.RMB_ZH,"二、实际发货减退货折合RMB");
//        operatingStatements.add(operatingStatementTotal);
//
//        return  operatingStatements;
//    }
//
//    /**
//     * 订单汇总统计
//     * @param beginDate
//     * @param endDate
//     * @return
//     */
//    private List<OperatingStatement> orderSum(String beginDate, String endDate,String yearMonth) {
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate date = LocalDate.parse(beginDate, fmt);
//        int year = date.getYear();
//        int month = date.getMonth().getValue();
//        List<OperatingStatement> operatingStatements = new ArrayList<>();
//        BigDecimal orderTotal = BigDecimal.ZERO;
//        final int[] sort = {1};
//        //外销
//        List<JSONObject> exOrderCountData = operationMapper.getExOrderCountData(beginDate,endDate);
//        final BigDecimal[] exOrderTotal = {BigDecimal.ZERO};
//        exOrderCountData.stream().forEach(e -> {
//            OperatingStatement operatingStatement = new OperatingStatement();
//            String cexchCode = (String) e.get(OperationConstants.CEXCH_CODE);
//            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
//            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
//
//            operatingStatement.setCexchName(CurrencyUtil.convertName(cexchCode));
//            operatingStatement.setIsum(isum);
//            operatingStatement.setInatsum(inatsum);
//            operatingStatement.setYear(year);
//            operatingStatement.setMonth(month);
//            operatingStatement.setYearmonth(yearMonth);
//            operatingStatement.setParentCode(OperationConstants.ORDER_100);
//            operatingStatement.setSort(sort[0]++);
//            operatingStatement.setLevelType(OperationConstants.LEVEL_TYPE_2);
//            operatingStatement.setName("1、"+OperationConstants.EX_ZH + cexchCode);
//            exOrderTotal[0] = exOrderTotal[0].add(inatsum);
//            operatingStatements.add(operatingStatement);
//        });
//        if(exOrderCountData==null||exOrderCountData.size()<=0){
//            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.ORDER_100,BigDecimal.ZERO
//                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"1、外销 USD");
//            operatingStatements.add(operatingStatement);
//        }
//
//        //外销折算RMB
//        OperatingStatement operatingStatementEx = getOperatingStatement(year,month,yearMonth,OperationConstants.ORDER_100,exOrderTotal[0]
//                , OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,OperationConstants.EX_ZH+"折算RMB金额");
//        operatingStatements.add(operatingStatementEx);
//
//        //内销折算RMB
//        List<JSONObject> saleOrderCountData = operationMapper.getSaleOrderCountData( beginDate,endDate);
//        final BigDecimal[] saleOrderTotal = {BigDecimal.ZERO};
//        saleOrderCountData.stream().forEach(e -> {
//            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
//            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
//            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
//            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.ORDER_100,isum
//                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"2、内销 RMB");
//            saleOrderTotal[0] = saleOrderTotal[0].add(isum);
//            operatingStatements.add(operatingStatement);
//        });
//        if(saleOrderCountData==null||saleOrderCountData.size()<=0){
//            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.ORDER_100,BigDecimal.ZERO
//                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"2、内销 RMB");
//            operatingStatements.add(operatingStatement);
//        }
//
//        //订单合计
//        orderTotal = saleOrderTotal[0].add(exOrderTotal[0]);
//        OperatingStatement operatingStatementTotal = getOperatingStatement(year,month,yearMonth,OperationConstants.ORDER_100,orderTotal,OperationConstants.RMB_ZH,"一、订单折合RMB");
//        operatingStatements.add(operatingStatementTotal);
//
//        return  operatingStatements;
//    }
//}
