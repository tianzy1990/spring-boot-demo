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
	public void testaop(){
		Map<String, Object> selectById = selectById("select * from city where id=?", 1l, getDataSource());
		System.out.println(selectById);
	}
	public void testaopinsert(String sql, Map<String, Object> map, String key){
		long insertByMap = insertByMap(sql, getDataSource(), map, key);
		System.out.println(insertByMap);
	}
	public Integer selectsku(){
		Map<String, Object> selectById = selectById("select value from test where id=?", 1l, getDataSource1());
		return Integer.valueOf(selectById.get("value").toString()).intValue();
	}
	public Integer reducesku(){
		return getDataSource1().getJdbcTemplate().update("update test set value=value-1 where id=1");
	}
}
