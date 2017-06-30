package com.tool;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

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
	 * 
	 * @Title: main
	 * @Description: TODO
	 * @author tianzy
	 * @date 2017年6月30日下午5:11:13
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		new SystemConfig();
		System.out.println(getProperty("server.port"));
	}
}
