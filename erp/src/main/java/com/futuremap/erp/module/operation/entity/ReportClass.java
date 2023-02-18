package com.futuremap.erp.module.operation.entity;

import com.futuremap.erp.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author futuremap
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReportClass extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 报表类型0 经营表
     */
    private Integer reportType;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类型编码
     */
    private String typeCode;

    private String parentCode;

    /**
     * 排序编号
     */
    private Integer sort;

    private Integer level;

    private String unit;





}
