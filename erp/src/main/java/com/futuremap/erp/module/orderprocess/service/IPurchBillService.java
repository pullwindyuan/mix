package com.futuremap.erp.module.orderprocess.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.common.service.IBaseService;
import com.futuremap.erp.module.orderprocess.dto.PurchBillDto;
import com.futuremap.erp.module.orderprocess.entity.PurchBill;
import com.futuremap.erp.module.orderprocess.entity.PurchBillQuery;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-07-14
 */
public interface IPurchBillService extends IBaseService<PurchBill> {
    IPageWithExtra<PurchBillDto> findList(PageWithExtra<PurchBillDto> page, PurchBillQuery purchBillQuery);

    IPageWithExtra<PurchBillDto> findListFromMapper(PageWithExtra<PurchBillDto> page, PurchBillQuery purchBillQuery);

    List<PurchBillDto> findList(PurchBillQuery purchBillQuery);
    List<PurchBillDto> sum(PurchBillQuery purchBillQuery);
    List<PurchBillDto> sumSaleBill(PurchBillQuery purchBillQuery);
}
