package com.taxholic.web.admin.board.controller;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taxholic.web.admin.board.dto.Board;
import com.taxholic.web.admin.board.service.BoaardService;

@Controller
@RequestMapping("/pageView/")
public class treeController{
	
	
	@Autowired
	BoaardService boardService;
	
	@RequestMapping(value = "/tree.do")
	public String list() {
		return "manager:jstree/tree";
	} 
	
	@RequestMapping(value = "/treet.do")
	public String listt() {
		return "manager:jstree/index";
	} 
	
	@RequestMapping(value = "/listJson.do", method = RequestMethod.POST)
	public String getJson(Board board, Model model) {
		model.addAttribute("dataList",boardService.getList());
	    return "jsonView";
	}
	
	
	
	/*@RequestMapping(value = "/treeControl.do", method = RequestMethod.POST)
	public void treeControl(HttpSession session, HttpServletRequest request, Model model) {
		
		System.out.println(request);
	}*/
	
	
	/*
	 * @SuppressWarnings("unchecked")
	@RequestMapping(value="/pageView/**",method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView pageView(HttpSession session, HttpServletRequest request, ModelMap modelMap) {
		String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		ModelAndView mav = WebUtil.getModelAndView(restOfTheUrl);
	
		Enumeration<String> names = request.getParameterNames();
		
		String name = "";
		// 이전 페이지에서 던진 파라미터 넣기
		while(names.hasMoreElements()) {
			name = names.nextElement();
			mav.addObject(name, request.getParameter(name));
		}
			
		return mav;
	}
	 */
	
}