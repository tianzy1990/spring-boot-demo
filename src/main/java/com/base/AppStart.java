package com.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages="com.*")
@ServletComponentScan//初始化过滤器CustomFilter
@EnableTransactionManagement//如果将tomcat-jdbc排除 不会开启默认的事务管理 需手动开启
public class AppStart extends SpringBootServletInitializer {
	public static void main(String[] args) throws Exception {
        SpringApplication.run(AppStart.class, args);
    }
	
	//防止war包部署后启动tomcat找不到初始化类
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		builder.sources(this.getClass());  
		return super.configure(builder);
	}
}
