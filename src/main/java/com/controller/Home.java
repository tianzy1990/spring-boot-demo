package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.BaseController;
import com.service.XService;

@Controller
@RequestMapping("/home")
public class Home extends BaseController {
	@Autowired
	XService service;

	@RequestMapping("/index/{name}")
	public String tohome(Model model, @PathVariable("name") String name) {
		model.addAttribute("name", name);
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