package com.futuremap.erp.module.constants;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rubio@futuremap.com.cn
 * @date 2021/7/6 15:47
 */
public class Constants {
    public static final String DEFAULT_STR = "";
    public static final Integer DEFAULT_INT = 0;
    public static final Integer DIVIDE_LEVEL_12 = 12;
    public static final Integer ORDER_TYPE_0 = 0;
    public static final Integer ORDER_TYPE_1 = 1;//委外
    public static final Integer ORDER_TYPE_2 = 2;//挪货
    public static final Integer ORDER_TYPE_3 = 3;//内部生产
    public static final Integer ORDER_TYPE_4 = 4;//
    public static final Integer ORDER_TYPE_5 = 5;//无生产,委外核销
    public static final Integer ORDER_TYPE_6 = 6;//经过包装车间

    public static final Integer ORDER_TYPE_EXCEPTION = -2;
    public static final Integer ORDER_SRC_INNER = 1;
    public static final Integer ORDER_SRC_OUTER = 0;

    public static final String CAMOTYPE_4 = "4";//人力成本
    public static final String CAMOTYPE_3 = "3";//制造成本
    public static final String CAMOTYPE_0 = "0";//材料成本
    public static final String ERP002 = "erp002";
    public static final String ERP003 = "erp003";
    public static final String ERP004 = "erp004";
    public static final String ERP006 = "erp006";
    public static final String ERP010 = "erp010";

    public static final String CDEPNAME_02 = "针车车间";
    public static final String CDEPNAME_01 = "裁剪车间";
    public static final String DEPT_CODE_020303 = "020303";//包装车间
    public static final String DEPT_CODE_020301 = "020301";//针车车间


    public static final DateTimeFormatter time_format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 各个菜单对应的表名称
     */
    public static final Map<String, String> RESOURCE_TABLES;

    static {
        RESOURCE_TABLES = new HashMap<>();
        RESOURCE_TABLES.put("用户管理列表查看", "'auth_user'");
        RESOURCE_TABLES.put("订单全过程", "'sale_order_process'");
        RESOURCE_TABLES.put("采购订单列表", "'purch_order'");
        RESOURCE_TABLES.put("材料出库列表", "'recordout_list'");
        RESOURCE_TABLES.put("销售出库列表", "'saleout_list'");
        RESOURCE_TABLES.put("发票回款列表", "'sale_bill'");
        RESOURCE_TABLES.put("完工产品列表", "'recordin_list'");
        RESOURCE_TABLES.put("经营表", "'operating_statement'");
        RESOURCE_TABLES.put("收付款对应表", "'purch_sale_bill_mapper'");
        RESOURCE_TABLES.put("公司列表", "'company_info'");
        RESOURCE_TABLES.put("CRM", "'crm_mgr'");
        RESOURCE_TABLES.put("客户账期", "'order_amount_mgr'");
        RESOURCE_TABLES.put("订单成本表", "'order_cost'");
        RESOURCE_TABLES.put("报价对比表", "'quotation_total'");
        RESOURCE_TABLES.put("销售提成表", "'sale_kpi'");
        RESOURCE_TABLES.put("挪货订单", "'switch_order'");
        RESOURCE_TABLES.put("获取当前用户", "'auth_user'");
        RESOURCE_TABLES.put("采购订单", "'purch_order','purch_bill'");
        RESOURCE_TABLES.put("客户账期：导入", "'none'");
        RESOURCE_TABLES.put("报价对比表：导入", "'none'");
        RESOURCE_TABLES.put("挪货记录：导入", "'none'");
        RESOURCE_TABLES.put("挪货记录：新增", "'none'");
        RESOURCE_TABLES.put("挪货记录：编辑", "'none'");
        RESOURCE_TABLES.put("挪货记录：删除", "'none'");
    }
}