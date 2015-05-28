package edu.tongji.server.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

import tj.reuse.ConfigComponent;
import License.license;
import PM.PerformanceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.tongji.FaultManagement;
import edu.tongji.server.stub.TJServerInterface;

public class TJServer implements TJServerInterface {

	private ArrayList<ArrayList<String>> studentInfo;

	private ConfigComponent cm;
	private FaultManagement fm;
	private license li;
	private PerformanceManager pm;

	private Properties props;

	private Logger logger = Logger.getLogger(TJServer.class);

	private static final String configFilepath = "./config.properties";

	public TJServer() throws RemoteException {
		// init
		this.init();
	}

	// init
	private void init() {
		// Read from configuration file:
		// 1. FM log directory path
		// 2. PM file directory path
		// 3. License capacity
		// 4. Student List file path
		this.initCM();
		this.initFM();
		this.initPM();
		this.initLicense();
		this.initStudentInfo();
	}

	private void initCM() {
		this.cm = new ConfigComponent();
		this.props = this.cm.readProperties(configFilepath);
	}

	private void initFM() {
		this.fm = FaultManagement.getInstance();

		String logDirPath = this.props.getProperty("LogDirPath");
		if (logDirPath == null) {
			logDirPath = "./log"; // Default value
			this.props = cm.writeProperties(configFilepath, "LogDirPath",
					logDirPath); // Write back
		}
		this.fm.setLogDirPath(logDirPath);
	}

	private void initPM() {
		String pmDirPath = this.props.getProperty("PMDirPath");
		if (pmDirPath == null) {
			pmDirPath = "./performance/"; // Default value
			this.props = cm.writeProperties(configFilepath, "PMDirPath",
					pmDirPath); // Write back
		}
		File pmDir = new File(pmDirPath);
		if (!pmDir.exists()) {
			pmDir.mkdirs();
		}
		this.pm = new PerformanceManager(pmDirPath);
	}

	private void initLicense() {
		int licenseCapacity;
		try {
			licenseCapacity = Integer.parseInt(this.props
					.getProperty("LicenseCapacity"));
		} catch (NumberFormatException e) {
			licenseCapacity = 100; // Default value
			this.props = cm.writeProperties(configFilepath, "LicenseCapacity",
					String.valueOf(licenseCapacity)); // Write back
		}
		this.li = new license(licenseCapacity);
	}

	private void initStudentInfo() {
		String studentInfoFilepath = this.props
				.getProperty("StudentInfoFilePath");
		if (studentInfoFilepath == null) {
			studentInfoFilepath = "./studentInfo.properties"; // Default value
			this.props = cm.writeProperties(configFilepath,
					"StudentInfoFilePath", "./studentInfo.json"); // Write
																	// back
		}

		Gson gson = new Gson();
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					studentInfoFilepath));
			Type type = new TypeToken<ArrayList<ArrayList<String>>>() {
			}.getType();
			this.studentInfo = gson.fromJson(br, type);

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> query(int team) throws RemoteException {
		ArrayList<String> ans = new ArrayList<String>();

		logger.debug("Query <Team No. " + team + ">");

		// PM
		this.pm.AddData("NumberOfReceivedQuery", 1);

		// License
		if (!this.li.isProvide_service()) {
			logger.error("Cannot provide service!");

			// FM
			this.fm.generateWarningMessage("Deny service!");

			// PM
			this.pm.AddData("NumberOfDeniedService", 1);
			this.pm.AddData("NumberOfReturnedMessage", 1);

			ans.add("NO LICENSE");

			return ans;
		}

		logger.info("Provide service!");

		// FM
		this.fm.generateWarningMessage("Query <Team No. " + team
				+ ">. Provide service!");

		// PM
		this.pm.AddData("NumberOfProvidedService", 1);
		this.pm.AddData("NumberOfReturnedMessage", 1);

		if (team < 1 || team > 10) {
			ans.add("NON-EXISTENT");

			return ans;
		}

		ans = this.studentInfo.get(team - 1);

		return ans;
	}

}
