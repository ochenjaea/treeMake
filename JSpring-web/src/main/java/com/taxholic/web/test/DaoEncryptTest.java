/*package com.taxholic.web.test;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.taxholic.core.authority.AuthDto;
import com.taxholic.web.test.dto.EncryptDto;
import com.taxholic.web.test.service.TestService;

public class DaoEncryptTest extends BaseTestCase{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	EncryptDto  dto = new EncryptDto();
	
	@Autowired
	private TestService testService;
	
	public void setUp() {
		dto.setUserId("user3");
		dto.setPasswd("passwd");
		dto.setEmail("user333");
	}
	
	
	@Test
	public void main() {
		
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		//testService.insertUser(dto);
		
//		logger.debug(message.getMessage("board.filePath"));
		
		List<EncryptDto> t = testService.getList();
		
		for(int i=0;i<t.size();i++){
			System.out.println(t.get(i).getUserId());
			System.out.println(t.get(i).getPasswd());
			System.out.println(t.get(i).getEmail());
		}
		
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
}*/
