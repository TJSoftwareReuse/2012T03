package edu.tongji;

import static org.junit.Assert.fail;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TJServerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// Configure log4j
		PropertyConfigurator.configure("log4j.properties");

		// init
		
	}

	@After
	public void tearDown() throws Exception {
		// Destory
		
	}

	@Test 
	public void testQuery(String studentname) {
		fail("Not yet implemented");
	}
	
	
	

}
