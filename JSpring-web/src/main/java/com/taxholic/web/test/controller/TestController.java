package com.taxholic.web.test.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taxholic.core.authority.AuthDto;
import com.taxholic.web.test.dto.EncryptDto;
import com.taxholic.web.test.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController{
	
	@Autowired
	private MessageSourceAccessor message;
	
	@Autowired
	private TestService testService;
	
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	@ResponseBody
	public String login() {
		 return "";
	} 
	
	@RequestMapping(value = "/getMsg.do", method = RequestMethod.GET)
	@ResponseBody
	public String getMsg() {
		return  message.getMessage("stockChart.filePath");
		
	} 
	
	@RequestMapping(value = "/getId.do", method = RequestMethod.GET)
	@ResponseBody
	public String getId() {
		return  testService.getId();
	} 
	
	@RequestMapping(value = "/insertRole.do", method = RequestMethod.GET)
	@ResponseBody
	public String insertRole() {
		EncryptDto dto = new EncryptDto();
		dto.setUserId("user2");
		dto.setPasswd("passwd");		
		 testService.insertUser(dto);
		 return "";
	} 

	@RequestMapping(value = "/getJson.do", method = RequestMethod.GET)
	public String getJson(AuthDto authDto, Model model) {
		
		model.addAttribute("auth",new AuthDto());
	 
	    return "jsonView";
		
	}
		
		
}