package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.base.AppStart;
import com.dao.XDao;
import com.tool.Page;

//这是JUnit的注解，通过这个注解让SpringJUnit4ClassRunner这个类提供Spring测试上下文。  
@RunWith(SpringJUnit4ClassRunner.class)
// 这是Spring Boot注解，为了进行集成测试，需要通过这个注解加载和配置Spring应用上下
@SpringBootTest(classes = AppStart.class)
// web应用测试注解
@WebAppConfiguration
public class AppTest {
	@Autowired
	XDao dao;

	@Test
	public void selectById() {
		City city = new City();
		Map<String, Object> selectById = dao.selectById("select id,name,countrycode,district,population from city where id=?", 1l, dao.getDataSource1());
		System.out.println(selectById);
		city = dao.selectById("select id,name,countrycode,district,population from city where id=?", 1l, city, dao.getDataSource1());
		System.out.println(city.getName());
	}

	@Test
	public void selectByMap() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", 1);
		Map<String, Object> selectByMap = dao.selectByMap("select id,name,countrycode,district,population from city where id=:id", map, dao.getDataSource1());
		System.out.println("without T type: " + selectByMap);
		City city = new City();
		city = dao.selectByMap("select id,name,countrycode,district,population from city where id=:id", map, city, dao.getDataSource1());
		System.out.println("with T type: " + city.getName());
	}

	@Test
	public void selectByEntity() throws Exception {
		City city = new City();
		city.setId(1l);
		city = dao.selectByEntity("select id,name,countrycode,district,population from city where id=:id", city, dao.getDataSource1());
		System.out.println("without T type: " + city.getName());
		city = dao.selectByEntity("select id,name,countrycode,district,population from city where id=:id", city, city, dao.getDataSource1());
		System.out.println("with T type: " + city.getName());
	}

	@Test
	public void selectByParam() throws Exception {
		Map<String, Object> selectByParam = dao.selectByParam("select id,name,countrycode,district,population from city where id=? and countrycode=?", dao.getDataSource1(), 1, "AFG");
		System.out.println("without T type: " + selectByParam);
		City city = new City();
		city = dao.selectByParam("select id,name,countrycode,district,population from city where id=? and countrycode=?", city, dao.getDataSource1(), 1, "AFG");
		System.out.println("with T type: " + city.getName());
	}
	
	@Test
	public void pageByEntity() throws Exception {
		City city = new City();
		city.setCountrycode("AFG");
		Page<City> pageByEntity = dao.pageByEntity("select id,name,countrycode,district,population from city where countrycode=:countrycode", city, dao.getDataSource1(), 1, 5);
		System.out.println("without T type: " + pageByEntity.toString());
		city.setCountrycode("AUS");
		pageByEntity = dao.pageByEntity("select id,name,countrycode,district,population from city where countrycode=:countrycode", city, city, dao.getDataSource1(), 1, 5);
		System.out.println("with T type: " + pageByEntity.toString());
	}
	
	@Test
	public void pageByMap() throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("countrycode", "AFG");
		Page<Map<String, Object>> pageByMap = dao.pageByMap("select id,name,countrycode,district,population from city where countrycode=:countrycode", map, dao.getDataSource1(), 1, 5);
		System.out.println("without T type: " + pageByMap.toString());
		map.put("countrycode", "AUS");
		Page<City> pageByMap2 = dao.pageByMap("select id,name,countrycode,district,population from city where countrycode=:countrycode", map, new City(), dao.getDataSource1(), 1, 5);
		System.out.println("with T type: " + pageByMap2.toString());
	}
	@Test
	public void pageByParam() throws Exception {
		Page<Map<String, Object>> pageByMap = dao.pageByParam("select id,name,countrycode,district,population from city where countrycode=?", dao.getDataSource1(), 1, 5, "AFG");
		System.out.println("without T type: " + pageByMap.toString());
		Page<City> pageByMap2 = dao.pageByParam("select id,name,countrycode,district,population from city where countrycode=?", new City(), dao.getDataSource1(), 1, 5, "AUS");
		System.out.println("with T type: " + pageByMap2.toString());
	}
}
