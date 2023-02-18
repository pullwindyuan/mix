package com.futuremap.erp.module.orderprocess.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.futuremap.erp.common.auth.DataScope;
import com.futuremap.erp.common.model.IBaseMapper;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.PurchOrder;
import com.futuremap.erp.module.orderprocess.entity.PurchOrderQuery;
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
public interface PurchOrderMapper extends IBaseMapper<PurchOrder> {
    //@DataScope(isColumnScope = true)
    IPageWithExtra<PurchOrder> findList(PageWithExtra<PurchOrder> page, PurchOrderQuery purchOrderQuery) ;

    List<PurchOrder> findList(@Param("purchOrderQuery") PurchOrderQuery purchOrderQuery);
    List<PurchOrder> sum(@Param("purchOrderQuery") PurchOrderQuery purchOrderQuery);
}
