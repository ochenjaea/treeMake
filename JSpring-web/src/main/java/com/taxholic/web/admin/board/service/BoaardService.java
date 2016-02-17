package com.taxholic.web.admin.board.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taxholic.core.web.dao.CommonDao;
import com.taxholic.web.admin.board.dto.Board;


@Service
public class BoaardService{
	
	@Autowired
	private CommonDao dao;
	
	
	public List<Board> getList(){
		
		List<Board> list = new ArrayList<Board>();
		for(int i = 1; i <= 10; i++){
			Board board = new Board();
			board.setNo(i);
			board.setTitle("제목이다");
			board.setUserNm("merong");
			board.setRegDate(new Date());
			
			list.add(board);
		}
		
		return list;
		
	}
}
