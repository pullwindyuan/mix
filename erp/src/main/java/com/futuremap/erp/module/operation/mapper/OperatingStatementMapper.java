package com.futuremap.erp.module.operation.mapper;

import com.futuremap.erp.module.operation.entity.OperatingStatement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.futuremap.erp.module.operation.entity.OperatingStatementQuery;
import com.futuremap.erp.module.operation.entity.OperatingStatementSubQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
    *  Mapper 接口
    * </p>
*
* @author futuremap
* @since 2021-06-01
*/
@Mapper
public interface OperatingStatementMapper extends BaseMapper<OperatingStatement> {

    List<OperatingStatement> findList(@Param("operatingStatementQuery") OperatingStatementQuery operatingStatementQuery);

    List<OperatingStatement> findListByNotInCompanyCode(@Param("operatingStatementQuery") OperatingStatementSubQuery operatingStatementQuery);

    List<OperatingStatement> findListByNotInCompanyCodeAndClassCode(@Param("operatingStatementQuery") OperatingStatementQuery operatingStatementQuery);
    List<OperatingStatement> sumSubList(@Param("operatingStatementQuery")OperatingStatementQuery operatingStatementQuery);
    List<OperatingStatement> sumList(@Param("operatingStatementQuery")OperatingStatementQuery operatingStatementQuery);

    List<OperatingStatement> sumCompany(@Param("yearmonth")String yearmonth);
}
