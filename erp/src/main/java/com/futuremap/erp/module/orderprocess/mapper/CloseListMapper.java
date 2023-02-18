package com.futuremap.erp.module.orderprocess.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.model.IBaseMapper;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.CloseOrder;
import com.futuremap.erp.module.orderprocess.entity.CloseOrderQuery;
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
public interface CloseListMapper extends IBaseMapper<CloseOrder> {

    IPageWithExtra<CloseOrder> findList(PageWithExtra<CloseOrder> page, CloseOrderQuery closeOrderQuery);

    List<CloseOrder> findList(@Param("closeOrderQuery") CloseOrderQuery closeOrderQuery);

    List<CloseOrder> sum(@Param("closeOrderQuery") CloseOrderQuery closeOrderQuery);

    List<CloseOrder> sumByCsocode(@Param("closeOrderQuery") CloseOrderQuery closeOrderQuery);

    IPageWithExtra<CloseOrder> findByCsocode(PageWithExtra<CloseOrder> page, CloseOrderQuery closeOrderQuery);
}
