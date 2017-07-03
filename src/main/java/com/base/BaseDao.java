package com.base;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.tool.Page;

public class BaseDao {
	@Autowired
	@Qualifier(value = "dataSource1") // 与WebAppConfig文件中数据源名称对应
	private DataSource dataSource1;

	@Autowired
	@Qualifier(value = "dataSource2")
	private DataSource dataSource2;

	public NamedParameterJdbcDaoSupport getDataSource1() {
		NamedParameterJdbcDaoSupport namedParameterJdbcDaoSupport = new NamedParameterJdbcDaoSupport();
		namedParameterJdbcDaoSupport.setDataSource(dataSource1);
		return namedParameterJdbcDaoSupport;
	}

	public NamedParameterJdbcDaoSupport getDataSource2() {
		NamedParameterJdbcDaoSupport namedParameterJdbcDaoSupport = new NamedParameterJdbcDaoSupport();
		namedParameterJdbcDaoSupport.setDataSource(dataSource2);
		return namedParameterJdbcDaoSupport;
	}

	public <T> T selectById(String sql, Long id, T t, NamedParameterJdbcDaoSupport jdbc) {
		return (T) jdbc.getJdbcTemplate().queryForObject(sql, t.getClass(), id);
	}

	public Map<String, Object> selectById(String sql, Long id, NamedParameterJdbcDaoSupport jdbc) {
		return jdbc.getJdbcTemplate().queryForMap(sql, id);
	}

	public <T> T selectByEntity(String sql, T entity, NamedParameterJdbcDaoSupport jdbc) throws Exception {
		return selectByMap(sql, getParamMap(entity), entity, jdbc);
	}

	public <T> T selectByEntity(String sql, Object entity, T t, NamedParameterJdbcDaoSupport jdbc) throws Exception {
		return selectByMap(sql, getParamMap(entity), t, jdbc);
	}

	public <T> T selectByMap(String sql, Map<String, ?> map, T t, NamedParameterJdbcDaoSupport jdbc) {
		return (T) jdbc.getNamedParameterJdbcTemplate().queryForObject(sql, map, t.getClass());
	}

	public Map<String, Object> selectByMap(String sql, Map<String, ?> map, NamedParameterJdbcDaoSupport jdbc) {
		return jdbc.getNamedParameterJdbcTemplate().queryForMap(sql, map);
	}

	public <T> T selectByParam(String sql, T t, NamedParameterJdbcDaoSupport jdbc, Object... param) {
		return (T) jdbc.getJdbcTemplate().queryForObject(sql, t.getClass(), param);
	}

	public Map<String, Object> selectByParam(String sql, NamedParameterJdbcDaoSupport jdbc, Object... param) {
		return jdbc.getJdbcTemplate().queryForMap(sql, param);
	}

	public <T> Page<T> pageByEntity(String sql, Object entity, T t, NamedParameterJdbcDaoSupport jdbc, int pageIndex, int pageSize) throws Exception {
		return pageByMap(sql, getParamMap(entity), t, jdbc, pageIndex, pageSize);
	}

	public <T> Page<T> pageByEntity(String sql, T entity, NamedParameterJdbcDaoSupport jdbc, int pageIndex, int pageSize) throws Exception {
		return pageByMap(sql, getParamMap(entity), entity, jdbc, pageIndex, pageSize);
	}

	public Page<Map<String, Object>> pageByMap(String sql, Map<String, Object> map, NamedParameterJdbcDaoSupport jdbc, int pageIndex, int pageSize) {
		return getPageByMap(sql, jdbc, map, pageIndex, pageSize);
	}

	public <T> Page<T> pageByMap(String sql, Map<String, Object> map, T t, NamedParameterJdbcDaoSupport jdbc, int pageIndex, int pageSize) {
		return getPageByMap(sql, jdbc, map, pageIndex, pageSize, t);
	}

	public Page<Map<String, Object>> pageByParam(String sql, NamedParameterJdbcDaoSupport jdbc, int pageIndex, int pageSize, Object... param) {
		return getPageByParam(sql, jdbc, pageIndex, pageSize, param);
	}

	public <T> Page<T> pageByParam(String sql, T t, NamedParameterJdbcDaoSupport jdbc, int pageIndex, int pageSize, Object... param) {
		return getPageByParam(sql, jdbc, pageIndex, pageSize, t, param);
	}

	public List<Map<String, Object>> getListByMap(String sql, Map<String, Object> map, NamedParameterJdbcDaoSupport jdbc) {
		return jdbc.getNamedParameterJdbcTemplate().queryForList(sql, map);
	}

	public <T> List<T> getListByMap(String sql, Map<String, Object> map, NamedParameterJdbcDaoSupport jdbc, T t) {
		return (List<T>) jdbc.getNamedParameterJdbcTemplate().queryForList(sql, map, t.getClass());
	}

	public List<Map<String, Object>> getListByParam(String sql, NamedParameterJdbcDaoSupport jdbc, Object... param) {
		return jdbc.getJdbcTemplate().queryForList(sql, param);
	}

	public <T> List<T> getListByParam(String sql, NamedParameterJdbcDaoSupport jdbc, T t, Object... param) {
		return (List<T>) jdbc.getJdbcTemplate().queryForList(sql, t.getClass(), param);
	}

	public long insertByMap(String sql, NamedParameterJdbcDaoSupport jdbc, Map<String, Object> map, String key) {
		KeyHolder keyholder = new GeneratedKeyHolder();
		SqlParameterSource paramSource = new MapSqlParameterSource(map);
		jdbc.getNamedParameterJdbcTemplate().update(sql, paramSource, keyholder, new String[] { key });
		return jdbc.getNamedParameterJdbcTemplate().update(sql, map);
	}

