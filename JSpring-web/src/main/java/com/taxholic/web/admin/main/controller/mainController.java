package com.taxholic.web.admin.main.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class mainController{
	
	
	@RequestMapping(value = "main.do")
	public String main() {
		return "manager:admin/main";
	} 
	
	
	
}