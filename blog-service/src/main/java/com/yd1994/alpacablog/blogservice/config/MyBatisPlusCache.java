package com.yd1994.alpacablog.blogservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yd
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.yd1994.alpacablog.blogservice.mapper")
public class MyBatisPlusCache {

    /**
     * 乐观锁
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 分页
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor interceptor = new PaginationInterceptor();
        interceptor.setDialectType("mysql");
        return interceptor;
    }

    /**
     * 逻辑删除
     * (不适用：当资源曾经存在但被删除，http需要返回410状态码。)
     * @return
     */
    /*
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }
    */
}

