package com.taxholic.web.admin.board.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taxholic.core.web.dao.CommonDao;
import com.taxholic.web.admin.board.dto.Board;


@Service
public class TreetService{
	
	@Autowired
	private CommonDao dao;
	
	
	public Object getList(){
		
		Object list = treeData((List<Map<String, Object>>) this.dao.getList("tree.getGroup"));
		return list;
		
	}
	
	private Object treeData(List<Map<String, Object>> list){
		
		List<Object> returnList = new ArrayList<Object>();
		
		List<Object> tmpList = new ArrayList<Object>();
		
		for(int i=0;i<list.size();i++){
			tmpList = new ArrayList<Object>();
			Map<String,Object> treeMap = new HashMap<String, Object>();
			treeMap.put("id","tree_"+list.get(i).get("SEQ"));
			treeMap.put("text",list.get(i).get("GROUP_NAME"));
			
			if(i !=0 && (int)list.get(i-1).get("PARENT_SEQ") == (int)list.get(i).get("SEQ")){
				treeMap.put("children",returnList);
				tmpList.add(treeMap);
				returnList = tmpList;
			}else if(i !=0 && (int)list.get(i-1).get("PARENT_SEQ") != (int)list.get(i).get("SEQ")){
				//treeMap.put("children",returnList);
				returnList.add(treeMap);
			}
		}
		
		return returnList;
	}
	
	/*private Object treeMakeChildrenData(){
		
	}*/
}
