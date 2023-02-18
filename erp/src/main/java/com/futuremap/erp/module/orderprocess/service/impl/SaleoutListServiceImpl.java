package com.futuremap.erp.module.orderprocess.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.SaleoutList;
import com.futuremap.erp.module.orderprocess.entity.SaleoutQuery;
import com.futuremap.erp.module.orderprocess.mapper.SaleoutListMapper;
import com.futuremap.erp.module.orderprocess.service.ISaleoutListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SaleoutListServiceImpl extends ServiceImpl<SaleoutListMapper, SaleoutList> implements ISaleoutListService {

    public IPageWithExtra<SaleoutList> findList(PageWithExtra<SaleoutList> page, SaleoutQuery saleoutQuery) {
        return baseMapper.findList(page,saleoutQuery);
    }

    public List<SaleoutList> findList(SaleoutQuery saleoutQuery) {
        return  baseMapper.findList(saleoutQuery);
    }

    public List<SaleoutList> sum(SaleoutQuery saleoutQuery) {
        return  baseMapper.sum(saleoutQuery);
    }

    @Override
    public Integer insertBatch(Collection<SaleoutList> entityList) {
        return baseMapper.insertBatchSomeColumn(entityList);
    }
}
