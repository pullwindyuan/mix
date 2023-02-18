package com.futuremap.erp.module.orderprocess.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.RecordinList;
import com.futuremap.erp.module.orderprocess.entity.RecordinQuery;
import com.futuremap.erp.module.orderprocess.mapper.RecordinListMapper;
import com.futuremap.erp.module.orderprocess.service.IRecordinListService;
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
public class RecordinListServiceImpl extends ServiceImpl<RecordinListMapper, RecordinList> implements IRecordinListService {

    public IPageWithExtra<RecordinList> findList(PageWithExtra<RecordinList> page, RecordinQuery recordinQuery) {
        IPageWithExtra<RecordinList> list = baseMapper.findList(page, recordinQuery);

        return list;
    }

    public List<RecordinList> findList(RecordinQuery recordinQuery) {
        return baseMapper.findList(recordinQuery);
    }

    public List<RecordinList> sum(RecordinQuery recordinQuery) {
        return baseMapper.sum(recordinQuery);
    }

    @Override
    public Integer insertBatch(Collection<RecordinList> entityList) {
        return baseMapper.insertBatchSomeColumn(entityList);
    }
}
