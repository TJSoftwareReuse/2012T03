package edu.tongji;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tj.reuse.ConfigComponent;
import License.license;
import PM.PerformanceManager;

import edu.tongji.server.impl.TJServer;
import edu.tongji.server.stub.TJServerInterface;

public class TJServerTest {

	private TJServerInterface serverInterface;
	private TJServerInterface serverInterface2;
	private TJServerInterface serverInterface3;

	
	private Properties studentInfo;

	private ConfigComponent cm;
	private Properties props;

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
		this.serverInterface2 = (TJServerInterface) registry.lookup("TJServer");
		this.serverInterface3 = (TJServerInterface) registry.lookup("TJServer");

		this.studentInfo = new Properties();

		InputStream in = new BufferedInputStream(new FileInputStream(
				"./studentInfo.properties"));
		this.studentInfo.load(in);
		in.close();
	}

	@After
	public void tearDown() throws Exception {
		// Destory
		this.serverInterface = null;
		this.serverInterface2 = null;
		this.serverInterface3 = null;
		this.studentInfo = null;
	}

	@Test
	public void pressureTest() {
		try {
			for (int i = 0; i < 1000; i++) {
				assertEquals(this.studentInfo.getProperty("胡圣托"),
						this.serverInterface.query("胡圣托"));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void multiThreadTest() {
		MTServerTest test1 = new MTServerTest(this.serverInterface, "胡圣托", this.studentInfo.getProperty("胡圣托"));
		MTServerTest test2 = new MTServerTest(this.serverInterface2, "喻帅", this.studentInfo.getProperty("喻帅"));
		
		test1.start();
		test2.start();
		
		try {
			test1.join();
			test2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void inittest() throws Exception {
		cm=serverInterface3.getcm();
		props=serverInterface3.getprops();
		
		assertEquals(props, cm.readProperties("./config.properties"));
		assertEquals(props.getProperty("LogDirPath"), "./log");
		assertEquals(props.getProperty("PMDirPath"), "./performance/");
		assertEquals(props.getProperty("LicenseCapacity"), "100");

	}

	public class MTServerTest extends Thread {
		
		private TJServerInterface server;
		private String name;
		private String team;
		
		MTServerTest(TJServerInterface server, String name, String team) {
			this.server = server;
			this.name = name;
			this.team = team;
		}

		@Override
		public void run() {
			Random random = new Random();
			try {
				for (int i = 0; i < 1000; i++) {
					Thread.sleep(Math.abs(random.nextLong()) % 32 + 1);
					assertEquals(team, this.server.query(this.name));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
	}
	@Test 
	public void testQuery(String studentname) {
		fail("Not yet implemented");
	}
	
	
	

}
