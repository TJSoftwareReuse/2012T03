package edu.tongji;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
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
	private ArrayList<ArrayList<String>> studentInfo;

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
			BufferedReader br = new BufferedReader(new FileReader(
					"./studentInfo.json"));
			Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
			}.getType();
			this.studentInfo = gson.fromJson(br, type);

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
		// Destroy
		this.serverInterface = null;
		this.studentInfo = null;
	}

	@Test
	public void testQuery() {
		try {
			Random random = new Random();
			for (int i = 0; i < 1000; i++) {
				int team = Math.abs(random.nextInt()) % 11 + 1;
				ArrayList<String> teamMembers = serverInterface.query(team);
				assertEquals(teamMembers, this.studentInfo.get(team - 1));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
