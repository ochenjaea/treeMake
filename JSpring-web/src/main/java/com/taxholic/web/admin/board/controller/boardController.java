package com.taxholic.web.admin.board.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taxholic.web.admin.board.dto.Board;
import com.taxholic.web.admin.board.service.BoaardService;

@Controller
@RequestMapping("/admin/board")
public class boardController{
	
	
	@Autowired
	BoaardService boardService;
	
	@RequestMapping(value = "/list.do")
	public String list() {
		return "manager:admin/board/boardList";
	} 
	
	@RequestMapping(value = "/listJson.do", method = RequestMethod.POST)
	public String getJson(Board board, Model model) {
		model.addAttribute("dataList",boardService.getList());
	    return "jsonView";
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String , String> delete(Board board, Model model) {
		
		Map<String, String> jsonObject = new HashMap<String, String>();
	    jsonObject.put("result","success");
	    
	    return jsonObject; 
	    
	}
	
	
}