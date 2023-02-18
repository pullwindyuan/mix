package com.futuremap.erp.module.orderprocess.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.common.model.IBaseMapper;
import com.futuremap.erp.common.page.IPageWithExtra;
import com.futuremap.erp.common.page.PageWithExtra;
import com.futuremap.erp.module.orderprocess.entity.RecordoutList;
import com.futuremap.erp.module.orderprocess.entity.RecordoutQuery;
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
public interface RecordoutListMapper extends IBaseMapper<RecordoutList> {

    IPageWithExtra<RecordoutList> findList(PageWithExtra<RecordoutList> page, RecordoutQuery recordoutQuery);

    List<RecordoutList> findList(@Param("recordoutQuery")RecordoutQuery recordoutQuery);

    List<RecordoutList> sum(@Param("recordoutQuery")RecordoutQuery recordoutQuery);
}
