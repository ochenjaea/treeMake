package com.taxholic.web.admin.board.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taxholic.web.admin.board.dto.Board;
import com.taxholic.web.admin.board.service.BoaardService;
import com.taxholic.web.admin.board.service.TreetService;

@Controller
@RequestMapping("/ajax/")
public class ajaxController{
	
	
	@Autowired
	TreetService treeService;
	
	@RequestMapping(value = "/listJson.do", method = RequestMethod.GET)
	@ResponseBody
	public Object getJson(HttpServletRequest request,Model model) {
		//model.addAttribute(treeService.getList());
	    //return "jsonView";
		//return treeService.getList();
		List<Map<String, Object>> resultMap = (List<Map<String, Object>>) treeService.getList(request);
	    
		return resultMap;
	}
	
	@RequestMapping(value = "/treeControl.do",method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Object treeControl(HttpServletRequest request, HttpServletResponse response, Model model) {
		//model.addAttribute(treeService.getList());
	    //return "jsonView";
		//return treeService.getList();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		
		treeService.treeControl(request,response);
		resultMap.put("code", "ok");
		return resultMap;
	}
	/*

	@RequestMapping(value = "/treeControl.do",method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Object treeControl(HttpServletRequest request, HttpServletResponse response, @PathVariable String serviceName, HttpSession session, Model model) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		System.out.println(request);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("hi", "hello");
	    
		return resultMap;
	}
	*/
	/*
	@RequestMapping(value="/listJson.do",method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Object webRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String serviceName, HttpSession session) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		List<Map<String, Object>> resultMap = treeService.getList();
	    
		return resultMap;
	}
	*/
	
}