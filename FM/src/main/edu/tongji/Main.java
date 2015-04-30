package edu.tongji;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FaultManagement fm = new FaultManagement();
		fm.generateWarningMessage("test");
		fm.generateWarningMessage("test2", "./warning/");
	}

}
