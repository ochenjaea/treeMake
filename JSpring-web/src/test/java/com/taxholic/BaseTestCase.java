package com.taxholic;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taxholic.configuration.beans.AnnotationConfiguration;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AnnotationConfiguration.class)
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
//@Transactional
public class BaseTestCase extends Assert {
	
	
	@AfterClass
	public static void testDown() {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void dummy() {
		
	
//		System.out.println(">>>>" + message.getMessage("board.filePath"));
//		this.testServce.insert();
//		System.out.println(">>>>>" + this.testServce.select().size()); 
	
	}
}
