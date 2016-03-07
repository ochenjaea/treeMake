package com.taxholic.web.admin.tree.dto;

import org.springframework.stereotype.Repository;

import com.taxholic.core.annotation.Encrypt;

@Repository
public class Tree {
	
	private int seq;

	private String groupName;

	private String updateName;
	
	private int parentSeq;
	
	private int oldParentSeq;
	
	private String Url;
	
	private int Cnt;
	
	private int level;
	
	private int orderNum;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public int getParentSeq() {
		return parentSeq;
	}

	public void setParentSeq(int parentSeq) {
		this.parentSeq = parentSeq;
	}

	public int getOldParentSeq() {
		return oldParentSeq;
	}

	public void setOldParentSeq(int oldParentSeq) {
		this.oldParentSeq = oldParentSeq;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public int getCnt() {
		return Cnt;
	}

	public void setCnt(int cnt) {
		Cnt = cnt;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public void treeInit(){
		this.setCnt(0);
		this.setGroupName("");
		this.setOldParentSeq(0);
		this.setParentSeq(0);
		this.setSeq(0);
		this.setUpdateName("");
		this.setUrl("");
		this.setLevel(0);
		this.setOrderNum(0);
		
		
	}
}
