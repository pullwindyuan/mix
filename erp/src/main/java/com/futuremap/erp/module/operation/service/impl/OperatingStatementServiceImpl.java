package com.futuremap.erp.module.operation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.module.constants.OperationConstants;
import com.futuremap.erp.module.erp.mapper.OperationMapper;
import com.futuremap.erp.module.operation.entity.CcodeMapping;
import com.futuremap.erp.module.operation.entity.OperatingStatement;
import com.futuremap.erp.module.operation.entity.OperatingStatementQuery;
import com.futuremap.erp.module.operation.entity.OperatingStatementSubQuery;
import com.futuremap.erp.module.operation.mapper.OperatingStatementMapper;
import com.futuremap.erp.module.operation.service.IOperatingStatementService;
import com.futuremap.erp.utils.BeanCopierUtils;
import com.futuremap.erp.utils.BeanUtil;
import com.futuremap.erp.utils.BigDecimalUtil;
import com.futuremap.erp.utils.CurrencyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.futuremap.erp.module.constants.OperationConstants.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-01
 */
@Service
@Slf4j
public class OperatingStatementServiceImpl extends ServiceImpl<OperatingStatementMapper, OperatingStatement> implements IOperatingStatementService {
    @Resource
    OperationMapper operationMapper;
    @Autowired
    ReportClassServiceImpl reportClassService;
    @Autowired
    CcodeMappingServiceImpl ccodeMappingService;

