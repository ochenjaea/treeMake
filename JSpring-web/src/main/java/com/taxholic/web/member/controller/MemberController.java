package com.taxholic.web.member.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MemberController{
	
	@RequestMapping(value = "login.do", method = RequestMethod.GET)
	public String login() {
		 return "none:admin/login";
	} 
}