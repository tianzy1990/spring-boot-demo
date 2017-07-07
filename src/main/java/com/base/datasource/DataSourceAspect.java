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
     * 使用空方法定义切点表达式 针对注解  基于注解的 切入点
     */
	@Pointcut("@annotation(com.base.datasource.tx)")
    public void tx() {
    }

	/**
	 * 设置数据源名称
	 * @Title: setDataSourceKey
	 * @Description: 设置数据源名称
	 * @author tianzy
	 * @date 2017年7月7日下午3:48:43
	 *
	 * @param point
	 */
    @Before("tx()")
    public void setDataSourceKey(JoinPoint point){
    	String args1 = point.getArgs()[0].toString();// 规定带有tx注解的方法 第一个参数是用来指定数据源名称的字段
		DataSourceContextHolder.setDataSourceType(args1);  //设置数据源
    }
}