package edu.tongji;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * FaultManagement is a class which is used for generating error/warning files
 * 
 * @author Tom Hu
 */
public class FaultManagement {

	private static final FaultManagement fm = new FaultManagement(); // Singleton
	private Logger logger = Logger.getLogger(FaultManagement.class);

	private String currentDate;
	private long logCount;

	private long logFileSizeLimitation = 256 << 20; // File size(Byte) (>= 256
													// MB,
													// <= 4096 MB), default
													// value is 256 MB
	private File currentLogDir;
	private File currentLogFile;

	private FaultManagement() {
		logger.debug("Initializing...");

		// Set log dir
		this.setLogDirPath("./log");

		logger.debug("Initialization completed!");
	}

	// Utils
	/**
	 * Set log directory path
	 * 
	 * @param dirPath
	 *            log directory path
	 */
	public void setLogDirPath(String dirPath) {
		File logDir = new File(dirPath);

		if (logDir.equals(this.currentLogDir)) {
			return;
		}

		if (logDir.exists() && logDir.isFile()) {
			// Invalid path
			// TODO: Throw exception - Invalid path
			try {
				logger.error("Invalid directory path!");
				logger.error("Log directory path: "
						+ this.currentLogDir.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

		if (!logDir.exists()) {
			logDir.mkdirs(); // If the given directory doesn't exist, we will
								// create a new folder
		}

		this.currentLogDir = logDir;

		try {
			logger.info("Log directory set!");
			logger.info("Log directory path: "
					+ this.currentLogDir.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.currentDate = new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date());
		this.logCount = 0;

		// Create log file
		this.createNewLogFile();
	}

	/**
	 * Set log file size limitation
	 * 
	 * @param fileSizeLimitation
	 *            file size limitation
	 */
	public void setLogFileSizeLimitation(long fileSizeLimitation) {
		if (fileSizeLimitation < 256 || fileSizeLimitation > 4096) {
			// Wrong number
			// TODO: Throw exception - Invalid file size
			logger.error("Invalid file size!");
			logger.error("Log file size limitation: "
					+ this.logFileSizeLimitation + " Byte");
			return;
		}

		logger.info("Log file size limitation changed!");
		logger.info("Log file size limitation: " + fileSizeLimitation + " MB");

		this.logFileSizeLimitation = fileSizeLimitation << 20;
	}

	private void checkFileSize(long additionalByte) {
		long fileSize = this.currentLogFile.length() + additionalByte;

		if (fileSize > this.logFileSizeLimitation) {
			logger.info("Log file size over limitation!");
			// Create new log file
			this.createNewLogFile();
		}
	}

	private void checkCurrentDate(String date) {
		if (!date.equals(this.currentDate)) {
			logger.debug("Date changed!");
			// New date and count
			this.currentDate = date;
			this.logCount = 0;

			// Create new log file
			this.createNewLogFile();
		}
	}

	private void createNewLogFile() {
		String filename = this.currentDate + "_" + (++this.logCount) + ".log";
		try {
			logger.debug("Creating log file...");
			File logFile = new File(this.currentLogDir, filename);

			if (!logFile.exists()) {
				logFile.createNewFile();
			}

			this.currentLogFile = logFile;

			logger.info("New log file created!");
			logger.info("Log file path: "
					+ this.currentLogFile.getCanonicalPath());

			logger.debug("Create operation completed!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeToLogFile(String message) {
		Date now = new Date();
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateTime = dateTimeFormat.format(now); // Get current date time
		String date = dateFormat.format(now);

		// Check date
		this.checkCurrentDate(date); // May create new log file

		// Text to append
		String appendText = dateTime + "\nMESSAGE:\n" + message + "\n\n";

		// Check file size
		this.checkFileSize(appendText.length()); // May create new log file

		// Write
		try {
			logger.debug("Writing message to file...");

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(this.currentLogFile.getPath(), true),
					"UTF-8"));
			bw.write(appendText);
			bw.close();

			logger.debug("Write operation completed!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static FaultManagement getInstance() {
		return fm;
	}

	/**
	 * Generate the error/warning file in default directory
	 * 
	 * @param message
	 *            the error/warning message will be saved in the file
	 */
	public void generateWarningMessage(String message) {
		this.writeToLogFile(message);
	}
}