package com.futuremap.erp.module.auth.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.futuremap.erp.module.auth.entity.Resource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
@TableName("auth_resource")
@ApiModel(value="Resource对象", description="功能表")
public class ResourceDto extends Resource implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 功能对应的列数据权限信息
     */
    @ApiModelProperty(value = "功能对应的列数据权限信息")
    private List<ColumnDto> columnDtoList;
}
