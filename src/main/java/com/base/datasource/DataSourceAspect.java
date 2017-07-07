package com.base.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Order(1)// 保证该AOP在@Transactional之前执行
@Component
public class DataSourceAspect {
	
    /**
     * 使用空方法定义切点表达式
     */
	@Pointcut("@annotation(com.base.datasource.tx)")
    public void tx() {
    }

    @Before("tx()")
    public void setDataSourceKey(JoinPoint point){
    	String args1 = point.getArgs()[0].toString();
		DataSourceContextHolder.setDataSourceType(args1);  
    }
}