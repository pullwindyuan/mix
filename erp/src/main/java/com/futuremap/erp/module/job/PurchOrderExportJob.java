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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.futuremap.erp.module.comp.entity.CompanyInfo;
import com.futuremap.erp.module.comp.service.impl.CompanyInfoServiceImpl;
import com.futuremap.erp.module.constants.OrderProcessConstants;
import com.futuremap.erp.module.erp.mapper.CloseVouchMapper;
import com.futuremap.erp.module.erp.mapper.PurchMapper;
import com.futuremap.erp.module.orderCost.entity.SwitchOrder;
import com.futuremap.erp.module.orderCost.service.impl.SwitchOrderServiceImpl;
import com.futuremap.erp.module.orderprocess.entity.*;
import com.futuremap.erp.module.orderprocess.service.impl.*;
import com.futuremap.erp.module.saleorder.entity.SaleOrder;
import com.futuremap.erp.module.saleorder.service.impl.SaleOrderServiceImpl;
import com.futuremap.erp.utils.BigDecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @title PurchOrderExportJob
 * @description TODO
 * @date 2021/5/28 14:45
 */
@Component
@Slf4j
public class PurchOrderExportJob {


    @Resource
    PurchMapper purchMapper;
    @Resource
    PurchOrderServiceImpl purchOrderService;
    @Resource
    RecordoutListServiceImpl recordoutListService;
    @Resource
    RecordinListServiceImpl recordinListService;
    @Resource
    SaleoutListServiceImpl saleoutListService;
    @Resource
    SaleOrderServiceImpl saleOrderService;
    @Resource
    CloseListServiceImpl closeListService;
    @Resource
    CloseVouchMapper closeVouchMapper;
    @Resource
    CompanyInfoServiceImpl companyInfoService;

    @Resource
    SwitchOrderServiceImpl switchOrderService;

    @Resource
    SaleBillServiceImpl saleBillService;

    @Resource
    PurchBillServiceImpl purchBillService;



    //    @Scheduled(cron = "0 0 1 * * ? *")
    public void startPurchOrderExportByDay(){
        String endDate ="2020-10-01";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(endDate, fmt);
        puarchOrderExport(currentDate);
    }

    public void startHistoryPurchOrderExportByDay() {
//        String startDate ="2020-08-01";
//        String endDate = "2021-05-01";
        String startDate ="2021-01-01";
        String endDate = "2021-10-01";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(startDate, fmt);
        LocalDate lastDate = LocalDate.parse(endDate, fmt);
        while (currentDate.compareTo(lastDate)<=0){
            puarchOrderExport(currentDate);
            currentDate = currentDate.plusMonths(1);
        }

    }
    public void startHistorySaleoutExportByDay() {
//        String startDate ="2020-08-01";
//        String endDate = "2021-05-01";
        String startDate ="2021-01-01";
        String endDate = "2021-10-01";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(startDate, fmt);
        LocalDate lastDate = LocalDate.parse(endDate, fmt);
        while (currentDate.compareTo(lastDate)<=0){
            saleoutExport(currentDate);
            currentDate = currentDate.plusMonths(1);
        }

    }
    public void startHistoryRecordInExportByDay() {
//        String startDate ="2020-08-01";
//        String endDate = "2021-05-01";
        String startDate ="2021-01-01";
        String endDate = "2021-10-01";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(startDate, fmt);
        LocalDate lastDate = LocalDate.parse(endDate, fmt);
        while (currentDate.compareTo(lastDate)<=0){
            recordInExport(currentDate);
            currentDate = currentDate.plusMonths(1);
        }

    }

    public void startHistoryrecordOutExportByDay() {
//        String startDate ="2020-08-01";
//        String endDate = "2021-05-01";
        String startDate ="2021-01-01";
        String endDate = "2021-10-01";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(startDate, fmt);
        LocalDate lastDate = LocalDate.parse(endDate, fmt);
        while (currentDate.compareTo(lastDate)<=0){
            recordOutExport(currentDate);
            currentDate = currentDate.plusMonths(1);
        }

    }

    public void startHistoryrpurchQtyUpdateExportByDay() {
//        String startDate ="2020-08-01";
//        String endDate = "2021-05-01";
        String startDate ="2021-01-01";
        String endDate = "2021-10-01";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(startDate, fmt);
        LocalDate lastDate = LocalDate.parse(endDate, fmt);
        while (currentDate.compareTo(lastDate)<=0){
            purchQtyUpdate(currentDate);
            currentDate = currentDate.plusMonths(1);
        }

    }

    //供应商与销售客户映射
    public void startPurchToSaleOrderExport() {
        String endDate ="2021-02-01";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(endDate, fmt);
        purchToSaleOrder(currentDate);

    }

    public void startHistoryPurchToSaleOrderExport() {
        String startDate ="2021-01-01";
        String endDate = "2021-10-01";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(startDate, fmt);
        LocalDate lastDate = LocalDate.parse(endDate, fmt);
        while (currentDate.compareTo(lastDate)<=0){
            purchToSaleOrder(currentDate);
            currentDate = currentDate.plusMonths(1);
        }

    }

