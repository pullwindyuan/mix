/**
 * 
 */
package com.futuremap.erp.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.futuremap.erp.common.auth.column.MyColumnPermissionHandler;
import com.futuremap.erp.common.auth.column.MyColumnPermissionInterceptor;
import com.futuremap.erp.common.auth.data.MyDataPermissionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @author 作者 E-mail:
* @version 创建时间：2021年1月18日 上午11:31:07
* 类说明
*/

/**
 * @author futuremap
 *
 */
@Configuration
//@EnableTransactionManagement
//@MapperScan("com.futuremap.*")//扫描文件
public class MybatisPlusConfig {
	
	/**
     * 性能分析拦截器，不建议生产使用 用来观察 SQL 执行情况及执行时长, 默认dev,staging 环境开启
     * @return com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
     */
    //@Bean
    //@Profile({"dev", "pre"})
    //public PerformanceInterceptor performanceInterceptor(){

        //启用性能分析插件, SQL是否格式化 默认false,此处开启
      //  return new PerformanceInterceptor().setFormat(true);
  //  }


    /*
     * 分页插件，自动识别数据库类型
     * 多租户，请参考官网【插件扩展】
     * */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
//        // paginationInterceptor.setOverflow(false);
//        // 设置最大单页限制数量，默认 500 条，-1 不受限制
//        // paginationInterceptor.setLimit(500);
//        // 开启 count 的 join 优化,只针对部分 left join
//        //paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
//        return paginationInterceptor;
//    }
    @Bean
    public  MyDataPermissionHandler myDataPermissionHandler(){
        return   new MyDataPermissionHandler();
    }
    @Bean
    public MyColumnPermissionHandler myColumnPermissionHandler(){
        return   new MyColumnPermissionHandler();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 添加字段权限插件
        MyColumnPermissionInterceptor myColumnPermissionInterceptor = new MyColumnPermissionInterceptor();
        myColumnPermissionInterceptor.setColumnPermissionHandler(myColumnPermissionHandler());
        interceptor.addInnerInterceptor(myColumnPermissionInterceptor);


        // 添加数据权限插件
        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor();
        // 添加自定义的数据权限处理器
        dataPermissionInterceptor.setDataPermissionHandler(myDataPermissionHandler());
        interceptor.addInnerInterceptor(dataPermissionInterceptor);

        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    
    /**
     * 自动填充功能
     * @return
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MetaHandler());
        return globalConfig;
    }

    @Bean
    public BatchSqlInjector easySqlInjector() {
        return new   BatchSqlInjector();
    }
}
