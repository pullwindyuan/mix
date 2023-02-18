package com.futuremap.erp.module.orderprocess.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.common.service.IBaseService;
import com.futuremap.erp.module.orderprocess.entity.CloseOrder;
import com.futuremap.erp.module.orderprocess.entity.CloseOrderQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-05-28
 */
public interface ICloseListService extends IBaseService<CloseOrder> {

    IPageWithExtra<CloseOrder> findList(PageWithExtra<CloseOrder> page, CloseOrderQuery closeOrderQuery);

    IPageWithExtra<CloseOrder> findByCsocode(PageWithExtra<CloseOrder> page, CloseOrderQuery closeOrderQuery);

    List<CloseOrder> findList(CloseOrderQuery closeOrderQuery);

    List<CloseOrder> sum(CloseOrderQuery closeOrderQuery);
    List<CloseOrder> sumByCsocode(CloseOrderQuery closeOrderQuery);
}
