package com.futuremap.erp.module.orderprocess.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.model.IBaseMapper;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.RecordinList;
import com.futuremap.erp.module.orderprocess.entity.RecordinQuery;
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
public interface RecordinListMapper extends IBaseMapper<RecordinList> {

    IPageWithExtra<RecordinList> findList(PageWithExtra<RecordinList> page, RecordinQuery recordinQuery);

   List<RecordinList> findList(@Param("recordinQuery")RecordinQuery recordinQuery);
    List<RecordinList> sum(@Param("recordinQuery")RecordinQuery recordinQuery);
}
