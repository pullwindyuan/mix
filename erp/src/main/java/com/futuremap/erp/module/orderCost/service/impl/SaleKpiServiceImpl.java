package com.futuremap.erp.module.orderCost.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.common.page.IPageEmpty;
import com.futuremap.erp.module.auth.service.impl.ColumnServiceImpl;
import com.futuremap.erp.module.auth.service.impl.RoleColumnServiceImpl;
import com.futuremap.erp.module.auth.service.impl.UserRoleServiceImpl;
import com.futuremap.erp.module.constants.OrderProcessConstants;
import com.futuremap.erp.module.orderCost.entity.CrmMgr;
import com.futuremap.erp.module.orderCost.entity.SaleKpi;
import com.futuremap.erp.module.orderCost.entity.SaleKpiQuery;
import com.futuremap.erp.module.orderCost.mapper.SaleKpiDS1Mapper;
import com.futuremap.erp.module.orderCost.service.ISaleKpiService;
import com.futuremap.erp.module.quotation.entity.Quotation;
import com.futuremap.erp.utils.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rubio@futuremap.com.cn
 * @date 2021/6/3 9:50
 */
@Service
public class SaleKpiServiceImpl extends ServiceImpl<SaleKpiDS1Mapper, SaleKpi> implements ISaleKpiService {

    @Autowired
    SaleKpiDS1Mapper saleKpiDS1Mapper;

    @Autowired
    UserRoleServiceImpl userRoleServiceImpl;
    @Autowired
    RoleColumnServiceImpl roleColumnServiceImpl;
    @Autowired
    ColumnServiceImpl columnServiceImpl;

    public void buildSaleKpiList(String companyLabel, String dmonth, List<SaleKpi> saleKpiList) {
        String[] startAndEndDay = GeneralUtils.getStartAndEndDay(dmonth);
        String startDay = startAndEndDay[0];
        String endDay = startAndEndDay[1];
        if (saleKpiList == null || saleKpiList.isEmpty()) {
            saleKpiList = new ArrayList<>();
            DynamicDataSourceContextHolder.push(companyLabel);
            //saleKpiList = saleKpiDS1Mapper.getSaleKpiListByTime(startDay, endDay);//从销售订单
            saleKpiList = saleKpiDS1Mapper.getSaleKpiListByTime0001(startDay, endDay);//从发票

        }

        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        saleKpiList.stream().forEach(saleKpi -> {
            String orderDetailCode = saleKpi.getOrderDetailCode();
            String orderNumber = saleKpi.getOrderNumber();
            String productCode = saleKpi.getProductCode();
            saleKpi.setCompanyLabel(companyLabel);
            saleKpi.setDmonth(dmonth);
            saleKpiDS1Mapper.delOld(orderDetailCode, orderNumber, productCode);

        });


        saveBatch(saleKpiList);

        saleKpiDS1Mapper.updateCompanyInfo();
        saleKpiList = saleKpiDS1Mapper.getSaleKpiList(companyLabel, dmonth);
        saleKpiList.stream().forEach(saleKpi -> {
            String orderDetailCode = saleKpi.getOrderDetailCode();
            String orderNumber = saleKpi.getOrderNumber();
            String productCode = saleKpi.getProductCode();
            //订单金额
            BigDecimal orderValue = saleKpi.getOrderValue();

            //下单当月汇率
            BigDecimal exchangeRate = GeneralUtils.getBigDecimalVal(saleKpi.getExchangeRate());

            //净利润
            BigDecimal profit = GeneralUtils.getBigDecimalVal(saleKpiDS1Mapper.getProfit(orderDetailCode, orderNumber, productCode));
            if (!profit.equals(BigDecimal.ZERO)) {
                saleKpi.setRetainedProfit(profit);
            }


            Quotation quotationProp = saleKpiDS1Mapper.getQuotationProp(orderDetailCode, orderNumber, productCode);
            if (quotationProp != null) {
                //提点
                BigDecimal profitRate = quotationProp.getCommissionPercent();
                saleKpi.setProfitRate(profitRate);

                //提成金额
                BigDecimal profitAmount = orderValue.multiply(profitRate).multiply(exchangeRate);
                saleKpi.setProfitAmount(profitAmount);

                //提成占净利比
                if (profitAmount.compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal outAmountRate = profitAmount.divide(profitAmount, 12, BigDecimal.ROUND_HALF_UP);
                    saleKpi.setOutAmountRate(outAmountRate);
                }
                //占提成总额比例
                if (orderValue.compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal outTotalRate = profitAmount.divide(orderValue, 12, BigDecimal.ROUND_HALF_UP);
                    saleKpi.setOutTotalRate(outTotalRate);
                }
            }

            //报批表毛利
            BigDecimal quotationProfit = GeneralUtils.getBigDecimalVal(saleKpiDS1Mapper.getQuotationProfit(orderDetailCode, orderNumber, productCode));
            saleKpi.setDefinedProfit(quotationProfit);

            //本月到账金额
            DynamicDataSourceContextHolder.push(companyLabel);
            BigDecimal returnAmount = GeneralUtils.getBigDecimalVal(saleKpiDS1Mapper.getExReturnAmount(orderDetailCode, orderNumber, productCode));
            saleKpi.setCurrentAmount(returnAmount);

            DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
            updateById(saleKpi);
        });


    }


    public IPage<SaleKpi> findList(Page<SaleKpi> page, SaleKpiQuery saleKpiQuery) {
        String columnVisit = GeneralUtils.getColumnVisit(userRoleServiceImpl, roleColumnServiceImpl, columnServiceImpl, "sale_kpi");
        IPage<SaleKpi> iPage = new IPageEmpty<>();
        if (columnVisit != null) {
            saleKpiQuery.setColumnVisit(columnVisit);
            //saleKpiQuery.setColumnVisit("*");
            iPage = saleKpiDS1Mapper.findList(page, saleKpiQuery);
        }

        return iPage;
    }
}
