package com.futuremap.erp.module.orderCost.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.common.page.IPageEmpty;
import com.futuremap.erp.module.auth.service.impl.ColumnServiceImpl;
import com.futuremap.erp.module.auth.service.impl.RoleColumnServiceImpl;
import com.futuremap.erp.module.auth.service.impl.UserRoleServiceImpl;
import com.futuremap.erp.module.auth.service.impl.UserServiceImpl;
import com.futuremap.erp.module.constants.Constants;
import com.futuremap.erp.module.constants.OrderProcessConstants;
import com.futuremap.erp.module.orderCost.entity.*;
import com.futuremap.erp.module.orderCost.mapper.OrderCostDS1Mapper;
import com.futuremap.erp.module.orderCost.mapper.OrderCostDS2Mapper;
import com.futuremap.erp.module.orderCost.mapper.OrderCostTotalDS1Mapper;
import com.futuremap.erp.module.orderCost.service.IOrderCostService;
import com.futuremap.erp.utils.GeneralUtils;
import com.futuremap.erp.utils.ServiceUtils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rubio@futuremap.com.cn
 * @date 2021/6/3 9:50
 */
@Service
public class OrderCostServiceImpl extends ServiceImpl<OrderCostDS2Mapper, OrderCost> implements IOrderCostService {

    @Autowired
    OrderCostDS2Mapper orderCostDS2Mapper;
    @Autowired
    OrderCostDS1Mapper orderCostDS1Mapper;
    @Autowired
    OrderCostTotalDS1Mapper orderCostTotalDS1Mapper;
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    UserRoleServiceImpl userRoleServiceImpl;
    @Autowired
    RoleColumnServiceImpl roleColumnServiceImpl;
    @Autowired
    ColumnServiceImpl columnServiceImpl;


    public IPage<OrderCost> findList(IPage<OrderCost> page, OrderCostQuery orderCostQuery) {
        String columnVisit = GeneralUtils.getColumnVisit(userRoleServiceImpl, roleColumnServiceImpl, columnServiceImpl, "order_cost");
        String rowVisit = GeneralUtils.getRowVisit(userServiceImpl, userRoleServiceImpl);
        IPage<OrderCost> iPage = new IPageEmpty<>();
        if (columnVisit != null) {
            orderCostQuery.setColumnVisit(columnVisit);
            //orderCostQuery.setColumnVisit("*");
            if (rowVisit != null) {
                orderCostQuery.setCpersonCode(rowVisit);
            }

            iPage = orderCostDS1Mapper.findList(page, orderCostQuery);
        }
        return iPage;
    }

    public List<OrderCost> findLists(OrderCostQuery orderCostQuery) {
        return orderCostDS1Mapper.findLists(orderCostQuery);
    }

    private List<String> getLastinvcode(List<String> cinvcodes) {
        List<String> allCinv = new ArrayList<>();
        List<String> my_res = new ArrayList<>();
        for (String cinvcode : cinvcodes) {
            allCinv.addAll(getChildCinvcode(cinvcode));
        }
        for (String cinvcode : cinvcodes) {
            if (!allCinv.contains(cinvcode)) {
                my_res.add(cinvcode);
            }
        }
        return my_res;
    }


    private List<String> getChildCinvcode(String cinvcode) {
        List<String> res = new ArrayList<>();
        String parentBomId = orderCostDS2Mapper.getBomId(cinvcode);
        List<Bom> bomList = orderCostDS2Mapper.getBomList(parentBomId);
        while (bomList.size() > 0) {
            List<String> bomids = new ArrayList<>();
            Map<String, Object> param = new HashMap<>();
            for (Bom bom : bomList) {
                String invCode = bom.getInvCode();
                if (invCode != null && !res.contains(invCode)) {
                    res.add(invCode);
                }
                String bomId = orderCostDS2Mapper.getBomId(invCode);
                if (bomId != null && !bomids.contains(bomId)) {
                    bomids.add(bomId);
                }
            }

            if (bomids.size() > 0) {
                param.put("bomids", bomids);
                bomList = orderCostDS2Mapper.getBomLists(param);
            } else {
                break;
            }


        }
        return res;
    }


    //计算半成品的材料成本
    private BigDecimal getSimplePirce(String orderDetailCode, String orderNumber, String cAmoType, String cinvcode) {
        BigDecimal all_price = BigDecimal.ZERO;
        List<OrderCost> ocns;
        if (orderNumber != null) {
            ocns = orderCostDS2Mapper.getManufactureOrdersInfos(orderDetailCode, orderNumber, Constants.DEPT_CODE_020301);
        } else {
            ocns = orderCostDS2Mapper.getManufactureOrdersInfos0001(orderDetailCode, Constants.DEPT_CODE_020301);
        }


        if (!ocns.isEmpty()) {

            Map<String, BigDecimal> invCodeCntMap = new HashMap<>();
            String parentBomId = orderCostDS2Mapper.getBomId(cinvcode);
            List<Bom> bomList = orderCostDS2Mapper.getBomList(parentBomId);
            for (Bom bom : bomList) {
                invCodeCntMap.put(bom.getInvCode(), bom.getBaseQtyN());
            }
            List<String> cinvcodes = new ArrayList<>();
            for (OrderCost oc : ocns) {
                if (oc != null) {
                    String cc = oc.getCinvcode();
                    cinvcodes.add(cc);
                }
            }
            if (cinvcodes.size() > 0) {
                List<String> lastinvcodes = getLastinvcode(cinvcodes);

                if (lastinvcodes.size() > 0) {

                    for (OrderCost oc : ocns) {//这些是要经过针车车间加工的物料
                        if (oc != null) {
                            String odc = oc.getOrderDetailCode();
                            String on = oc.getOrderNumber();
                            String dm = oc.getDmonth();
                            String cc = oc.getCinvcode();
                            if (lastinvcodes.contains(cc)) {
                                if (odc != null && on != null) {
                                    BigDecimal unit_p = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getManufactureCostByItem(odc, on, cAmoType, dm));
                                    BigDecimal unit_c = GeneralUtils.getBigDecimalVal(invCodeCntMap.get(cc));
                                    BigDecimal bom_p = unit_p.multiply(unit_c);

                                    all_price = all_price.add(bom_p);
                                }
                            }


                        }
                    }
                }
            }

        }