	public long insertByEntity(String sql, NamedParameterJdbcDaoSupport jdbc, Object entity, String key) throws Exception {
		return insertByMap(sql, jdbc, getParamMap(entity), key);
	}

	public int deleteById(String sql, long id, NamedParameterJdbcDaoSupport jdbc) {
		return jdbc.getJdbcTemplate().update(sql, id);
	}

	public int deleteByMap(String sql, Map<String, Object> map, NamedParameterJdbcDaoSupport jdbc) {
		return jdbc.getNamedParameterJdbcTemplate().update(sql, map);
	}

	public int deleteByParam(String sql, NamedParameterJdbcDaoSupport jdbc, Object... param) {
		return jdbc.getJdbcTemplate().update(sql, param);
	}

	public int deleteByEntity(String sql, Object entity, NamedParameterJdbcDaoSupport jdbc) throws Exception {
		return deleteByMap(sql, getParamMap(entity), jdbc);
	}

	public int updateByMap(String sql, Map<String, Object> map, NamedParameterJdbcDaoSupport jdbc) {
		return jdbc.getNamedParameterJdbcTemplate().update(sql, map);
	}

	public int updateByParam(String sql, NamedParameterJdbcDaoSupport jdbc, Object... param) {
		return jdbc.getJdbcTemplate().update(sql, param);
	}

	public int updateByEntity(String sql, Object entity, NamedParameterJdbcDaoSupport jdbc) throws Exception {
		return updateByMap(sql, getParamMap(entity), jdbc);
	}

	private Page<Map<String, Object>> getPageByMap(String sql, NamedParameterJdbcDaoSupport jdbc, Map<String, Object> map, int pageIndex, int pageSize) {
		if (pageIndex > 0 && pageSize > 0) {
			sql = getSql(sql, pageIndex, pageSize);
			List<Map<String, Object>> listByMap = getListByMap(sql, map, jdbc);
			long totalRow = getTotalRow(jdbc);
			return new Page<Map<String, Object>>(totalRow, pageIndex, pageSize, listByMap);
		} else {
			return new Page<Map<String, Object>>(0, 0, 0, getListByMap(sql, map, jdbc));
		}
	}

	private <T> Page<T> getPageByMap(String sql, NamedParameterJdbcDaoSupport jdbc, Map<String, Object> map, int pageIndex, int pageSize, T t) {
		if (pageIndex > 0 && pageSize > 0) {
			sql = getSql(sql, pageIndex, pageSize);
			List<T> listByMap = getListByMap(sql, map, jdbc, t);
			long totalRow = getTotalRow(jdbc);
			return new Page<T>(totalRow, pageIndex, pageSize, listByMap);
		} else {
			return new Page<T>(0, 0, 0, getListByMap(sql, map, jdbc, t));
		}
	}

	private Page<Map<String, Object>> getPageByParam(String sql, NamedParameterJdbcDaoSupport jdbc, int pageIndex, int pageSize, Object... param) {
		if (pageIndex > 0 && pageSize > 0) {
			sql = getSql(sql, pageIndex, pageSize);
			List<Map<String, Object>> listByParam = getListByParam(sql, jdbc, param);
			long totalRow = getTotalRow(jdbc);
			return new Page<Map<String, Object>>(totalRow, pageIndex, pageSize, listByParam);
		} else {
			return new Page<Map<String, Object>>(0, 0, 0, getListByParam(sql, jdbc, param));
		}
	}

	private <T> Page<T> getPageByParam(String sql, NamedParameterJdbcDaoSupport jdbc, int pageIndex, int pageSize, T t, Object... param) {
		if (pageIndex > 0 && pageSize > 0) {
			sql = getSql(sql, pageIndex, pageSize);
			List<T> listByParam = getListByParam(sql, jdbc, t, param);
			long totalRow = getTotalRow(jdbc);
			return new Page<T>(totalRow, pageIndex, pageSize, listByParam);
		} else {
			return new Page<T>(0, 0, 0, getListByParam(sql, jdbc, t, param));
		}
	}

	private String getSql(String sql, int pageIndex, int pageSize) {
		int mysqlIndex = pageIndex - 1;
		sql = StringUtils.replaceOnceIgnoreCase(sql, "select", "select sql_calc_found_rows ");
		sql += " limit " + mysqlIndex * pageSize + "," + pageSize;
		return sql;
	}

	private long getTotalRow(NamedParameterJdbcDaoSupport jdbc) {
		String totalsql = "select found_rows()";
		SqlRowSet queryForRowSet = jdbc.getJdbcTemplate().queryForRowSet(totalsql);
		return queryForRowSet.getLong(1);
	}

	/**
	 * 获取实体的参数
	 * 
	 * @Title: getParamMap
	 * @Description: 获取对象的参数
	 * @author tianzy
	 * @date 2017年7月3日上午9:19:52
	 *
	 * @param obj
	 *            实体
	 * @return
	 * @throws Exception
	 * @
	 */
	private HashMap<String, Object> getParamMap(Object obj) throws Exception {
		Class<?> clazz = obj.getClass();
		Field[] fields = obj.getClass().getDeclaredFields();// 获得属性
		HashMap<String, Object> paramMap = new HashMap<>();
		for (Field field : fields) {
			String fieldName = field.getName();
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
			Method getMethod = pd.getReadMethod();// 获得get方法
			Object o = getMethod.invoke(obj);// 执行get方法返回一个Object
			if (o != null) {
				paramMap.put(fieldName, o);
			}
		}
		return paramMap;
	}
}
