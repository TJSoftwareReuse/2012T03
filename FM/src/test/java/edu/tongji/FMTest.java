package edu.tongji;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit testing for FaultManagement
 * 
 * @author Tom Hu
 */
public class FMTest {

	private FaultManagement fm;
	private int count = 0;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Initialize
		fm = FaultManagement.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		// Destroy
		fm = null;
	}

	@Test
	public void testGenerateWarningMessageWithSpecificPath() {
		String message = "222222222";
		String logDir = "./warning/";
		String currentDate = new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date());
		String filenamePrefix = currentDate + "_";
		String filenameSuffix = ".log";
		for (int i = 0; i < 20; i++) {
			String filename = filenamePrefix + this.count + filenameSuffix;
			fm.setLogDirPath(logDir);
			fm.generateWarningMessage(message);
			String content = "";
			
			// Read file & Compare
			try {
				FileReader fr = new FileReader(logDir + filename);
				BufferedReader br = new BufferedReader(fr);
				String line = br.readLine();
				while(line != null){
				    content += line;
				    content += "\n";
				    line = br.readLine();
				}
				br.close();
				fr.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Assert
			assertEquals(currentDate + " No." + this.count + "\nMESSAGE:\n" + message + "\n", content);
			
			this.count++;
		}
	}

	@Test
	public void testGenerateWarningMessage() {
		this.count = 20;
		String message = "1234567890";
		String currentDate = new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date());
		String filenamePrefix = currentDate + "_";
		String filenameSuffix = ".log";
		for (int i = 0; i < 20; i++) {
			String filename = filenamePrefix + this.count + filenameSuffix;
			fm.generateWarningMessage(message);
			String content = "";
			
			// Read file & Compare
			try {
				FileReader fr = new FileReader(filename);
				BufferedReader br = new BufferedReader(fr);
				String line = br.readLine();
				while(line != null){
				    content += line;
				    content += "\n";
				    line = br.readLine();
				}
				br.close();
				fr.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Assert
			assertEquals(currentDate + " No." + this.count + "\nMESSAGE:\n" + message + "\n", content);
			
			this.count++;
		}
	}

}
