package com.futuremap.erp.module.quotation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.futuremap.erp.module.quotation.entity.QuotationTotal;
import com.futuremap.erp.module.quotation.entity.QuotationTotalQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-10
 */
public interface IQuotationTotalService extends IService<QuotationTotal> {

    IPage<QuotationTotal> findList(Page<QuotationTotal> page, QuotationTotalQuery quotationTotalQuery);
}
