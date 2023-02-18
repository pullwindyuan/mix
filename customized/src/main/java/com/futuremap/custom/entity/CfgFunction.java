package com.futuremap.custom.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author futuremap
 * @since 2021-01-27
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("cfg_function")
public class CfgFunction extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父ID
     */
    private String parentId;

    /**
     * 功能名称
     */
    private String name;

    /**
     * 方法类型
     */
    private String methodType;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * url
     */
    private String url;

    /**
     * 级别
     */
    private Boolean level;



}