        return all_price;
    }


    //获取订单成本中的单价，包括了材料、制造、人工成本，通过cAmoType字段来区分，对应ERP系统里功能是“完工产品成本汇总表”
    private Map<String, Object> getUnitPrice(String orderDetailCode, String orderNumber, String cinvcode, String cAmoType, String ds, String companyLabel, String dmonth) {


        DynamicDataSourceContextHolder.push(ds);
        BigDecimal unit_price = BigDecimal.ZERO;
        Integer orderType = Constants.ORDER_TYPE_0;
        List<OrderCost> ocns = new ArrayList<>();
        //首先要获取制造订单号，行号这些信息
        OrderCost ocn;
        if (orderNumber != null) {
            ocn = orderCostDS2Mapper.getManufactureOrdersInfo(orderDetailCode, orderNumber, cinvcode, Constants.DEPT_CODE_020303);
        } else {
            ocn = orderCostDS2Mapper.getManufactureOrdersInfo0001(orderDetailCode, cinvcode, Constants.DEPT_CODE_020303);
        }

        if (ocn != null) {
            ocns.add(ocn);
            if (cAmoType == Constants.CAMOTYPE_0) {
                DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                orderCostDS1Mapper.updateByType(dmonth, companyLabel, orderDetailCode, cinvcode, Constants.ORDER_TYPE_6);
                DynamicDataSourceContextHolder.push(companyLabel);
                orderType = Constants.ORDER_TYPE_6;
            }
        }

        if (!ocns.isEmpty()) {

            for (OrderCost oc : ocns) {
                if (oc != null) {
                    String odc = oc.getOrderDetailCode();
                    String on = oc.getOrderNumber();
                    String dm = oc.getDmonth();

                    if (odc != null && on != null) {

                        if (dm != null) {
                            unit_price = unit_price.add(GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getItemCostByType(odc, on, cAmoType, dm)));
                            if (unit_price.compareTo(BigDecimal.ZERO) == 0) { //这种算是异常的订单处理
                                Integer dm_n = Integer.parseInt(dm) + 1;
                                unit_price = unit_price.add(GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getItemCostByType(odc, on, cAmoType, String.valueOf(dm_n))));
                            }

                        } else {
                            unit_price = unit_price.add(GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getItemCostByType0001(odc, on, cAmoType, dmonth)));
                        }


                    }
                }
            }


        }
        if (unit_price.compareTo(BigDecimal.ZERO) == 0) {//如果该订单没有到“包装车间”，则要统计到其他二个车间的情况
            DynamicDataSourceContextHolder.push(companyLabel);
            unit_price = getSimplePirce(orderDetailCode, orderNumber, cAmoType, cinvcode);
        }
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("unitPrice", unit_price);
        resMap.put("orderType", orderType);
        return resMap;
    }

    public void calcMonthBill(String companyLabel, String dmonth) {

        String[] startAndEndDay = GeneralUtils.getStartAndEndDay(dmonth);
        String startDay = startAndEndDay[0];
        String endDay = startAndEndDay[1];


        Map<String, Object> param = new HashMap<>();
        param.put("startDate", startDay);
        param.put("endDate", endDay);
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        List<String> companyCodeList = orderCostDS1Mapper.getCompanyCodeList(companyLabel);
        param.put("excompany", companyCodeList);
        DynamicDataSourceContextHolder.push(companyLabel);


        BigDecimal export_amount = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getExport_amount(param));//出口收入
        BigDecimal domestic_amount = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getDomestic_amount2(param));//内销收入
        BigDecimal all_amount = export_amount.add(domestic_amount);//内销+外销收入，要和订单收入金额汇总，达到相等


        BigDecimal mgr_cost = BigDecimal.ZERO;
        BigDecimal run_cost = BigDecimal.ZERO;
        BigDecimal manufacture_cost = BigDecimal.ZERO;

        if (companyLabel.equals(Constants.ERP002) || companyLabel.equals(Constants.ERP004) || companyLabel.equals(Constants.ERP006)) {
            mgr_cost = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getItemCost(dmonth, "5502"));//管理费用
            run_cost = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getItemCost(dmonth, "5501"));//营业费用
            manufacture_cost = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getItemCost(dmonth, "4105"));//制造费用
        } else if (companyLabel.equals(Constants.ERP003)) {
            mgr_cost = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getItemCost(dmonth, "6602"));//管理费用
            run_cost = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getItemCost(dmonth, "6601"));//营业费用
            manufacture_cost = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getItemCost(dmonth, "5101"));//制造费用
        } else if (companyLabel.equals(Constants.ERP010)) {
            mgr_cost = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getItemCost(dmonth, "551"));//管理费用
            run_cost = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getItemCost(dmonth, "517"));//营业费用
            manufacture_cost = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getItemCost(dmonth, "145"));//制造费用
        }

        BigDecimal labor_cost = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getLabor_cost(dmonth));//人工费

        OrderCostTotal oct = new OrderCostTotal();
        oct.setCompanyLabel(companyLabel);
        oct.setDmonth(dmonth);
        oct.setDomesticAmount(domestic_amount);
        oct.setExportAmount(export_amount);
        oct.setMgrCost(mgr_cost);
        oct.setRunCost(run_cost);
        oct.setManufactureCost(manufacture_cost);
        oct.setLaborCost(labor_cost);
        oct.setTotalAmount(all_amount);
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        orderCostTotalDS1Mapper.delOrderCostTotal(dmonth, companyLabel);//删除旧数据
        orderCostTotalDS1Mapper.insert(oct);

    }

    private List<OrderCost> getOuterOrder(String companyLabel, String startDay, String endDay) {
        DynamicDataSourceContextHolder.push(companyLabel);
        List<OrderCost> outerOrderList = orderCostDS2Mapper.getOrderCodeList(startDay, endDay);
        outerOrderList.stream().forEach(orderCost -> {
            orderCost.setOrderSrc(Constants.ORDER_SRC_OUTER);
            orderCost.setCompanyLabel(companyLabel);

        });


        return outerOrderList;
    }

    private List<OrderCost> getInnerOrder(String companyLabel, String startDay, String endDay) {
        //内销订单
        DynamicDataSourceContextHolder.push(companyLabel);
        List<OrderCost> innerOrderList;
        if (Constants.ERP010.equals(companyLabel)) {
            innerOrderList = orderCostDS2Mapper.getOrderAndIncomeList0(startDay, endDay);
        } else {

            if (Constants.ERP002.equals(companyLabel) || Constants.ERP003.equals(companyLabel)) {
                innerOrderList = orderCostDS2Mapper.getOrderAndIncomeList3(startDay, endDay);
            } else {
                innerOrderList = orderCostDS2Mapper.getOrderAndIncomeList(startDay, endDay);
            }

        }

        innerOrderList.stream().forEach(orderCost -> {
            orderCost.setOrderSrc(Constants.ORDER_SRC_INNER);
            orderCost.setCompanyLabel(companyLabel);
        });
        return innerOrderList;
    }

    private List<OrderCost> getOrders(String companyLabel, String startDay, String endDay) {
        startDay = startDay.substring(0, 10);
        endDay = endDay.substring(0, 10);

        List<OrderCost> orderList = new ArrayList<>(); //内销+外销
        orderList.addAll(getOuterOrder(companyLabel, startDay, endDay));
        orderList.addAll(getInnerOrder(companyLabel, startDay, endDay));


//        List<OrderCost> _orderList = new ArrayList<>();
//        orderList.stream().forEach(orderCost -> {
//            if (orderCost != null) {
//                String orderDetailCode = orderCost.getOrderDetailCode();
//                String orderNumber = orderCost.getOrderNumber();
//                String cinvcode = orderCost.getCinvcode();
//                String invoiceId = orderCost.getInvoiceId();
//
//
////                if (orderDetailCode.equals("IB2021001C002") && orderNumber.equals("2")) {
////                    _orderList.add(orderCost);
////                }
////                if (orderDetailCode.equals("IB2020061C") && cinvcode.equals("IB-1301000022")) {
////                    _orderList.add(orderCost);
////                }
////                if (orderDetailCode.equals("SC2020110E") && orderNumber.equals("2")) {
////                    _orderList.add(orderCost);
////                }
////                if (orderDetailCode.equals("NF2021031E") && orderNumber.equals("1")) {
////                    _orderList.add(orderCost);
////                }
//
////                if (orderDetailCode.equals("NF2021082E") && orderNumber.equals("7")) {
////                    _orderList.add(orderCost);
////                }
////                if (orderDetailCode.equals("IB2021001C") && cinvcode.equals("W5655-MIX160-004")) {
////                    _orderList.add(orderCost);
////                }
////                if (orderDetailCode.equals("IB2020057C") && cinvcode.equals("IB-1301000023") && orderNumber.equals("3") && invoiceId.equals("0000000124")) {
////                    _orderList.add(orderCost);
////                }
//                if (orderDetailCode.equals("IB2020040M-13") && cinvcode.equals("IB-1309000020")) {
//                    _orderList.add(orderCost);
//                }
//            }
//
//        });
//
//        return _orderList;

        return orderList;
    }


    public void updateFatherChildOrders(String startDay, String endDay, String dmonth, String companyLabel) {
        try {
            startDay = startDay.substring(0, 10);
            endDay = endDay.substring(0, 10);

            DynamicDataSourceContextHolder.push(companyLabel);
            List<OrderCost> flist = orderCostDS2Mapper.getOCbyType1(startDay, endDay);
            for (OrderCost orderCost : flist) {
                String orderDetailCode = orderCost.getOrderDetailCode();
                String orderNumber = orderCost.getOrderNumber();
                String cinvcode = orderCost.getCinvcode();
                String irowno = orderCost.getIrowno();
                String cparentCode = orderCost.getCparentCode();
                String autoid = orderCost.getAutoid();

                if (orderDetailCode != null
                        && autoid != null
                        && orderNumber != null
                        && cinvcode != null
                        && irowno != null
                        && cparentCode != null) {
                    DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                    orderCostDS1Mapper.updateF(orderDetailCode, orderNumber, cinvcode, irowno, cparentCode, autoid);
                }

            }
            DynamicDataSourceContextHolder.push(companyLabel);

            List<OrderCost> clist = orderCostDS2Mapper.getOCbyType2(startDay, endDay);
            for (OrderCost orderCost : clist) {
                String orderDetailCode = orderCost.getOrderDetailCode();
                String orderNumber = orderCost.getOrderNumber();
                String cinvcode = orderCost.getCinvcode();
                String irowno = orderCost.getIrowno();
                String autoid = orderCost.getAutoid();
                String cchildCode = orderCost.getCchildCode();
                if (orderDetailCode != null
                        && autoid != null
                        && orderNumber != null
                        && cinvcode != null
                        && irowno != null
                        && cchildCode != null) {
                    DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                    orderCostDS1Mapper.updateC(orderDetailCode, orderNumber, cinvcode, irowno, cchildCode, autoid);
                }
            }

            DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
            List<OrderCost> childlist = orderCostDS1Mapper.getClist(dmonth, companyLabel);
            for (OrderCost orderCost_child : childlist) {

                //增加的材料成本
                BigDecimal materialCost = GeneralUtils.getBigDecimalVal(orderCost_child.getMaterialCost());
                String cchildCode = orderCost_child.getCchildCode();

                OrderCost orderCost_father = orderCostDS1Mapper.getFone(cchildCode);
                if (orderCost_father != null) {


                    BigDecimal income = GeneralUtils.getBigDecimalVal(orderCost_father.getIncome());

                    BigDecimal mc = GeneralUtils.getBigDecimalVal(orderCost_father.getMaterialCost());
                    mc = mc.add(materialCost);
                    orderCost_father.setMaterialCost(mc);

                    //成本小计
                    BigDecimal cs = GeneralUtils.getBigDecimalVal(orderCost_father.getCostSum());
                    cs = cs.add(materialCost);
                    orderCost_father.setCostSum(cs);

                    //毛利小计
                    BigDecimal pf = GeneralUtils.getBigDecimalVal(orderCost_father.getProfitSum());
                    pf = pf.subtract(materialCost);
                    orderCost_father.setProfitSum(pf);

                    //毛利率小计
                    if (income.compareTo(BigDecimal.ZERO) == 1) {
                        BigDecimal psr = GeneralUtils.getBigDecimalVal(pf.divide(income, Constants.DIVIDE_LEVEL_12, BigDecimal.ROUND_HALF_UP));
                        orderCost_father.setProfitSumRadio(psr);
                    }


                    //合计成本
                    BigDecimal ct = GeneralUtils.getBigDecimalVal(orderCost_father.getCostTotal());
                    ct = ct.add(materialCost);
                    orderCost_father.setCostTotal(ct);

                    //利润
                    BigDecimal pp = GeneralUtils.getBigDecimalVal(orderCost_father.getProfit());
                    pp = pp.subtract(materialCost);
                    orderCost_father.setProfit(pp);

                    //利润率
                    if (income.compareTo(BigDecimal.ZERO) == 1) {
                        BigDecimal ppr = GeneralUtils.getBigDecimalVal(pp.divide(income, Constants.DIVIDE_LEVEL_12, BigDecimal.ROUND_HALF_UP));
                        orderCost_father.setProfitRadio(ppr);
                    }


                    updateById(orderCost_father);

                    orderCost_child.setCostTotal(BigDecimal.ZERO);
                    orderCost_child.setCostSum(BigDecimal.ZERO);
                    orderCost_child.setRunCost(BigDecimal.ZERO);
                    orderCost_child.setMgrCost(BigDecimal.ZERO);
                    orderCost_child.setLaborCost(BigDecimal.ZERO);
                    orderCost_child.setProfitRadio(BigDecimal.ZERO);
                    orderCost_child.setProfitSumRadio(BigDecimal.ZERO);
                    orderCost_child.setManufactureCost(BigDecimal.ZERO);
                    orderCost_child.setMaterialCost(BigDecimal.ZERO);
                    orderCost_child.setTaxPrice(BigDecimal.ZERO);
                    orderCost_child.setIncome(BigDecimal.ZERO);
                    orderCost_child.setProfit(BigDecimal.ZERO);
                    orderCost_child.setProfitSum(BigDecimal.ZERO);
                    updateById(orderCost_child);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void updateIncome(OrderCost orderCost, String companyLabel, BigDecimal income, String
            orderDetailCode, String orderNumber, String cinvcode, String irowno, String csbvcode, String autoid) {
        if (income.compareTo(BigDecimal.ZERO) == 0) {
            BigDecimal orderIncome;
            DynamicDataSourceContextHolder.push(companyLabel);

            if (irowno != null) {
                if (orderCost.getOrderSrc() == Constants.ORDER_SRC_OUTER) { //外销订单
                    orderIncome = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getOrderIncome_outer(orderDetailCode, orderNumber, cinvcode, csbvcode, irowno, autoid));
                } else {//内销订单
                    if (companyLabel.equals(Constants.ERP010)) {
                        orderIncome = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getOrderIncome_Co10(orderDetailCode, cinvcode, csbvcode, irowno, orderNumber, autoid));
                    } else {
                        orderIncome = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getOrderIncome_inner(orderDetailCode, orderNumber, cinvcode, irowno, csbvcode, autoid));
                    }
                }
            } else {
                if (orderCost.getOrderSrc() == Constants.ORDER_SRC_OUTER) { //外销订单
                    orderIncome = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getOrderIncome_outer1(orderDetailCode, orderNumber, cinvcode, csbvcode, autoid));
                } else {//内销订单
                    if (companyLabel.equals(Constants.ERP010)) {
                        orderIncome = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getOrderIncome_Co10_1(orderDetailCode, cinvcode, csbvcode, orderNumber, autoid));
                    } else {
                        orderIncome = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getOrderIncome_inner1(orderDetailCode, orderNumber, cinvcode, csbvcode, autoid));
                    }
                }
            }


            orderCost.setIncome(orderIncome);
        }
    }

    private BigDecimal getNearPrice(String cinvcode, String dmonth, String companyLabel) {
        DynamicDataSourceContextHolder.push(companyLabel);
        BigDecimal materialCost_unit;
        materialCost_unit = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_unit10(cinvcode));
        if (materialCost_unit.compareTo(BigDecimal.ZERO) == 0) {
            materialCost_unit = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_unit10_plus(cinvcode, dmonth.substring(0, 4)));
        }
        return materialCost_unit;
    }


    public void updateMaterialCost(OrderCost orderCost, BigDecimal material_cost, String companyLabel, String
            orderDetailCode, String orderNumber, String cinvcode, BigDecimal fquantity, String dmonth) {
        if (material_cost.compareTo(BigDecimal.ZERO) == 0) {

            BigDecimal materialCost;
            BigDecimal hexiaoCost = BigDecimal.ZERO;
            Integer orderType = Constants.ORDER_TYPE_0;
            BigDecimal laborCost = BigDecimal.ZERO;
            BigDecimal manufactureCost = BigDecimal.ZERO;

            if (companyLabel.equals(Constants.ERP010)) {
                BigDecimal materialCost_unit = getNearPrice(cinvcode, dmonth, companyLabel);
                materialCost = materialCost_unit.multiply(fquantity);
                orderCost.setTaxPrice(materialCost_unit);
                if (materialCost_unit.compareTo(BigDecimal.ZERO) == 0) {
                    orderCost.setOrderType(Constants.ORDER_TYPE_EXCEPTION);
                }


            } else {
                //包含了有包装，针车，裁剪等车间材料成本
                if (Constants.ERP002.equals(companyLabel)) {
                    Map<String, Object> resMap = getUnitPrice(orderDetailCode, orderNumber, cinvcode, Constants.CAMOTYPE_0, companyLabel, companyLabel, dmonth);
                    BigDecimal materialCost_unit = (BigDecimal) resMap.get("unitPrice");
                    orderType = (Integer) resMap.get("orderType");
                    materialCost = materialCost_unit.multiply(fquantity);
                    orderCost.setTaxPrice(materialCost_unit);
                    //处理核销
                    hexiaoCost = fillHexiao(orderCost, orderDetailCode, cinvcode, orderNumber, fquantity);


                } else {
                    DynamicDataSourceContextHolder.push(companyLabel);
                    BigDecimal materialCost_unit;
                    if (orderNumber != null) {
                        materialCost_unit = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_1(orderDetailCode, orderNumber, cinvcode));

                    } else {
                        materialCost_unit = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_1X(orderDetailCode, cinvcode));
                    }
                    //处理异常的订单
                    if (materialCost_unit.compareTo(BigDecimal.ZERO) == 0) {
                        Map<String, Object> map_mc;
                        if (orderNumber != null) {
                            map_mc = orderCostDS2Mapper.getMaterialCost_1001(orderDetailCode, orderNumber, cinvcode);

                        } else {
                            map_mc = orderCostDS2Mapper.getMaterialCost_1001X(orderDetailCode, cinvcode);

                        }

                        if (map_mc != null) { //异常的订单，取值的字段都不一样
                            Object csocode = map_mc.get("csocode");
                            Object iNatUnitPrice = map_mc.get("iNatUnitPrice");
                            if (csocode != null && iNatUnitPrice == null) {
                                materialCost_unit = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_1000(cinvcode));
                            }
                        }


                    }

                    materialCost = materialCost_unit.multiply(fquantity);
                    orderCost.setTaxPrice(materialCost_unit);
                }


                if (Constants.ERP002.equals(companyLabel)) {
                    //人工费
                    BigDecimal laborCost_unit = (BigDecimal) getUnitPrice(orderDetailCode, orderNumber, cinvcode, Constants.CAMOTYPE_4, companyLabel, companyLabel, dmonth).get("unitPrice");
                    laborCost = laborCost_unit.multiply(fquantity);
                    //制造费
                    BigDecimal manufactureCost_unit = (BigDecimal) getUnitPrice(orderDetailCode, orderNumber, cinvcode, Constants.CAMOTYPE_3, companyLabel, companyLabel, dmonth).get("unitPrice");
                    manufactureCost = manufactureCost_unit.multiply(fquantity);

                    orderCost.setLaborCost(laborCost);
                    orderCost.setManufactureCost(manufactureCost);
                } else {
                    BigDecimal df = BigDecimal.ZERO; //非制造企业的人工费和制造费用为0
                    orderCost.setLaborCost(df);
                    orderCost.setManufactureCost(df);
                }


                //委外订单,每个订单都要检查一下，如果有，则要加上这部分的成本
                materialCost = materialCost.add(processWW(orderDetailCode, orderNumber, fquantity, orderCost));
            }
            if (orderType != Constants.ORDER_TYPE_6 && hexiaoCost.compareTo(BigDecimal.ZERO) == 1) {
                materialCost = hexiaoCost.subtract(laborCost).subtract(manufactureCost);
            }

            orderCost.setMaterialCost(materialCost);
        }
    }

    private BigDecimal fillHexiao(OrderCost orderCost, String orderDetailCode, String cinvcode, String
            orderNumber, BigDecimal fquantity) {
        BigDecimal hexiao_unit = BigDecimal.ZERO;
        BigDecimal hexiao = BigDecimal.ZERO;
        if (orderNumber != null) {
            hexiao_unit = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_Hexiao(orderDetailCode, orderNumber, cinvcode));
        } else {
            hexiao_unit = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_HexiaoX(orderDetailCode, cinvcode));
        }


        if (hexiao_unit != null) {
            hexiao = hexiao_unit.multiply(fquantity);
            orderCost.setTaxPrice(hexiao_unit);
            orderCost.setHexiaoCost(hexiao);
        }
        return hexiao;
    }

    private void flagWW(String companyLabel, String dmonth) {
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        List<OrderCost> orderCostList = orderCostDS1Mapper.getOrdersByTime(dmonth, companyLabel);
        for (OrderCost orderCost : orderCostList) {
            String orderNumber = orderCost.getOrderNumber();
            String orderDetailCode = orderCost.getOrderDetailCode();
            BigDecimal materialCost_ww;
            DynamicDataSourceContextHolder.push(companyLabel);
            if (orderNumber != null) {
                materialCost_ww = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_ww(orderDetailCode, orderNumber));
            } else {
                materialCost_ww = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_wwX(orderDetailCode));
            }
            if (materialCost_ww.compareTo(BigDecimal.ZERO) == 1) {
                orderCost.setOrderType(Constants.ORDER_TYPE_1);
                DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                updateById(orderCost);
            }

        }
    }


    private BigDecimal processWW(String orderDetailCode, String orderNumber, BigDecimal fquantity, OrderCost
            orderCost) {
        BigDecimal materialCost_ww;
        if (orderNumber != null) {
            materialCost_ww = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_ww(orderDetailCode, orderNumber));
        } else {
            materialCost_ww = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_wwX(orderDetailCode));
        }

        materialCost_ww = materialCost_ww.multiply(fquantity);
        if (orderCost.getTaxPrice() == null || orderCost.getTaxPrice().compareTo(BigDecimal.ZERO) == 0) {
            orderCost.setTaxPrice(materialCost_ww);
        }

        return materialCost_ww;
    }


    public void process0materialByNameLike(String dmonth) {
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        List<OrderCost> zmaterials = orderCostDS2Mapper.getOmaterial(dmonth);
        for (OrderCost orderCost : zmaterials) {
            try {
                Integer id = orderCost.getId();
                String orderDetailCodeLike = orderCost.getOrderDetailCode() + "%";

                String orderNumber = orderCost.getOrderNumber();
                String cinvcode = orderCost.getCinvcode();
                String companyLabel = orderCost.getCompanyLabel();
                BigDecimal fquantity = orderCost.getFquantity();

                DynamicDataSourceContextHolder.push(companyLabel);
                Map<String, Object> resMap = orderCostDS2Mapper.getMaterialCost_2(orderDetailCodeLike, orderNumber, cinvcode);
                if (resMap != null) {
                    Object matericalCost_unit_obj = resMap.get("matericalCost_unit");
                    Object orderDetailCodeObj = resMap.get("orderDetailCode");

                    if (matericalCost_unit_obj != null && orderDetailCodeObj != null) {
                        BigDecimal matericalCost_unit = (BigDecimal) matericalCost_unit_obj;
                        String unOrderDetailCode = (String) orderDetailCodeObj;

                        if (matericalCost_unit.compareTo(BigDecimal.ZERO) == 1) {
                            orderCost.setMaterialCost(matericalCost_unit.multiply(fquantity));
                            orderCost.setTaxPrice(matericalCost_unit);

                            if (unOrderDetailCode.contains("|002")) {
                                String orderDetailCode002 = orderCost.getOrderDetailCode() + "002";
                                OrderCost oc = new OrderCost();
                                updateMaterialCost(oc, BigDecimal.ZERO, Constants.ERP002, orderDetailCode002, orderNumber, cinvcode, fquantity, dmonth);
                                orderCost.setTaxPrice(oc.getTaxPrice());
                                orderCost.setHexiaoCost(oc.getHexiaoCost());
                                orderCost.setMaterialCost(oc.getMaterialCost());
                                orderCost.setManufactureCost(oc.getManufactureCost());
                                orderCost.setLaborCost(oc.getLaborCost());

                            }

                            DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                            updateById(orderCost);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public void processPurchaseOrders(String dmonth, String companyLabel) {
        if (companyLabel.equals(Constants.ERP002)) {
            DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
            List<OrderCost> zmaterials = orderCostDS2Mapper.getOmaterial(dmonth);
            zmaterials.stream().forEach(orderCost -> {

                try {
                    String cinvcode = orderCost.getCinvcode();
                    String orderDetailCode = orderCost.getOrderDetailCode();
                    String orderNumber = orderCost.getOrderNumber();
                    BigDecimal fquantity = orderCost.getFquantity();
                    DynamicDataSourceContextHolder.push(companyLabel);
                    BigDecimal nearPrice_unit = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getiNatUnitPrice(orderDetailCode, orderNumber, cinvcode));
                    if (nearPrice_unit.compareTo(BigDecimal.ZERO) == 1) {
                        orderCost.setTaxPrice(nearPrice_unit);
                        BigDecimal material_cost = nearPrice_unit.multiply(fquantity);
                        orderCost.setMaterialCost(material_cost);
                        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                        updateById(orderCost);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void process6to4orders(String dmonth, String companyLabel) {
        if (companyLabel.equals(Constants.ERP006)) {
            DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
            List<OrderCost> zmaterials = orderCostDS2Mapper.getOmaterial(dmonth);
            zmaterials.stream().forEach(orderCost -> {
                try {
                    String cinvcode = orderCost.getCinvcode();
                    String orderDetailCode = orderCost.getOrderDetailCode();
                    BigDecimal fquantity = orderCost.getFquantity();
                    String orderNumber = orderCost.getOrderNumber();
                    DynamicDataSourceContextHolder.push(Constants.ERP004);
                    BigDecimal materialCost_unit = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_1(orderDetailCode, orderNumber, cinvcode));
                    if (materialCost_unit.compareTo(BigDecimal.ZERO) == 0) {
                        materialCost_unit = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_0001(orderDetailCode, cinvcode));
                    }
                    if (materialCost_unit.compareTo(BigDecimal.ZERO) == 0 && orderDetailCode.contains("-Revised")) {
                        orderDetailCode = orderDetailCode.replace("-Revised", "");
                        materialCost_unit = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_0001(orderDetailCode, cinvcode));
                    }
                    if (materialCost_unit.compareTo(BigDecimal.ZERO) == 0) {
                        orderDetailCode = orderDetailCode + "%";
                        materialCost_unit = GeneralUtils.getBigDecimalVal(orderCostDS2Mapper.getMaterialCost_0002(orderDetailCode, cinvcode));
                    }

                    BigDecimal materialCost = materialCost_unit.multiply(fquantity);
                    orderCost.setTaxPrice(materialCost_unit);
                    orderCost.setMaterialCost(materialCost);
                    DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                    updateById(orderCost);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        }
    }


    private void processSwitchOrder(String dmonth, String companyLabel) {

        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        //处理挪货
        List<OrderCost> orderCostList = orderCostDS1Mapper.getOrderCostList6(dmonth, companyLabel);//挪库订单,材料成本为0的订单
        orderCostList.stream().forEach(orderCost -> {
            try {
                String orderDetailCode = orderCost.getOrderDetailCode();
                String cinvcode = orderCost.getCinvcode();
                BigDecimal materialCost = orderCost.getMaterialCost();
                BigDecimal fquantity = orderCost.getFquantity();

                if (materialCost == null || materialCost.compareTo(BigDecimal.ZERO) == 0) {
                    OrderCost switch_oc = orderCostDS1Mapper.getOC(orderDetailCode, cinvcode);//被挪用的订单
                    if (switch_oc != null) {
                        String orderDetailCode_switch = switch_oc.getOrderDetailCode();
                        String orderNumber_switch = switch_oc.getOrderNumber();
                        updateMaterialCost(orderCost, BigDecimal.ZERO, companyLabel, orderDetailCode_switch, orderNumber_switch, cinvcode, fquantity, dmonth);
                        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                        updateById(orderCost);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });


    }


    private void flagOwnerOrder(String companyLabel, String dmonth) {
        Map<String, Object> param = new HashMap<>();
        param.put("companyLabel", companyLabel);
        param.put("dmonth", dmonth);
        param.put("orderType", Constants.ORDER_TYPE_3);
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        List<String> companyCodeList = orderCostDS1Mapper.getCompanyCodeList(companyLabel);
        param.put("excompany", companyCodeList);
        orderCostDS1Mapper.updateOwnd(param);


        //处理抛单
        if (companyLabel.equals(Constants.ERP002)) {
            orderCostDS1Mapper.updateOtype2(dmonth, companyLabel, Constants.ORDER_TYPE_3);
        } else if (companyLabel.equals(Constants.ERP003)) {
            orderCostDS1Mapper.updateOtype3(dmonth, companyLabel, Constants.ORDER_TYPE_3);
        }
    }

    private void updateRevisedorders(String companyLabel, String dmonth) {
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        List<OrderCost> zmaterials = orderCostDS2Mapper.getOmaterial(dmonth);
        zmaterials.stream().forEach(orderCost -> {
            try {
                String orderDetailCode = orderCost.getOrderDetailCode();
                if (orderDetailCode.contains("-Revised")) {
                    String odc_sw = orderDetailCode.replace("-Revised", "");
                    String orderNumber = orderCost.getOrderNumber();
                    String cinvcode = orderCost.getCinvcode();
                    BigDecimal fquantity = orderCost.getFquantity();
                    updateMaterialCost(orderCost, BigDecimal.ZERO, companyLabel, odc_sw, orderNumber, cinvcode, fquantity, dmonth);
                    DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                    updateById(orderCost);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void updateProp(String companyLabel, String dmonth) {
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        List<OrderCost> orderCostList = orderCostDS1Mapper.getOrdersByTime(dmonth, companyLabel);
        orderCostList.stream().forEach(orderCost -> {
            String orderDetailCode = orderCost.getOrderDetailCode();
            String cinvcode = orderCost.getCinvcode();
            String orderNumber = orderCost.getOrderNumber();
            String ddmonth = orderCost.getDmonth();
            String ccusCode = orderCost.getCcusCode();
            String ccusName = orderCost.getCcusName();
            Integer orderType = orderCost.getOrderType();
            String deliverDay = orderCost.getDeliverDay();
            String invoiceId = orderCost.getInvoiceId();

            if (orderType != null) {

                //客户
                if ((ccusName == null || "".equals(ccusName)) && ccusCode != null) {
                    DynamicDataSourceContextHolder.push(companyLabel);
                    String ccusname = orderCostDS2Mapper.getCcusname(ccusCode);
                    if (ccusname != null) {
                        orderCost.setCcusName(ccusname); //客户名称
                    }
                }

                //业务员
                DynamicDataSourceContextHolder.push(companyLabel);
                String cpersoncode = orderCostDS2Mapper.getCpersoncodeOuter(orderDetailCode);
                if (cpersoncode == null || "".equals(cpersoncode)) {
                    cpersoncode = orderCostDS2Mapper.getCpersoncodeInner(orderDetailCode);
                }
                if (cpersoncode != null) {
                    orderCost.setCpersonCode(cpersoncode);
                    String cpersonName = orderCostDS2Mapper.getCpersonName(cpersoncode);
                    if (cpersonName != null) {
                        orderCost.setCpersonName(cpersonName);
                    }
                }

                //供应商
                List<Cven> cvenList = orderCostDS2Mapper.getCvenList(orderDetailCode, orderNumber);
                if (cvenList.size() == 0) {
                    if (orderDetailCode.contains("-Revised")) {
                        String new_odc = orderDetailCode.substring(0, orderDetailCode.indexOf("-Revised"));
                        cvenList = orderCostDS2Mapper.getCvenList(new_odc, orderNumber);
                        if (cvenList.size() == 0) {
                            new_odc = new_odc + "%";
                            cvenList = orderCostDS2Mapper.getCvenListLike(new_odc, orderNumber);
                        }
                    }

                    if (cvenList.size() == 0) {
                        String new_odc = orderDetailCode + "%";
                        cvenList = orderCostDS2Mapper.getCvenListLike(new_odc, orderNumber);
                    }

                    if (cvenList.size() == 0) {
                        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                        SwitchOrder switchOrder = orderCostDS1Mapper.getSwitchOrder(orderDetailCode, orderNumber, ddmonth);
                        if (switchOrder != null) {
                            String orderSwitch = switchOrder.getOrderSwitch();
                            String orderNumberSwitch = switchOrder.getOrderNumberSwitch();
                            DynamicDataSourceContextHolder.push(companyLabel);
                            cvenList = orderCostDS2Mapper.getCvenList(orderSwitch, orderNumberSwitch);
                        }

                        DynamicDataSourceContextHolder.push(companyLabel);

                    }

                }


                if (cvenList != null && cvenList.size() > 0) {
                    List<String> cvenCodes = new ArrayList<>();
                    List<String> cvenNames = new ArrayList<>();
                    for (Cven cven : cvenList) {
                        cvenCodes.add(cven.getCvencode());
                        cvenNames.add(cven.getCvenname());
                    }
                    String cvenCode = GeneralUtils.list2str(cvenCodes, "#");
                    String cvenName = GeneralUtils.list2str(cvenNames, "#");
                    orderCost.setCvenCode(cvenCode);
                    orderCost.setCvenName(cvenName);
                }

                //发货日期
                if (deliverDay == null && ccusCode != null) {
                    orderCost.setDeliverDay(ServiceUtils.getDeliverDay(orderCostDS2Mapper, companyLabel, orderDetailCode, orderNumber, cinvcode, invoiceId));
                }


                DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                updateById(orderCost);
            }


        });
    }


    public String getReturnDay(String invoiceid) {
        return orderCostDS2Mapper.getReturnDay(invoiceid);
    }


    private void updateRD(String companyLabel, String dmonth) {
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        List<String> nullRPDs = orderCostDS1Mapper.getNullRDs(companyLabel, dmonth);

        for (String invoiceid : nullRPDs) {
            DynamicDataSourceContextHolder.push(companyLabel);
            String returnDay = getReturnDay(invoiceid);
            if (returnDay != null) {
                DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                orderCostDS1Mapper.updateRDs(companyLabel, dmonth, invoiceid, returnDay);
            }
        }
    }


    private void madeBySelfOrders(String companyLabel, String dmonth) {
        //如果不是erp002,则其他子公司的制造订单放在erp002的，要标识出来
        if (!companyLabel.equals(Constants.ERP002)) {
            List<OrderCost> orderCostList = orderCostDS1Mapper.getOrdersByTime(dmonth, companyLabel);
            orderCostList.stream().forEach(orderCost -> {

                try {
                    Integer id = orderCost.getId();
                    String orderDetailCode = orderCost.getOrderDetailCode();
                    String orderNumber = orderCost.getOrderNumber();
                    String cinvcode = orderCost.getCinvcode();
                    BigDecimal mcost = orderCost.getMaterialCost();
                    BigDecimal income = GeneralUtils.getBigDecimalVal(orderCost.getIncome());
                    BigDecimal fquantity = GeneralUtils.getBigDecimalVal(orderCost.getFquantity());
                    //根据名称规律
                    String odc1 = orderDetailCode + "002";
                    String odc2 = orderDetailCode + "003";
                    //这些订单代表自家生产的
                    OrderCost fromOC = orderCostDS1Mapper.getformatOC(odc1, odc2, orderNumber, cinvcode, id, Constants.ORDER_TYPE_3);
                    if (fromOC != null && mcost.compareTo(BigDecimal.ZERO) == 0) {
                        orderCost.setOrderType(Constants.ORDER_TYPE_4);
                        //数量,sql已经作大于0判断
                        BigDecimal count = GeneralUtils.getBigDecimalVal(fromOC.getFquantity());
                        //材料
                        BigDecimal materialCost = GeneralUtils.getBigDecimalVal(fromOC.getMaterialCost());
                        BigDecimal materialCost_new = materialCost.divide(count, Constants.DIVIDE_LEVEL_12, BigDecimal.ROUND_HALF_UP).multiply(fquantity);
                        orderCost.setMaterialCost(materialCost_new);

                        //人力
                        BigDecimal laborCost = GeneralUtils.getBigDecimalVal(fromOC.getLaborCost());
                        BigDecimal laborCost_new = laborCost.divide(count, Constants.DIVIDE_LEVEL_12, BigDecimal.ROUND_HALF_UP).multiply(fquantity);
                        orderCost.setLaborCost(laborCost_new);

                        //制造
                        BigDecimal manufactureCost = GeneralUtils.getBigDecimalVal(fromOC.getManufactureCost());
                        BigDecimal manufactureCost_new = manufactureCost.divide(count, Constants.DIVIDE_LEVEL_12, BigDecimal.ROUND_HALF_UP).multiply(fquantity);
                        orderCost.setManufactureCost(manufactureCost_new);

                        //管理
                        BigDecimal mgrCost = GeneralUtils.getBigDecimalVal(fromOC.getMgrCost());
                        //运营
                        BigDecimal runCost = GeneralUtils.getBigDecimalVal(fromOC.getRunCost());

                        //成本小计
                        BigDecimal costSum_new = materialCost_new.add(manufactureCost_new).add(laborCost_new);
                        orderCost.setCostSum(costSum_new);

                        //毛利小计
                        BigDecimal profitSum_new = income.subtract(costSum_new);
                        orderCost.setProfitSum(profitSum_new);

                        //毛利率小计
                        if (income.compareTo(BigDecimal.ZERO) == 1) {
                            BigDecimal profitSumRadio_new = profitSum_new.divide(income, Constants.DIVIDE_LEVEL_12, BigDecimal.ROUND_HALF_UP);
                            orderCost.setProfitSumRadio(profitSumRadio_new);
                        }


                        //成本合计
                        BigDecimal costTotal_new = mgrCost.add(runCost).add(costSum_new);
                        orderCost.setCostTotal(costTotal_new);

                        //利润
                        BigDecimal profit_new = income.subtract(costTotal_new);
                        orderCost.setProfit(profit_new);

                        //利润率
                        if (income.compareTo(BigDecimal.ZERO) == 1) {
                            BigDecimal profitRadio_new = profit_new.divide(income, Constants.DIVIDE_LEVEL_12, BigDecimal.ROUND_HALF_UP);
                            orderCost.setProfitRadio(profitRadio_new);
                        }

                        updateById(orderCost);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
    }


    /**
     * 组装订单成本数据
     */
    public void buildOrderCostList(String companyLabel, String dmonth) {


        String[] startAndEndDay = GeneralUtils.getStartAndEndDay(dmonth);
        String startDay = startAndEndDay[0];
        String endDay = startAndEndDay[1];

        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        orderCostDS1Mapper.delOldData(dmonth, companyLabel);


        List<OrderCost> orderList = getOrders(companyLabel, startDay, endDay);
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        if (orderList.size() > 0) {

            saveBatch(orderList);

            orderList = orderCostDS1Mapper.getOrdersByTime(dmonth, companyLabel);

            //更新材料成本和收入金额
            DynamicDataSourceContextHolder.push(companyLabel);
            orderList.stream().forEach(orderCost -> {
                String orderDetailCode = orderCost.getOrderDetailCode();
                String orderNumber = orderCost.getOrderNumber();
                String invoiceId = orderCost.getInvoiceId();
                String irowno = orderCost.getIrowno();
                String autoid = orderCost.getAutoid();
                BigDecimal income = GeneralUtils.getBigDecimalVal(orderCost.getIncome());
                BigDecimal material_cost = GeneralUtils.getBigDecimalVal(orderCost.getMaterialCost());
                BigDecimal fquantity = GeneralUtils.getBigDecimalVal(orderCost.getFquantity());
                String cinvcode = orderCost.getCinvcode();
                orderCost.setDmonth(dmonth);


                updateMaterialCost(orderCost, material_cost, companyLabel, orderDetailCode, orderNumber, cinvcode, fquantity, dmonth);
                updateIncome(orderCost, companyLabel, income, orderDetailCode, orderNumber, cinvcode, irowno, invoiceId, autoid);
            });


            DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
            updateBatchById(orderList);

            //材料成本为0的可能是因为订单号的问题
            process0materialByNameLike(dmonth);

            //材料成本为0的可能是挪货问题
            processSwitchOrder(dmonth, companyLabel);

            //材料成本为0的可能是因为Revised的订单
            updateRevisedorders(companyLabel, dmonth);

            //有的是直接走采购，这里主要针对ERP002
            processPurchaseOrders(dmonth, companyLabel);

            //有的是在006却在004处理
            process6to4orders(dmonth, companyLabel);

            //标识委外订单
            flagWW(companyLabel, dmonth);

            DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
            BigDecimal all_amount = GeneralUtils.getBigDecimalVal(orderCostDS1Mapper.getAllAmount(dmonth));//收入总额
            BigDecimal all_mgrCost = GeneralUtils.getBigDecimalVal(orderCostDS1Mapper.getMgrCost(dmonth));//管理费总额
            BigDecimal all_runCost = GeneralUtils.getBigDecimalVal(orderCostDS1Mapper.getRunCost(dmonth));//运营费总额

            BigDecimal mgr_cost_scale = all_mgrCost.divide(all_amount, Constants.DIVIDE_LEVEL_12, BigDecimal.ROUND_HALF_UP);
            BigDecimal run_cost_scale = all_runCost.divide(all_amount, Constants.DIVIDE_LEVEL_12, BigDecimal.ROUND_HALF_UP);


            if (all_amount.compareTo(BigDecimal.ZERO) == 1) {//月度发票总额不为0,因除数不为能为0
                List<OrderCost> orderCostList = orderCostDS1Mapper.getOrdersByTime(dmonth, companyLabel);
                updateData(orderCostList, mgr_cost_scale, run_cost_scale, companyLabel, dmonth);
            }


            DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
            orderCostDS1Mapper.updateCompanyInfo();


            //处理合并订单
            if (companyLabel.equals(Constants.ERP010)) {
                updateFatherChildOrders(startDay, endDay, dmonth, companyLabel);
            }

            //是自己的订单标识出来
            flagOwnerOrder(companyLabel, dmonth);

            //自家生产的订单处理
            madeBySelfOrders(companyLabel, dmonth);

            //补充客户、业务员、供应商等信息
            updateProp(companyLabel, dmonth);

            //回款日期更新
            updateRD(companyLabel, dmonth);
        }
    }

    //处理
    private void updateData(List<OrderCost> ocList, BigDecimal mgr_cost_scale, BigDecimal run_cost_scale, String
            companyLabel, String dmonth) {
        for (OrderCost orderCost : ocList) {
            BigDecimal income = GeneralUtils.getBigDecimalVal(orderCost.getIncome());
            String orderDetailCode = orderCost.getOrderDetailCode();
            String orderNumber = orderCost.getOrderNumber();
            String cinvcode = orderCost.getCinvcode();
            BigDecimal fquantity = orderCost.getFquantity();
            BigDecimal materialCost = GeneralUtils.getBigDecimalVal(orderCost.getMaterialCost());

            orderCost.setMgrCost(mgr_cost_scale.multiply(income));
            orderCost.setRunCost(run_cost_scale.multiply(income));

            //成本小计
            BigDecimal costSum = materialCost.add(orderCost.getManufactureCost()).add(orderCost.getLaborCost());
            orderCost.setCostSum(costSum);
            //毛利小计
            BigDecimal profitSum = income.subtract(costSum);
            orderCost.setProfitSum(profitSum);
            //合计成本
            BigDecimal costTotal = orderCost.getMgrCost().add(orderCost.getRunCost()).add(orderCost.getCostSum());
            orderCost.setCostTotal(costTotal);
            //利润
            BigDecimal profit = income.subtract(costTotal);
            orderCost.setProfit(profit);

            if (income.compareTo(BigDecimal.ZERO) == 1) {
                //毛利率小计
                BigDecimal profitSumRadio = profitSum.divide(income, Constants.DIVIDE_LEVEL_12, BigDecimal.ROUND_HALF_UP);
                orderCost.setProfitSumRadio(profitSumRadio);
                //利润率
                BigDecimal profitRadio = profit.divide(income, Constants.DIVIDE_LEVEL_12, BigDecimal.ROUND_HALF_UP);
                orderCost.setProfitRadio(profitRadio);
            }

            DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
            orderCostDS1Mapper.updateById(orderCost);
        }
    }

    public List<Map<String, Object>> getCven() {
        List<Map<String, Object>> res = new ArrayList<>();
        List<String> keyslist = new ArrayList<>();
        List<Map<String, Object>> cvenMapList = orderCostDS1Mapper.getCven();
        for (Map<String, Object> cvenMap : cvenMapList) {

            String key = (String) cvenMap.get("cven_code");
            String val = (String) cvenMap.get("cven_name");
            String[] keylist = key.split("#");
            String[] vallist = val.split("#");
            int idx = 0;
            if (keylist.length == vallist.length) {
                for (String code : keylist) {
                    if (!keyslist.contains(code)) {
                        Map<String, Object> resMap = new HashMap<>();
                        resMap.put("cven_code", code);
                        resMap.put("cven_name", vallist[idx]);
                        res.add(resMap);
                        keyslist.add(code);
                    }
                    idx++;
                }
            }


        }

        return res;
    }
}
