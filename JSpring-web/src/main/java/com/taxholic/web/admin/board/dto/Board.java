package com.taxholic.web.admin.board.dto;

import java.util.Date;

public class Board {
	
	private int no;					//일련번호
	private int seq;					//번호
	private int fid;					//글 그룹
	private int lev;					//글 깊이
	private int stp;					//글 순서
	private String userId;			//아이디
	private String userNm;			//이름
	private String title;				//제목
	private String content;			//내용
	private String ip;					//아이피
	private String readCnt;			//조회수
	private Date regDate;			//등록일
	private String flag;				//게시판구분	
	private String isNew;			//새글 구분
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public int getLev() {
		return lev;
	}
	public void setLev(int lev) {
		this.lev = lev;
	}
	public int getStp() {
		return stp;
	}
	public void setStp(int stp) {
		this.stp = stp;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getReadCnt() {
		return readCnt;
	}
	public void setReadCnt(String readCnt) {
		this.readCnt = readCnt;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	
}
