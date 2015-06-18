package edu.tongji.server.stub;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface TJServerInterface extends Remote {
	
	public ArrayList<String> query(int team) throws RemoteException;
	public String query(String studentName) throws RemoteException;
	
}
