package com.futuremap.erp.module.orderprocess.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.model.IBaseMapper;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.dto.PurchBillDto;
import com.futuremap.erp.module.orderprocess.entity.PurchBill;
import com.futuremap.erp.module.orderprocess.entity.PurchBillQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    *  Mapper 接口
    * </p>
*
* @author futuremap
* @since 2021-07-13
*/
@Mapper
public interface PurchBillMapper extends IBaseMapper<PurchBill> {

    IPageWithExtra<PurchBillDto> findList(PageWithExtra<PurchBillDto> page, PurchBillQuery purchBillQuery);

    IPageWithExtra<PurchBillDto> findListFromMapper(PageWithExtra<PurchBillDto> page, PurchBillQuery purchBillQuery);

    List<PurchBillDto> findList(@Param("purchBillQuery")PurchBillQuery purchBillQuery);

    List<PurchBillDto> sum(@Param("purchBillQuery") PurchBillQuery purchBillQuery);

    List<PurchBillDto> sumSaleBill(@Param("purchBillQuery") PurchBillQuery purchBillQuery);
}
