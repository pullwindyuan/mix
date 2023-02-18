package com.futuremap.erp.common.auth;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 是否开启DataScope校验，默认是
     *
     * @return
     */
    boolean isDataScope() default false;


    int dataFilterType() default 1;

    /**
     * 是否开启字段校验，默认是
     *
     * @return
     */
    boolean isColumnScope() default false;

    /**
     * 限制范围的字段名称 （除个人外）
     */
    String[] orgScopeNames() default {"dept_id"};

    /**
     * 限制数据流装，范围是个人时的字段
     */
    String[] selfScopeNames() default {"created_by"};


    String sql() default "";
}