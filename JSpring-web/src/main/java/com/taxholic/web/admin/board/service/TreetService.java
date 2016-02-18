package com.taxholic.web.admin.board.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taxholic.core.web.dao.CommonDao;
import com.taxholic.web.admin.board.dto.Board;


@Service
public class TreetService{
	
	@Autowired
	private CommonDao dao;
	
	public Map<String, Integer> dataMap = new HashMap<String, Integer>();
	
	public TreetService(){
		dataMap.put("create", 0);
		dataMap.put("remove", 1);
		dataMap.put("renameC", 2);
		dataMap.put("move", 3);
	}	
	
	public Object getList(HttpServletRequest request){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		paramMap.put("parent_id", request.getParameter("node").toString().replace("tree_", ""));
		Object list = treeData((List<Map<String, Object>>) this.dao.getList("tree.getTree",paramMap));
		return list;
		
	}
	
	private Object treeData(List<Map<String, Object>> list){
		
		List<Object> returnList = new ArrayList<Object>();
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> treeMap = new HashMap<String, Object>();
			treeMap.put("id","tree_"+list.get(i).get("SEQ"));
			treeMap.put("text",list.get(i).get("GROUP_NAME"));
			
			treeMap.put("children",checkChildren((int) list.get(i).get("SEQ")));
			/*if(i !=0 && (int)list.get(i-1).get("PARENT_SEQ") == (int)list.get(i).get("SEQ")){
				treeMap.put("children",returnList);
				tmpList.add(treeMap);
				returnList = tmpList;
			}else if(i !=0 && (int)list.get(i-1).get("PARENT_SEQ") != (int)list.get(i).get("SEQ")){
				//treeMap.put("children",returnList);
				returnList.add(treeMap);
			}*/
			returnList.add(treeMap);
		}
		
		return returnList;
	}
	
	private boolean checkChildren(int parentId){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("seq", parentId);
		
		Map<String,Object> resultMap = (Map<String, Object>) this.dao.getObject("tree.checkChildNode",paramMap);
		int cnt = Integer.parseInt(resultMap.get("CNT").toString());
		
		if(cnt == 0){
			return false;
		}else{
			return true;
		}
		//(List<Map<String, Object>>) this.dao.getList("tree.getTree")
	}
	
	public void treeControl(HttpServletRequest request, HttpServletResponse response){
		int status = dataMap.get(request.getParameter("type"));
		
		switch(status){
		case 0:
			addTree(request);
			break;
		case 1:
			System.out.println("remove");
			break;
		case 2:
			System.out.println("rename");
			break;
		case 3:
			System.out.println("move");
			break;
		default:
			System.out.println("exception");
			break;
		}
	}
	
	private boolean addTree(HttpServletRequest request){
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("parent_id", request.getParameter("data").toString().replace("tree_", ""));
		
		this.dao.insert("tree.insertTreeNode", paramMap);
		return true;
	}
	
	private boolean updateNameTree(HttpServletRequest request){
			
		return true;
	}
	
	private boolean removeTree(){
		
		return true;
	}
	
	private boolean updatePositionTree(){
		
		return true;
	}

}
