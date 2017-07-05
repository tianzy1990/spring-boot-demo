package test;

import java.util.HashMap;
import java.util.List;
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

	@Test
	public void getListByParam() throws Exception {
		List<Map<String, Object>> pageByMap = dao.getListByParam("select id,name,countrycode,district,population from city where countrycode=?", dao.getDataSource1(), new Object[] { "AFG" });
		System.out.println("without T type: " + pageByMap.toString());
		List<City> pageByMap2 = dao.getListByParam("select id,name,countrycode,district,population from city where countrycode=?", dao.getDataSource1(), new City(), new Object[] { "AUS" });
		System.out.println("with T type: " + pageByMap2.toString());
	}

	@Test
	public void getListByMap() throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("countrycode", "AFG");
		List<Map<String, Object>> pageByMap = dao.getListByMap("select id,name,countrycode,district,population from city where countrycode=:countrycode", map, dao.getDataSource1());
		System.out.println("without T type: " + pageByMap.toString());
		map.put("countrycode", "AUS");
		List<City> pageByMap2 = dao.getListByMap("select id,name,countrycode,district,population from city where countrycode=:countrycode", map, dao.getDataSource1(), new City());
		System.out.println("with T type: " + pageByMap2.toString());
	}

	@Test
	public void getListByEntity() throws Exception {
		City city = new City();
		city.setCountrycode("AFG");
		List<Map<String, Object>> pageByMap = dao.getListByEntity("select id,name,countrycode,district,population from city where countrycode=:countrycode", city, dao.getDataSource1());
		System.out.println("without T type: " + pageByMap.toString());
		city.setCountrycode("AUS");
		List<City> pageByMap2 = dao.getListByEntity("select id,name,countrycode,district,population from city where countrycode=:countrycode", city, dao.getDataSource1(), new City());
		System.out.println("with T type: " + pageByMap2.toString());
	}
	
	@Test
	public void insert() throws Exception {
		test.Test test = new test.Test();
		test.setName("insert");
		test.setValue("1");
		long pageByMap = dao.insertByEntity("insert test(name,value) values(:name,:value)", dao.getDataSource1(), test, "id");
		System.out.println("insertByEntity ID: " + pageByMap);
		HashMap<String, Object> map = new HashMap<>();
		map.put("name", "insert");
		map.put("value", 2);
		long pageByMap2 = dao.insertByMap("insert test(name,value) values(:name,:value)", dao.getDataSource1(), map, "id");
		System.out.println("insertByMap ID: " + pageByMap2);
	}
	
	@Test
	public void update() throws Exception {
		test.Test test = new test.Test();
		test.setName("update");
		test.setId(34l);
		int updateByEntity = dao.updateByEntity("update test set name=:name  where id=:id", test, dao.getDataSource1());
		System.out.println("updateByEntity result: " + updateByEntity);
		HashMap<String, Object> map = new HashMap<>();
		map.put("name", "update");
		map.put("id", 33);
		int updateByMap = dao.updateByMap("update test set name=:name  where id=:id", map, dao.getDataSource1());
		System.out.println("updateByMap result: " + updateByMap);
		int updateByParam = dao.updateByParam("update test set name=?  where id=?", dao.getDataSource1(), "update",32);
		System.out.println("updateByParam result: " + updateByParam);
	}
	
	@Test
	public void delete() throws Exception {
		int deleteById = dao.deleteById("delete from test where id=?", 29, dao.getDataSource1());
		System.out.println("deleteById result: " + deleteById);
		int deleteByParam = dao.deleteByParam("delete from test where id=?", dao.getDataSource1() ,30);
		System.out.println("deleteByParam result: " + deleteByParam);
		HashMap<String, Object> map = new HashMap<>();
		map.put("id", 31);
		int deleteByMap = dao.deleteByMap("delete from test where id=:id", map, dao.getDataSource1());
		System.out.println("deleteByMap result: " + deleteByMap);
		test.Test test = new test.Test();
		test.setId(29l);
		int deleteByEntity = dao.deleteByEntity("delete from test where id=:id", test, dao.getDataSource1());
		System.out.println("deleteByEntity result: " + deleteByEntity);
	}
}
