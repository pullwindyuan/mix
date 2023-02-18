package com.futuremap.erp.module.comp.entity;

import com.futuremap.erp.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author futuremap
 * @since 2021-05-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CompanyDataTableInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司编码
     */
    private String companyCode;

    /**
     * 对应mapper操作类路径
     */
    private String datasource;


    /**
     * 对应mapper操作类路径
     */
    private String name;

}
