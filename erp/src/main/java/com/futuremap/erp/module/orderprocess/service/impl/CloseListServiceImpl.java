package com.futuremap.erp.module.orderprocess.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.CloseOrder;
import com.futuremap.erp.module.orderprocess.entity.CloseOrderQuery;
import com.futuremap.erp.module.orderprocess.mapper.CloseListMapper;
import com.futuremap.erp.module.orderprocess.service.ICloseListService;
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
public class CloseListServiceImpl extends ServiceImpl<CloseListMapper, CloseOrder> implements ICloseListService {

    @Override
    public IPageWithExtra<CloseOrder> findList(PageWithExtra<CloseOrder> page, CloseOrderQuery closeOrderQuery) {
        return baseMapper.findList(page,closeOrderQuery);
    }
    @Override
    public IPageWithExtra<CloseOrder> findByCsocode(PageWithExtra<CloseOrder> page, CloseOrderQuery closeOrderQuery) {
        return baseMapper.findByCsocode(page,closeOrderQuery);
    }

    @Override
    public List<CloseOrder> findList(CloseOrderQuery closeOrderQuery) {
        return baseMapper.findList(closeOrderQuery);
    }

    @Override
    public List<CloseOrder> sum(CloseOrderQuery closeOrderQuery) {
        return baseMapper.sum(closeOrderQuery);
    }

    @Override
    public List<CloseOrder> sumByCsocode(CloseOrderQuery closeOrderQuery) {
        return baseMapper.sumByCsocode(closeOrderQuery);
    }

    @Override
    public Integer insertBatch(Collection<CloseOrder> entityList) {
        return baseMapper.insertBatchSomeColumn(entityList);
    }


}
