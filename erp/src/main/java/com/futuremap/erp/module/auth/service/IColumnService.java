package com.futuremap.erp.module.auth.service;

import com.futuremap.erp.module.auth.dto.ColumnDto;
import com.futuremap.erp.module.auth.entity.Column;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 功能表 服务类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-22
 */
public interface IColumnService extends IService<Column> {
    List<ColumnDto> findList(Column column);
    List<ColumnDto> findListByTables(String tables);
}
