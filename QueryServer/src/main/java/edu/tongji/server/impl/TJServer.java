package edu.tongji.server.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Properties;

import org.apache.log4j.Logger;

import tj.reuse.ConfigComponent;
import License.license;
import PM.PerformanceManager;
import edu.tongji.FaultManagement;
import edu.tongji.server.stub.TJServerInterface;

public class TJServer implements TJServerInterface {

	private Properties studentInfo;

	private ConfigComponent cm;
	private FaultManagement fm;
	private license li;
	private PerformanceManager pm;

	private Properties props;

	private Logger logger = Logger.getLogger(TJServer.class);

	private static final String configFilepath = "./config.properties";

	public Properties getprops()
	{
		return this.props;
	}

	public ConfigComponent getcm()
	{
		return this.cm;
	}

	public FaultManagement getfm()
	{
		return this.fm;
	}

	public license getli()
	{
		return this.li;
	}

	public PerformanceManager getpm()
	{
		return this.pm;
	}

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
					"StudentInfoFilePath", "./studentInfo.properties"); // Write back
		}
		
		this.studentInfo = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					studentInfoFilepath));
			this.studentInfo.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String query(String studentName) throws RemoteException {
		logger.debug("Query <" + studentName + ">");

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

			return "NO LICENSE";
		}

		logger.info("Provide service!");

		// FM
		this.fm.generateWarningMessage("Query <" + studentName
				+ ">. Provide service!");

		// PM
		this.pm.AddData("NumberOfProvidedService", 1);
		this.pm.AddData("NumberOfReturnedMessage", 1);

		String team = this.studentInfo.getProperty(studentName);

		if (team == null) {
			return "NON-EXISTENT";
		}

		return this.studentInfo.getProperty(studentName);
	}

}
