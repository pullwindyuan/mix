package com.futuremap.erp.module.orderprocess.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.common.service.IBaseService;
import com.futuremap.erp.module.orderprocess.entity.SaleBill;
import com.futuremap.erp.module.orderprocess.entity.SaleBillQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-07-13
 */
public interface ISaleBillService extends IBaseService<SaleBill> {

    IPageWithExtra<SaleBill> findList(PageWithExtra<SaleBill> page, SaleBillQuery saleBillQuery);
    List<SaleBill> sum(SaleBillQuery saleBillQuery);
}
