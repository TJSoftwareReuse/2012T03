package edu.tongji;

import static org.junit.Assert.fail;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.tongji.server.stub.TJServerInterface;

public class TJServerTest {
	
	private TJServerInterface serverInterface;

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
		Registry registry = LocateRegistry.getRegistry("localhost", 2015);
		
		this.serverInterface = (TJServerInterface) registry.lookup("TJServer");
	}

	@After
	public void tearDown() throws Exception {
		// Destory
		this.serverInterface = null;
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
