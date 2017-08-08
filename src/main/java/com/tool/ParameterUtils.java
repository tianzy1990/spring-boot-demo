package com.tool;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 参数验证工具类
 * 
 * @Title: ParameterUtils.java
 * @Package mall.common.utils
 * @Description: 参数验证工具类
 * @author tianzy
 * @date 2017年7月14日下午4:34:43
 */
public class ParameterUtils {
	public static String isNull(String value) throws CustomException {
		if (StringUtils.isEmpty(value)) {
			throw new CustomException("String类型参数为空");
		}
		return value;
	}

	public static Long isNull(Long value) throws CustomException {
		if (value == null || value <= 0) {
			throw new CustomException("Long类型参数为空");
		}
		return value;
	}

	public static Integer isNull(Integer value) throws CustomException {
		if (value == null || value <= 0) {
			throw new CustomException("Integer类型参数为空");
		}
		return value;
	}

	public static Float isNull(Float value) throws CustomException {
		if (value == null || value <= 0) {
			throw new CustomException("Float类型参数为空");
		}
		return value;
	}

	public static <T> List<T> isNull(List<T> value) throws CustomException {
		if (CollectionUtils.isEmpty(value)) {
			throw new CustomException("List类型参数为空");
		}
		return value;
	}
}
