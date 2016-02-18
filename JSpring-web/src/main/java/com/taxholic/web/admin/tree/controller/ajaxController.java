package com.taxholic.web.admin.tree.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taxholic.web.admin.tree.service.TreetService;

@Controller
@RequestMapping("/ajax/")
public class ajaxController{
	
	
	@Autowired
	TreetService treeService;
	
	@RequestMapping(value = "/listJson.do", method = RequestMethod.GET)
	@ResponseBody
	public Object getJson(HttpServletRequest request,Model model) {
		List<Map<String, Object>> resultMap = (List<Map<String, Object>>) treeService.getList(request);
	    
		return resultMap;
	}
	
	@RequestMapping(value = "/treeControl.do",method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Object treeControl(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		treeService.treeControl(request,response);
		resultMap.put("code", "ok");
		return resultMap;
	}
}