package com.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.BaseController;
import com.base.Login;
import com.feilong.core.date.DateUtil;

@Controller
public class SampleController extends BaseController {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	@RequestMapping("/hello/{myName}")
	@ResponseBody
	@Login
	String homemyName(@PathVariable String myName) {
		return "Hello " + myName + "! 当前时间：" + DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
}