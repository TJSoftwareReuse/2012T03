package edu.tongji;

import org.apache.log4j.PropertyConfigurator;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	PropertyConfigurator.configure("log4j.properties");
    	
        FaultManagement fm = FaultManagement.getInstance();
        for (int i = 0; i < 10000; i++) {
        	fm.generateWarningMessage("1111111111111111111111111111111111111111111111111111111111111");
        }
        
        
        fm.setLogDirPath("./warning");
        fm.generateWarningMessage("test2");
        fm.generateWarningMessage("test4123");
        fm.generateWarningMessage("t");
        fm.generateWarningMessage("test1231231231232");
    }

}