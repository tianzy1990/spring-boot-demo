package com.base;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.druid.pool.DruidDataSource;
import com.base.datasource.DynamicDataSource;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		registry.addInterceptor(new CustomInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
	
    @Bean(name="dataSource1")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf-8&useSSL=false");
        dataSource.setUsername("root");//用户名
        dataSource.setPassword("123456");//密码
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(500);
        dataSource.setMinIdle(1);
        dataSource.setMaxWait(60000);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setPoolPreparedStatements(false);
        return dataSource;
    }
    @Bean(name="dataSource2")
    public DataSource dataSource2() {
    	DruidDataSource dataSource = new DruidDataSource();
    	dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/world?characterEncoding=utf-8&useSSL=false");
    	dataSource.setUsername("root");//用户名
    	dataSource.setPassword("123456");//密码
    	dataSource.setInitialSize(5);
    	dataSource.setMaxActive(500);
    	dataSource.setMinIdle(1);
    	dataSource.setMaxWait(60000);
    	dataSource.setValidationQuery("SELECT 1");
    	dataSource.setTestOnBorrow(false);
    	dataSource.setTestWhileIdle(true);
    	dataSource.setPoolPreparedStatements(false);
    	return dataSource;
    }
    
    @Bean(name="dataSource")
    @Primary
    public DynamicDataSource dataSource(@Qualifier(value="dataSource1")DataSource dataSource1,@Qualifier(value="dataSource2")DataSource dataSource2) {
    	DynamicDataSource dynamicDataSource = new DynamicDataSource();
    	Map<Object, Object> targetDataSources=new HashMap<>();
    	targetDataSources.put("dataSource1", dataSource1);
    	targetDataSources.put("dataSource2", dataSource2);
		dynamicDataSource.setTargetDataSources(targetDataSources);
		dynamicDataSource.setDefaultTargetDataSource(dataSource1);
    	return dynamicDataSource;
    }
    
    //如果将tomcat-jdbc排除 也不会有默认的事务关系者 而且多数据源时需要手动创建多个事务管理器
    @Bean(name = "txManager1")
    @Order(10)
    public PlatformTransactionManager txManager1(@Qualifier(value="dataSource")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
//    @Bean(name = "txManager2")
//    @Order(10)
//    public PlatformTransactionManager txManager2(@Qualifier(value="dataSource2")DataSource dataSource) {
//    	return new DataSourceTransactionManager(dataSource);
//    }
//    @Bean(name = "txManager3")
//    @Order(10)
//    public PlatformTransactionManager txManager3(@Qualifier(value="DynamicDataSource")DynamicDataSource dataSource) {
//    	return new DataSourceTransactionManager(dataSource);
//    }
}