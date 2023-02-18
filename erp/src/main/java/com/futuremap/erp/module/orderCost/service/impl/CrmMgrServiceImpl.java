package com.futuremap.erp.module.orderCost.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.common.page.IPageEmpty;
import com.futuremap.erp.module.auth.service.impl.ColumnServiceImpl;
import com.futuremap.erp.module.auth.service.impl.RoleColumnServiceImpl;
import com.futuremap.erp.module.auth.service.impl.UserRoleServiceImpl;
import com.futuremap.erp.module.constants.Constants;
import com.futuremap.erp.module.constants.OrderProcessConstants;
import com.futuremap.erp.module.orderCost.entity.CrmMgr;
import com.futuremap.erp.module.orderCost.entity.CrmMgrQuery;
import com.futuremap.erp.module.orderCost.entity.OrderCost;
import com.futuremap.erp.module.orderCost.mapper.CrmMgrDS1Mapper;
import com.futuremap.erp.module.orderCost.mapper.OrderAmountMgrMapper;
import com.futuremap.erp.module.orderCost.mapper.OrderCostDS1Mapper;
import com.futuremap.erp.module.orderCost.mapper.OrderCostDS2Mapper;
import com.futuremap.erp.module.orderCost.service.ICrmMgrService;
import com.futuremap.erp.module.quotation.entity.QuotationTotal;
import com.futuremap.erp.utils.GeneralUtils;
import com.futuremap.erp.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rubio@futuremap.com.cn
 * @date 2021/6/3 9:50
 */
@Service
public class CrmMgrServiceImpl extends ServiceImpl<CrmMgrDS1Mapper, CrmMgr> implements ICrmMgrService {

    @Autowired
    CrmMgrDS1Mapper crmMgrDS1Mapper;

    @Autowired
    OrderCostDS2Mapper orderCostDS2Mapper;

    @Autowired
    OrderAmountMgrMapper orderAmountMgrMapper;

    @Autowired
    OrderCostDS1Mapper orderCostDS1Mapper;

    @Autowired
    UserRoleServiceImpl userRoleServiceImpl;
    @Autowired
    RoleColumnServiceImpl roleColumnServiceImpl;
    @Autowired
    ColumnServiceImpl columnServiceImpl;

