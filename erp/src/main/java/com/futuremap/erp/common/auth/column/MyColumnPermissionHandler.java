package com.futuremap.erp.common.auth.column;

import cn.hutool.core.util.StrUtil;
import com.futuremap.erp.common.auth.DataFilterMetaData;
import com.futuremap.erp.common.auth.DataFilterThreadLocal;
import com.futuremap.erp.common.auth.DataFilterTypeEnum;
import com.futuremap.erp.common.auth.DataScope;
import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.module.auth.entity.Column;
import com.futuremap.erp.module.auth.entity.User;
import com.futuremap.erp.module.auth.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class MyColumnPermissionHandler implements ColumnPermissionHandler {
    @Autowired
    UserServiceImpl userService;

    @Override
    @SneakyThrows
    public List<SelectItem> getSqlSegment(FromItem fromItem, String mappedStatementId) {

        DataScope dataScope = isDataScope(mappedStatementId);
        if(dataScope==null||!dataScope.isColumnScope()){
            return null;
        }
        try {
            //获取用户信息及权限信息
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            DataFilterTypeEnum structureEnum = DataFilterTypeEnum.getStructureEnum(dataScope.dataFilterType());
            String username = userDetails.getUsername();
            User user = userService.selectByPhone(username);
            // 1. 获取权限过滤相关信息
            DataFilterMetaData dataFilterMetaData = DataFilterMetaData.builder().
                    dataScopeFlag(dataScope.isDataScope()).
                    columnScopeFlag(dataScope.isColumnScope()).
                    selfScopeNames(dataScope.selfScopeNames()).
                    orgScopeNames(dataScope.orgScopeNames()).
                    filterType(structureEnum).userCode(user.getCode()).build();
            Table table = (Table) fromItem;
            List<Column> columnList = userService.getColumnList(user.getId());
            List<SelectItem> selectItems = new ArrayList<SelectItem>();
            if(columnList!=null&columnList.size()>0){
                log.debug("开始进行权限过滤,dataFilterMetaData:{} , tables: {},mappedStatementId: {}", dataFilterMetaData, table, mappedStatementId);
                String tableName = table.getFullyQualifiedName();
                String aliasName = table.getAlias().getName();
                columnList.forEach(e->{
                     if(e.getTableName().equals(tableName)){
                         selectItems.add(new SelectExpressionItem(
                         new net.sf.jsqlparser.schema.Column(StringUtils.isNotBlank(aliasName)?aliasName+"."+e.getColumnFiled():e.getColumnFiled())));
                     }
                });
            }else {
                throw new FuturemapBaseException("用户没有对应字段权限");
            }

           return  selectItems;
        } catch (Exception e) {
            log.error("MyDataPermissionHandler.err", e);
            throw new FuturemapBaseException("用户没有对应字段权限");
        } finally {
            DataFilterThreadLocal.clear();
        }

    }
    /**
     * 校验是否含有DataScope注解
     * @param namespace
     * @return
     * @throws ClassNotFoundException
     */
    private DataScope isDataScope(String namespace) throws ClassNotFoundException {
        if(StrUtil.isBlank(namespace)){
            return null;
        }
        //获取mapper名称
        String className = namespace.substring(0, namespace.lastIndexOf("."));
        //获取方法名
        String methodName = namespace.substring(namespace.lastIndexOf(".") + 1, namespace.length());
        //获取当前mapper 的方法
        Method[] methods = Class.forName(className).getMethods();

        Optional<Method> first = Arrays.asList(methods).stream().filter(method -> method.getName().equals(methodName)).findFirst();
        DataScope annotation = first.get().getAnnotation(DataScope.class);
        return annotation;
    }
}
