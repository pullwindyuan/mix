package com.futuremap.erp.module.saleorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.auth.DataScope;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.saleorder.dto.SaleOrderDto;
import com.futuremap.erp.module.saleorder.entity.SaleOrder;
import com.futuremap.erp.module.saleorder.entity.SaleOrderQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    *  Mapper 接口
    * </p>
*
* @author futuremap
* @since 2021-05-28
*/
@Mapper
public interface SaleOrderMapper extends BaseMapper<SaleOrder> {


     SaleOrder findOrderByCode(@Param("saleOrderQuery") SaleOrderQuery saleOrderQuery);


     //@DataScope(isDataScope = true,selfScopeNames = {"cpersoncode"},dataFilterType = 3)
     List<SaleOrder> findOrderList(@Param("csocodeList")List<String> csocodeList);


    IPageWithExtra<SaleOrderDto> findList(PageWithExtra<SaleOrderDto> page, SaleOrderQuery saleOrderQuery);

    IPageWithExtra<SaleOrderDto> findListFromView(PageWithExtra<SaleOrderDto> page, SaleOrderQuery saleOrderQuery);

    List<SaleOrder> findList(@Param("saleOrderQuery")SaleOrderQuery saleOrderQuery);

    List<SaleOrder> sum(@Param("saleOrderQuery")SaleOrderQuery saleOrderQuery);
}