    public static Map<String, Map<String,CcodeMapping>> ccodeMap = new ConcurrentHashMap<>();
    @PostConstruct
    public void getCcodeMapping(){
        List<CcodeMapping> list = ccodeMappingService.list();
//      Map<String, CcodeMapping> ccodeMap = list.stream().collect(Collectors.toMap(CcodeMapping::getCompanyCode, CcodeMapping -> CcodeMapping));
        Map<String, List<CcodeMapping>> collect = list.stream().collect(Collectors.groupingBy(CcodeMapping::getCompanyCode));
        Iterator<Map.Entry<String, List<CcodeMapping>>> iterator = collect.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, List<CcodeMapping>> next = iterator.next();
            String key = next.getKey();
            List<CcodeMapping> value = next.getValue();
            Map<String, CcodeMapping> collect1 = value.stream().collect(Collectors.toMap(CcodeMapping::getCcodeClass, CcodeMapping -> CcodeMapping));
            ccodeMap.put(key,collect1);
        }
    }

    public Map<String,List<OperatingStatement>> operationDataExport(LocalDate currentDate, String companyCode) {

        LocalDate date = currentDate.minusMonths(1);
        LocalDate startDate = date.minusMonths(ACTIVE_CUSTOMER_MONTHS);
//        LocalDate date = LocalDate.parse(beginDate, fmt);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter fmtYM = DateTimeFormatter.ofPattern("yyyyMM");

        int year = date.getYear();
        int month = date.getMonth().getValue();
        String yearMonth = date.format(fmtYM);
        String beginDate = date.format(fmt);
        String endDate = currentDate.format(fmt);

        String activeCustomerStartYearMonth = startDate.format(fmtYM);
        String activeCustomerEndYearMonth = yearMonth;
        List<OperatingStatement> operatingStatementArrayList = new ArrayList<>();
//        List<ReportClass> list = reportClassService.list();

        Map<String,List<OperatingStatement>> resultMap=new HashMap<>();

        //根据公司代码获取其贸易范围
        String tradeString = TRADE_TYPE.get(companyCode);
        JSONObject tradeType = JSONObject.parseObject(tradeString);
        //根据公司代码获取需要排除的其他关联公司
        String except = EXCEPT_COMPANY.get(companyCode);
        //根据公司代码获取对应的收款科目代码。注：每个公司的收款科目代码都可能不同
        String ccode = ccodeMap.get(companyCode).get(OperationConstants.ACCOUNTS_RECEIVABLE_117).getCcode();
        //拼装收款科目查询条件
        ccode = ccode.substring(0, ccode.length() - 1).concat("%'");
        String codeCondition = "like " + ccode;
        String settleCodeCondition = "like " + SETTLE_TYPE.get(companyCode);
        //主营销售收入代码查询条件
        String incomingCode = ccodeMap.get(companyCode).get(OperationConstants.COIN_EXPRISE_139).getCcode();
        incomingCode = incomingCode.substring(0, incomingCode.length() - 1).concat("%'");
        String incomingCodeCondition = "like " + incomingCode;

        //订单合计，这里没有使用临时表，内外销本来就是通过不同的表或者视图查询的，所以不需要用部门来区分内外销
        List<OperatingStatement> operatingStatements = orderSum(beginDate, endDate, yearMonth,tradeType);
        operatingStatementArrayList.addAll(operatingStatements);
        //实际发货合计，这里没有使用临时表，内外销本来就是通过不同的表或者视图查询的，所以不需要用部门来区分内外销
        List<OperatingStatement> operatingStatements1 = saleFhdSum(beginDate, endDate,yearMonth,tradeType);
        operatingStatementArrayList.addAll(operatingStatements1);

        //执行GL_Ledger存储过程是为了得到临时表,如果改为使用正式表就不在需要这个调用了
//        String ledgerTempTable = "tmpuf_"+yearMonth+"_"+  LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
//        log.info("tmptable:"+ledgerTempTable);
//        operationMapper.getLedgerReport(ledgerTempTable,yearMonth, OperationConstants.GL_13,String.valueOf(ccodeMap.get(companyCode).get(OperationConstants.SALESREVENUE_109).getCcode()));

        //产品销售收入
        List<OperatingStatement> operatingStatements02 = operationRevenueSum(
                year,
                month,
                yearMonth,
                codeCondition,
                tradeType,
                incomingCodeCondition);

        operatingStatementArrayList.addAll(operatingStatements02);

        //执行GL_Ledger存储过程是为了得到临时表,如果改为使用正式表就不在需要这个调用了
//        String tempTable = "tmpuf_"+yearMonth+"_"+  LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
//        log.info("tmptable:"+tempTable);
//        operationMapper.getLedgerReport(tempTable,yearMonth, OperationConstants.GL_14,ccodeMap.get(companyCode).get(OperationConstants.RECOVERY_AMOUNT_113).getCcode());

        //货款回收

        List<OperatingStatement> operatingStatements2 = paymentRecoverySum(
                year,
                month,
                yearMonth,
                codeCondition,
                settleCodeCondition,
                tradeType,
                except);
        operatingStatementArrayList.addAll(operatingStatements2);

        //执行GL_Ledger存储过程是为了得到临时表,如果改为使用正式表就不在需要这个调用了
//        String tempTableTwo = "tmpuf_"+yearMonth+"_"+  LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
//        log.info("tmptable:"+ledgerTempTable);
//        operationMapper.getLedgerReport(tempTableTwo,yearMonth, OperationConstants.GL_13, ccodeMap.get(companyCode).get(OperationConstants.ACCOUNTS_RECEIVABLE_117).getCcode());

        //应收账款
        List<OperatingStatement> operatingStatements04 = accountsReceivablePurchSum(
                year,
                month,
                yearMonth,
                activeCustomerStartYearMonth,
                codeCondition,
                tradeType,
                except);
        operatingStatementArrayList.addAll(operatingStatements04);
        resultMap.put("sum",operatingStatements04);

        //采购合计
        List<OperatingStatement> operatingStatements03 = purchSum(beginDate, endDate, year, month,yearMonth,companyCode);
        operatingStatementArrayList.addAll(operatingStatements03);
        //第七项
        OperatingStatement operatingStatement05 = getOperatingStatement(year, month,yearMonth,OperationConstants.OUTPUT_VALUE_124,OperationConstants.OUTPUT_VALUE_124, null, null, 1, 0, "七、宜心产值 RMB");
        operatingStatementArrayList.add(operatingStatement05);
        OperatingStatement operatingStatement06 = getOperatingStatement(year, month,yearMonth,OperationConstants.OUTPUT_VALUE_124,OperationConstants.PROCESSING_COST_125, null, null, 2, 1, "宜心外发加工费用 RMB");
        operatingStatementArrayList.add(operatingStatement06);

        //这个调用存储过程的代码不再需要了，已经全部改成使用正式表或者视图来获取数据
//        String balanceTempTable = "tmpuf_"+yearMonth+"_"+  LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
//        log.info("tmptable:"+balanceTempTable);
//        operationMapper.getBalanceReport(balanceTempTable,yearMonth);

        //期末库存
        JSONObject stockCountData = operationMapper.getEndPeriodStockCountData(
                //balanceTempTable,
                yearMonth,
                ccodeMap.get(companyCode).get(OperationConstants.STOCK_126).getCcode());
        BigDecimal stockEnd = BigDecimal.ZERO;
        if (stockCountData!=null&&stockCountData.get(OperationConstants.INATSUM)!=null){
            stockEnd = (BigDecimal) stockCountData.get(OperationConstants.INATSUM);
        }
        OperatingStatement operatingStatements3 = getOperatingStatement(year,month,yearMonth,OperationConstants.STOCK_126,OperationConstants.STOCK_126,stockCountData,"八、库存 RMB");
        operatingStatementArrayList.add(operatingStatements3);

        //周转率
        //期初库存
        JSONObject beginStockCountData = operationMapper.getBeginPeriodStockCountData(
                //balanceTempTable,
                yearMonth,
                ccodeMap.get(companyCode).get(OperationConstants.STOCK_126).getCcode());
        BigDecimal stockBegin = BigDecimal.ZERO;
        if (beginStockCountData!=null&&beginStockCountData.get(OperationConstants.INATSUM)!=null){
            stockBegin = (BigDecimal) beginStockCountData.get(OperationConstants.INATSUM);
        }

        //营业成本
        JSONObject operatingCostCountData = operationMapper.getOperatingCostCountData(
                //balanceTempTable,
                yearMonth,
                ccodeMap.get(companyCode).get(OperationConstants.SALE_COST_128).getCcode());
//        String cexchName = (String) operatingCostCountData.get(OperationConstants.CEXCH_NAME);
        BigDecimal cost = BigDecimal.ZERO;
        if (operatingCostCountData!=null&&operatingCostCountData.get(OperationConstants.INATSUM)!=null){
            cost = (BigDecimal) operatingCostCountData.get(OperationConstants.INATSUM);
        }
        OperatingStatement operatingStatements4 = getOperatingStatement(year, month,yearMonth,OperationConstants.SALE_COST_128,OperationConstants.SALE_COST_128, operatingCostCountData, "九 、产品销售成本");
        operatingStatementArrayList.add(operatingStatements4);

        //制造费用
        JSONObject manufacturingCostCountData = operationMapper.getManufacturingCostCountData(
                //balanceTempTable,
                yearMonth,
                ccodeMap.get(companyCode).get(OperationConstants.MANUFACTURING_COST_129).getCcode());
        OperatingStatement operatingStatements7 = getOperatingStatement(year, month,yearMonth,OperationConstants.MANUFACTURING_COST_129,OperationConstants.MANUFACTURING_COST_129, manufacturingCostCountData, "十、制造费用");
        operatingStatementArrayList.add(operatingStatements7);

        BigDecimal avgStock = BigDecimalUtil.devide2(stockEnd.add(stockBegin), new BigDecimal(2));
        //营业成本/存货平均余额
        BigDecimal itr = BigDecimalUtil.devide4(cost, avgStock);
        OperatingStatement stockOperatingStatements = getOperatingStatement(year, month,yearMonth,OperationConstants.STOCK_126,OperationConstants.STOCK_TURNOVER_127, itr,OperationConstants.RMB_ZH, "存货周转率");
        stockOperatingStatements.setLevelType(LEVEL_TYPE_2);
        operatingStatementArrayList.add(stockOperatingStatements);

        //计算汇总周转率用
        OperatingStatement beginStock = getOperatingStatement(year,month,yearMonth,OperationConstants.STOCK_126,OperationConstants.STOCK_126,beginStockCountData,"期初库存");
        OperatingStatement endStock = getOperatingStatement(year,month,yearMonth,OperationConstants.STOCK_126,OperationConstants.STOCK_126,stockCountData,"期末库存");
        OperatingStatement stockCost = getOperatingStatement(year, month,yearMonth,OperationConstants.SALE_COST_128,OperationConstants.SALE_COST_128, operatingCostCountData, "营业成本");
        List<OperatingStatement>  stockList = new ArrayList<>();
        stockList.add(beginStock);
        stockList.add(endStock);
        stockList.add(stockCost);

        //产品销售费用
        JSONObject operatingExpensesCountData = operationMapper.getOperatingExpensesCountData(
                //balanceTempTable,
                yearMonth,
                ccodeMap.get(companyCode).get(OperationConstants.SALE_EXPENSES_131).getCcode());
        OperatingStatement operatingStatements8 = getOperatingStatement(year, month,yearMonth,
                OperationConstants.SALE_EXPENSES_131,OperationConstants.SALE_EXPENSES_131,
                operatingExpensesCountData, "十二、产品销售费用");
        operatingStatementArrayList.add(operatingStatements8);

        //管理费用和营业外收支
        JSONObject managementExpensesCountData = operationMapper.getManagementExpensesCountData(
                //balanceTempTable,
                yearMonth,
                ccodeMap.get(companyCode).get(OperationConstants.MANAGEMENT_EXPENSES_133).getCcode());
        OperatingStatement operatingStatements10 = getOperatingStatement(year, month,yearMonth,
                OperationConstants.MANAGEMENT_EXPENSES_133, OperationConstants.MANAGEMENT_EXPENSES_133,
                managementExpensesCountData, "十四、管理费用和营业外收支");
        operatingStatementArrayList.add(operatingStatements10);

        //财务费用
        JSONObject financialExpensesCountData = operationMapper.getFinancialExpensesCountData(
                //balanceTempTable,
                yearMonth,
                ccodeMap.get(companyCode).get(OperationConstants.FINANCIAL_EXPENSES_134).getCcode());
        OperatingStatement operatingStatements11 = getOperatingStatement(year, month,yearMonth,
                OperationConstants.FINANCIAL_EXPENSES_134,OperationConstants.FINANCIAL_EXPENSES_134,
                financialExpensesCountData, "十五、财务费用");
        operatingStatementArrayList.add(operatingStatements11);

        //产品销售税金
        JSONObject taxesAndSurchargesCountData = operationMapper.getTaxesAndSurchargesCountData(
                //balanceTempTable,
                yearMonth,
                ccodeMap.get(companyCode).get(OperationConstants.SALE_TAX_132).getCcode());
        BigDecimal tax = BigDecimal.ZERO;
        if (taxesAndSurchargesCountData!=null&&taxesAndSurchargesCountData.get(OperationConstants.INATSUM)!=null){
            tax = (BigDecimal) taxesAndSurchargesCountData.get(OperationConstants.INATSUM);
        }
        OperatingStatement operatingStatements9 = getOperatingStatement(year, month,yearMonth,
                OperationConstants.SALE_TAX_132,OperationConstants.SALE_TAX_132,
                taxesAndSurchargesCountData, "十三、产品销售税金");
        operatingStatementArrayList.add(operatingStatements9);

        OperatingStatement operatingStatement = operatingStatements02.stream()
                .filter(e -> e.getClassCode().equals(OperationConstants.SALESREVENUE_109))
                .findFirst()
                .get();
        BigDecimal income =operatingStatement.getIsum();


        //减去主营业务成本（cost 就是）
        BigDecimal profit = BigDecimalUtil.subtract(income,cost);

        // 毛利率 : 主营业务收入 - 主营业务成本 - 产品销售税金 / 主营业务收入
        BigDecimal noTaxProfit = BigDecimalUtil.subtract(profit, tax);
        //计算汇总毛利率用
        OperatingStatement profitStatement = getOperatingStatement(year, month,yearMonth,OperationConstants.PROFIT_135,OperationConstants.PROFIT_135, noTaxProfit,OperationConstants.RMB_ZH, "利润");
        stockList.add(profitStatement);

        BigDecimal grossProfit = BigDecimalUtil.devide4(noTaxProfit, income);
        BigDecimal persentMultiply = BigDecimalUtil.multiply(grossProfit, new BigDecimal(100));
        OperatingStatement operatingStatements6 = getOperatingStatement(year, month,yearMonth,OperationConstants.GROSS_PROFIT_130,OperationConstants.GROSS_PROFIT_130, persentMultiply,OperationConstants.RMB_ZH, "十一、毛利率");
        operatingStatementArrayList.add(operatingStatements6);

        //利润: 主营业务收入 - 主营业务成本 - 产品销售费用 - 产品销售税金 - 财务费用 - 管理费用和营业外收支
        //减去产品销售费用
        noTaxProfit = BigDecimalUtil.subtract(noTaxProfit, operatingStatements8.getInatsum());

        //减去管理费用和营业外收支
        noTaxProfit = BigDecimalUtil.subtract(noTaxProfit, operatingStatements10.getInatsum());
        //减去财务费用
        noTaxProfit = BigDecimalUtil.subtract(noTaxProfit, operatingStatements11.getInatsum());

        OperatingStatement operatingStatements5 = getOperatingStatement(year, month,yearMonth,OperationConstants.PROFIT_135,OperationConstants.PROFIT_135, noTaxProfit,OperationConstants.RMB_ZH, "十六、利润");
        operatingStatementArrayList.add(operatingStatements5);

        //所得税
        JSONObject incomeTaxCountData = operationMapper.getIncomeTaxCountData(
                //balanceTempTable,
                yearMonth,
                ccodeMap.get(companyCode).get(OperationConstants.INCOME_TAX_136).getCcode());
        BigDecimal incomeTax = BigDecimal.ZERO;
        if (incomeTaxCountData!=null&&incomeTaxCountData.get(OperationConstants.INATSUM)!=null){
            incomeTax = (BigDecimal) incomeTaxCountData.get(OperationConstants.INATSUM);
        }
        OperatingStatement operatingStatements12 = getOperatingStatement(year, month,yearMonth,OperationConstants.INCOME_TAX_136,OperationConstants.INCOME_TAX_136, incomeTaxCountData, "十七、所得税");
        operatingStatementArrayList.add(operatingStatements12);

        //净利润
        BigDecimal netProfit =BigDecimalUtil.subtract(noTaxProfit, incomeTax);
        OperatingStatement operatingStatements13 = getOperatingStatement(year, month,yearMonth,OperationConstants.NET_PROFIT_137,OperationConstants.NET_PROFIT_137, netProfit, OperationConstants.RMB_ZH, "十八、净利润");
        operatingStatementArrayList.add(operatingStatements13);
        String tempTableThree = "tmpuf_"+yearMonth+"_"+  LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        String ledgerTempTable = "tmpuf_"+yearMonth+"_"+  LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        log.info("tmptable:"+ledgerTempTable);
        operationMapper.getLedgerReport(tempTableThree,yearMonth, OperationConstants.GL_13,ccodeMap.get(companyCode).get(OperationConstants.TAX_REFUND_138).getCcode());

        //退税款
        JSONObject taxRefundCountData = operationMapper.getTaxRefundCountData(
                //tempTableThree
                yearMonth,
                ccodeMap.get(companyCode).get(OperationConstants.TAX_REFUND_138).getCcode());
        OperatingStatement operatingStatements14 = getOperatingStatement(year, month,yearMonth,OperationConstants.TAX_REFUND_138,OperationConstants.TAX_REFUND_138, taxRefundCountData, "十九、退税款");
        operatingStatementArrayList.add(operatingStatements14);


        //删除临时表
//        operationMapper.dropTempTable(tempTable);
//        operationMapper.dropTempTable(tempTableTwo);
//        operationMapper.dropTempTable(tempTableThree);
//        operationMapper.dropTempTable(ledgerTempTable);
//        operationMapper.dropTempTable(balanceTempTable);

        resultMap.put("other",operatingStatementArrayList);
        resultMap.put("stock",stockList);

//        saveBatch(operatingStatementArrayList);
        return  resultMap;
    }


    private OperatingStatement getOperatingStatement(int year, int month,String yearMonth, String code,String classCode,JSONObject countData,String name) {
        OperatingStatement operatingStatement = new OperatingStatement();
        String cexchName =countData==null?OperationConstants.RMB_ZH: (String) countData.get(OperationConstants.CEXCH_NAME);
        BigDecimal isum =countData==null?BigDecimal.ZERO: (BigDecimal) countData.get(OperationConstants.ISUM);
        BigDecimal inatsum =countData==null?BigDecimal.ZERO:(BigDecimal) countData.get(OperationConstants.INATSUM);
        operatingStatement.setCexchName(cexchName);
        operatingStatement.setIsum(inatsum);
        operatingStatement.setInatsum(inatsum);
        operatingStatement.setYear(year);
        operatingStatement.setMonth(month);
        operatingStatement.setYearmonth(yearMonth);
        operatingStatement.setParentCode(code);
        operatingStatement.setClassCode(classCode);
        operatingStatement.setLevelType(OperationConstants.LEVEL_TYPE_1);
        operatingStatement.setSort(0);
        operatingStatement.setName(name);
        return operatingStatement;
    }

    public OperatingStatement getOperatingStatement(int year, int month, String yearMonth, String code, String classCode, BigDecimal value, String cexchName, String name) {
        return  getOperatingStatement(year,month,yearMonth,code,classCode,value,cexchName,null,null,name);

    }
    public OperatingStatement getOperatingStatement(int year, int month, String yearMonth, String code, String classCode, BigDecimal value, String cexchName, Integer levelType, Integer sort, String name) {
        OperatingStatement operatingStatement = new OperatingStatement();
        cexchName = StringUtils.isBlank(cexchName)?OperationConstants.RMB_ZH:cexchName;
        levelType = levelType==null?OperationConstants.LEVEL_TYPE_1:levelType;
        sort = sort==null?0:sort;
        value = value==null?BigDecimal.ZERO:value;
        operatingStatement.setCexchName(cexchName);
        operatingStatement.setIsum(value);
        operatingStatement.setInatsum(value);
        operatingStatement.setYear(year);
        operatingStatement.setMonth(month);
        operatingStatement.setYearmonth(yearMonth);
        operatingStatement.setParentCode(code);
        operatingStatement.setClassCode(classCode);
        operatingStatement.setLevelType(levelType);
        operatingStatement.setSort(sort);
        operatingStatement.setName(name);
        return operatingStatement;
    }

    /**
     * 库存
     * @param year
     * @param month
     * @param balanceTempTable
     * @return
     */
