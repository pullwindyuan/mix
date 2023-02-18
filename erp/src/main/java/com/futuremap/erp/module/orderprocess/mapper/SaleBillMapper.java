package com.futuremap.erp.module.orderprocess.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.model.IBaseMapper;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.SaleBill;
import com.futuremap.erp.module.orderprocess.entity.SaleBillQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    *  Mapper 接口
    * </p>
*
* @author futuremap
* @since 2021-07-12
*/
@Mapper
public interface SaleBillMapper extends IBaseMapper<SaleBill> {

    IPageWithExtra<SaleBill> findList(PageWithExtra<SaleBill> page, SaleBillQuery saleBillQuery);
    List<SaleBill> sum(@Param("saleBillQuery")SaleBillQuery saleBillQuery);
}
