package com.taxholic.web.admin.tree.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taxholic.core.web.dao.CommonDao;


@Service
public class TreetService{
	
	@Autowired
	private CommonDao dao;
	
	public Map<String, Integer> dataMap = new HashMap<String, Integer>();
	
	public Map<Object,Object> seqMap = new HashMap<Object, Object>();
	
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
			returnList.add(treeMap);
		}
		
		return returnList;
	}
	
	private boolean checkChildren(int parentId){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("seq", parentId);
		
		Map<String,Object> resultMap = (Map<String, Object>) this.dao.getObject("tree.checkChildNodeCnt",paramMap);
		int cnt = Integer.parseInt(resultMap.get("CNT").toString());
		
		if(cnt == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public void treeControl(HttpServletRequest request, HttpServletResponse response){
		int status = dataMap.get(request.getParameter("type"));
		
		switch(status){
		case 0:
			addTree(request);
			break;
		case 1:
			removeTree(request);
			break;
		case 2:
			updateNameTree(request);
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
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("seq", request.getParameter("id").toString().replace("tree_", ""));
		paramMap.put("parent_id", request.getParameter("parent_id").toString().replace("tree_", ""));
		paramMap.put("new_text", request.getParameter("new_text").toString());
		paramMap.put("old_text", request.getParameter("old_text").toString());
		
		this.dao.update("tree.updateTreeNodeText", paramMap);
		return true;
	}
	
	private boolean removeTree(HttpServletRequest request){
		List<Object> removeKeys = new ArrayList<Object>();
		seqMap = new HashMap<Object, Object>();
		
		seqMap.put(request.getParameter("data").toString().replace("tree_", ""), request.getParameter("data").toString().replace("tree_", ""));
		
		//삭제할 노드의 자식 노드들 검사
		childTreeNode( request.getParameter("data").toString().replace("tree_", ""));
		//삭제할 노드를 파라미터 저장
		for( Object key : seqMap.keySet() ){
			removeKeys.add(seqMap.get(key));
        }
		
		//paramMap.put
		this.dao.delete("tree.removeTreeNode", removeKeys);
		
		return true;
	}
	
	private void childTreeNode(Object seq){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("seq", seq);
		List<Map<String, Object>> resultListMap = (List<Map<String, Object>>) this.dao.getList("tree.checkChildNode",paramMap);
		
		for(int i=0;i<resultListMap.size();i++){
			resultListMap.get(i).get("SEQ");
			
			seqMap.put(resultListMap.get(i).get("SEQ"),resultListMap.get(i).get("SEQ"));
			
			childTreeNode(resultListMap.get(i).get("SEQ"));
		}
	}
	
	private boolean updatePositionTree(){
		
		return true;
	}

}
