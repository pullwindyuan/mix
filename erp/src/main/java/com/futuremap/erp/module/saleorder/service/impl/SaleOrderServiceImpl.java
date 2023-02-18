package com.futuremap.erp.module.saleorder.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.auth.dto.RoleColumnDto;
import com.futuremap.erp.module.auth.service.impl.RoleColumnServiceImpl;
import com.futuremap.erp.module.saleorder.dto.SaleOrderDto;
import com.futuremap.erp.module.saleorder.entity.SaleOrder;
import com.futuremap.erp.module.saleorder.entity.SaleOrderQuery;
import com.futuremap.erp.module.saleorder.mapper.SaleOrderMapper;
import com.futuremap.erp.module.saleorder.service.ISaleOrderService;
import com.futuremap.erp.utils.BeanUtil;
import com.futuremap.erp.utils.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-05-28
 */
@Service
public class SaleOrderServiceImpl extends ServiceImpl<SaleOrderMapper, SaleOrder> implements ISaleOrderService {
    public IPageWithExtra<SaleOrderDto> findList(PageWithExtra<SaleOrderDto> page, SaleOrderQuery saleOrderQuery) {
        IPageWithExtra<SaleOrderDto> pages = baseMapper.findList(page, saleOrderQuery);
        return pages;
    }

    public IPageWithExtra<SaleOrderDto> findListFromView(PageWithExtra<SaleOrderDto> page, SaleOrderQuery saleOrderQuery) {
        IPageWithExtra<SaleOrderDto> pages = baseMapper.findListFromView(page, saleOrderQuery);
        return pages;
    }

    public List<SaleOrder> findList(SaleOrderQuery saleOrderQuery) {
        return baseMapper.findList(saleOrderQuery);
    }

    public List<SaleOrder> sum(SaleOrderQuery saleOrderQuery) {
        return baseMapper.sum(saleOrderQuery);
    }
}
