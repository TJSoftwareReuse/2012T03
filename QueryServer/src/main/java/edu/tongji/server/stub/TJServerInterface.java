package edu.tongji.server.stub;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TJServerInterface extends Remote {
	
	public String query(String studentName) throws RemoteException;
	
}
