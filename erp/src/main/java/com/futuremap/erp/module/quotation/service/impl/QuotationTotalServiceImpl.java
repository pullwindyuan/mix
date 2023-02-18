package com.futuremap.erp.module.quotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.common.page.IPageEmpty;
import com.futuremap.erp.module.auth.service.impl.ColumnServiceImpl;
import com.futuremap.erp.module.auth.service.impl.RoleColumnServiceImpl;
import com.futuremap.erp.module.auth.service.impl.UserRoleServiceImpl;
import com.futuremap.erp.module.orderCost.entity.OrderCost;
import com.futuremap.erp.module.orderCost.mapper.OrderCostDS1Mapper;
import com.futuremap.erp.module.quotation.entity.Quotation;
import com.futuremap.erp.module.quotation.entity.QuotationTotal;
import com.futuremap.erp.module.quotation.entity.QuotationTotalQuery;
import com.futuremap.erp.module.quotation.mapper.QuotationTotalMapper;
import com.futuremap.erp.module.quotation.service.IQuotationTotalService;
import com.futuremap.erp.utils.BeanCopierUtils;
import com.futuremap.erp.utils.BigDecimalUtil;
import com.futuremap.erp.utils.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-10
 */
@Service
@Slf4j
public class QuotationTotalServiceImpl extends ServiceImpl<QuotationTotalMapper, QuotationTotal> implements IQuotationTotalService {


    @Autowired
    OrderCostDS1Mapper orderCostDS1Mapper;

    @Autowired
    QuotationTotalMapper quotationTotalMapper;

    @Autowired
    UserRoleServiceImpl userRoleServiceImpl;
    @Autowired
    RoleColumnServiceImpl roleColumnServiceImpl;
    @Autowired
    ColumnServiceImpl columnServiceImpl;

    public List<QuotationTotal> countQuotationData(List<Quotation> quotations) {
        List<QuotationTotal> quotationTotals = new ArrayList<>();

        try {
            quotations.stream().forEach(quotation -> {
                QuotationTotal quotationTotal = new QuotationTotal();
                String csocode = quotation.getCsocode();
                Integer irowno = quotation.getIrowno();
                String cinvcode = quotation.getCinvcode();
                String cinvname = quotation.getCinvname();
                String autoid = quotation.getAutoid();
                BigDecimal exchangeRate = quotation.getExchangeRate();

                quotationTotal.setCsocode(csocode);
                quotationTotal.setIrowno(irowno);
                quotationTotal.setCinvcode(cinvcode);
                quotationTotal.setAutoid(autoid);
                quotationTotal.setCinvname(cinvname);
                quotationTotal.setCompanyCode(quotation.getCompanyCode());
                quotationTotal.setCompanyName(quotation.getCompanyName());
                quotationTotal.setDdate(quotation.getDdate());
                quotationTotal.setExchangeRate(exchangeRate);

                BigDecimal laborCost1 = quotation.getLaborCost();
                BigDecimal materialCost1 = quotation.getMaterialCost();
                BigDecimal mgrCost1 = quotation.getMgrCost();
                BigDecimal exportingCost1 = quotation.getExportingCost();
                BigDecimal manufactureCost1 = quotation.getManufactureCost();


                BigDecimal cost = quotation.getCost();
                BigDecimal salePrice = quotation.getSalePrice();
                BigDecimal profit1 = BigDecimalUtil.multiply(BigDecimalUtil.devide4(BigDecimalUtil.subtract(salePrice, cost), salePrice), new BigDecimal(100));

                //报价成本
                BigDecimal quotationCost = laborCost1.add(materialCost1);
                quotationCost = quotationCost.add(manufactureCost1);
                quotationCost = quotationCost.add(mgrCost1);
                quotationCost = quotationCost.add(exportingCost1);
                quotationTotal.setQuotationCost(quotationCost);

                quotationTotal.setQuotationLaborCost(laborCost1);
                quotationTotal.setQuotationManufactureCost(manufactureCost1);
                quotationTotal.setQuotationMaterialCost(materialCost1);
                quotationTotal.setQuotationProfit(profit1);
                quotationTotal.setQuotationMgrCost(mgrCost1);
                quotationTotal.setQuotationShippingCost(exportingCost1);
                quotationTotal.setQuotationUnitprice(quotation.getUnitPrice());

                if (csocode != null && irowno != null && cinvcode != null) {
                    quotationTotalMapper.delOld(csocode, String.valueOf(irowno), cinvcode);
                    OrderCost orderCost = orderCostDS1Mapper.getOrderCostUnit(csocode, String.valueOf(irowno), cinvcode);
                    if (orderCost != null) {
                        fillActCostData(quotationTotal, orderCost);
                    }
                }
                quotationTotals.add(quotationTotal);
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导入报价表失败", e);
        }
        //批量保存汇总数据
        saveBatch(quotationTotals);
        return quotationTotals;
    }


    private void fillActCostData(QuotationTotal quotationTotal, OrderCost orderCost) {
        BeanCopierUtils.copyProperties(orderCost, quotationTotal);

        BigDecimal laborCost = GeneralUtils.getBigDecimalVal(orderCost.getLaborCost());
        BigDecimal materialCost = GeneralUtils.getBigDecimalVal(orderCost.getMaterialCost());
        BigDecimal mgrCost = GeneralUtils.getBigDecimalVal(orderCost.getMgrCost());
        BigDecimal runCost = GeneralUtils.getBigDecimalVal(orderCost.getRunCost());
        BigDecimal manufactureCost = GeneralUtils.getBigDecimalVal(orderCost.getManufactureCost());
        //实际成本
        BigDecimal actuaCost = laborCost.add(materialCost);
        actuaCost = actuaCost.add(mgrCost);
        actuaCost = actuaCost.add(runCost);
        actuaCost = actuaCost.add(manufactureCost);
        quotationTotal.setActualCost(actuaCost);

        //退税前利润
        BigDecimal profit = GeneralUtils.getBigDecimalVal(orderCost.getProfit());
        quotationTotal.setProfit(profit);

        //船务费用
        quotationTotal.setShippingCost(runCost);

        BigDecimal quotationCost = GeneralUtils.getBigDecimalVal(quotationTotal.getQuotationCost());

        //差异
        BigDecimal diff = BigDecimalUtil.subtract(quotationCost, actuaCost);
        diff = new BigDecimal(Math.abs(diff.doubleValue()));
        quotationTotal.setDiff(diff);

        //差异率
        BigDecimal diffRate = BigDecimalUtil.devide2(diff, quotationCost);
        quotationTotal.setDiffRadio(diffRate);
    }

    @Override
    public IPage<QuotationTotal> findList(Page<QuotationTotal> page, QuotationTotalQuery quotationTotalQuery) {
        String columnVisit = GeneralUtils.getColumnVisit(userRoleServiceImpl, roleColumnServiceImpl, columnServiceImpl, "quotation_total");
        IPage<QuotationTotal> iPage = new IPageEmpty<>();
        if (columnVisit != null) {
            quotationTotalQuery.setColumnVisit(columnVisit);
            //quotationTotalQuery.setColumnVisit("*");
            iPage = baseMapper.findList(page, quotationTotalQuery);
        }

        return iPage;
    }

    public List<QuotationTotal> findNullActCost() {
        return baseMapper.selectList(new QueryWrapper<QuotationTotal>().isNull("actual_cost"));
    }
}
