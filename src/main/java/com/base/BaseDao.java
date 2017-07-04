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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

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

	/**
	 * 查询返回指定类型对象
	 * @Title: selectById
	 * @Description: 通过主键查询返回指定类型对象
	 * @author tianzy
	 * @date 2017年7月4日上午9:54:54
	 *
	 * @param sql sql语句
	 * @param id 主键
	 * @param t 返回类型
	 * @param jdbc 数据源
	 * @return
	 */
	public <T> T selectById(String sql, Long id, T t, NamedParameterJdbcDaoSupport jdbc) {
		return jdbc.getJdbcTemplate().queryForObject(sql,new BeanPropertyRowMapper<T>((Class<T>) t.getClass()),id);  
	}

	/**
	 * 查询返回map
	 * @Title: selectById
	 * @Description: 通过主键查询返回map
	 * @author tianzy
	 * @date 2017年7月4日上午9:56:38
	 *
	 * @param sql sql语句
	 * @param id 主键
	 * @param jdbc 数据源
	 * @return
	 */
	public Map<String, Object> selectById(String sql, Long id, NamedParameterJdbcDaoSupport jdbc) {
		return jdbc.getJdbcTemplate().queryForMap(sql, id);
	}

	/**
	 * 查询返回实体类型对象
	 * @Title: selectByEntity
	 * @Description: 通过实体查询返回实体类型对象
	 * @author tianzy
	 * @date 2017年7月4日上午9:57:16
	 *
	 * @param sql sql语句
	 * @param entity 实体对象
	 * @param jdbc 数据源
	 * @return
	 * @throws Exception
	 */
	public <T> T selectByEntity(String sql, T entity, NamedParameterJdbcDaoSupport jdbc) throws Exception {
		return selectByMap(sql, getParamMap(entity), entity, jdbc);
	}

	/**
	 * 查询返回指定类型对象
	 * @Title: selectByEntity
	 * @Description: 通过实体查询返回指定类型对象
	 * @author tianzy
	 * @date 2017年7月4日上午9:58:36
	 *
	 * @param sql sql语句
	 * @param entity 实体对象
	 * @param t 返回类型
	 * @param jdbc 数据源
	 * @return
	 * @throws Exception
	 */
	public <T> T selectByEntity(String sql, Object entity, T t, NamedParameterJdbcDaoSupport jdbc) throws Exception {
		return selectByMap(sql, getParamMap(entity), t, jdbc);
	}

	/**
	 * 查询返回指定类型对象
	 * @Title: selectByMap
	 * @Description: 通过Map查询返回指定类型对象
	 * @author tianzy
	 * @date 2017年7月4日上午9:59:30
	 *
	 * @param sql sql语句
	 * @param map 请求参数map
	 * @param t 返回类型
	 * @param jdbc 数据源
	 * @return
	 */
	public <T> T selectByMap(String sql, Map<String, ?> map, T t, NamedParameterJdbcDaoSupport jdbc) {
		return (T) jdbc.getNamedParameterJdbcTemplate().queryForObject(sql,map,new BeanPropertyRowMapper<T>((Class<T>) t.getClass()));
	}

	/**
	 * 查询返回map
	 * @Title: selectByMap
	 * @Description: 通过Map查询返回map
	 * @author tianzy
	 * @date 2017年7月4日上午10:03:21
	 *
	 * @param sql sql语句
	 * @param map 请求参数map
	 * @param jdbc 数据源
	 * @return
	 */
	public Map<String, Object> selectByMap(String sql, Map<String, ?> map, NamedParameterJdbcDaoSupport jdbc) {
		return jdbc.getNamedParameterJdbcTemplate().queryForMap(sql, map);
	}

	/**
	 * 查询返回指定类型对象
	 * @Title: selectByParam
	 * @Description: 通过Param查询返回指定类型对象
	 * @author tianzy
	 * @date 2017年7月4日上午10:04:05
	 *
	 * @param sql sql语句
	 * @param t 返回类型
	 * @param jdbc 数据源
	 * @param param 参数
	 * @return
	 */
	public <T> T selectByParam(String sql, T t, NamedParameterJdbcDaoSupport jdbc, Object... param) {
		return (T) jdbc.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<T>((Class<T>) t.getClass()), param);
	}

	/**
	 * 查询返回Map
	 * @Title: selectByParam
	 * @Description: 通过Param查询返回Map
	 * @author tianzy
	 * @date 2017年7月4日上午10:07:06
	 *
	 * @param sql sql语句
	 * @param jdbc 数据源
	 * @param param 参数
	 * @return
	 */
	public Map<String, Object> selectByParam(String sql, NamedParameterJdbcDaoSupport jdbc, Object... param) {
		return jdbc.getJdbcTemplate().queryForMap(sql, param);
	}

	/**
	 * 获取指定类型分页数据
	 * @Title: pageByEntity
	 * @Description: 通过实体获取分页数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:08:25
	 *
	 * @param sql sql语句
	 * @param entity 实体
	 * @param t 返回类型 
	 * @param jdbc 数据源
	 * @param pageIndex 当前页数
	 * @param pageSize 每页条数
	 * @return
	 * @throws Exception
	 */
	public <T> Page<T> pageByEntity(String sql, Object entity, T t, NamedParameterJdbcDaoSupport jdbc, int pageIndex, int pageSize) throws Exception {
		return pageByMap(sql, getParamMap(entity), t, jdbc, pageIndex, pageSize);
	}

	/**
	 * 获取实体类型分页数据
	 * @Title: pageByEntity
	 * @Description: 通过实体获取实体类型分页数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:09:49
	 *
	 * @param sql sql语句
	 * @param entity 实体
	 * @param jdbc 数据源
	 * @param pageIndex 当前页数
	 * @param pageSize 每页条数
	 * @return
	 * @throws Exception
	 */
	public <T> Page<T> pageByEntity(String sql, T entity, NamedParameterJdbcDaoSupport jdbc, int pageIndex, int pageSize) throws Exception {
		return pageByMap(sql, getParamMap(entity), entity, jdbc, pageIndex, pageSize);
	}

	/**
	 * 获取map类型分页数据
	 * @Title: pageByMap
	 * @Description: 通过map获取map类型分页数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:10:48
	 *
	 * @param sql sql语句
	 * @param map 请求参数map
	 * @param jdbc 数据源
	 * @param pageIndex 当前页数
	 * @param pageSize 每页条数
	 * @return
	 */
	public Page<Map<String, Object>> pageByMap(String sql, Map<String, Object> map, NamedParameterJdbcDaoSupport jdbc, int pageIndex, int pageSize) {
		return getPageByMap(sql, jdbc, map, pageIndex, pageSize);
	}

	/**
	 * 获取指定类型分页数据
	 * @Title: pageByMap
	 * @Description: 通过map获取指定类型分页数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:12:01
	 *
	 * @param sql
	 * @param map
	 * @param t
	 * @param jdbc
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public <T> Page<T> pageByMap(String sql, Map<String, Object> map, T t, NamedParameterJdbcDaoSupport jdbc, int pageIndex, int pageSize) {
		return getPageByMap(sql, jdbc, map, pageIndex, pageSize, t);
	}

	/**
	 * 获取map类型分页数据
	 * @Title: pageByParam
	 * @Description: 通过Param获取map类型分页数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:13:36
	 *
	 * @param sql sql语句
	 * @param jdbc 数据源
	 * @param pageIndex 当前页数
	 * @param pageSize 每页条数
	 * @param param 参数数组
	 * @return
	 */
	public Page<Map<String, Object>> pageByParam(String sql, NamedParameterJdbcDaoSupport jdbc, int pageIndex, int pageSize, Object... param) {
		return getPageByParam(sql, jdbc, pageIndex, pageSize, param);
	}

	/**
	 * 获取指定类型分页数据
	 * @Title: pageByParam
	 * @Description: 通过Param获取指定类型分页数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:15:23
	 *
	 * @param sql sql语句
	 * @param t 返回类型
	 * @param jdbc 数据源
	 * @param pageIndex 当前页数
	 * @param pageSize 每页条数
	 * @param param 请求参数
	 * @return
	 */
	public <T> Page<T> pageByParam(String sql, T t, NamedParameterJdbcDaoSupport jdbc, int pageIndex, int pageSize, Object... param) {
		return getPageByParam(sql, jdbc, pageIndex, pageSize, t, param);
	}

	/**
	 * 获取列表数据
	 * @Title: getListByMap
	 * @Description: 通过map获取列表数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:16:30
	 *
	 * @param sql sql语句
	 * @param map 请求参数
	 * @param jdbc 数据源
	 * @return
	 */
	public List<Map<String, Object>> getListByMap(String sql, Map<String, Object> map, NamedParameterJdbcDaoSupport jdbc) {
		return jdbc.getNamedParameterJdbcTemplate().queryForList(sql, map);
	}
	

	/**
	 * 获取指定类型列表数据
	 * @Title: getListByMap
	 * @Description: 通过map获取指定类型列表数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:17:35
	 *
	 * @param sql sql语句
	 * @param map 请求参数
	 * @param jdbc 数据源
	 * @param t 返回类型
	 * @return
	 */
	public <T> List<T> getListByMap(String sql, Map<String, Object> map, NamedParameterJdbcDaoSupport jdbc, T t) {
		return jdbc.getNamedParameterJdbcTemplate().query(sql, map, new BeanPropertyRowMapper<T>((Class<T>) t.getClass()));
	}
	
	/**
	 * 获取列表数据
	 * @Title: getListByEntity 
	 * @Description: 通过实体获取列表数据
	 * @author tianzy
	 * @date 2017年7月4日下午5:21:28
	 *
	 * @param sql sql语句
	 * @param entity 实体
	 * @param jdbc 数据源
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getListByEntity(String sql, Object entity, NamedParameterJdbcDaoSupport jdbc) throws Exception {
		return getListByMap(sql, getParamMap(entity), jdbc);
	}
	
	/**
	 * 获取列表数据
	 * @Title: getListByEntity
	 * @Description: 通过实体获取指定类型列表数据
	 * @author tianzy
	 * @date 2017年7月4日下午5:21:34
	 *
	 * @param sql sql语句
	 * @param entity 实体
	 * @param jdbc 数据源
	 * @param t 返回类型
	 * @return
	 * @throws Exception
	 */
	public <T>List<T> getListByEntity(String sql, Object entity, NamedParameterJdbcDaoSupport jdbc,T t) throws Exception {
		return getListByMap(sql, getParamMap(entity), jdbc, t);
	}

	/**
	 * 获取列表数据
	 * @Title: getListByParam
	 * @Description: 通过Param获取列表数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:18:22
	 *
	 * @param sql sql语句
	 * @param jdbc 数据源
	 * @param param 请求参数
	 * @return
	 */
	public List<Map<String, Object>> getListByParam(String sql, NamedParameterJdbcDaoSupport jdbc, Object... param) {
		return jdbc.getJdbcTemplate().queryForList(sql, param);
	}

	/**
	 * 获取指定类型列表数据
	 * @Title: getListByParam
	 * @Description: 获取指定类型列表数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:20:28
	 *
	 * @param sql sql语句
	 * @param jdbc 数据源
	 * @param t 返回类型
	 * @param param 请求参数
	 * @return
	 */
	public <T> List<T> getListByParam(String sql, NamedParameterJdbcDaoSupport jdbc, T t, Object... param) {
		return (List<T>) jdbc.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<T>((Class<T>) t.getClass()),param);
	}

	/**
	 * 插入数据
	 * @Title: insertByMap
	 * @Description: 插入数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:21:17
	 *
	 * @param sql sql语句
	 * @param jdbc 数据源
	 * @param map 请求参数
	 * @param key 返回主键名称(适用于自增主键策略)
	 * @return
	 */
	public long insertByMap(String sql, NamedParameterJdbcDaoSupport jdbc, Map<String, Object> map, String key) {
		KeyHolder keyholder = new GeneratedKeyHolder();
		SqlParameterSource paramSource = new MapSqlParameterSource(map);
		jdbc.getNamedParameterJdbcTemplate().update(sql, paramSource, keyholder, new String[] { key });
		return jdbc.getNamedParameterJdbcTemplate().update(sql, map);
	}

	/**
	 * 插入数据
	 * @Title: insertByEntity
	 * @Description: 插入数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:23:07
	 *
	 * @param sql sql语句
	 * @param jdbc 数据源
	 * @param entity 实体
	 * @param key 返回主键名称(适用于自增主键策略)
	 * @return
	 * @throws Exception
	 */
	public long insertByEntity(String sql, NamedParameterJdbcDaoSupport jdbc, Object entity, String key) throws Exception {
		return insertByMap(sql, jdbc, getParamMap(entity), key);
	}

	/**
	 * 删除数据
	 * @Title: deleteById
	 * @Description: 删除数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:23:52
	 *
	 * @param sql sql语句
	 * @param id 主键
	 * @param jdbc 数据源
	 * @return
	 */
	public int deleteById(String sql, long id, NamedParameterJdbcDaoSupport jdbc) {
		return jdbc.getJdbcTemplate().update(sql, id);
	}

	/**
	 * 删除数据
	 * @Title: deleteByMap
	 * @Description: 删除数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:24:28
	 *
	 * @param sql sql语句
	 * @param map 请求参数
	 * @param jdbc 数据源
	 * @return
	 */
	public int deleteByMap(String sql, Map<String, Object> map, NamedParameterJdbcDaoSupport jdbc) {
		return jdbc.getNamedParameterJdbcTemplate().update(sql, map);
	}

	/**
	 * 删除数据
	 * @Title: deleteByParam
	 * @Description: 删除数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:25:08
	 *
	 * @param sql sql语句
	 * @param jdbc 数据源
	 * @param param 请求参数
	 * @return
	 */
	public int deleteByParam(String sql, NamedParameterJdbcDaoSupport jdbc, Object... param) {
		return jdbc.getJdbcTemplate().update(sql, param);
	}

	/**
	 * 删除数据
	 * @Title: deleteByEntity
	 * @Description: 删除数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:25:45
	 *
	 * @param sql sql语句
	 * @param entity 实体
	 * @param jdbc 数据源
	 * @return
	 * @throws Exception
	 */
	public int deleteByEntity(String sql, Object entity, NamedParameterJdbcDaoSupport jdbc) throws Exception {
		return deleteByMap(sql, getParamMap(entity), jdbc);
	}

	/**
	 * 更新数据
	 * @Title: updateByMap
	 * @Description: 更新数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:26:13
	 *
	 * @param sql sql语句
	 * @param map 请求参数
	 * @param jdbc 数据源
	 * @return
	 */
	public int updateByMap(String sql, Map<String, Object> map, NamedParameterJdbcDaoSupport jdbc) {
		return jdbc.getNamedParameterJdbcTemplate().update(sql, map);
	}

	/**
	 * 更新数据
	 * @Title: updateByParam
	 * @Description: 更新数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:26:51
	 *
	 * @param sql sql语句
	 * @param jdbc 数据源
	 * @param param 请求参数
	 * @return
	 */
	public int updateByParam(String sql, NamedParameterJdbcDaoSupport jdbc, Object... param) {
		return jdbc.getJdbcTemplate().update(sql, param);
	}

	/**
	 * 更新数据
	 * @Title: updateByEntity
	 * @Description: 更新数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:27:41
	 *
	 * @param sql sql语句
	 * @param entity 实体
	 * @param jdbc 数据源
	 * @return
	 * @throws Exception
	 */
	public int updateByEntity(String sql, Object entity, NamedParameterJdbcDaoSupport jdbc) throws Exception {
		return updateByMap(sql, getParamMap(entity), jdbc);
	}

	/**
	 * 获取map类型分页数据
	 * @Title: getPageByMap
	 * @Description: 获取分页数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:28:29
	 *
	 * @param sql sql语句
	 * @param jdbc 数据源
	 * @param map 参数
	 * @param pageIndex 当前页数
	 * @param pageSize 每页条数
	 * @return
	 */
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

	/**
	 * 获取指定类型分页数据
	 * @Title: getPageByMap
	 * @Description: 获取指定类型分页数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:30:11
	 *
	 * @param sql sql语言
	 * @param jdbc 数据源
	 * @param map 请求参数
	 * @param pageIndex 当前页数
	 * @param pageSize 每页条数
	 * @param t 返回类型
	 * @return
	 */
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
	
	/**
	 * 获取map类型分页数据
	 * @Title: getPageByParam
	 * @Description: 获取map类型分页数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:31:20
	 *
	 * @param sql sql语句
	 * @param jdbc 数据源
	 * @param pageIndex 当前页数
	 * @param pageSize 每页条数
	 * @param param 请求参数
	 * @return
	 */
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

	/**
	 * 获取指定类型分页数据
	 * @Title: getPageByParam
	 * @Description: 获取指定类型分页数据
	 * @author tianzy
	 * @date 2017年7月4日上午10:32:22
	 *
	 * @param sql sql语句
	 * @param jdbc 数据源
	 * @param pageIndex 当前页数
	 * @param pageSize 每页条数
	 * @param t 返回类型
	 * @param param 请求参数
	 * @return
	 */
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

	/**
	 * 获取分页sql
	 * @Title: getSql
	 * @Description: 获取分页sql
	 * @author tianzy
	 * @date 2017年7月4日上午10:33:35
	 *
	 * @param sql sql语句
	 * @param pageIndex 当前页数
	 * @param pageSize 每页条数
	 * @return
	 */
	private String getSql(String sql, int pageIndex, int pageSize) {
		int mysqlIndex = pageIndex - 1;
		sql = StringUtils.replaceOnceIgnoreCase(sql, "select", "select sql_calc_found_rows ");
		sql += " limit " + mysqlIndex * pageSize + "," + pageSize;
		return sql;
	}

	/**
	 * 获取未分页总条数
	 * @Title: getTotalRow
	 * @Description: 获取未分页总条数
	 * @author tianzy
	 * @date 2017年7月4日上午10:34:19
	 *
	 * @param jdbc 数据源
	 * @return
	 */
	private long getTotalRow(NamedParameterJdbcDaoSupport jdbc) {
		String totalsql = "select found_rows()";
		Long num = jdbc.getJdbcTemplate().queryForObject(totalsql, Long.class);
		return num;
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
