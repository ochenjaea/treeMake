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
import com.taxholic.web.admin.tree.dto.Tree;
	

@Service
public class TreetServiceD{
	
	@Autowired
	private CommonDao dao;
	
	@Autowired
	private Tree treeDto;
	
	private static final Logger logger = LoggerFactory.getLogger(TreetService.class);
	//private static Logger logger;// Logger.getLogger(TreetService.class);
	
	
	public Map<String, Integer> dataMap = new HashMap<String, Integer>();
	
	public Map<String, Object> seqMap = new HashMap<String, Object>();
	
	public TreetServiceD(){
		dataMap.put("create", 0);
		dataMap.put("remove", 1);
		dataMap.put("renameC", 2);
		dataMap.put("move", 3);
		dataMap.put("urlChange", 4);
		dataMap.put("copy", 5);
		dataMap.put("cut", 6);
		
		logger.debug("TreeService");
	}	
	
	//ss
	public Object getList(HttpServletRequest request){
		//Map<String,Object> paramMap = new HashMap<String,Object>();
		
		//paramMap.put("parent_id", request.getParameter("node").toString().replace("tree_", ""));
		treeDto.setParentSeq(Integer.parseInt(request.getParameter("node").toString().replace("tree_", "")));
		List<Tree> getTreelist = this.dao.getList("tree2.getTree",treeDto);
		
		Object list = treeData(getTreelist, request);
		return list;
	}
	
	//ss
	private Object treeData(List<Tree> list, HttpServletRequest request){
		
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
			treeMap.put("id","tree_"+list.get(i).getSeq());
			
			if(checkMode.equals("admin")){
				treeMap.put("text",list.get(i).getGroupName());
				treeMap.put("href",list.get(i).getUrl());
			}else{
				treeMap.put("text",list.get(i).getGroupName()+"("+list.get(i).getUrl()+")");
				treeMap.put("href",list.get(i).getUrl());
			}
			
			
			treeMap.put("children",checkChildren(list.get(i).getSeq()));
			returnList.add(treeMap);
		}
		
