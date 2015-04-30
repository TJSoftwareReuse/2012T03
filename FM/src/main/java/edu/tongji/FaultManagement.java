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

	private FaultManagement() {
		this.currentDate = new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date());
		this.logCount = 0;
	}

	public static FaultManagement getInstance() {
		return fm;
	}

	/**
	 * Generating the error/warning file in default directory
	 * 
	 * @param message
	 *            the error/warning message will be saved in the file
	 */
	public void generateWarningMessage(String message) {
		this.generateWarningMessage(message, "./");
	}

	/**
	 * Generating the error/warning file in a specific directory
	 * 
	 * @param message
	 *            the error/warning message will be saved in the file
	 * @param filepath
	 *            the specific path for saving the file
	 */
	public void generateWarningMessage(String message, String filepath) {
		Date now = new Date();
		// SimpleDateFormat dateFormat = new SimpleDateFormat(
		//		"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat filenameFormat = new SimpleDateFormat("yyyy-MM-dd");
		// String dateTime = dateFormat.format(now); // Get current date time
		String date = filenameFormat.format(now); // Get current date
		if (date != this.currentDate) {
			this.logCount = 0;
		}
		String filename = date + "_" + (logCount++) + ".log"; // Filename

		try {
			File parentDir = new File(filepath);

			if (!parentDir.exists()) {
				logger.debug("Parent directory doesn't exist!");
				parentDir.mkdirs(); // If the given directory doesn't exist, we
									// will create a new folder
			}
			File warningFile = new File(parentDir, filename);

			if (!warningFile.exists()) {
				logger.debug("File doesn't exist!");
				warningFile.createNewFile();
			}

			logger.debug("Path: " + warningFile.getPath());

			// Write message
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(warningFile.getPath()), "UTF-8"));
			bw.write(date + " No." + logCount);
			bw.newLine();
			bw.write("MESSAGE:");
			bw.newLine();
			bw.write(message);
			bw.newLine();
			bw.close();

			logger.debug("Done!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}