//    private List<OperatingStatement> stockSum(int year, int month,String yearMonth, String balanceTempTable) {
//        List<OperatingStatement> operatingStatements = new ArrayList<>();
//        //期末库存
//        JSONObject stockCountData = operationMapper.getEndPeriodStockCountData(balanceTempTable);
//        OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.STOCK_126,OperationConstants.STOCK_126,stockCountData,"八、库存 RMB");
//        operatingStatements.add(operatingStatement);
//        BigDecimal isum = BigDecimal.ZERO;
//        if(stockCountData!=null){
//            BigDecimal inatsum = (BigDecimal)stockCountData.get(OperationConstants.INATSUM);
//            isum = inatsum==null?BigDecimal.ZERO:inatsum;
//        }

        //存货周转率
//        //营业成本
//        JSONObject operatingCostCountData = operationMapper.getOperatingCostCountData(balanceTempTable);
//        BigDecimal operatingCost = BigDecimal.ZERO;
//        if(operatingCostCountData!=null){
//            BigDecimal inatsum = (BigDecimal)operatingCostCountData.get(OperationConstants.INATSUM);
//            operatingCost =inatsum==null?BigDecimal.ZERO:inatsum;
//        }

//        BigDecimal avgStock = BigDecimalUtil.devide2(isum.add(beginStock), new BigDecimal(2));
//        //营业成本/存货平均余额
//        BigDecimal itr = BigDecimalUtil.devide4(operatingCost, avgStock);
//        OperatingStatement stockOperatingStatements = getOperatingStatement(year, month,yearMonth,OperationConstants.STOCK_126,OperationConstants.STOCK_TURNOVER_127, itr,OperationConstants.RMB_ZH, "存货周转率");
//        operatingStatements.add(stockOperatingStatements);
//        return operatingStatements;
//    }

    /**
     * 应收账款
     * @param year
     * @param month
     * @return
     */
    private List<OperatingStatement> accountsReceivablePurchSum( int year,
                                                                 int month,
                                                                 String yearMonth,
                                                                 String activeCustomerStartYearMonth,
                                                                 String codeCondition,
                                                                 JSONObject departCondition,
                                                                 String except) {
        List<OperatingStatement> operatingStatements = new ArrayList<>();
        BigDecimal orderTotal = BigDecimal.ZERO;
        final int[] sort = {1};

        //String table = "GL_AccMultiAss";
        //获取每月收款折合人民币总和，包括本币和外币
        //List<String> except = Arrays.asList(new String[]{"EASI001", "9999", "W0001", "HD154", "HD151"});
        //String codeCondition = "like '1131%'";
        //String departCondition = "like '0105%'";
        List<String> yearMonthList = Arrays.asList(new String[]{yearMonth});
        List<JSONObject> accountsReceivableCountData = operationMapper.getAccountsReceivableTotalDataByMonth(
                codeCondition,
                activeCustomerStartYearMonth,
                yearMonth,
                ACTIVE_CUSTOMER_MONTHS);
        BigDecimal isum;
        BigDecimal inatsum;
        if(accountsReceivableCountData.size()  > 0 && accountsReceivableCountData.get(0) != null) {
            isum = accountsReceivableCountData.get(0).getBigDecimal("me");
            inatsum = isum;
            //应收汇总 折合RMB
            OperatingStatement operatingStatementTotal = getOperatingStatement(
                    year,
                    month,
                    yearMonth,
                    OperationConstants.ACCOUNTS_RECEIVABLE_117,
                    OperationConstants.ACCOUNTS_RECEIVABLE_117,
                    inatsum,
                    OperationConstants.RMB_ZH,
                    "五、应收账款 折合RMB");
            operatingStatements.add(operatingStatementTotal);
        }

        //外销
        //String departCondition = "like '0105%'";
        String externalDepart = departCondition.getString(EXTERNAL_TRADE);
        String domesticDepart = departCondition.getString(DOMESTIC_TRADE);

        if(externalDepart != null) {
            //不为null说明有外销
            if(externalDepart.equals(SINGLE_TRADE)) {
                //进入这个条件说明该企业只存在外销，不需要区分条件
                externalDepart = null;
            }else {
                externalDepart = "like '" + externalDepart + "%'";
            }
            accountsReceivableCountData = operationMapper.getAccountsReceivableTotalDataByMonthAndDepart(
                    codeCondition,
                    activeCustomerStartYearMonth,
                    yearMonth,
                    ACTIVE_CUSTOMER_MONTHS,
                    externalDepart);
            if(accountsReceivableCountData.size() > 0 && accountsReceivableCountData.get(0) != null) {
                Map<String, BigDecimal> isumMap = new HashMap<>();
                Map<String, BigDecimal> inatsumMap = new HashMap<>();
                accountsReceivableCountData.forEach(e->{
                    String cexchName = e.getString("cexch_name");
                    BigDecimal mc_f = e.getBigDecimal("me_f");
                    BigDecimal mc = e.getBigDecimal("me");
                    if(cexchName != null && !cexchName.equals("人民币") && !cexchName.equals("欧元") && !cexchName.equals("美元") ) {
                        log.debug("++++++++++++++++++++++++++++++++++\n" +
                                "++++++++++++++++++++++++++++++++++++++\n" +
                                "++++++++++++++++++++++++++++++++++++\n" +
                                "+++特殊币种：" + cexchName + "+++++++++++++++\n" +
                                "+++++++++++++++++++++++++++++++++++++++\n" +
                                "++++++++++++++++++++++++++++++++++++++\n" +
                                "++++++++++++++++++++++++++++++++++++++\n");
                    }
                    if(RMB_ZH.equals(cexchName) || cexchName == null) {//人民币
                        inatsumMap.put(RMB_ZH, mc);
                        isumMap.put(RMB_ZH, mc);
                    }else {
                        inatsumMap.put(cexchName, mc_f);
                        isumMap.put(cexchName, mc);
                    }
                });
                inatsumMap.forEach((k,v)->{
                    OperatingStatement operatingStatementEx = getOperatingStatement(
                            year,
                            month,
                            yearMonth,
                            OperationConstants.ACCOUNTS_RECEIVABLE_117,
                            OperationConstants.EX_ACCOUNTS_RECEIVABLE_118 + k.hashCode(),
                            v,
                            k,
                            OperationConstants.LEVEL_TYPE_2,
                            sort[0] ++,
                            "外销： " + k);
                    operatingStatements.add(operatingStatementEx);
                    if(!RMB_ZH.equals(k)) {
                        OperatingStatement operatingStatementExRMB = getOperatingStatement(
                                year,
                                month,
                                yearMonth,
                                OperationConstants.ACCOUNTS_RECEIVABLE_117,
                                OperationConstants.EX_ACCOUNTS_RECEIVABLE_1180+ k.hashCode(),
                                isumMap.get(k),
                                k,
                                OperationConstants.LEVEL_TYPE_2,
                                sort[0] ++,
                                //OperationConstants.EX_ZH +
                                        "外销：" + k + "折算RMB");
                        operatingStatements.add(operatingStatementExRMB);
                    }

                });
            }

        }

        //内销
        isum = BigDecimal.ZERO;
        inatsum = isum;
        if(domesticDepart != null) {
            //不为null说明有内销
            if(domesticDepart.equals(SINGLE_TRADE)) {
                //进入这个条件说明该企业只存在内销，不需要区分条件
                domesticDepart = null;
            }else {
                domesticDepart = "like '" + domesticDepart + "%'";
            }
            accountsReceivableCountData = operationMapper.getAccountsReceivableTotalDataByMonthAndDepart(
                    codeCondition,
                    activeCustomerStartYearMonth,
                    yearMonth,
                    ACTIVE_CUSTOMER_MONTHS,
                    domesticDepart);
            if (accountsReceivableCountData.size() > 0 && accountsReceivableCountData.get(0) != null) {
                isum = accountsReceivableCountData.get(0).getBigDecimal("me");
                inatsum = isum;
                //exAccountsReceivableCountDataTotal[0] = exAccountsReceivableCountDataTotal[0].add(inatsum);

            }
            OperatingStatement operatingStatementDom = getOperatingStatement(
                    year,
                    month,
                    yearMonth,
                    OperationConstants.ACCOUNTS_RECEIVABLE_117,
                    OperationConstants.ACCOUNTS_RECEIVABLE_SUM_119,
                    inatsum,
                    OperationConstants.RMB_ZH,
                    OperationConstants.LEVEL_TYPE_2,
                    sort[0],
                    "内销 RMB");
            operatingStatements.add(operatingStatementDom);
        }

        return operatingStatements;
    }

    /**
     * 采购汇总
     * @param beginDate
     * @param endDate
     * @param year
     * @param month
     * @return
     */
    private List<OperatingStatement> purchSum(String beginDate, String endDate, int year, int month,String yearMonth,String companyCode) {
        List<OperatingStatement> operatingStatements = new ArrayList<>();
        BigDecimal orderTotal = BigDecimal.ZERO;
        final int[] sort = {1};
        //外币采购
        List<JSONObject> purchCountData = operationMapper.getPurchCountData(beginDate, endDate);

        final BigDecimal[] exPurchTotal = {BigDecimal.ZERO};
        purchCountData.stream().forEach(e->{
            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
            if(!cexchName.equals(OperationConstants.RMB_ZH)){
                exPurchTotal[0] = exPurchTotal[0].add(inatsum);
            }

        });
        //委外
        List<JSONObject> outSourcePurchCountData = operationMapper.getOutSourcePurchCountData(beginDate, endDate);
        outSourcePurchCountData.stream().forEach(e->{
            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
            if(!cexchName.equals(OperationConstants.RMB_ZH)){
                exPurchTotal[0] = exPurchTotal[0].add(inatsum);
            }

        });

        //外币采购折算RMB
        OperatingStatement operatingStatementEx =  getOperatingStatement(year,month,yearMonth,OperationConstants.PURCHASE_120,OperationConstants.EX_COIN_121,exPurchTotal[0]
                ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"1、外币采购 RMB");
        operatingStatements.add(operatingStatementEx);

        //RMB采购
        final BigDecimal[] purchTotal = {BigDecimal.ZERO};
        purchCountData.stream().forEach(e->{
            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
            if(cexchName.equals(OperationConstants.RMB_ZH)){
                purchTotal[0] = purchTotal[0].add(isum);
            }
        });
        //委外
        outSourcePurchCountData.stream().forEach(e->{
            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
            if(cexchName.equals(OperationConstants.RMB_ZH)){
                purchTotal[0] = purchTotal[0].add(isum);
            }
        });


        OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.PURCHASE_120,OperationConstants.RMB_COIN_122,purchTotal[0]
                ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"2、人民币采购 RMB");
        operatingStatements.add(operatingStatement);
        //获取内销相关的采购
        BigDecimal isum = BigDecimal.ZERO;
        if(companyCode.equals(OperationConstants.COMPANY_HD151)){//智拓全是内销
                isum=purchTotal[0];
        }else {
            JSONObject inPurchCountData = operationMapper.getInPurchCountData(beginDate, endDate);
            if (inPurchCountData != null && inPurchCountData.get("isum") != null){
                isum = (BigDecimal) inPurchCountData.get("isum");
            }
            //委外
            JSONObject outSourceInPurchCountData = operationMapper.getOutSourceInPurchCountData(beginDate, endDate);
            if (outSourceInPurchCountData != null && outSourceInPurchCountData.get("isum") != null){
                isum = isum.add((BigDecimal) outSourceInPurchCountData.get("isum"));
            }
        }
        OperatingStatement purchOperatingStatements = getOperatingStatement(year, month, yearMonth,
                OperationConstants.PURCHASE_120,
                OperationConstants.PURCHASE_SUM_123,
                isum,
                OperationConstants.RMB_ZH,
                OperationConstants.LEVEL_TYPE_2,
                sort[0]++, "3、其中：内销采购");
        operatingStatements.add(purchOperatingStatements);

        //采购汇总 折合RMB

        orderTotal = exPurchTotal[0].add(purchTotal[0]);
        OperatingStatement operatingStatementTotal = getOperatingStatement(year,month,yearMonth,OperationConstants.PURCHASE_120,OperationConstants.PURCHASE_120,orderTotal,OperationConstants.RMB_ZH,"六、采购 折合RMB");
        operatingStatements.add(operatingStatementTotal);

        //外销相关的采购用汇总减去内销即可
        BigDecimal isumEx = orderTotal.subtract(isum);
        OperatingStatement purchOperatingStatementsEx = getOperatingStatement(year, month, yearMonth,
                OperationConstants.PURCHASE_120,
                OperationConstants.PURCHASE_SUM_1230,
                isumEx,
                OperationConstants.RMB_ZH,
                OperationConstants.LEVEL_TYPE_2,
                sort[0]++, "4、其中：外销采购");
        operatingStatements.add(purchOperatingStatementsEx);

        return operatingStatements;
    }
    /**
     * 产品销售收入汇总
     * 2021-10-11:决定使用账套区分内外销
     * @param year
     * @param month
     * @return
     */
    private List<OperatingStatement> operationRevenueSum(
            int year,
            int month,
            String yearMonth,
            String codeCondition,
            JSONObject departCondition,
            String incomingCodeCondition) {
        List<OperatingStatement> operatingStatements = new ArrayList<>();
        BigDecimal orderTotal = BigDecimal.ZERO;
        final int[] sort = {1};

        final BigDecimal[] exOperationRevenueTotal = {BigDecimal.ZERO};
        AtomicBoolean exflag = new AtomicBoolean(false);

        //汇总
        List<JSONObject> operationRevenueCountData = operationMapper.getOperationAllRevenueCountData(
                //codeCondition,
                null,
                incomingCodeCondition,
                yearMonth);

        BigDecimal isum;
        BigDecimal inatsum;
        inatsum = BigDecimal.ZERO;
        if(operationRevenueCountData.size() > 0 && operationRevenueCountData.get(0) != null) {
            isum = operationRevenueCountData.get(0).getBigDecimal("mc");
            inatsum = isum;
        }

        OperatingStatement operatingStatementTotal = getOperatingStatement(
                year,
                month,
                yearMonth,
                OperationConstants.SALESREVENUE_109,
                OperationConstants.SALESREVENUE_109,
                inatsum,
                OperationConstants.RMB_ZH,
                "三、产品销售收入 折合RMB");
        operatingStatements.add(operatingStatementTotal);

        //外销
        //String departCondition = "like '0105%'";
        String externalDepart = departCondition.getString(EXTERNAL_TRADE);
        String domesticDepart = departCondition.getString(DOMESTIC_TRADE);
        final BigDecimal[] innerToExTotal = {BigDecimal.ZERO};
        if(externalDepart != null) {
            //不为null说明有外销
            if(externalDepart.equals(SINGLE_TRADE)) {
                //进入这个条件说明该企业只存在外销，不需要区分条件
                externalDepart = null;
            }else {
                //  注意：这里区分内外销稍有差别，内销能够获取到客户信息，而外销获取不到，是null,所以条件要和其他不一样
                externalDepart = "like '" + externalDepart + "%'";
                //假内销实外销折算RMB
                //只有内外销都有的企业才需要区分假内销
                List<JSONObject>  operationRevenueInAsExCountData = operationMapper.getOperationRevenueCountData(
                        externalDepart,
                        incomingCodeCondition,
                        yearMonth);
                if(operationRevenueInAsExCountData.size() > 0 && operationRevenueInAsExCountData.get(0) != null) {
                    operationRevenueInAsExCountData.stream().forEach(e -> {
                        String cexchName= (String) e.get(CEXCH_NAME);
                        BigDecimal inAsExIsum = (BigDecimal) e.get("mc");
                        BigDecimal inAsExInatsum = inAsExIsum;
                        innerToExTotal[0] = innerToExTotal[0].add(inAsExIsum);
                        log.info("----假内销实外销折算RMB:" + inAsExIsum.toString());
                    });
                    OperatingStatement operatingStatementInnerToEx = getOperatingStatement(year, month, yearMonth, OperationConstants.SALESREVENUE_109, OperationConstants.EX_SALESREVENUE_SUM_1110, innerToExTotal[0]
                            , OperationConstants.RMB_ZH, OperationConstants.LEVEL_TYPE_2, sort[0]++, "外销：RMB");
                    operatingStatements.add(operatingStatementInnerToEx);
                }
                //  注意：这里区分内外销稍有差别，内销能够获取到客户信息，而外销获取不到，是null,所以条件要和其他不一样
                externalDepart = "is null";
            }
            operationRevenueCountData = operationMapper.getOperationRevenueCountData(
                    //codeCondition,
                    externalDepart,
                    incomingCodeCondition,
                    yearMonth);
            if(operationRevenueCountData.size() > 0 && operationRevenueCountData.get(0) != null) {
                Map<String, BigDecimal> isumMap = new HashMap<>();
                Map<String, BigDecimal> inatsumMap = new HashMap<>();
                operationRevenueCountData.forEach(e->{
                    String cexchName = e.getString("cexch_name");
                    BigDecimal mc_f = e.getBigDecimal("mc_f");
                    BigDecimal mc = e.getBigDecimal("mc");
                    if(RMB_ZH.equals(cexchName) || cexchName == null) {//人民币
                        inatsumMap.put(RMB_ZH, mc);
                        isumMap.put(RMB_ZH, mc);
                    }else {
                        inatsumMap.put(cexchName, mc_f);
                        isumMap.put(cexchName, mc);
                    }
                });
                inatsumMap.forEach((k,v)->{
                    if(v.doubleValue() != 0) {
                        OperatingStatement operatingStatementEx = getOperatingStatement(
                                year,
                                month,
                                yearMonth,
                                OperationConstants.SALESREVENUE_109,
                                OperationConstants.EX_SALESREVENUE_110 + k.hashCode(),
                                v,
                                k,
                                OperationConstants.LEVEL_TYPE_2,
                                sort[0]++,
                                "外销： " + k);
                        operatingStatements.add(operatingStatementEx);
                    }
                    if (!RMB_ZH.equals(k)) {
                        OperatingStatement operatingStatementExRMB = getOperatingStatement(
                                year,
                                month,
                                yearMonth,
                                OperationConstants.SALESREVENUE_109,
                                OperationConstants.EX_SALESREVENUE_SUM_111 + k.hashCode(),
                                isumMap.get(k),
                                k,
                                OperationConstants.LEVEL_TYPE_2,
                                sort[0]++,
                                "外销：" + k + "折算RMB");
                        operatingStatements.add(operatingStatementExRMB);
                    }
                });
            }
        }

        //内销
        isum = BigDecimal.ZERO;
        inatsum = isum;
        if(domesticDepart != null) {
            //不为null说明有内销
            if(domesticDepart.equals(SINGLE_TRADE)) {
                //进入这个条件说明该企业只存在内销，不需要区分条件
                domesticDepart = null;
            }else {
                domesticDepart = "like '" + domesticDepart + "%'";
            }
            operationRevenueCountData = operationMapper.getOperationRevenueCountData(
                    //codeCondition,
                    domesticDepart,
                    incomingCodeCondition,
                    yearMonth);
            if (operationRevenueCountData.size() > 0 && operationRevenueCountData.get(0) != null) {
                isum = operationRevenueCountData.get(0).getBigDecimal("mc");
                inatsum = isum;
                //exAccountsReceivableCountDataTotal[0] = exAccountsReceivableCountDataTotal[0].add(inatsum);

            }
            OperatingStatement operatingStatementDom = getOperatingStatement(
                    year,
                    month,
                    yearMonth,
                    OperationConstants.SALESREVENUE_109,
                    OperationConstants.SALESREVENUE_SUM_112,
                    inatsum,
                    OperationConstants.RMB_ZH,
                    OperationConstants.LEVEL_TYPE_2,
                    sort[0],
                    "内销 RMB");
            operatingStatements.add(operatingStatementDom);
        }

        return  operatingStatements;
    }
    /**
     * 本月货款回收额汇总
     * @param year
     * @param month
     * @param codeCondition         用于币种科目筛选
     * @param settleCodeCondition   用于筛选结算科目
     * @param yearMonth             用于时间筛选
     * @param departCondition       用于区分内销和外销
     * @param except                用于排除集团下子公司
     *
     * @return
     */
    private List<OperatingStatement> paymentRecoverySum(int year,
                                                        int month,
                                                        String yearMonth,
                                                        String codeCondition,
                                                        String settleCodeCondition,
                                                        JSONObject departCondition,
                                                        String except) {
        List<OperatingStatement> operatingStatements = new ArrayList<>();
        BigDecimal orderTotal = BigDecimal.ZERO;
        final int[] sort = {1};

        //本月货款回收额 折合RMB
        List<JSONObject> paymentRecoveryCountData = operationMapper.getAllPaymentRecoveryCountData(
                codeCondition,
                settleCodeCondition,
                yearMonth,
                null);

        BigDecimal isum, isum1;
        BigDecimal inatsum, inatsum1;
        if(paymentRecoveryCountData.size() > 0 && paymentRecoveryCountData.get(0) != null) {
            isum = paymentRecoveryCountData.get(0).getBigDecimal("md");
            inatsum = isum;
            //本月货款回收额汇总 折合RMB
            OperatingStatement operatingStatementTotal = getOperatingStatement(
                    year,
                    month,
                    yearMonth,
                    OperationConstants.RECOVERY_AMOUNT_113,
                    OperationConstants.RECOVERY_AMOUNT_113,
                    inatsum,
                    OperationConstants.RMB_ZH,
                    "四、本月货款回收额 折合RMB");
            operatingStatements.add(operatingStatementTotal);
        }

        //外销
        //String departCondition = "like '0105%'";
        String externalDepart = departCondition.getString(EXTERNAL_TRADE);
        String domesticDepart = departCondition.getString(DOMESTIC_TRADE);

        if(externalDepart != null) {
            //不为null说明有外销
            if(externalDepart.equals(SINGLE_TRADE)) {
                //进入这个条件说明该企业只存在外销，不需要区分条件
                externalDepart = null;
            }else {
                externalDepart = "like '" + externalDepart + "%'";
            }
            paymentRecoveryCountData = operationMapper.getPaymentRecoveryCountData(
                    codeCondition,
                    settleCodeCondition,
                    yearMonth,
                    externalDepart);
            if(paymentRecoveryCountData.size() > 0 && paymentRecoveryCountData.get(0) != null) {
                Map<String, BigDecimal> isumMap = new HashMap<>();
                Map<String, BigDecimal> inatsumMap = new HashMap<>();
                paymentRecoveryCountData.forEach(e->{
                    String cexchName = e.getString("cexch_name");
                    BigDecimal mc_f = e.getBigDecimal("md_f");
                    BigDecimal mc = e.getBigDecimal("md");
                    if(RMB_ZH.equals(cexchName) || cexchName == null) {//人民币
                        inatsumMap.put(RMB_ZH, mc);
                        isumMap.put(RMB_ZH, mc);
                    }else {
                        inatsumMap.put(cexchName, mc_f);
                        isumMap.put(cexchName, mc);
                    }
                });
                inatsumMap.forEach((k,v)->{
                    OperatingStatement operatingStatementEx = getOperatingStatement(
                            year,
                            month,
                            yearMonth,
                            OperationConstants.RECOVERY_AMOUNT_113,
                            OperationConstants.EX_RECOVERY_AMOUNT_114 + k.hashCode(),
                            v,
                            k,
                            OperationConstants.LEVEL_TYPE_2,
                            sort[0] ++,
                             "外销 ：" + k);
                    operatingStatements.add(operatingStatementEx);
                    if(!RMB_ZH.equals(k)) {
                        OperatingStatement operatingStatementExRMB = getOperatingStatement(
                                year,
                                month,
                                yearMonth,
                                OperationConstants.RECOVERY_AMOUNT_113,
                                OperationConstants.EX_RECOVERY_AMOUNT_SUM_115 + + k.hashCode(),
                                isumMap.get(k),
                                k,
                                OperationConstants.LEVEL_TYPE_2,
                                sort[0] ++,
                                "外销：" + k + "折算RMB");
                        operatingStatements.add(operatingStatementExRMB);
                    }

                });
            }

        }

        //内销
        isum = BigDecimal.ZERO;
        inatsum = isum;
        if(domesticDepart != null) {
            //不为null说明有内销
            if(domesticDepart.equals(SINGLE_TRADE)) {
                //进入这个条件说明该企业只存在内销，不需要区分条件
                domesticDepart = null;
            }else {
                domesticDepart = "like '" + domesticDepart + "%'";
            }
            paymentRecoveryCountData = operationMapper.getPaymentRecoveryCountData(
                    codeCondition,
                    settleCodeCondition,
                    yearMonth,
                    domesticDepart);
            if (paymentRecoveryCountData.size() > 0 && paymentRecoveryCountData.get(0) != null) {
                isum = paymentRecoveryCountData.get(0).getBigDecimal("md");
                inatsum = isum;
                //exAccountsReceivableCountDataTotal[0] = exAccountsReceivableCountDataTotal[0].add(inatsum);

            }
            OperatingStatement operatingStatementDom = getOperatingStatement(
                    year,
                    month,
                    yearMonth,
                    OperationConstants.RECOVERY_AMOUNT_113,
                    OperationConstants.RECOVERY_AMOUNT_SUM_116 + + RMB_ZH.hashCode(),
                    inatsum,
                    OperationConstants.RMB_ZH,
                    OperationConstants.LEVEL_TYPE_2,
                    sort[0],
                    "内销 RMB");
            operatingStatements.add(operatingStatementDom);
        }

        return  operatingStatements;
    }

    /**
     * 实际发货退货汇总
     * @param beginDate
     * @param endDate
     * @return
     */
    private List<OperatingStatement> saleFhdSum(String beginDate, String endDate,String yearMonth,JSONObject departCondition) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(beginDate, fmt);
        int year = date.getYear();
        int month = date.getMonth().getValue();
        List<OperatingStatement> operatingStatements = new ArrayList<>();
        BigDecimal orderTotal = BigDecimal.ZERO;
        final int[] sort = {1};

        //外销
        //String departCondition = "like '0105%'";
        final BigDecimal[] exOutTotal = {BigDecimal.ZERO};
        final BigDecimal[] innerToExTotal = {BigDecimal.ZERO};
        String externalDepart = departCondition.getString(EXTERNAL_TRADE);
        String domesticDepart = departCondition.getString(DOMESTIC_TRADE);
        if(externalDepart != null) {
            //不为null说明有外销
            if(externalDepart.equals(SINGLE_TRADE)) {
                //进入这个条件说明该企业只存在外销，不需要区分条件
                externalDepart = null;
            }else {
                externalDepart = "like '" + externalDepart + "%'";
            }
            List<JSONObject> exOutCountData = operationMapper.getExOutCountData(beginDate, endDate);
            if(exOutCountData.size() > 0 && exOutCountData.get(0) != null) {
                Map<String, BigDecimal> isumMap = new HashMap<>();
                Map<String, BigDecimal> inatsumMap = new HashMap<>();
                exOutCountData.forEach(e->{
                    String cexchName = e.getString("cexch_name");
                    BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
                    BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
                    exOutTotal[0] = exOutTotal[0].add(isum);
                    if(RMB_ZH.equals(cexchName) || cexchName == null) {//人民币
                        inatsumMap.put(RMB_ZH, isum);
                        isumMap.put(RMB_ZH, isum);
                    }else {
                        inatsumMap.put(cexchName, inatsum);
                        isumMap.put(cexchName, isum);
                    }
                });
                inatsumMap.forEach((k,v)->{
                    OperatingStatement operatingStatementEx = getOperatingStatement(
                            year,
                            month,
                            yearMonth,
                            OperationConstants.DELIVERY_104,
                            OperationConstants.EX_DELIVERY_105+ k.hashCode(),
                            v,
                            k,
                            OperationConstants.LEVEL_TYPE_2,
                            sort[0] ++,
                            "外销实际发货 " + k);
                    operatingStatements.add(operatingStatementEx);
                    if(!RMB_ZH.equals(k)) {
                        OperatingStatement operatingStatementExRMB = getOperatingStatement(
                                year,
                                month,
                                yearMonth,
                                OperationConstants.DELIVERY_104,
                                OperationConstants.EX_DELIVERY_SUM_106 + k.hashCode(),
                                isumMap.get(k),
                                k,
                                OperationConstants.LEVEL_TYPE_2,
                                sort[0] ++,
                                "外销实际发货" + k + "折算RMB");
                        operatingStatements.add(operatingStatementExRMB);
                    }

                });
            }
            //假内销实外销折算RMB

            List<JSONObject> saleFhdInAsExCountData = operationMapper.getSaleFhdCountData(beginDate, endDate, externalDepart);
            if(saleFhdInAsExCountData.size() > 0 && saleFhdInAsExCountData.get(0) != null) {
                saleFhdInAsExCountData.stream().forEach(e -> {
                    String cexchCode = (String) e.get(OperationConstants.CEXCH_CODE);
                    BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
                    BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
                    innerToExTotal[0] = innerToExTotal[0].add(isum);
                    //if(cexchCode.equals(OperationConstants.USD)){
                    //operatingStatement.setIsum(operatingStatement.getIsum().add(isum));
                    //内销单里的外销没有外币金额，都是人民币,还有一个问题，内销单里的inatsum是不含税的,所以统计到总人民币要使用isum
                    //operatingStatement.setInatsum(operatingStatement.getInatsum().add(inatsum));
                    //exOrderTotal[0] = exOrderTotal[0].add(isum);
                    //}
                    log.info("----假内销实外销折算RMB:" + isum.toString());
                });
                OperatingStatement operatingStatementInnerToEx = getOperatingStatement(year, month, yearMonth, OperationConstants.DELIVERY_104, OperationConstants.EX_DELIVERY_SUM_1060, innerToExTotal[0]
                        , OperationConstants.RMB_ZH, OperationConstants.LEVEL_TYPE_2, sort[0]++, "外销实际发货RMB金额");
                operatingStatements.add(operatingStatementInnerToEx);
            }
        }

        final BigDecimal[] saleFhdTotal = {BigDecimal.ZERO};
        //内销
        if(domesticDepart != null) {
            //不为null说明有内销
            if(domesticDepart.equals(SINGLE_TRADE)) {
                //进入这个条件说明该企业只存在内销，不需要区分条件
                domesticDepart = null;
            }else {
                domesticDepart = "like '" + domesticDepart + "%'";
            }
            List<JSONObject> saleFhdCountData = operationMapper.getSaleFhdCountData( beginDate,endDate, domesticDepart);
            if (saleFhdCountData.size() > 0 && saleFhdCountData.get(0) != null) {
                saleFhdCountData.stream().forEach(e -> {
                    String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
                    BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
                    BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
                    OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.DELIVERY_104,OperationConstants.DELIVERY_SUM_107,isum
                            ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"内销实际发货 RMB");
                    saleFhdTotal[0] = saleFhdTotal[0].add(isum);
                    operatingStatements.add(operatingStatement);
                });
            }
        }

        //内销实收退货额
        List<JSONObject> dispatchCountData = operationMapper.getDispatchCountData(beginDate, endDate);
        final BigDecimal[] dispatchTotal = {BigDecimal.ZERO};
        dispatchCountData.stream().forEach(e->{
            String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
            BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
            BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
//            if(cexchName.equals(OperationConstants.RMB)){
            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.DELIVERY_104,OperationConstants.REFUND_SUM_108,isum
                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"内销实际退货金额");
            dispatchTotal[0] = dispatchTotal[0].add(isum);
            operatingStatements.add(operatingStatement);
//            }
        });
        if(dispatchCountData==null||dispatchCountData.size()<=0){
            OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.DELIVERY_104,OperationConstants.REFUND_SUM_108,BigDecimal.ZERO
                    ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"内销实际退货金额");
            operatingStatements.add(operatingStatement);
        }

        //实际发货减退货折合RMB
        orderTotal = exOutTotal[0].add(saleFhdTotal[0]).add(dispatchTotal[0]);
        OperatingStatement operatingStatementTotal = getOperatingStatement(year,month,yearMonth,OperationConstants.DELIVERY_104,OperationConstants.DELIVERY_104,orderTotal,OperationConstants.RMB_ZH,"二、实际发货减退货折合RMB");
        operatingStatements.add(operatingStatementTotal);

        return  operatingStatements;
    }

    /**
     * 订单汇总统计
     * @param beginDate
     * @param endDate
     * @return
     */
    private List<OperatingStatement> orderSum(String beginDate, String endDate,String yearMonth,JSONObject departCondition) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(beginDate, fmt);
        int year = date.getYear();
        int month = date.getMonth().getValue();
        List<OperatingStatement> operatingStatements = new ArrayList<>();
        BigDecimal orderTotal = BigDecimal.ZERO;
        final int[] sort = {1};

        //外销
        final BigDecimal[] exOrderTotal = {BigDecimal.ZERO};
        final BigDecimal[] innerToExTotal = {BigDecimal.ZERO};
        //外销
        //String departCondition = "like '0105%'";
        String externalDepart = departCondition.getString(EXTERNAL_TRADE);
        String domesticDepart = departCondition.getString(DOMESTIC_TRADE);

        if(externalDepart != null) {
            //不为null说明有外销
            if(externalDepart.equals(SINGLE_TRADE)) {
                //进入这个条件说明该企业只存在外销，不需要区分条件
                externalDepart = null;
            }else {
                externalDepart = "like '" + externalDepart + "%'";
            }
            List<JSONObject> exOrderCountData = operationMapper.getExOrderCountData(beginDate,endDate);
            if(exOrderCountData.size() > 0 && exOrderCountData.get(0) != null) {
                Map<String, BigDecimal> isumMap = new HashMap<>();
                Map<String, BigDecimal> inatsumMap = new HashMap<>();
                exOrderCountData.forEach(e->{
                    String cexchName = e.getString("cexch_name");
                    BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
                    BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
                    exOrderTotal[0] = exOrderTotal[0].add(isum);
                    if(RMB_ZH.equals(cexchName) || cexchName == null) {//人民币
                        inatsumMap.put(RMB_ZH, isum);
                        isumMap.put(RMB_ZH, isum);
                    }else {
                        inatsumMap.put(cexchName, inatsum);
                        isumMap.put(cexchName, isum);
                    }
                });
                inatsumMap.forEach((k,v)->{
                    OperatingStatement operatingStatementEx = getOperatingStatement(
                            year,
                            month,
                            yearMonth,
                            OperationConstants.ORDER_100,
                            OperationConstants.EX_ORDER_101+ k.hashCode(),
                            v,
                            k,
                            OperationConstants.LEVEL_TYPE_2,
                            sort[0] ++,
                            "外销： " + k);
                    operatingStatements.add(operatingStatementEx);
                    if(!RMB_ZH.equals(k)) {
                        OperatingStatement operatingStatementExRMB = getOperatingStatement(
                                year,
                                month,
                                yearMonth,
                                OperationConstants.ORDER_100,
                                OperationConstants.EX_ORDER_SUM_102 + k.hashCode(),
                                isumMap.get(k),
                                k,
                                OperationConstants.LEVEL_TYPE_2,
                                sort[0] ++,
                                "外销：" + k + "折算RMB");
                        operatingStatements.add(operatingStatementExRMB);
                    }

                });
            }
            //假内销实外销折算RMB

            List<JSONObject> saleOrderInAsExCountData = operationMapper.getSaleOrderCountData(beginDate, endDate, externalDepart);
            if(saleOrderInAsExCountData.size() > 0 && saleOrderInAsExCountData.get(0) != null) {
                saleOrderInAsExCountData.stream().forEach(e -> {
                    String cexchCode = (String) e.get(OperationConstants.CEXCH_CODE);
                    BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
                    BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
                    innerToExTotal[0] = innerToExTotal[0].add(isum);
                    //if(cexchCode.equals(OperationConstants.USD)){
                    //operatingStatement.setIsum(operatingStatement.getIsum().add(isum));
                    //内销单里的外销没有外币金额，都是人民币,还有一个问题，内销单里的inatsum是不含税的,所以统计到总人民币要使用isum
                    //operatingStatement.setInatsum(operatingStatement.getInatsum().add(inatsum));
                    //exOrderTotal[0] = exOrderTotal[0].add(isum);
                    //}
                    log.info("----假内销实外销折算RMB:" + isum.toString());
                });
                OperatingStatement operatingStatementInnerToEx = getOperatingStatement(year, month, yearMonth, OperationConstants.ORDER_100, OperationConstants.EX_ORDER_SUM_1020, innerToExTotal[0]
                        , OperationConstants.RMB_ZH, OperationConstants.LEVEL_TYPE_2, sort[0]++, OperationConstants.EX_ZH + "RMB金额");
                operatingStatements.add(operatingStatementInnerToEx);
            }
        }

        final BigDecimal[] saleOrderTotal = {BigDecimal.ZERO};
        //内销
        if(domesticDepart != null) {
            //不为null说明有内销
            if(domesticDepart.equals(SINGLE_TRADE)) {
                //进入这个条件说明该企业只存在内销，不需要区分条件
                domesticDepart = null;
            }else {
                domesticDepart = "like '" + domesticDepart + "%'";
            }
            List<JSONObject> saleOrderCountData = operationMapper.getSaleOrderCountData( beginDate,endDate, domesticDepart);
            if (saleOrderCountData.size() > 0 && saleOrderCountData.get(0) != null) {
                saleOrderCountData.stream().forEach(e -> {
                    String cexchName = (String) e.get(OperationConstants.CEXCH_NAME);
                    BigDecimal isum = (BigDecimal) e.get(OperationConstants.ISUM);
                    BigDecimal inatsum = (BigDecimal) e.get(OperationConstants.INATSUM);
                    OperatingStatement operatingStatement = getOperatingStatement(year,month,yearMonth,OperationConstants.ORDER_100,OperationConstants.ORDER_SUM_103,isum
                            ,OperationConstants.RMB_ZH,OperationConstants.LEVEL_TYPE_2,sort[0]++,"内销 RMB");
                    saleOrderTotal[0] = saleOrderTotal[0].add(isum);
                    operatingStatements.add(operatingStatement);
                });
            }
        }

        //订单合计
        orderTotal = saleOrderTotal[0].add(exOrderTotal[0].add(innerToExTotal[0]));
        OperatingStatement operatingStatementTotal = getOperatingStatement(year,month,yearMonth,OperationConstants.ORDER_100,OperationConstants.ORDER_100,orderTotal,OperationConstants.RMB_ZH,"一、订单折合RMB");
        operatingStatements.add(operatingStatementTotal);

        return  operatingStatements;
    }

    public  Map<String, List<OperatingStatement>> findList(OperatingStatementQuery operatingStatementQuery){
//        List<OperatingStatement> dataList = baseMapper.sumCompany(operatingStatementQuery.getYearmonth());
        //获取默认数据（汇总数据，对应公司code 为 COMPANY_SUM_CODE）
        List<OperatingStatement> dataList = baseMapper.findList(operatingStatementQuery);
        //处理采购账期问题
        Map<String, List<OperatingStatement>> collectMap = dataList.stream().collect(Collectors.groupingBy(OperatingStatement::getYearmonth));
//        DateTimeFormatter yyyymm = DateTimeFormatter.ofPattern("yyyyMMdd");
//        Iterator<Map.Entry<String, List<OperatingStatement>>> iterator = collectMap.entrySet().iterator();
//
//        while (iterator.hasNext()) {
//            Map.Entry<String, List<OperatingStatement>> next = iterator.next();
//            List<OperatingStatement> values = next.getValue();
//            String key = next.getKey();
//            LocalDate parse = LocalDate.parse(key + "01", yyyymm);
//            //已账期数据处理
//            List<OperatingStatement> operatingStatements = dealExpriseData(values, parse);
//            //重新排序
//            List<OperatingStatement> collectList = operatingStatements.stream().sorted(Comparator.comparing(OperatingStatement::getParentCode).thenComparing(OperatingStatement::getLevelType)
//                    .thenComparing(OperatingStatement::getSort)).collect(Collectors.toList());
//            collectMap.put(key,collectList);
//        }
        //按科目汇总
        List<OperatingStatement> sumList =  baseMapper.sumList(operatingStatementQuery);
        collectMap.put("sum",sumList);
         return  collectMap;
    }

    public  Map<String, List<OperatingStatement>> findListByNotInCompanyCode(OperatingStatementSubQuery operatingStatementQuery){
//        List<OperatingStatement> dataList = baseMapper.sumCompany(operatingStatementQuery.getYearmonth());
        //获取默认数据（汇总数据，对应公司code 为 COMPANY_SUM_CODE）
        if(StringUtils.isBlank(operatingStatementQuery.getCompanyCode())) {
            operatingStatementQuery.setCompanyCode(COMPANY_SUM_CODE);
        }
        List<OperatingStatement> dataList = baseMapper.findListByNotInCompanyCode(operatingStatementQuery);
        //处理采购账期问题
        Map<String, List<OperatingStatement>> collectMap = dataList.stream().collect(Collectors.groupingBy(OperatingStatement::getYearmonth));
        DateTimeFormatter yyyymm = DateTimeFormatter.ofPattern("yyyyMMdd");
        Iterator<Map.Entry<String, List<OperatingStatement>>> iterator = collectMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, List<OperatingStatement>> next = iterator.next();
            List<OperatingStatement> values = next.getValue();
            String key = next.getKey();
            LocalDate parse = LocalDate.parse(key + "01", yyyymm);
            //已账期数据处理
            List<OperatingStatement> operatingStatements = dealExpriseData(values, parse);
            //重新排序
            List<OperatingStatement> collectList = operatingStatements.stream().sorted(Comparator.comparing(OperatingStatement::getParentCode).thenComparing(OperatingStatement::getLevelType)
                    .thenComparing(OperatingStatement::getSort)).collect(Collectors.toList());
            collectMap.put(key,collectList);
        }
        //按科目汇总
        List<OperatingStatement> sumList =  baseMapper.sumSubList(BeanUtil.copyProperties(operatingStatementQuery, OperatingStatementQuery.class));
        collectMap.put("sum",sumList);
        return  collectMap;
    }

    public  Map<String, List<OperatingStatement>> findListByNotInCompanyCodeAndClassCode(OperatingStatementQuery operatingStatementQuery){
//        List<OperatingStatement> dataList = baseMapper.sumCompany(operatingStatementQuery.getYearmonth());
        //获取默认数据（汇总数据，对应公司code 为 COMPANY_SUM_CODE）
        List<OperatingStatement> dataList = baseMapper.findListByNotInCompanyCodeAndClassCode(operatingStatementQuery);
        //处理采购账期问题
        Map<String, List<OperatingStatement>> collectMap = dataList.stream().collect(Collectors.groupingBy(OperatingStatement::getYearmonth));
        DateTimeFormatter yyyymm = DateTimeFormatter.ofPattern("yyyyMMdd");
        Iterator<Map.Entry<String, List<OperatingStatement>>> iterator = collectMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, List<OperatingStatement>> next = iterator.next();
            List<OperatingStatement> values = next.getValue();
            String key = next.getKey();
            LocalDate parse = LocalDate.parse(key + "01", yyyymm);
            //已账期数据处理
            List<OperatingStatement> operatingStatements = dealExpriseData(values, parse);
            //重新排序
            List<OperatingStatement> collectList = operatingStatements.stream().sorted(Comparator.comparing(OperatingStatement::getParentCode).thenComparing(OperatingStatement::getLevelType)
                    .thenComparing(OperatingStatement::getSort)).collect(Collectors.toList());
            collectMap.put(key,collectList);
        }
        //按科目汇总
        List<OperatingStatement> sumList =  baseMapper.sumList(operatingStatementQuery);
        collectMap.put("sum",sumList);
        return  collectMap;
    }

    private List<OperatingStatement> dealExpriseData(List<OperatingStatement> values,LocalDate parse) {
        LocalDate now = LocalDate.now();
        boolean expriseFlag = now.with(TemporalAdjusters.firstDayOfMonth()).isAfter(parse.plusMonths(3));
        List<OperatingStatement> purchOperatingStatementList = new ArrayList<>();
        List<OperatingStatement> operatingStatementList = new ArrayList<>();
        values.stream().forEach(e->{
            //判断查询数据月份与当前时间是否到账
            if(e.getClassCode().equals(OperationConstants.EX_COIN_121)||
                    e.getClassCode().equals(OperationConstants.RMB_COIN_122)||
                    e.getClassCode().equals(OperationConstants.PURCHASE_SUM_123)){
                purchOperatingStatementList.add(e);
                return;
            }
            operatingStatementList.add(e);
        });
        final int[] index = {1};
        Map<String, OperatingStatement> operatingStatementMap = new HashMap<>();
        purchOperatingStatementList.stream().forEach(item->{
            item.setSort(index[0]++);
            operatingStatementList.add(item);
            OperatingStatement operatingStatement = operatingStatementMap.get(item.getYearmonth());
            if(operatingStatement == null) {
                operatingStatement = new OperatingStatement();
                BeanCopierUtils.copyProperties(item,operatingStatement);
                operatingStatement.setSort(index[0]++);
                operatingStatement.setName("已到账期金额");
                operatingStatement.setIsum(expriseFlag?item.getIsum():BigDecimal.ZERO);
                operatingStatement.setClassCode(OperationConstants.COIN_EXPRISE_139);
                operatingStatementMap.put(item.getYearmonth(), operatingStatement);
                operatingStatementList.add(operatingStatement);
                log.info(item.getYearmonth() + ":" + item.getName() + ":" + item.getIsum());
            }else {
                if(expriseFlag) {
                    log.info("累加：" + item.getYearmonth() + ":" + item.getName() + ":" + item.getIsum());
                    operatingStatement.setIsum(operatingStatement.getIsum().add(item.getIsum()));
                }
            }

            OperatingStatement operatingStatement1 = new OperatingStatement();
            BeanCopierUtils.copyProperties(item,operatingStatement1);
            operatingStatement1.setSort(index[0]++);
            operatingStatement1.setName("未到账期金额");
            operatingStatement1.setIsum(expriseFlag?BigDecimal.ZERO:item.getIsum());
            operatingStatement1.setClassCode(OperationConstants.COIN_NO_EXPRISE_140);
            operatingStatementList.add(operatingStatement1);
        });
        return operatingStatementList;
    }


    @Override
    public List<OperatingStatement> countCompany(LocalDate currentDate){
        LocalDate date = currentDate.minusMonths(1);
        DateTimeFormatter fmtYM = DateTimeFormatter.ofPattern("yyyyMM");
        String yearMonth = date.format(fmtYM);
        return baseMapper.sumCompany(yearMonth);
    }
}
