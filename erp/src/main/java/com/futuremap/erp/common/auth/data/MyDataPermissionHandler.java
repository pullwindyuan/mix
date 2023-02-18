package com.futuremap.erp.common.auth.data;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.futuremap.erp.common.auth.DataFilterMetaData;
import com.futuremap.erp.common.auth.DataFilterThreadLocal;
import com.futuremap.erp.common.auth.DataFilterTypeEnum;
import com.futuremap.erp.common.auth.DataScope;
import com.futuremap.erp.common.exception.FuturemapBaseException;
import com.futuremap.erp.module.auth.entity.User;
import com.futuremap.erp.module.auth.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class MyDataPermissionHandler implements DataPermissionHandler {

    @Autowired
    UserServiceImpl userService;
    /**
     * @param where             原SQL Where 条件表达式
     * @param mappedStatementId Mapper接口方法ID
     * @return
     */
    @SneakyThrows
    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {

        DataScope dataScope = isDataScope(mappedStatementId);
        if(dataScope==null||!dataScope.isDataScope()){
          return where;
        }
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            DataFilterTypeEnum structureEnum = DataFilterTypeEnum.getStructureEnum(dataScope.dataFilterType());
            String username = userDetails.getUsername();
            User user = userService.selectByPhone(username);
    //        DataFilterThreadLocal.set();
            // 1. 获取权限过滤相关信息
            DataFilterMetaData dataFilterMetaData = DataFilterMetaData.builder().
                    dataScopeFlag(dataScope.isDataScope()).
                    columnScopeFlag(dataScope.isColumnScope()).
                        selfScopeNames(dataScope.selfScopeNames()).
                        orgScopeNames(dataScope.orgScopeNames()).
                        filterType(structureEnum).userCode(user.getCode()).build();

            log.debug("开始进行权限过滤,dataFilterMetaData:{} , where: {},mappedStatementId: {}", dataFilterMetaData, where, mappedStatementId);

            Expression expression = new HexValue(" 1 = 1 ");
            if (where == null) {
                where = expression;
            }
            if(dataFilterMetaData.dataScopeFlag){
                Expression inExpression = getExpression(where, dataFilterMetaData);
                if (inExpression != null){
                    return inExpression;
                }
            }


        } catch (Exception e) {
            log.error("MyDataPermissionHandler.err", e);
            new FuturemapBaseException("用户没有数据权限");
        } finally {
            DataFilterThreadLocal.clear();
        }
        return where;
    }

    private Expression getExpression(Expression where, DataFilterMetaData dataFilterMetaData) {
        switch (dataFilterMetaData.filterType) {
            // 查看全部
            case ALL:
                return where;
            // 查看本人所在组织机构以及下属机构
            case DEPT_SETS:
                // 创建IN 表达式
                // 创建IN范围的元素集合
                Set<Long> deptIds = dataFilterMetaData.getDeptIds();
                // 把集合转变为JSQLParser需要的元素列表
                ItemsList itemsList = new ExpressionList(deptIds.stream().map(LongValue::new).collect(Collectors.toList()));
                InExpression inExpression = new InExpression(new Column(dataFilterMetaData.getOrgScopeNames()[0]), itemsList);
                return new AndExpression(where, inExpression);
            // 查看当前部门的数据
            case DEPT:
                //  = 表达式
                // dept_id = deptId
                EqualsTo equalsTo = new EqualsTo();
                equalsTo.setLeftExpression(new Column(dataFilterMetaData.getOrgScopeNames()[0]));
                equalsTo.setRightExpression(new LongValue(dataFilterMetaData.getDeptId()));
                // 创建 AND 表达式 拼接Where 和 = 表达式
                // WHERE xxx AND dept_id = 3
                return new AndExpression(where, equalsTo);
            // 查看自己的数据
            case SELF:
                // create_by = userId
                EqualsTo selfEqualsTo = new EqualsTo();
                selfEqualsTo.setLeftExpression(new Column(dataFilterMetaData.getSelfScopeNames()[0]));
                selfEqualsTo.setRightExpression(new StringValue(dataFilterMetaData.getUserCode()));
                return new AndExpression(where, selfEqualsTo);
            case DIY:
                return new AndExpression(where, new StringValue(dataFilterMetaData.getSql()));
            default:
                break;


        }
        return null;
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
