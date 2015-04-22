package tj.reuse;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

public abstract class ConfigComponent implements ConfigInterface{
	
	//根据key读取value
	public String readValue(String filePath,String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream (new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty (key);
			System.out.println(key+value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Can't find "+filePath+" files");
			return null;
		}
	}
	//读取properties的全部信息
	public Properties readProperties(String filePath) { 
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream (new FileInputStream(filePath));
	        props.load(in);
	        Enumeration en = props.propertyNames();
	        while (en.hasMoreElements()) {
	        	String key = (String) en.nextElement();
	        	String Property = props.getProperty (key);
	        	System.out.println(key+Property);
	        }
	        return props;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Can't find "+filePath+" files");
			return null;
		}
	}
	//写入properties信息
	public Properties writeProperties(String filePath,String parameterName
			,String parameterValue) {
		Properties props = new Properties();
	    try {
	    	InputStream fis = new FileInputStream(filePath);
	    	props.load(fis);
	    	OutputStream fos = new FileOutputStream(filePath);
	    	props.setProperty(parameterName, parameterValue);
	    	props.store(fos, "Update '" + parameterName + "' value");
	    	return props;
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	System.err.println("Visit "+filePath+" for updating "
	    			+parameterName+" value error");
	    	return null;
	    }
	}
	
}
