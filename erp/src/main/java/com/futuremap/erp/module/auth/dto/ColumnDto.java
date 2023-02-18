package com.futuremap.erp.module.auth.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.futuremap.erp.module.auth.entity.Column;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * <p>
 * 功能表
 * </p>
 *
 * @author futuremap
 * @since 2021-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ColumnDto对象", description="表字段数据")
public class ColumnDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 表名称
     */
    @ApiModelProperty(value = "表名称")
    private String tableName;

    /**
     * 列信息
     */
    @ApiModelProperty(value = "列信息")
    private List<Column> columns;

    public void addColumn(Column column) {
        if(columns == null) {
            columns = new ArrayList<>();
        }
        columns.add(column);
    }

    public ColumnDto() {
        super();
    }

    public ColumnDto(Column column) {
        super();
        this.setTableName(column.getTableName());
        this.addColumn(column);
    }
    public static List<ColumnDto> createColumnDtoList(List<Column> columnList) {
        Map<String,ColumnDto> columnDtoMap = new HashMap<>();
        columnList.forEach(e->{
            String tableName = e.getTableName();
            ColumnDto columnDto = columnDtoMap.get(tableName);
            if(columnDto == null) {
                columnDto = new ColumnDto(e);
                columnDtoMap.put(tableName, columnDto);
            }else {
                columnDto.addColumn(e);
            }
        });
        List<ColumnDto> list = new ArrayList<>();
        columnDtoMap.forEach((k,e)->{
            list.add(e);
        });
        return list;
    }
}
