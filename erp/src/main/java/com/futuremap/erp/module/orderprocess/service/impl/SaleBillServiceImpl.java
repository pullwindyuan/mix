package com.futuremap.erp.module.orderprocess.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.SaleBill;
import com.futuremap.erp.module.orderprocess.entity.SaleBillQuery;
import com.futuremap.erp.module.orderprocess.mapper.SaleBillMapper;
import com.futuremap.erp.module.orderprocess.service.ISaleBillService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-07-13
 */
@Service
public class SaleBillServiceImpl extends ServiceImpl<SaleBillMapper, SaleBill> implements ISaleBillService {


    @Override
    public IPageWithExtra<SaleBill> findList(PageWithExtra<SaleBill> page, SaleBillQuery saleBillQuery) {
        return baseMapper.findList(page,saleBillQuery);
    }

    @Override
    public List<SaleBill> sum(SaleBillQuery saleBillQuery) {
        return baseMapper.sum(saleBillQuery);
    }

    @Override
    public Integer insertBatch(Collection<SaleBill> entityList) {
        return baseMapper.insertBatchSomeColumn(entityList);
    }
}
