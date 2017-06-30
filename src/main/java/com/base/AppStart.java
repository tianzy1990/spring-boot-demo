package com.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages="com.*")
@ServletComponentScan//初始化过滤器CustomFilter
@EnableTransactionManagement//如果将tomcat-jdbc排除 不会开启默认的事务管理 需手动开启
public class AppStart {
	public static void main(String[] args) throws Exception {
        SpringApplication.run(AppStart.class, args);
    }
}
