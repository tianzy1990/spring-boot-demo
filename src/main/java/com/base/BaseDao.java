package com.base;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;


public class BaseDao {
	@Autowired
	@Qualifier(value="mydateSource")
	private DataSource mydateSource;
	
	@Autowired
	@Qualifier(value="mydate")
	private DataSource mydate;
	public NamedParameterJdbcDaoSupport getmydateSource(){
		NamedParameterJdbcDaoSupport namedParameterJdbcDaoSupport = new NamedParameterJdbcDaoSupport();
		namedParameterJdbcDaoSupport.setDataSource(mydateSource);
		return namedParameterJdbcDaoSupport;
	}
	
	public NamedParameterJdbcDaoSupport getmydate(){
		NamedParameterJdbcDaoSupport namedParameterJdbcDaoSupport = new NamedParameterJdbcDaoSupport();
		namedParameterJdbcDaoSupport.setDataSource(mydate);
		return namedParameterJdbcDaoSupport;
	}
}