    //销售发票回款处理
    public void startInvoiceExport() {
        String endDate ="2021-02-01";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(endDate, fmt);
        invoiceExport( currentDate);

    }
    public void startHistoryInvoiceExport() {
        String startDate ="2021-01-01";
        String endDate = "2021-10-01";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(startDate, fmt);
        LocalDate lastDate = LocalDate.parse(endDate, fmt);
        while (currentDate.compareTo(lastDate)<=0){
            invoiceExport(currentDate);
            currentDate = currentDate.plusMonths(1);
        }

    }


    //付款发票处理
    public void startInvoicePaymentExport(){
        String endDate ="2021-02-01";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(endDate, fmt);
        invoicePaymentExport(currentDate);
    }
    public void startHistoryInvoicePaymentExport(){
        String startDate ="2021-01-01";
        String endDate = "2021-10-01";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(startDate, fmt);
        LocalDate lastDate = LocalDate.parse(endDate, fmt);
        while (currentDate.compareTo(lastDate)<=0){
            invoicePaymentExport(currentDate);
            currentDate = currentDate.plusMonths(1);
        }
    }


    /**
     * 采购订单导入
     * @param currentDate
     */
    private void puarchOrderExport(LocalDate currentDate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startLocalDate = currentDate.minusMonths(1);
        String startDate = startLocalDate.format(fmt);
        String endDate = currentDate.format(fmt);
        List<CompanyInfo> companyInfoList = companyInfoService.list();//new QueryWrapper<CompanyInfo>().notIn("id",1)
        companyInfoList.stream().forEach(comp->{
            String ds = comp.getDatasource();
            try {
                DynamicDataSourceContextHolder.push(ds);
                List<PurchOrder> purchOrderList = new ArrayList<>();
                //采购订单 pullwind已核对并修改
                List<PurchOrder> purchOrderInfos = purchMapper.getPurchOrderInfoByDay(startDate, endDate);
                //委外采购单 本质上就是委外订单，pullwind已核对
                List<PurchOrder> outsourcePurchOrderInfoByCsocode = purchMapper.getOutsourcePurchOrderInfoByDay(startDate, endDate);
                //材料出库 pullwind已核对并修改
                List<RecordoutList> workshopOutInfos = purchMapper.getWorkshopOutByDay(startDate, endDate);
                //成品入库 pullwind已核对并修改
                List<RecordinList> finishedProducts = purchMapper.getFinishedProductsByDay(startDate, endDate);
                //委外入库 应该查询的是委外入库而不是委外到货 pullwind已核对并修改,
                List<RecordinList> outSourceFinishedProductsByCsocode = purchMapper.getOutSourceFinishedProductsByDay(startDate, endDate);
                //存货明细 pullwind已核对并修改:不在需要取这个数据，这里取出的数据有误
//                List<RecordinList> finishedProducts = new ArrayList<>();
                //List<RecordinList> stockFinishedProducts = purchMapper.getStockFinishedProductsByDay(startDate, endDate);
                //委外成品入库
                finishedProducts.addAll(outSourceFinishedProductsByCsocode);
                //pullwind已核对并修改:不在需要取这个数据，这里取出的数据有误
                //finishedProducts.addAll(stockFinishedProducts);
                //注意：挪货记录是手工导入我们的系统，所以需要从我们的库中查询即可

                //采购入库
                List<JSONObject> purchOrderRecordeIn = purchMapper.getPurchOrderRecordeInByDay(startDate, endDate);


                //内销发货出库 pullwind已核对并修改
                List<SaleoutList> productDeliverys = purchMapper.getProductDeliveryByDay(startDate, endDate);
                //出口发货出库 pullwind已核对并修改
                List<SaleoutList> exProductDeliverys = purchMapper.getExProductDeliveryByDay(startDate, endDate);
                productDeliverys.addAll(exProductDeliverys);
//                List<CloseOrder> closeListBySaleOrder = closeVouchMapper.getCloseListByDay(startDate,endDate);

                //切换数据源，写入数据到本地数据库
                DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                //采购单
                if(purchOrderInfos!=null&purchOrderInfos.size()>0){
                    purchOrderInfos.stream().forEach(info->{
                            info.setCompanyCode(comp.getCompanyCode());
                            info.setCompanyName(comp.getCompanyName());
                            info.setOutSourceFlag(0);
                            purchOrderList.add(info);
                    });
                }
                //委外采购订单
                if(outsourcePurchOrderInfoByCsocode!=null&outsourcePurchOrderInfoByCsocode.size()>0){
                    outsourcePurchOrderInfoByCsocode.stream().forEach(info->{
                            info.setCompanyCode(comp.getCompanyCode());
                            info.setCompanyName(comp.getCompanyName());
                            info.setOutSourceFlag(1);
                            purchOrderList.add(info);
                    });
                }
                if(purchOrderList.size()>0){
                    purchOrderService.insertBatch(purchOrderList);
                    //purchOrderService.saveBatch(purchOrderList);
                }
                //采购入库更新
                purchOrderRecordeIn.stream().forEach(e->{
                    String csocode = (String) e.get("csocode");
                    Integer irowno = (Integer) e.get("isoseq");
                    String cordercode = (String) e.get("cordercode");
                    String cinvcode = (String) e.get("cinvcode");

                    UpdateWrapper updateWrapper = new UpdateWrapper();
                    updateWrapper.eq("csocode",csocode);
                    updateWrapper.eq("irowno",irowno);
                    updateWrapper.eq("cpoid",cordercode);
                    updateWrapper.eq("cinvcode",cinvcode);
                    List<PurchOrder> purchList = purchOrderService.list(new QueryWrapper<PurchOrder>().eq("csocode",csocode).eq("irowno",irowno)
                            .eq("cpoid",cordercode).eq("cinvcode",cinvcode));
                    final BigDecimal[] iquantity = {(BigDecimal) e.get("iquantity")};
                    if(purchList!=null&purchList.size()>0){
                        if(purchList.size()==1){
                            PurchOrder purchOrder = purchList.get(0);
                            updateWrapper.set("receivedqty", BigDecimalUtil.add(purchOrder.getReceivedqty(), iquantity[0]));
                            purchOrderService.update(null,updateWrapper);
                        }else {
                            purchList.stream().forEach(order->{
                                BigDecimal subtract = BigDecimalUtil.subtract(order.getIquantity(), order.getReceivedqty());
                                BigDecimal remainIquantity = BigDecimalUtil.subtract(iquantity[0], subtract);
                                if(subtract.compareTo(BigDecimal.ZERO)>0){
                                    if(remainIquantity.compareTo(BigDecimal.ZERO)<0){
                                        order.setReceivedqty(BigDecimalUtil.add(order.getReceivedqty(), iquantity[0]));
                                    }else {
                                        order.setReceivedqty(order.getIquantity());
                                    }
                                    purchOrderService.updateById(order);
                                }
                                iquantity[0] =remainIquantity;
                                if(iquantity[0].compareTo(BigDecimal.ZERO)<0){
                                    return;
                                }


                            });
                        }
                    }

                });

                //材料出库
                List<RecordoutList> recordoutLists = new ArrayList<>();
                if(workshopOutInfos!=null&workshopOutInfos.size()>0){
                    workshopOutInfos.stream().forEach(info->{
                            info.setCompanyCode(comp.getCompanyCode());
                            info.setCompanyName(comp.getCompanyName());
                            recordoutLists.add(info);
                    });
//                    recordoutListService.saveBatch(recordoutLists);
                    recordoutListService.insertBatch(recordoutLists);
                }

                //成品入库
                List<RecordinList> recordinLists = new ArrayList<>();
                if(finishedProducts!=null&finishedProducts.size()>0){
                    finishedProducts.stream().forEach(info->{
                            info.setCompanyName(comp.getCompanyName());
                            info.setCompanyCode(comp.getCompanyCode());
                            recordinLists.add(info);
                    });
                }
                //挪货记录
                List<SwitchOrder> order_switch = switchOrderService.list(new QueryWrapper<SwitchOrder>().between("create_time", startDate, endDate));
                List<RecordinList> transferRecordinList = order_switch.stream().map(item -> {
                    RecordinList ri = new RecordinList();
                    ri.setCompanyName(comp.getCompanyName());
                    ri.setCompanyCode(comp.getCompanyCode());
                    ri.setCsocode(item.getOrderSwitch());
                    ri.setIrowno(item.getOrderNumber());
                    ri.setCinvcode(item.getProductCode());
                    ri.setCinvname(item.getProductName());
                    ri.setIquantity(new BigDecimal(item.getOrderCount()));
                    return ri;
                }).collect(Collectors.toList());
                recordinLists.addAll(transferRecordinList);
                if (recordinLists.size()>0){
//                    recordinListService.saveBatch(recordinLists);
                    recordinListService.insertBatch(recordinLists);
                }

                //销售出库
                List<SaleoutList> saleoutLists = new ArrayList<>();
                if(productDeliverys!=null&productDeliverys.size()>0){
                    productDeliverys.stream().forEach(info->{
                            info.setCompanyCode(comp.getCompanyCode());
                            info.setCompanyName(comp.getCompanyName());
//                            SaleOrder saleOrder = saleOrderService.getOne(new QueryWrapper<SaleOrder>().eq("csocode", info.getCsocode()).eq("irowno", info.getIrowno()));
//                            if(saleOrder!=null){
//                                info.setInatsum(saleOrder.getInatsum());
//                            }
                            saleoutLists.add(info);
                    });
//                    saleoutListService.saveBatch(saleoutLists);
                    saleoutListService.insertBatch(saleoutLists);
                }


            } catch (Exception exception) {
                log.error("销售订单处理异常",exception);
            }

        });

        DynamicDataSourceContextHolder.clear();
    }


