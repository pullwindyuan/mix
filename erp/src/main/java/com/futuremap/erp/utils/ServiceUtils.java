package com.futuremap.erp.utils;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.futuremap.erp.module.constants.Constants;
import com.futuremap.erp.module.orderCost.mapper.OrderCostDS2Mapper;

/**
 * @author rubio@futuremap.com.cn
 * @date 2021/9/23 14:32
 */
public class ServiceUtils {

//    public static String getDeliverDay(OrderCostDS2Mapper orderCostDS2Mapper, String companyLabel, String orderDetailCode, String orderNumber, String cinvcode, String ccusCode) {
//        String deliverDay = null;
//        DynamicDataSourceContextHolder.push(companyLabel);
//        if (companyLabel.equals(Constants.ERP010)) {
//            deliverDay = orderCostDS2Mapper.getDeliverDay_erp10(orderDetailCode, orderNumber, cinvcode, ccusCode);
//        } else if (companyLabel.equals(Constants.ERP003)) {
//            deliverDay = orderCostDS2Mapper.getDeliverDay_erp3(orderDetailCode, orderNumber, cinvcode, ccusCode);
//        } else {
//            deliverDay = orderCostDS2Mapper.getDeliverDay(orderDetailCode, orderNumber, cinvcode, ccusCode);
//        }
//        return deliverDay;
//    }
    public static String getDeliverDay(OrderCostDS2Mapper orderCostDS2Mapper, String companyLabel, String orderDetailCode, String orderNumber, String productCode, String invoiceId) {
        DynamicDataSourceContextHolder.push(companyLabel);
        return  orderCostDS2Mapper.getDeliverDay_all(orderDetailCode, orderNumber,productCode, invoiceId);
    }
}
