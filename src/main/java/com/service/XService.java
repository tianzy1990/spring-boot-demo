package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
