package com.futuremap.erp.module.orderprocess.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.dto.PurchBillDto;
import com.futuremap.erp.module.orderprocess.entity.PurchBill;
import com.futuremap.erp.module.orderprocess.entity.PurchBillQuery;
import com.futuremap.erp.module.orderprocess.mapper.PurchBillMapper;
import com.futuremap.erp.module.orderprocess.service.IPurchBillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-07-14
 */
@Service
public class PurchBillServiceImpl extends ServiceImpl<PurchBillMapper, PurchBill> implements IPurchBillService {


    @Override
    public IPageWithExtra<PurchBillDto> findList(PageWithExtra<PurchBillDto> page, PurchBillQuery purchBillQuery) {
        return baseMapper.findList(page,purchBillQuery);
    }

    @Override
    public IPageWithExtra<PurchBillDto> findListFromMapper(PageWithExtra<PurchBillDto> page, PurchBillQuery purchBillQuery) {
        return baseMapper.findListFromMapper(page,purchBillQuery);
    }

    @Override
    public List<PurchBillDto> findList(PurchBillQuery purchBillQuery) {
        return baseMapper.findList(purchBillQuery);
    }

    @Override
    public List<PurchBillDto> sum(PurchBillQuery purchBillQuery) {
        return baseMapper.sum(purchBillQuery);
    }

    @Override
    public List<PurchBillDto> sumSaleBill(PurchBillQuery purchBillQuery) {
        return baseMapper.sumSaleBill(purchBillQuery);
    }

    @Override
    public Integer insertBatch(Collection<PurchBill> entityList) {
        return baseMapper.insertBatchSomeColumn(entityList);
    }
}
