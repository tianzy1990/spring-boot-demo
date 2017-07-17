package com.base.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 
 * @Title: DynamicDataSource.java
 * @Package com.base.datasource
 * @Description: 重写实现动态数据源
 * @author tianzy
 * @date 2017年7月7日下午3:50:53
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		String type = DataSourceContextHolder.getDataSourceType();
		return type;
	}
}