    //销售出库
    private  void saleoutExport(LocalDate currentDate){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startLocalDate = currentDate.minusMonths(1);
        String startDate = startLocalDate.format(fmt);
        String endDate = currentDate.format(fmt);
        List<CompanyInfo> companyInfoList = companyInfoService.list();//new QueryWrapper<CompanyInfo>().notIn("id",1)
        companyInfoList.stream().forEach(comp-> {
            String ds = comp.getDatasource();
            try {
                DynamicDataSourceContextHolder.push(ds);
                //内销发货出库
                List<SaleoutList> productDeliverys = purchMapper.getProductDeliveryByDay(startDate, endDate);
                //出口发货出库
                List<SaleoutList> exProductDeliverys = purchMapper.getExProductDeliveryByDay(startDate, endDate);
                productDeliverys.addAll(exProductDeliverys);
                //切换数据源
                DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                //销售出库
                List<SaleoutList> saleoutLists = new ArrayList<>();
                if (productDeliverys != null & productDeliverys.size() > 0){
                    productDeliverys.stream().forEach(info -> {
                        info.setCompanyCode(comp.getCompanyCode());
                        info.setCompanyName(comp.getCompanyName());
                        saleoutLists.add(info);
                    });
//                    saleoutListService.saveBatch(saleoutLists);
                    saleoutListService.insertBatch(saleoutLists);
                }


            } catch (Exception exception) {
                log.error("销售出库处理异常", exception);
            }
        });
    }
    //完成品入库
    private  void recordInExport(LocalDate currentDate){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startLocalDate = currentDate.minusMonths(1);
        String startDate = startLocalDate.format(fmt);
        String endDate = currentDate.format(fmt);
        List<CompanyInfo> companyInfoList = companyInfoService.list();//new QueryWrapper<CompanyInfo>().notIn("id",1)
        companyInfoList.stream().forEach(comp-> {
            String ds = comp.getDatasource();
            try {
                DynamicDataSourceContextHolder.push(ds);
                //成品入库
                List<RecordinList> finishedProducts = purchMapper.getFinishedProductsByDay(startDate, endDate);
                //委外到货
                List<RecordinList> outSourceFinishedProductsByCsocode = purchMapper.getOutSourceFinishedProductsByDay(startDate, endDate);
                //存货明细
                List<RecordinList> stockFinishedProducts = purchMapper.getStockFinishedProductsByDay(startDate, endDate);
                finishedProducts.addAll(outSourceFinishedProductsByCsocode);
                finishedProducts.addAll(stockFinishedProducts);
                //切换数据源
                DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);



                //成品入库
                List<RecordinList> recordinLists = new ArrayList<>();
                if(finishedProducts!=null&finishedProducts.size()>0){
                    finishedProducts.stream().forEach(info->{
                        info.setCompanyName(comp.getCompanyName());
                        info.setCompanyCode(comp.getCompanyCode());
                        recordinLists.add(info);
                    });
                }
                //挪货记录
                List<SwitchOrder> order_switch = switchOrderService.list(new QueryWrapper<SwitchOrder>().between("create_time", startDate, endDate));
                List<RecordinList> transferRecordinList = order_switch.stream().map(item -> {
                    RecordinList ri = new RecordinList();
                    ri.setCompanyName(comp.getCompanyName());
                    ri.setCompanyCode(comp.getCompanyCode());
                    ri.setCsocode(item.getOrderSwitch());
                    ri.setIrowno(item.getOrderNumber());
                    ri.setCinvcode(item.getProductCode());
                    ri.setCinvname(item.getProductName());
                    ri.setIquantity(item.getOrderCount()==null?BigDecimal.ZERO:new BigDecimal(item.getOrderCount()));
                    return ri;
                }).collect(Collectors.toList());
                recordinLists.addAll(transferRecordinList);
                if (recordinLists.size()>0){
//                    recordinListService.saveBatch(recordinLists);
                    recordinListService.insertBatch(recordinLists);
                }

            } catch (Exception exception) {
                log.error("完成品入库处理异常", exception);
            }
        });
    }


    //材料出库
    private  void recordOutExport(LocalDate currentDate){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startLocalDate = currentDate.minusMonths(1);
        String startDate = startLocalDate.format(fmt);
        String endDate = currentDate.format(fmt);
        List<CompanyInfo> companyInfoList = companyInfoService.list();//new QueryWrapper<CompanyInfo>().notIn("id",1)
        companyInfoList.stream().forEach(comp-> {
            String ds = comp.getDatasource();
            try {
                DynamicDataSourceContextHolder.push(ds);
                //材料出库
                List<RecordoutList> workshopOutInfos = purchMapper.getWorkshopOutByDay(startDate, endDate);
                //切换数据源
                DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                //材料出库
                List<RecordoutList> recordoutLists = new ArrayList<>();
                if(workshopOutInfos!=null&workshopOutInfos.size()>0){
                    workshopOutInfos.stream().forEach(info->{
                        info.setCompanyCode(comp.getCompanyCode());
                        info.setCompanyName(comp.getCompanyName());
                        recordoutLists.add(info);
                    });
//                    recordoutListService.saveBatch(recordoutLists);
                    recordoutListService.insertBatch(recordoutLists);
                }

            } catch (Exception exception) {
                log.error("材料出库处理异常", exception);
            }
        });
    }

    //采购
    private  void purchOrderExport(LocalDate currentDate){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startLocalDate = currentDate.minusMonths(1);
        String startDate = startLocalDate.format(fmt);
        String endDate = currentDate.format(fmt);
        List<CompanyInfo> companyInfoList = companyInfoService.list();//new QueryWrapper<CompanyInfo>().notIn("id",1)
        companyInfoList.stream().forEach(comp-> {
            String ds = comp.getDatasource();
            try {
                DynamicDataSourceContextHolder.push(ds);
                List<PurchOrder> purchOrderList = new ArrayList<>();
                //采购订单
                List<PurchOrder> purchOrderInfos = purchMapper.getPurchOrderInfoByDay(startDate, endDate);
                //委外采购单
                List<PurchOrder> outsourcePurchOrderInfoByCsocode = purchMapper.getOutsourcePurchOrderInfoByDay(startDate, endDate);
                //切换数据源
                DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                //采购单
                if(purchOrderInfos!=null&purchOrderInfos.size()>0){
                    purchOrderInfos.stream().forEach(info->{
                        info.setCompanyCode(comp.getCompanyCode());
                        info.setCompanyName(comp.getCompanyName());
                        info.setOutSourceFlag(0);
                        purchOrderList.add(info);
                    });
                }
                //委外采购订单
                if(outsourcePurchOrderInfoByCsocode!=null&outsourcePurchOrderInfoByCsocode.size()>0){
                    outsourcePurchOrderInfoByCsocode.stream().forEach(info->{
                        info.setCompanyCode(comp.getCompanyCode());
                        info.setCompanyName(comp.getCompanyName());
                        info.setOutSourceFlag(1);
                        purchOrderList.add(info);
                    });
                }
                if(purchOrderList.size()>0){
//                    purchOrderService.insertBatch(purchOrderList);
                    purchOrderService.saveBatch(purchOrderList);
                }
            } catch (Exception exception) {
                log.error("采购订单入库处理异常", exception);
            }
        });
    }



      //采购入库数量
    private  void purchQtyUpdate(LocalDate currentDate){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startLocalDate = currentDate.minusMonths(1);
        String startDate = startLocalDate.format(fmt);
        String endDate = currentDate.format(fmt);
        List<CompanyInfo> companyInfoList = companyInfoService.list();//new QueryWrapper<CompanyInfo>().notIn("id",1)
        companyInfoList.stream().forEach(comp-> {
            String ds = comp.getDatasource();
            try {
                DynamicDataSourceContextHolder.push(ds);
                //采购入库
                List<JSONObject> purchOrderRecordeIn = purchMapper.getPurchOrderRecordeInByDay(startDate, endDate);
                //切换数据源
                DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                //采购入库更新
                purchOrderRecordeIn.stream().forEach(e->{
                    String csocode = (String) e.get("csocode");
                    Integer irowno = (Integer) e.get("isoseq");
                    String cordercode = (String) e.get("cordercode");
                    String cinvcode = (String) e.get("cinvcode");

                    UpdateWrapper updateWrapper = new UpdateWrapper();
                    updateWrapper.eq("csocode",csocode);
                    updateWrapper.eq("irowno",irowno);
                    updateWrapper.eq("cpoid",cordercode);
                    updateWrapper.eq("cinvcode",cinvcode);
                    List<PurchOrder> purchList = purchOrderService.list(new QueryWrapper<PurchOrder>().eq("csocode",csocode).eq("irowno",irowno)
                            .eq("cpoid",cordercode).eq("cinvcode",cinvcode));
                    final BigDecimal[] iquantity = {(BigDecimal) e.get("iquantity")};
                    if(purchList!=null&purchList.size()>0){
                        if(purchList.size()==1){
                            PurchOrder purchOrder = purchList.get(0);
                            updateWrapper.set("receivedqty", BigDecimalUtil.add(purchOrder.getReceivedqty(), iquantity[0]));
                            purchOrderService.update(null,updateWrapper);
                        }else {
                            purchList.stream().forEach(order->{
                                BigDecimal subtract = BigDecimalUtil.subtract(order.getIquantity(), order.getReceivedqty());
                                BigDecimal remainIquantity = BigDecimalUtil.subtract(iquantity[0], subtract);
                                if(subtract.compareTo(BigDecimal.ZERO)>0){
                                    if(remainIquantity.compareTo(BigDecimal.ZERO)<0){
                                        order.setReceivedqty(BigDecimalUtil.add(order.getReceivedqty(), iquantity[0]));
                                    }else {
                                        order.setReceivedqty(order.getIquantity());
                                    }
                                    purchOrderService.updateById(order);
                                }
                                iquantity[0] =remainIquantity;
                                if(iquantity[0].compareTo(BigDecimal.ZERO)<0){
                                    return;
                                }


                            });
                        }
                    }

                });

            } catch (Exception exception) {
                log.error("采购订单入库数量处理异常", exception);
            }
        });
    }




    private void purchToSaleOrder(LocalDate currentDate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startLocalDate = currentDate.minusMonths(1);
        String startDate = startLocalDate.format(fmt);
        String endDate = currentDate.format(fmt);
        List<CompanyInfo> companyInfoList = companyInfoService.list();//new QueryWrapper<CompanyInfo>().notIn("id",1)

        List<CloseOrder> closeOrderByDayAll= new ArrayList<>();
        companyInfoList.stream().forEach(comp-> {
            try {
                String ds = comp.getDatasource();
                DynamicDataSourceContextHolder.push(ds);
                List<CloseOrder> exCloseListByDay = closeVouchMapper.getExCloseListByDay(startDate, endDate);
                List<CloseOrder> innerCloseListByDay = closeVouchMapper.getInnerCloseListByDay(startDate, endDate);
                List<CloseOrder> outSourceExCloseListByDay = closeVouchMapper.getOutSourceExCloseListByDay(startDate, endDate);
                List<CloseOrder> outSourceInnerExCloseListByDay = closeVouchMapper.getOutSourceInnerExCloseListByDay(startDate, endDate);

                closeOrderByDayAll.addAll(exCloseListByDay);
                closeOrderByDayAll.addAll(innerCloseListByDay);
                closeOrderByDayAll.addAll(outSourceExCloseListByDay);
                closeOrderByDayAll.addAll(outSourceInnerExCloseListByDay);
                closeOrderByDayAll.stream().forEach(info->{
                        info.setCompanyCode(comp.getCompanyCode());
                        info.setCompanyName(comp.getCompanyName());
                        info.setPayableMoney(info.getInatmoney());
                        info.setPaidMoney(BigDecimal.ZERO);
                        info.setNoPay(info.getInatmoney());
                });

                //切换数据源
                DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);

                if(closeOrderByDayAll.size()>0){
    //                closeListService.saveBatch(closeOrderByDayAll);
                    closeListService.insertBatch(closeOrderByDayAll);
                    closeOrderByDayAll.clear();
                }
            } catch (Exception e) {
                log.error("供应商映射客户订单处理异常",e);
            }
        });

        DynamicDataSourceContextHolder.clear();
    }


    //销售回款发票处理
    private void invoiceExport(LocalDate currentDate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startLocalDate = currentDate.minusMonths(1);
        String startDate = startLocalDate.format(fmt);
        String endDate = currentDate.format(fmt);
        List<CompanyInfo> companyInfoList = companyInfoService.list();//new QueryWrapper<CompanyInfo>().notIn("id",1)

        List<JSONObject> invoiceListByDayAll= new ArrayList<>();
        List<SaleBill> invoiceList = new ArrayList<>();
        companyInfoList.stream().forEach(comp->{
            String ds = comp.getDatasource();
            DynamicDataSourceContextHolder.push(ds);
            //外销回款 发票 pullwind已核对并修改
            List<SaleBill> exInvoiceList = closeVouchMapper.getExInvoiceListByDay(startDate, endDate);
            exInvoiceList.stream().forEach(e->{
                e.setCompanyCode(comp.getCompanyCode());
                e.setCompanyName(comp.getCompanyName());
                invoiceList.add(e);
            });

            //内销回款 发票 pullwind已核对并修改
            List<SaleBill> innerInvoiceList = closeVouchMapper.getInnerInvoiceListByDay(startDate, endDate);
            innerInvoiceList.stream().forEach(e->{
                e.setCompanyCode(comp.getCompanyCode());
                e.setCompanyName(comp.getCompanyName());
                invoiceList.add(e);
            });

            //根据应收款找回款发票
            List<JSONObject> ardetailListByDay = closeVouchMapper.getArdetailListByDay(startDate, endDate);
            //销售发票回款处理
            ardetailListByDay.stream().forEach(e->{
                //获取发票号
                String cVouchID = (String) e.get("cVouchID2");
                //收款时间
                String dVouchDate = String.valueOf(e.get("dRegDate2"));
                //收款单号
                String receiptCode  = (String) e.get("cVouchID");

                //出口发票列表
                List<JSONObject> invoiceListByDay = closeVouchMapper.getExInvoiceListByCcode(cVouchID,dVouchDate,receiptCode);
                if(invoiceListByDay==null||invoiceListByDay.size()==0){
                    //内销发票列表
                     invoiceListByDay = closeVouchMapper.getInnerInvoiceListByCsbvcode(cVouchID,dVouchDate,receiptCode);
                }
                invoiceListByDayAll.addAll(invoiceListByDay);

            });
        });

        //切换数据源
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        //保存销售发票信息
//        saleBillService.saveBatch(invoiceList);
        saleBillService.insertBatch(invoiceList);

        invoiceListByDayAll.stream().forEach(item->{
            //更新已回款发票信息
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("csocode",item.get("csocode"));
            updateWrapper.eq("irowno",item.get("irowno"));
            updateWrapper.eq("cinvcode",item.get("cinvcode"));
            updateWrapper.eq("bill_code",item.get("billCode"));

//            SaleBill one = saleBillService.getOne(new QueryWrapper<SaleBill>().eq("csocode",item.get("csocode")).eq("irowno",item.get("irowno")).last("limit 1"));
//            if(one!=null){
//                BigDecimal isum = one.getConlectionMoney();
//                BigDecimal inatsum = one.getConlectionNatMoney();
            BigDecimal inatsum = (BigDecimal) item.get( "inatsum");
            if(inatsum!=null&& inatsum.compareTo(BigDecimal.ZERO)>0){
                updateWrapper.set("dvouch_date",item.get( "ddate"));
                updateWrapper.set("receipt_code",item.get( "receiptCode"));
                updateWrapper.set("conlection_money", (BigDecimal) item.get( "isum"));
                updateWrapper.set("conlection_nat_money",inatsum);
                saleBillService.update(null,updateWrapper);
            }
//            }

        });
        DynamicDataSourceContextHolder.clear();
    }


    //采购付款发票处理
    private void invoicePaymentExport(LocalDate currentDate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startLocalDate = currentDate.minusMonths(1);
        String startDate = startLocalDate.format(fmt);
        String endDate = currentDate.format(fmt);
        List<CompanyInfo> companyInfoList = companyInfoService.list();//new QueryWrapper<CompanyInfo>().notIn("id",1)

        List<JSONObject> invoiceListByDayAll= new ArrayList<>();
        List<PurchBill> invoicePaymentListAll= new ArrayList<>();
        companyInfoList.stream().forEach(comp->{
            String ds = comp.getDatasource();
            String companyCode = comp.getCompanyCode();
            String companyName = comp.getCompanyName();

            DynamicDataSourceContextHolder.push(ds);
            List<PurchBill> invoicePaymentList = closeVouchMapper.getInvoicePaymentListByDay(startDate, endDate);
            List<PurchBill> outSourceInvoicePaymentList = closeVouchMapper.getOutSourceInvoicePaymentListByDay(startDate, endDate);
            invoicePaymentList.stream().forEach(e->{
                e.setCompanyCode(companyCode);
                e.setCompanyName(companyName);
                invoicePaymentListAll.add(e);
            });
            outSourceInvoicePaymentList.stream().forEach(e->{
                e.setCompanyCode(companyCode);
                e.setCompanyName(companyName);
                invoicePaymentListAll.add(e);
            });
            //根据应付款找付款款发票
            List<JSONObject> apDetailListByDay = closeVouchMapper.getApDetailListByDay(startDate, endDate);
            apDetailListByDay.stream().forEach(e->{
                //获取发票号
                String cpbvcode = (String) e.get("cVouchID2");
                //收款时间
                String dVouchDate = String.valueOf(e.get("dRegDate2"));
                //收款单号
                String receiptCode  = (String) e.get("cVouchID");
                //采购发票回款处理  采购及委外
                List<JSONObject> invoiceListByDay = closeVouchMapper.getInvoicePaymentListByCpbvcode(cpbvcode,dVouchDate,receiptCode,companyCode);
                invoiceListByDayAll.addAll(invoiceListByDay);
            });
        });

        //切换数据源
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        //保存采购发票
        purchBillService.insertBatch(invoicePaymentListAll);
//        purchBillService.saveBatch(invoicePaymentListAll);
        invoiceListByDayAll.stream().forEach(item->{
            //更新发票已付款信息
            UpdateWrapper updateWrapper = new UpdateWrapper();
//            updateWrapper.eq("csocode",item.get("csocode"));
//            updateWrapper.eq("cordercode",item.get("cordercode"));
//            updateWrapper.eq("cinvcode",item.get("cinvcode"));
            updateWrapper.eq("autoid",item.get("autoid"));
            updateWrapper.eq("company_code",item.get("companyCode"));
//            updateWrapper.eq("bill_code",item.get("billCode"));


//            PurchBill one = purchBillService.getOne(updateWrapper);
//            if(one!=null){
//                BigDecimal inatsum = (BigDecimal) item.get("inatsum");
//                updateWrapper.set("paid_money",inatsum);
//                updateWrapper.set("payable_money", BigDecimalUtil.subtract(one.getInatmoney(),inatsum));
//                updateWrapper.set("no_pay", BigDecimalUtil.subtract(one.getInatmoney(),inatsum));
                updateWrapper.set("payment_dvouch_date",item.get( "ddate"));
                updateWrapper.set("payment_receipt_code",item.get( "receiptCode"));
                updateWrapper.set("payment_money", (BigDecimal) item.get( "isum"));
                updateWrapper.set("payment_nat_money",(BigDecimal) item.get( "inatsum"));
                purchBillService.update(null,updateWrapper);
//            }



        });


        DynamicDataSourceContextHolder.clear();
    }


    //每天处理订单未完结的订单
    public void startPurchOrderExport(){
        List<SaleOrder> saleOrderList = saleOrderService.list(new QueryWrapper<SaleOrder>().ne("status",OrderProcessConstants.PAYCOLLECT).groupBy("csocode"));
        List<CompanyInfo> companyInfoList = companyInfoService.list();//new QueryWrapper<CompanyInfo>().notIn("id",1)
        Map<String, CompanyInfo> companyMap = companyInfoList.stream().collect(Collectors.toMap(CompanyInfo::getCompanyCode, CompanyInfo -> CompanyInfo));
        saleOrderList.stream().forEach(e->{
            String csocode = e.getCsocode();
            CompanyInfo comp = companyMap.get(e.getCompanyCode());
            String ds = comp.getDatasource();
            try {
                List<PurchOrder> purchOrderList = new ArrayList<>();
                UpdateWrapper updateWrapper = new UpdateWrapper();
                updateWrapper.eq("csocode", csocode);
                DynamicDataSourceContextHolder.push(ds);
                //采购订单
                List<PurchOrder> purchOrderInfos = purchMapper.getPurchOrderInfoByCsocode(csocode);
                //委外采购单
                List<PurchOrder> outsourcePurchOrderInfoByCsocode = purchMapper.getOutsourcePurchOrderInfoByCsocode(csocode);
                //材料出库
                List<RecordoutList> workshopOutInfos = purchMapper.getWorkshopOutByCsocode(csocode);
                //成品入库
                List<RecordinList> finishedProducts = purchMapper.getFinishedProductsByCsocode(csocode);
                //考虑委外情况
                List<RecordinList> outSourceFinishedProductsByCsocode = purchMapper.getOutSourceFinishedProductsByCsocode(csocode);
                finishedProducts.addAll(outSourceFinishedProductsByCsocode);

                List<SaleoutList> productDeliverys = purchMapper.getProductDeliveryByCsocode(csocode);
                if(productDeliverys==null||productDeliverys.size()==0){
                    productDeliverys = purchMapper.getExProductDeliveryByCsocode(csocode);
                }
//                List<CloseOrder> closeListBySaleOrder = closeVouchMapper.getCloseListBySaleOrder(csocode);

                //切换数据源
                DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
                //采购单
                if(purchOrderInfos!=null&purchOrderInfos.size()>0){
                    purchOrderInfos.stream().forEach(info->{
                        PurchOrder purchOrder = purchOrderService.getOne(new QueryWrapper<PurchOrder>().eq("autoid", info.getAutoid()));
                        if(purchOrder==null){
                            info.setCompanyCode(e.getCompanyCode());
                            info.setCompanyName(e.getCompanyName());
                            info.setOutSourceFlag(0);
                            purchOrderList.add(info);
                        }
                    });
                }
                //委外采购订单
                if(outsourcePurchOrderInfoByCsocode!=null&outsourcePurchOrderInfoByCsocode.size()>0){
                    outsourcePurchOrderInfoByCsocode.stream().forEach(info->{
                        PurchOrder purchOrder = purchOrderService.getOne(new QueryWrapper<PurchOrder>().eq("autoid", info.getAutoid()));
                        if(purchOrder==null){
                            info.setCompanyCode(e.getCompanyCode());
                            info.setCompanyName(e.getCompanyName());
                            info.setOutSourceFlag(1);
                            purchOrderList.add(info);
                        }
                    });
                }
                if(purchOrderList.size()>0){
                    purchOrderService.saveBatch(purchOrderList);
                    updateWrapper.set("status",OrderProcessConstants.PURCH);
                    saleOrderService.update(null, updateWrapper);
                }

                List<RecordoutList> recordoutLists = new ArrayList<>();

                if(workshopOutInfos!=null&workshopOutInfos.size()>0){
                    workshopOutInfos.stream().forEach(info->{
                        RecordoutList one = recordoutListService.getOne(new QueryWrapper<RecordoutList>().eq("autoid", info.getAutoid()));
                        if(one==null){
                            info.setCompanyCode(e.getCompanyCode());
                            info.setCompanyName(e.getCompanyName());
                            recordoutLists.add(info);
                        }

                    });
                    recordoutListService.saveBatch(recordoutLists);
//                    if(e.getStatus()== OrderProcessConstants.PURCH){
                    updateWrapper.set("status",OrderProcessConstants.MATERIAL);
                    saleOrderService.update(null, updateWrapper);
//                    }
                }
                List<RecordinList> recordinLists = new ArrayList<>();
                if(finishedProducts!=null&finishedProducts.size()>0){
                    finishedProducts.stream().forEach(info->{
                        RecordinList one = recordinListService.getOne(new QueryWrapper<RecordinList>().eq("autoid", info.getAutoid()));
                        if(one==null){
                            info.setCompanyName(e.getCompanyName());
                            info.setCompanyCode(e.getCompanyCode());
                            recordinLists.add(info);
                        }

                    });
                }
                //挪货记录
                List<SwitchOrder> order_switch = switchOrderService.list(new QueryWrapper<SwitchOrder>().eq("order_switch", csocode));
                List<RecordinList> transferRecordinList = order_switch.stream().map(item -> {
                    RecordinList ri = new RecordinList();
                    ri.setCompanyName(e.getCompanyName());
                    ri.setCompanyCode(e.getCompanyCode());
                    ri.setCsocode(item.getOrderSwitch());
                    ri.setIrowno(item.getOrderNumber());
                    ri.setCinvcode(item.getProductCode());
                    ri.setCinvname(item.getProductName());
                    ri.setIquantity(new BigDecimal(item.getOrderCount()));
//                        ri.setDdate(item.ge);

                    return ri;
                }).collect(Collectors.toList());
                recordinLists.addAll(recordinLists);
                recordinListService.saveBatch(recordinLists);
                updateWrapper.set("status",OrderProcessConstants.FINISH);
                saleOrderService.update(null, updateWrapper);


                List<SaleoutList> saleoutLists = new ArrayList<>();
                if(productDeliverys!=null&productDeliverys.size()>0){
                    productDeliverys.stream().forEach(info->{
                        SaleoutList one = saleoutListService.getOne(new QueryWrapper<SaleoutList>().eq("autoid", info.getAutoid()));
                        if(one==null){

                            info.setCompanyCode(e.getCompanyCode());
                            info.setCompanyName(e.getCompanyName());
                            SaleOrder saleOrder = saleOrderService.getOne(new QueryWrapper<SaleOrder>().eq("csocode", info.getCsocode()).eq("irowno", info.getIrowno()));
                            //                        info.setIpprice(BigDecimalUtil.multiply(info.getIquantity(),one.getItaxunitprice()));
                            info.setInatsum(saleOrder.getInatsum());
                            saleoutLists.add(info);
                        }
                    });
                    saleoutListService.saveBatch(saleoutLists);
//                    if(e.getStatus()==OrderProcessConstants.FINISH){
                    updateWrapper.set("status", OrderProcessConstants.SALEOOUT);
                    saleOrderService.update(null, updateWrapper);
//                    }
                }

//                if(closeListBySaleOrder!=null&closeListBySaleOrder.size()>0){
//                    closeListBySaleOrder.stream().forEach(info->{
//                        info.setCompanyCode(e.getCompanyCode());
//                        info.setCompanyName(e.getCompanyName());
//                    });
//                    closeListService.saveBatch(closeListBySaleOrder);
//                    updateWrapper.set("status",OrderProcessConstants.PAYCOLLECT);
//                    saleOrderService.update(null, updateWrapper);
//                }

            } catch (Exception exception) {
                log.error("销售订单"+csocode+"处理异常",exception);
            }

        });

        DynamicDataSourceContextHolder.clear();
    }


    public  void createPurchBillToSaleBillMapper() {
        //切换默认master
        DynamicDataSourceContextHolder.push(OrderProcessConstants.MASTER);
        purchMapper.createPurchBillToSaleBillMapper();
        DynamicDataSourceContextHolder.clear();
    }
}
