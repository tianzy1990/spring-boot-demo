package com.tool;

import java.io.Serializable;

/**
 * 自定义异常
 * @Title: CustomException.java
 * @Package mall.common.utils
 * @Description: 自定义异常
 * @author tianzy
 * @date 2017年7月14日下午3:04:49
 */
public class CustomException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	private int errorCode;

	public int getErrorCode() {
		return errorCode;
	}

	public CustomException() {
		super();
	}

	public CustomException(String msg) {
		super(msg);
	}

	public CustomException(int errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}

	public CustomException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CustomException(Throwable cause) {
		super(cause);
	}
}
