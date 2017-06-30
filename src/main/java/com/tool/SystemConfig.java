package com.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class SystemConfig {

	private static Properties props;

	/**
	 * 
	 * @Title: SystemConfig
	 * @Description: TODO
	 * @param 
	 * @author tianzy
	 * @date 2017年6月30日下午5:09:43
	 */
	public SystemConfig() {

		try {
			ClassPathResource resource = new ClassPathResource("application.properties");
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取属性
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {

		return props == null ? null : props.getProperty(key);

	}

	/**
	 * 获取属性
	 * 
	 * @param key
	 *            属性key
	 * @param defaultValue
	 *            属性value
	 * @return
	 */
	public static String getProperty(String key, String defaultValue) {

		return props == null ? null : props.getProperty(key, defaultValue);

	}

	/**
	 * 获取properyies属性
	 * 
	 * @return
	 */
	public static Properties getProperties() {
		return props;
	}
	
	/**
	 * 获取任意properyies属性
	 * 
	 * @return
	 * @throws IOException 
	 */
	public static String getAnyPropertie(String filePath, String key) throws IOException {
		InputStream inputstream = SystemConfig.class.getResourceAsStream("/application.properties");
		Properties properties = new Properties();
		properties.load(inputstream);
		return properties.getProperty(key);
	}

	/**
	 * 
	 * @Title: main
	 * @Description: TODO
	 * @author tianzy
	 * @date 2017年6月30日下午5:11:13
	 *
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws Exception {
		props=new Properties();
		File file = ResourceUtils.getFile("classpath:application.properties");
		InputStream filea = SystemConfig.class.getResourceAsStream("/application.properties");
		System.out.println(file.getName());
		props.load(filea);
		System.out.println(getProperty("server.port"));
	}
}
