package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.spring.stat.SpringStatUtils;
import com.base.BaseController;
import com.service.XService;

@Controller
@RequestMapping("/home")
public class Home extends BaseController {
	@Autowired
	XService service;
	
	@Autowired
    private Environment env;//获取application.properties


	@RequestMapping("/index/{name}")
	public String tohome(Model model, @PathVariable("name") String name) {
		model.addAttribute("name", name);
		System.out.println(env.getProperty("spring.main.show-banner"));
		return "home";
	}

	@RequestMapping("/testt")
	@ResponseBody
	public String testtransactional() {
		service.testtransactional();
		return "ok";
	}

	@RequestMapping("/testdao")
	@ResponseBody
	public String testdao() {
		service.testdao();
		return "ok";
	}
}