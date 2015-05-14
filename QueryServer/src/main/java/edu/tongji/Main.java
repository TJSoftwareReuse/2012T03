package edu.tongji;

import org.apache.log4j.PropertyConfigurator;
import tj.reuse.ConfigComponent; // CM
import edu.tongji.FaultManagement; // FM
import License.license; // License
import PM.PerformanceManager; // PM

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	PropertyConfigurator.configure("log4j.properties");
    	
    	ConfigComponent cm = new ConfigComponent();
    	FaultManagement fm = FaultManagement.getInstance();
    	license li = new license(123);
    	PerformanceManager pm = new PerformanceManager("./performance");
    	
    }

}