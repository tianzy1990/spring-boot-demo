package test;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.base.AppStart;
import com.dao.XDao;

//这是JUnit的注解，通过这个注解让SpringJUnit4ClassRunner这个类提供Spring测试上下文。  
@RunWith(SpringJUnit4ClassRunner.class)  
//这是Spring Boot注解，为了进行集成测试，需要通过这个注解加载和配置Spring应用上下  
@SpringBootTest(classes = AppStart.class) 
//web应用测试注解
@WebAppConfiguration  
public class AppTest {
	@Autowired 
	XDao dao;
	@Test
	public void selectById() {
		Map<String, Object> selectById = dao.selectById("select * from city where id=?", 1l, dao.getDataSource1());
		System.out.println(selectById);
	}
}
