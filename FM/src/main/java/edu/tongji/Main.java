package edu.tongji;

import org.apache.log4j.PropertyConfigurator;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	PropertyConfigurator.configure("log4j.properties");
    	
        FaultManagement fm = FaultManagement.getInstance();
        fm.generateWarningMessage("test");
        fm.generateWarningMessage("test2", "./warning/");
    }

}