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
package com.futuremap.erp.module.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @title OperationConstants
 * @description TODO
 * @date 2021/6/2 9:15
 */
public class OperationConstants {

    public static final String  EX_ZH="外销";

    public static final String  SALE_ZH="内销";


    public static final String RMB = "RMB";
    public static final String USD = "USD";
    public static final String USD_ZH = "美元";
    public static final String RMB_ZH = "人民币";

    public static final Integer LEVEL_TYPE_1 = 1;

    public static final Integer LEVEL_TYPE_2 = 2;
    public static final String CEXCH_NAME = "cexch_name";
    public static final String INATSUM = "inatsum";
    public static final String ISUM = "isum";
    public static final String CEXCH_CODE = "cexch_code";
    public static final String GL_14 = "GL14";
    public static final String GL_13 = "GL13";


    public static final String ORDER_100 = "100";

    public static final String EX_ORDER_101 = "101";

    public static final String EX_ORDER_SUM_102 = "102";

    public static final String EX_ORDER_SUM_1020 = "1020";

    public static final String ORDER_SUM_103 = "103";

    public static final String DELIVERY_104 = "104";

    public static final String EX_DELIVERY_105 = "105";

    public static final String EX_DELIVERY_SUM_106 = "106";

    public static final String EX_DELIVERY_SUM_1060 = "1060";

    public static final String DELIVERY_SUM_107 = "107";

    public static final String REFUND_SUM_108 = "108";


    public static final String SALESREVENUE_109 = "109";

    public static final String EX_SALESREVENUE_110 = "110";

    public static final String EX_SALESREVENUE_SUM_111 = "111";

    public static final String EX_SALESREVENUE_SUM_1110 = "1110";

    public static final String SALESREVENUE_SUM_112 = "112";

    public static final String SALESREVENUE_SUM_1120 = "1120";

    public static final String SALESREVENUE_SUM_1121 = "1121";

    public static final String RECOVERY_AMOUNT_113 = "113";

    public static final String EX_RECOVERY_AMOUNT_114 = "114";

    public static final String EX_RECOVERY_AMOUNT_SUM_115 = "115";

    public static final String RECOVERY_AMOUNT_SUM_116 = "116";

    public static final String RECOVERY_AMOUNT_SUM_1160 = "1160";

    public static final String RECOVERY_AMOUNT_SUM_1161 = "1161";

    //应收账款
    public static final String ACCOUNTS_RECEIVABLE_117 = "117";

    public static final String EX_ACCOUNTS_RECEIVABLE_118 = "118";
    public static final String EX_ACCOUNTS_RECEIVABLE_1180 = "1180";
    public static final String ACCOUNTS_RECEIVABLE_SUM_119 = "119";
    public static final String ACCOUNTS_RECEIVABLE_SUM_1190 = "1190";
    public static final String ACCOUNTS_RECEIVABLE_SUM_1191 = "1191";

    public static final String PURCHASE_120 = "120";

    public static final String EX_COIN_121 = "121";

    public static final String RMB_COIN_122 = "122";

    public static final String PURCHASE_SUM_123 = "123";

    public static final String PURCHASE_SUM_1230 = "1230";

    public static final String OUTPUT_VALUE_124 = "124";

    public static final String PROCESSING_COST_125 = "125";

    public static final String STOCK_126 = "126";

    public static final String STOCK_TURNOVER_127 = "127";

    public static final String SALE_COST_128 = "128";

    public static final String SALE_COST_1280 = "1280";

    public static final String SALE_COST_1281 = "1281";

    public static final String MANUFACTURING_COST_129 = "129";

    public static final String GROSS_PROFIT_130 = "130";


    public static final String SALE_EXPENSES_131 = "131";

    public static final String SALE_TAX_132 = "132";

    public static final String MANAGEMENT_EXPENSES_133 = "133";

    public static final String FINANCIAL_EXPENSES_134 = "134";

    public static final String PROFIT_135 = "135";

    public static final String INCOME_TAX_136 = "136";

    public static final String NET_PROFIT_137 = "137";

    public static final String TAX_REFUND_138 = "138";

