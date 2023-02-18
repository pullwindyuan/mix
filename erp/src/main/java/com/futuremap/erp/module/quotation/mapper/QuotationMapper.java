package com.futuremap.erp.module.quotation.mapper;

import com.futuremap.erp.module.quotation.entity.Quotation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author futuremap
 * @since 2021-06-10
 */
@Mapper
public interface QuotationMapper extends BaseMapper<Quotation> {


    @Delete("DELETE FROM quotation WHERE csocode=#{csocode} AND irowno=#{irowno} AND cinvcode=#{cinvcode} and company_code=#{companyCode} and ddate=#{ddate}")
    void delOld(@Param("csocode") String csocode, @Param("irowno") String irowno, @Param("cinvcode") String cinvcode, @Param("companyCode") String companyCode, @Param("ddate") String ddate);

}
