package com.futuremap.erp.module.orderprocess.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.PurchOrder;
import com.futuremap.erp.module.orderprocess.entity.PurchOrderQuery;
import com.futuremap.erp.module.orderprocess.mapper.PurchOrderMapper;
import com.futuremap.erp.module.orderprocess.service.IPurchOrderService;
import com.futuremap.erp.module.saleorder.mapper.SaleOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-05-28
 */
@Service
public class PurchOrderServiceImpl extends ServiceImpl<PurchOrderMapper, PurchOrder> implements IPurchOrderService {
    @Autowired
    SaleOrderMapper saleOrderMapper;

    public IPageWithExtra<PurchOrder> findList(PageWithExtra<PurchOrder> page, PurchOrderQuery purchOrderQuery) {
        IPageWithExtra<PurchOrder> pages = baseMapper.findList(page, purchOrderQuery);
//        List<PurchOrder> records = pages.getRecords();
//        Map<String, List<PurchOrder>> collect = records.stream().collect(Collectors.groupingBy(PurchOrder::getCsocode));
//        List<String> csocodes =  new ArrayList<>(collect.keySet());
//        List<SaleOrder> orderList = saleOrderMapper.findOrderList(csocodes);
//        List<PurchOrder> resultList = records.stream().filter(item ->
//                orderList.stream().filter(e -> item.getCsocode().equals(e.getCsocode())).collect(Collectors.toList()).size() > 0
//        ).collect(Collectors.toList());
//        pages.setRecords(resultList);
        return pages;
    }

    public List<PurchOrder> findList(PurchOrderQuery purchOrderQuery) {
        return  baseMapper.findList(purchOrderQuery);
    }

    public List<PurchOrder> sum(PurchOrderQuery purchOrderQuery) {
        return  baseMapper.sum(purchOrderQuery);
    }

    @Override
    public Integer insertBatch(Collection<PurchOrder> entityList) {
        return baseMapper.insertBatchSomeColumn(entityList);
    }
}
