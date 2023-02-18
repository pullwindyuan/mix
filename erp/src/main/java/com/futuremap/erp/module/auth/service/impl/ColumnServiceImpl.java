package com.futuremap.erp.module.auth.service.impl;

import com.futuremap.erp.module.auth.dto.ColumnDto;
import com.futuremap.erp.module.auth.entity.Column;
import com.futuremap.erp.module.auth.mapper.ColumnMapper;
import com.futuremap.erp.module.auth.service.IColumnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 功能表 服务实现类
 * </p>
 *
 * @author futuremap
 * @since 2021-06-22
 */
@Service
public class ColumnServiceImpl extends ServiceImpl<ColumnMapper, Column> implements IColumnService {

    @Autowired
    ColumnMapper columnMapper;
    @Override
    public List<ColumnDto> findList(Column column) {
        List<Column> list = columnMapper.findList(column);
        return ColumnDto.createColumnDtoList(list);
    }
    @Override
    public List<ColumnDto> findListByTables(String tables) {
        List<Column> list = columnMapper.findListByTables(tables);
        return ColumnDto.createColumnDtoList(list);
    }
}
