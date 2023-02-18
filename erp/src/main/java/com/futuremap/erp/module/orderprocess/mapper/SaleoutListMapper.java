package com.futuremap.erp.module.orderprocess.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.model.IBaseMapper;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.SaleoutList;
import com.futuremap.erp.module.orderprocess.entity.SaleoutQuery;
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
public interface SaleoutListMapper extends IBaseMapper<SaleoutList> {

    IPageWithExtra<SaleoutList> findList(PageWithExtra<SaleoutList> page, SaleoutQuery saleoutQuery);

    List<SaleoutList> findList(@Param("saleoutQuery")SaleoutQuery saleoutQuery);

    List<SaleoutList> sum(@Param("saleoutQuery")SaleoutQuery saleoutQuery);
}
