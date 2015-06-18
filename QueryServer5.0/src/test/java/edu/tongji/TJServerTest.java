package edu.tongji;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.tongji.server.stub.TJServerInterface;

public class TJServerTest {

	private TJServerInterface serverInterface;
	private ArrayList<ArrayList<String>> studentInfo1;
	private Properties studentInfo2;

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

		// Initialize
		Registry registry = LocateRegistry.getRegistry("localhost", 2015);
		this.serverInterface = (TJServerInterface) registry.lookup("TJServer");

		Gson gson = new Gson();
		try {
			// Student Info 1
			BufferedReader br = new BufferedReader(new FileReader(
					"./studentInfo.json"));
			Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
			}.getType();
			this.studentInfo1 = gson.fromJson(br, type);

			br.close();

			// Student Info 2
			InputStream in = new BufferedInputStream(new FileInputStream(
					"./studentInfo.properties"));
			this.studentInfo2 = new Properties();
			this.studentInfo2.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
		// Destroy
		this.serverInterface = null;
		this.studentInfo1 = null;
		this.studentInfo2 = null;
	}

	@Test
	public void testQuery1() {
		try {
			Random random = new Random();
			for (int i = 0; i < 1000; i++) {
				int team = Math.abs(random.nextInt()) % 11 + 1;
				ArrayList<String> teamMembers = serverInterface.query(team);
				assertEquals(teamMembers, this.studentInfo1.get(team - 1));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQuery2() {
		try {
			for (int i = 0; i < 1000; i++) {
				assertEquals(this.studentInfo2.getProperty("胡圣托"),
						this.serverInterface.query("胡圣托"));
			}
			for (int i = 0; i < 1000; i++) {
				assertEquals("NON-EXISTENT", this.serverInterface.query("张睿"));
			}
			for (int i = 0; i < 1000; i++) {
				assertEquals("NO LICENSE", this.serverInterface.query("与帅"));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
