package com.futuremap.erp.module.orderprocess.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.RecordoutList;
import com.futuremap.erp.module.orderprocess.entity.RecordoutQuery;
import com.futuremap.erp.module.orderprocess.mapper.RecordoutListMapper;
import com.futuremap.erp.module.orderprocess.service.IRecordoutListService;
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
public class RecordoutListServiceImpl extends ServiceImpl<RecordoutListMapper, RecordoutList> implements IRecordoutListService {

    public IPageWithExtra<RecordoutList> findList(PageWithExtra<RecordoutList> page, RecordoutQuery recordoutQuery) {
        return baseMapper.findList(page,recordoutQuery);
    }
    public List<RecordoutList> findList( RecordoutQuery recordoutQuery) {
        return baseMapper.findList(recordoutQuery);
    }
    public List<RecordoutList> sum( RecordoutQuery recordoutQuery) {
        return baseMapper.sum(recordoutQuery);
    }
    @Override
    public Integer insertBatch(Collection<RecordoutList> entityList) {
        return baseMapper.insertBatchSomeColumn(entityList);
    }
}
