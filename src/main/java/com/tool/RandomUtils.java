package com.tool;

import java.util.Random;

/**
 * 随机数工具类
 * 
 * @Title: RandomUtils.java
 * @Package mall.common.utils
 * @Description: 随机数工具类
 * @author tianzy
 * @date 2017年7月17日上午9:56:22
 */
public class RandomUtils {
	public static String getRandom(int length) {
		StringBuilder sb = new StringBuilder();
		String[] array = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(array[random.nextInt(10)]);
		}
		return sb.toString();
	}
	
	public static Float getRandom(float min, float max) {
		return new Random().nextFloat()*(max-min)+min;
	}
}
