package com.taxholic.web.admin.tree.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}