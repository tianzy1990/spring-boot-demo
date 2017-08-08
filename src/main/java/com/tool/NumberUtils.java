package com.tool;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数字工具类
 * 
 * @Title: NumberUtils.java
 * @Package mall.common.utils
 * @Description: 数字工具类
 * @author tianzy
 * @date 2017年7月19日上午10:46:18
 */
public class NumberUtils {
	/**
	 * 保留2为小数 四舍五入
	 * @Title: keeptwo
	 * @Description: 保留2为小数 四舍五入
	 * @author tianzy
	 * @date 2017年7月19日上午10:58:59
	 *
	 * @param value
	 * @return
	 */
	public static float keeptwo(float value) {
		BigDecimal bg = new BigDecimal(value).setScale(2, RoundingMode.UP);
		return bg.floatValue();
	}
}
