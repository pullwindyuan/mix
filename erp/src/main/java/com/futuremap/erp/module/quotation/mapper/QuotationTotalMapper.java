package com.futuremap.erp.module.quotation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.futuremap.erp.module.quotation.entity.QuotationTotal;
import com.futuremap.erp.module.quotation.entity.QuotationTotalQuery;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    *  Mapper 接口
    * </p>
*
* @author futuremap
* @since 2021-06-10
*/
@Mapper
public interface QuotationTotalMapper extends BaseMapper<QuotationTotal> {


    @Delete("DELETE FROM quotation_total WHERE csocode=#{csocode} AND irowno=#{irowno} AND cinvcode=#{cinvcode}")
    void delOld(@Param("csocode") String csocode, @Param("irowno") String irowno, @Param("cinvcode") String cinvcode);

    IPage<QuotationTotal> findList(Page<QuotationTotal> page,QuotationTotalQuery quotationTotalQuery);


}
