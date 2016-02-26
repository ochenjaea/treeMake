/*package com.taxholic.web.test;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxholic.core.constants.SystemConstants;

public class PropertyEncryptTest extends BaseTestCase{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	 private String USER = "shkr";
	 private String PASSWORD = "Wkwkdaus";
	 private String URL = "jdbc:log4jdbc:mysql://127.0.0.1/stock";
	
	@Test
	public void encryptTest() {
		
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
		standardPBEStringEncryptor.setAlgorithm("PBEWithMD5AndDES");
		standardPBEStringEncryptor.setPassword(SystemConstants.ENCRYPT_KEY);
		
		logger.debug("Encrypted user  : "+ standardPBEStringEncryptor.encrypt(USER));
		logger.debug("Encrypted password  : "+ standardPBEStringEncryptor.encrypt(PASSWORD));
		logger.debug("Encrypted url  : "+ standardPBEStringEncryptor.encrypt(URL));
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
}
*/