package com.taxholic.web.admin.tree.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taxholic.core.web.dao.CommonDao;
	

@Service
public class TreetService{
	
	@Autowired
	private CommonDao dao;
	
	private static final Logger logger = LoggerFactory.getLogger(TreetService.class);
	//private static Logger logger;// Logger.getLogger(TreetService.class);
	
	
	public Map<String, Integer> dataMap = new HashMap<String, Integer>();
	
	public Map<String, Object> seqMap = new HashMap<String, Object>();
	
	public TreetService(){
		dataMap.put("create", 0);
		dataMap.put("remove", 1);
		dataMap.put("renameC", 2);
		dataMap.put("move", 3);
		dataMap.put("urlChange", 4);
		dataMap.put("copy", 5);
		dataMap.put("cut", 6);
		
		logger.debug("TreeService");
	}	
	
	public Object getList(HttpServletRequest request){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		paramMap.put("parent_id", request.getParameter("node").toString().replace("tree_", ""));
		Object list = treeData((List<Map<String, Object>>) this.dao.getList("tree.getTree",paramMap), request);
		return list;
	}
	
	private Object treeData(List<Map<String, Object>> list, HttpServletRequest request){
		
		List<Object> returnList = new ArrayList<Object>();
		
		Cookie[] cookies = request.getCookies();
		
		String checkMode = "";
		
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("mode")) {
					checkMode = cookies[i].getValue();
					break;
				}
			}
		}
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> treeMap = new HashMap<String, Object>();
			treeMap.put("id","tree_"+list.get(i).get("SEQ"));
			
			if(checkMode.equals("admin")){
				treeMap.put("text",list.get(i).get("GROUP_NAME"));
				treeMap.put("href",list.get(i).get("URL"));
			}else{
				treeMap.put("text",list.get(i).get("GROUP_NAME")+"("+list.get(i).get("URL")+")");
				treeMap.put("href",list.get(i).get("URL"));
			}
			
			
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
	
	public Map<String, Object> treeControl(HttpServletRequest request, HttpServletResponse response){
		int status = dataMap.get(request.getParameter("type"));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		switch(status){
		case 0:
			int resultSeq = addTree(request);
			if(request.getParameter("data").equals("tree_0")){
				resultMap.put("root_make", true);
			}else{
				resultMap.put("root_make", false);
			}
			resultMap.put("parent_id", request.getParameter("data"));
			resultMap.put("seq", resultSeq);
			resultMap.put("type", "create");
			resultMap.put("result", "ok");
			break;
		case 1:
			removeTree(request);
			resultMap.put("type", "remove");
			resultMap.put("result", "ok");
			break;
		case 2:
			updateNameTree(request);
			resultMap.put("type", "updateName");
			resultMap.put("result", "ok");
			break;
		case 3:
			updatePositionTree(request);
			resultMap.put("type", "updatePosition");
			resultMap.put("result", "ok");
			break;
		case 4:
			updateUrlTree(request);
			resultMap.put("type", "urlChange");
			resultMap.put("result", "ok");
			break;
		case 5:
			updateCopyTree(request);
			resultMap.put("type", "copy");
			resultMap.put("result", "ok");
			break;
		case 6:
			boolean state = updateCutTree(request);
			if(state){
				resultMap.put("result", "ok");
			}else{
				resultMap.put("result", "nok");
			}
			resultMap.put("type", "cut");
			
			break;
		default:
			System.out.println("exception");
			resultMap.put("result", "nok");
			break;
		}
		
		return resultMap;
	}
	
	/**
	 * 트리 노드 추가
	 * @param request
	 * @return
	 */
	private int addTree(HttpServletRequest request){
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("parent_id", request.getParameter("data").toString().replace("tree_", ""));
		
		int t= this.dao.insert("tree.insertTreeNode", paramMap);
		return Integer.parseInt(paramMap.get("seq").toString());
		
	}
	
	/**
	 * 트리 노드 이름 수정
	 * @param request
	 * @return
	 */
	private boolean updateNameTree(HttpServletRequest request){
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("seq", request.getParameter("id").toString().replace("tree_", ""));
		paramMap.put("new_text", request.getParameter("new_text").toString());
		paramMap.put("old_text", request.getParameter("old_text").toString());
		
		this.dao.update("tree.updateTreeNodeText", paramMap);
		return true;
	}
	
	/**
	 * 트리 노드 삭제
	 * @param request
	 * @return
	 */
	private boolean removeTree(HttpServletRequest request){
		List<Object> removeKeys = new ArrayList<Object>();
		seqMap = new HashMap<String, Object>();
		
		
		String[] values = request.getParameterValues("data[]");
		
		for(int i=0;i<values.length;i++){
			seqMap.put(values[i].toString().replace("tree_", ""), values[i].toString().replace("tree_", ""));
			
			//삭제할 노드의 자식 노드들 검사
			childTreeNode(values[i].toString().replace("tree_", ""));
			//삭제할 노드를 파라미터 저장
			for( Object key : seqMap.keySet() ){
				removeKeys.add(seqMap.get(key));
	        }
			
			//paramMap.put
			this.dao.delete("tree.removeTreeNode", removeKeys);
		}
		
		return true;
	}
	
	private void childTreeNode(Object seq){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("seq", seq);
		List<Map<String, Object>> resultListMap = (List<Map<String, Object>>) this.dao.getList("tree.checkChildNode",paramMap);
		
		for(int i=0;i<resultListMap.size();i++){
			resultListMap.get(i).get("SEQ");
			
			seqMap.put(resultListMap.get(i).get("SEQ").toString(),resultListMap.get(i).get("SEQ"));
			
			childTreeNode(resultListMap.get(i).get("SEQ"));
		}
	}
	
	/**
	 * 트리 노드 이동
	 * @param request
	 * @return
	 */
	private boolean updatePositionTree(HttpServletRequest request){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		String parent_id =  request.getParameter("parent_id").toString().replace("tree_", "");
		String old_parent_id =  request.getParameter("old_parent").toString().replace("tree_", "");
		
		if(parent_id.equals("#")){
			parent_id = "0";
		}
		
		if(old_parent_id.equals("#")){
			old_parent_id = "0";
		}
		
		paramMap.put("seq", request.getParameter("id").toString().replace("tree_", ""));
		paramMap.put("new_parent", parent_id);
		paramMap.put("old_parent", old_parent_id);
		
		this.dao.update("tree.updateTreeNodePosition", paramMap);
		
		return true;
	}
	
	private boolean updateUrlTree(HttpServletRequest request){
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("seq", request.getParameter("data").toString().replace("tree_", ""));
		paramMap.put("url", request.getParameter("url").toString());
		
		this.dao.update("tree.updateTreeNodeUrl", paramMap);
		return true;
	}
	
	private boolean updateCopyTree(HttpServletRequest request){
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("copy_seq", request.getParameter("copy_seq").toString().replace("tree_", ""));
		paramMap.put("new_parent", request.getParameter("new_parent").toString().replace("tree_", ""));
		
		seqMap = new HashMap<String, Object>();
		childTreeNode(request.getParameter("copy_seq").toString().replace("tree_", ""));
	//	seqMap.remove("");
		this.dao.insert("tree.copyNode", paramMap);
		
		
		updateCopyChildTree(paramMap.get("seq"),request.getParameter("copy_seq").toString().replace("tree_", ""));
		return true;
	}
	
	private void updateCopyChildTree(Object new_parent,Object copy_seq){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		//paramMap.put("new_parent",new_parent);
		paramMap.put("seq",copy_seq);
		List<Map<String, Object>> resultListMap = (List<Map<String, Object>>) this.dao.getList("tree.checkChildrenNode",paramMap);
		//System.out.println(resultListMap);
		
		for(int i=0;i<resultListMap.size();i++){
			resultListMap.get(i).get("seq");
			paramMap.put("copy_seq", resultListMap.get(i).get("SEQ"));
			paramMap.put("new_parent",  new_parent);
			
			this.dao.insert("tree.copyNode", paramMap);
			
			updateCopyChildTree(paramMap.get("seq"),resultListMap.get(i).get("SEQ"));
		}
	}
	
	private boolean updateCutTree(HttpServletRequest request){
		seqMap = new HashMap<String, Object>();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("seq", request.getParameter("old_parent").toString().replace("tree_", ""));
		paramMap.put("new_parent", request.getParameter("parent_id").toString().replace("tree_", ""));
		childTreeNode(request.getParameter("old_parent").toString().replace("tree_", ""));
		
		String checkKey = request.getParameter("parent_id").toString().replace("tree_", "").toString();
		
		Object check = seqMap.get(checkKey);
		if(check == null || check  == ""){
			this.dao.update("tree.updateTreeNodePosition", paramMap);
			return true;
		}else{
			return false;
		}
	}
	

}
