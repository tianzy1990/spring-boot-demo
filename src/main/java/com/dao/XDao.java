package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.base.BaseDao;

@Repository
public class XDao extends BaseDao {

	public void testdao() {
		List<Map<String, Object>> queryForList = getDataSource1().getJdbcTemplate().queryForList("select * from city limit 1");
		System.out.println(queryForList);
		List<Map<String, Object>> queryForList1 = getDataSource2().getJdbcTemplate().queryForList("select * from city limit 1");
		System.out.println(queryForList1);
	}
	public void testtransactional(String sql) {
		getDataSource1().getJdbcTemplate().update(sql);
	}
}
