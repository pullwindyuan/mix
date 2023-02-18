package com.futuremap.erp.common.auth.column;

import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.SelectItem;

import java.util.List;

public interface ColumnPermissionHandler {
    /**
     * 获取数据权限 SQL 片段
     *
     * @param mappedStatementId Mybatis MappedStatement Id 根据该参数可以判断具体执行方法
     * @return JSqlParser 条件表达式
     */
    List<SelectItem> getSqlSegment(FromItem fromItem, String mappedStatementId);
}