    public void buildCrmMgrList(String companyLabel, String dmonth, List<CrmMgr> crmMgrList) {
        String[] startAndEndDay = GeneralUtils.getStartAndEndDay(dmonth);
        String startDay = startAndEndDay[0];
        String endDay = startAndEndDay[1];
        if (crmMgrList == null || crmMgrList.isEmpty()) {
            crmMgrList = new ArrayList<>();
            DynamicDataSourceContextHolder.push(companyLabel);

            //从销售订单
            //List<CrmMgr> crmMgrListOuter = crmMgrDS1Mapper.getOuterCrmMgrListByTime(startDay, endDay);
            //List<CrmMgr> crmMgrListInner = crmMgrDS1Mapper.getInnerCrmMgrListByTime(startDay, endDay);

            //从发票
            List<CrmMgr> crmMgrListOuter = crmMgrDS1Mapper.getOuterCrmMgrListByTime0001(startDay, endDay);
            List<CrmMgr> crmMgrListInner = crmMgrDS1Mapper.getInnerCrmMgrListByTime0001(startDay, endDay);

            crmMgrList.addAll(crmMgrListOuter);
            crmMgrList.addAll(crmMgrListInner);
        }

        crmMgrList.stream().forEach(crmMgr -> {
            String orderDetailCode = crmMgr.getOrderDetailCode();
            String orderNumber = crmMgr.getOrderNumber();
            String productCode = crmMgr.getProductCode();
            crmMgr.setCompanyLabel(companyLabel);
            crmMgr.setDmonth(dmonth);
            DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
            crmMgrDS1Mapper.delOld(orderDetailCode, orderNumber, productCode);
        });
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        saveBatch(crmMgrList);
        crmMgrDS1Mapper.updateCompanyInfo();
        crmMgrList = crmMgrDS1Mapper.getCrmMgrListByTime(companyLabel, dmonth);
        crmMgrList.stream().forEach(crmMgr -> {
                    String orderDetailCode = crmMgr.getOrderDetailCode();
                    String orderNumber = crmMgr.getOrderNumber();
                    String productCode = crmMgr.getProductCode();
                    String customerCode = crmMgr.getCustomerCode();
                    String invoiceId = null;

                    DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                    OrderCost orderCost = orderCostDS1Mapper.getOrderCostUnit(orderDetailCode, orderNumber, productCode);
                    if (orderCost != null) {
                        invoiceId = orderCost.getInvoiceId();
                        BigDecimal profitRadio = orderCost.getProfitRadio();
                        BigDecimal materialCost = orderCost.getMaterialCost();
                        BigDecimal manufactureCost = orderCost.getManufactureCost();
                        BigDecimal laborCost = orderCost.getLaborCost();
                        BigDecimal mgrCost = orderCost.getMgrCost();
                        BigDecimal income = orderCost.getIncome();
                        String deliverDay = orderCost.getDeliverDay();
                        String returnDay = orderCost.getPlanReturnDay();

                        crmMgr.setInvoiceId(invoiceId);
                        crmMgr.setProfitRate(profitRadio);
                        crmMgr.setMaterialCost(materialCost);
                        crmMgr.setManufactureCost(manufactureCost);
                        crmMgr.setLaborCost(laborCost);
                        crmMgr.setMgrCost(mgrCost);
                        crmMgr.setReturnAmount(income);
                        //发货日期
                        crmMgr.setDeliverDay(deliverDay);
                        //回款日期
                        crmMgr.setReturnDate(GeneralUtils.string2date(returnDay));
                    }
                    QuotationTotal quotationTotal = crmMgrDS1Mapper.getQuotationTotal(orderDetailCode, orderNumber, productCode);
                    if (quotationTotal != null) {
                        BigDecimal shippingCost = quotationTotal.getShippingCost();
                        BigDecimal profit = quotationTotal.getProfit();
                        //船务费用
                        crmMgr.setShippingCost(shippingCost);
                        //利润
                        crmMgr.setProfit(profit);
                    }


                    String dday = crmMgr.getDeliverDay();
                    if (dday == null&&invoiceId!=null) {

                        dday = ServiceUtils.getDeliverDay(orderCostDS2Mapper, companyLabel, orderDetailCode, orderNumber, productCode, invoiceId);
                    }
                    if (dday != null) {
                        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                        Integer limitBack = orderAmountMgrMapper.getLimitBack(customerCode);
                        if (limitBack == null) {
                            limitBack = 90;
                        }
                        String returnPlanDate = GeneralUtils.getDateAfterNDays(dday, limitBack);
                        LocalDateTime prd = GeneralUtils.string2date(returnPlanDate);
                        //应回款日期
                        crmMgr.setReturnPlanDate(prd);
                    }

                    LocalDateTime returnDate = crmMgr.getReturnDate();

                    if (invoiceId != null && returnDate == null) {
                        DynamicDataSourceContextHolder.push(companyLabel);
                        String returnDay = orderCostDS2Mapper.getReturnDay(invoiceId);
                        returnDate = GeneralUtils.string2date(returnDay);

                        //实际回款日期
                        crmMgr.setReturnDate(returnDate);
                    }


                }


        );
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        updateBatchById(crmMgrList);


    }


    public IPage<CrmMgr> findList(Page<CrmMgr> page, CrmMgrQuery crmMgrQuery) {
        String columnVisit = GeneralUtils.getColumnVisit(userRoleServiceImpl, roleColumnServiceImpl, columnServiceImpl, "crm_mgr");
        IPage<CrmMgr> iPage = new IPageEmpty<>();
        if (columnVisit != null) {
            crmMgrQuery.setColumnVisit(columnVisit);
            //crmMgrQuery.setColumnVisit("*");

            iPage = crmMgrDS1Mapper.findList(page, crmMgrQuery);

        }

        return iPage;
    }


}
