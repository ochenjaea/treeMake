package com.taxholic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.taxholic.web.test.dto.EncryptDto;
import com.taxholic.web.test.service.TestService;

public class DaoEncryptTest extends BaseTestCase{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	EncryptDto  dto = new EncryptDto();
	
	@Autowired
	private TestService testService;
	
	public void setUp() {
		dto.setUserId("user2");
		dto.setPasswd("passwd");
	}
	
	
	@Test
	public void encryptTest() {
		
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		testService.insertUser(dto);
		
//		logger.debug(message.getMessage("board.filePath"));
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
}
