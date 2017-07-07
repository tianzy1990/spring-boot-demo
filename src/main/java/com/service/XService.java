package com.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.datasource.tx;
import com.dao.XDao;

@Service
public class XService {
	
	@Autowired
	XDao dao;

	@Transactional(value="txManager1")//多个事务管理器时 需要指明使用哪个事务管理器
	public void testtransactional() {
		dao.testtransactional("INSERT test(name,value) VALUES (1,1)");
		dao.testtransactional("INSERT test(name,value) VALUES (2,2)");
	}

	public void testdao() {
		dao.testdao();
	}
	
	@tx
	@Transactional
	public void testaop(String a, String b,String c) {
		Map<String, Object> map=new HashMap<>();
		map.put("name", 111);
		map.put("v", 111);
		dao.testaopinsert("INSERT test(name,value) VALUES (:name,:v)", map, "id");
		int adddd=1/0;
		map.put("name", 222);
		map.put("v", 222);
		dao.testaopinsert("INSERT test(name,value) VALUES (:name,:v)", map, "id");
	}
}