    public static final String COIN_EXPRISE_139 = "139";
    public static final String COIN_NO_EXPRISE_140 = "140";
//    public static final String RMB_COIN_EXPRISE_141 = "141";
//    public static final String RMB_COIN_NO_EXPRISE_142 = "142";
//    public static final String PURCHASE_SUM_EXPRISE_143 = "143";
//    public static final String PURCHASE_SUM_NO_EXPRISE_144 = "144";





    public static final String COMPANY_SUM_CODE = "0000";


    /**
     * 珠海智拓科技有限公司
     */
    public static final String COMPANY_HD151 = "HD151";
    /**
     * 不合法
     */
    public static final String COMPANY_9999 = "9999";
    /**
     * 珠海横琴新区宜心企业管理咨询有限公司
     */
    public static final String COMPANY_EASI001 = "EASI001";
    /**
     * ESSEN GROUP (MACAO) COMPANY LIMITED
     */
    public static final String COMPANY_HD154 = "HD154";
    /**
     * MEX
     */
    public static final String COMPANY_W0001 = "W0001";

    /**
     * 内贸
     */
    public static final String SINGLE_TRADE = "single";

    /**
     * 内贸
     */
    public static final String DOMESTIC_TRADE = "domestic";
    /**
     * 外贸
     */
    public static final String EXTERNAL_TRADE = "external";

    /**
     * 内贸
     */
    public static final String DOMESTIC_TRADE_TYPE = "{\"domestic\":\"" + SINGLE_TRADE + "\"}";
    /**
     * 外贸
     */
    public static final String EXTERNAL_TRADE_TYPE = "{\"external\":\"" + SINGLE_TRADE + "\"}";

    /**
     * 内外贸
     */
    public static final String ALL_TRADE_TYPE = "{\"domestic\":\"0112\",\"external\":\"0105\"}";

    /**
     * 内贸
     */
    public static final String SUM_ALL_TRADE_TYPE = "sum";

    public static final Map<String, String> TRADE_TYPE;
    static {
        TRADE_TYPE = new HashMap<>();
        TRADE_TYPE.put(COMPANY_HD151, DOMESTIC_TRADE_TYPE);
        TRADE_TYPE.put(COMPANY_EASI001, EXTERNAL_TRADE_TYPE);
        TRADE_TYPE.put(COMPANY_W0001, EXTERNAL_TRADE_TYPE);
        TRADE_TYPE.put(COMPANY_HD154, EXTERNAL_TRADE_TYPE);
        TRADE_TYPE.put(COMPANY_9999, ALL_TRADE_TYPE);
    }

    /**
     * 结算科目:银行存款
     */
    public static final String DEPOSIT_SETTLE_TYPE = "'1002%'";

    public static final Map<String, String> SETTLE_TYPE;
    static {
        SETTLE_TYPE = new HashMap<>();
        SETTLE_TYPE.put(COMPANY_HD151, "'102%'");
        SETTLE_TYPE.put(COMPANY_EASI001, DEPOSIT_SETTLE_TYPE);
        SETTLE_TYPE.put(COMPANY_W0001, DEPOSIT_SETTLE_TYPE);
        SETTLE_TYPE.put(COMPANY_HD154, DEPOSIT_SETTLE_TYPE);
        SETTLE_TYPE.put(COMPANY_9999, DEPOSIT_SETTLE_TYPE);
    }

    /**
     * 各个公司需要排除的内部关联公司
     */
    public static final Map<String, String> EXCEPT_COMPANY;
    static {
        EXCEPT_COMPANY = new HashMap<>();
        EXCEPT_COMPANY.put(COMPANY_HD151, "'EASI001', '9999', 'W0001', 'HD154'");
        EXCEPT_COMPANY.put(COMPANY_EASI001, "'9999', 'W0001', 'HD154', 'HD151'");
        EXCEPT_COMPANY.put(COMPANY_W0001, "'EASI001', '9999', 'HD154', 'HD151'");
        EXCEPT_COMPANY.put(COMPANY_HD154, "'EASI001', '9999', 'W0001', 'HD151'");
        EXCEPT_COMPANY.put(COMPANY_9999, "'EASI001', '9999', 'W0001', 'HD154', 'HD151'");
    }

    public static final Integer ACTIVE_CUSTOMER_MONTHS = 8;
}
