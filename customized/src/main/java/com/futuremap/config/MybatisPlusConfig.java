/**
 * 
 */
package com.futuremap.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.futuremap.config.MetaHandler;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baomidou.mybatisplus.core.config.GlobalConfig;

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
public class MybatisPlusConfig {
	
	/**
     * 性能分析拦截器，不建议生产使用 用来观察 SQL 执行情况及执行时长, 默认dev,staging 环境开启
     * @return com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
     */
    @Bean
    @Profile({"dev", "pre"})
    public PerformanceMonitorInterceptor PerformanceMonitorInterceptor(){

        //启用性能分析插件, SQL是否格式化 默认false,此处开启
        return new PerformanceMonitorInterceptor();
    }


    /*
     * 分页插件，自动识别数据库类型
     * 多租户，请参考官网【插件扩展】
     * */
    @Bean
    public PaginationInnerInterceptor PaginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        //paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
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
   
}