		return returnList;
	}
	
	//ss
	private boolean checkChildren(int parentId){
		//Map<String,Object> paramMap = new HashMap<String,Object>();
	//	paramMap.put("seq", parentId);
		treeDto.setParentSeq(parentId);
		int cnt  = (int) this.dao.getObject("tree2.checkChildNodeCnt",treeDto);
	/*	int cnt = (Tree)resultMap.getCnt();
		*/
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
	//ss
	private int addTree(HttpServletRequest request){
		
		//Map<String,Object> paramMap = new HashMap<String,Object>();
		//paramMap.put("parent_id", request.getParameter("data").toString().replace("tree_", ""));
		
		treeDto.setParentSeq(Integer.parseInt(request.getParameter("data").toString().replace("tree_", "")));
		
		int t= this.dao.insert("tree2.insertTreeNode", treeDto);
		return treeDto.getSeq();
		
	}
	
	/**
	 * 트리 노드 이름 수정
	 * @param request
	 * @return
	 */
	//ss
	private boolean updateNameTree(HttpServletRequest request){
		
		/*Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("seq", request.getParameter("id").toString().replace("tree_", ""));
		paramMap.put("new_text", request.getParameter("new_text").toString());
		paramMap.put("old_text", request.getParameter("old_text").toString());
		*/
		treeDto.setSeq(Integer.parseInt(request.getParameter("id").toString().replace("tree_", "")));
		treeDto.setGroupName(request.getParameter("old_text").toString());
		treeDto.setUpdateName(request.getParameter("new_text").toString());
		
		this.dao.update("tree2.updateTreeNodeText", treeDto);
		return true;
	}
	
	/**
	 * 트리 노드 삭제
	 * @param request
	 * @return
	 */
	//ss
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
			this.dao.delete("tree2.removeTreeNode", removeKeys);
		}
		
		return true;
	}
	
	//ss
	private void childTreeNode(Object seq){
		///Map<String,Object> paramMap = new HashMap<String,Object>();
		//paramMap.put("seq", seq);
		
		treeDto.setSeq(Integer.parseInt(seq.toString()));
		
		List<Tree> resultListMap = this.dao.getList("tree2.checkChildNode",treeDto);
		
		for(int i=0;i<resultListMap.size();i++){
			resultListMap.get(i).getSeq();
			seqMap.put(String.valueOf(resultListMap.get(i).getSeq()),resultListMap.get(i).getSeq());
			
			childTreeNode(resultListMap.get(i).getSeq());
		}
	}
	
	/**
	 * 트리 노드 이동
	 * @param request
	 * @return
	 */
	//ssss
	private boolean updatePositionTree(HttpServletRequest request){
		//Map<String,Object> paramMap = new HashMap<String,Object>();
		
		String parent_id =  request.getParameter("parent_id").toString().replace("tree_", "");
		String old_parent_id =  request.getParameter("old_parent").toString().replace("tree_", "");
		
		if(parent_id.equals("#")){
			parent_id = "0";
		}
		
		if(old_parent_id.equals("#")){
			old_parent_id = "0";
		}
		
		
		/*paramMap.put("seq", request.getParameter("id").toString().replace("tree_", ""));
		paramMap.put("new_parent", parent_id);
		paramMap.put("old_parent", old_parent_id);
		*/
		
		treeDto.setSeq(Integer.parseInt(request.getParameter("id").toString().replace("tree_", "")));
		treeDto.setOldParentSeq(Integer.parseInt(old_parent_id));
		treeDto.setParentSeq(Integer.parseInt(parent_id));
		
		this.dao.update("tree2.updateTreeNodePosition", treeDto);
		
		return true;
	}
	
	//ss
	private boolean updateUrlTree(HttpServletRequest request){
		
		/*Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("seq", request.getParameter("data").toString().replace("tree_", ""));
		paramMap.put("url", request.getParameter("url").toString());
		*/
		
		treeDto.setSeq(Integer.parseInt(request.getParameter("data").toString().replace("tree_", "")));
		treeDto.setUrl( request.getParameter("url").toString());
		this.dao.update("tree2.updateTreeNodeUrl", treeDto);
		return true;
	}
	
	//ss
	private boolean updateCopyTree(HttpServletRequest request){
		
		/*Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("copy_seq", request.getParameter("copy_seq").toString().replace("tree_", ""));
		paramMap.put("new_parent", request.getParameter("new_parent").toString().replace("tree_", ""));
		*/
		
		
		
		seqMap = new HashMap<String, Object>();
		childTreeNode(request.getParameter("copy_seq").toString().replace("tree_", ""));
	//	seqMap.remove("");
		treeDto.setSeq(Integer.parseInt(request.getParameter("copy_seq").toString().replace("tree_", "")));
		treeDto.setParentSeq(Integer.parseInt(request.getParameter("new_parent").toString().replace("tree_", "")));
		int seq = this.dao.insert("tree2.copyNode", treeDto);
		
		
		updateCopyChildTree(treeDto.getSeq(),Integer.parseInt(request.getParameter("copy_seq").toString().replace("tree_", "")));
		return true;
	}
	
	//ss
	private void updateCopyChildTree(Object new_parent,Object copy_seq){
		//Map<String,Object> paramMap = new HashMap<String,Object>();
		//paramMap.put("new_parent",new_parent);
		//paramMap.put("seq",copy_seq);
		
		treeDto.setSeq(Integer.parseInt(copy_seq.toString()));
		
		List<Tree> resultListMap =this.dao.getList("tree2.checkChildrenNode",treeDto);
		//System.out.println(resultListMap);
		
		for(int i=0;i<resultListMap.size();i++){
			resultListMap.get(i).getSeq();
			
			treeDto.setSeq(resultListMap.get(i).getSeq());
			treeDto.setParentSeq(Integer.parseInt(new_parent.toString()));
		//	paramMap.put("copy_seq", resultListMap.get(i).getSeq());
		//	paramMap.put("new_parent",  new_parent);
			
			this.dao.insert("tree2.copyNode", treeDto);
			
			updateCopyChildTree(treeDto.getSeq(),resultListMap.get(i).getSeq());
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
			this.dao.update("tree2.updateTreeNodePosition", paramMap);
			return true;
		}else{
			return false;
		}
	}
	

}
