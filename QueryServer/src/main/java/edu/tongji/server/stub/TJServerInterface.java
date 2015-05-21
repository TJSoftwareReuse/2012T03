package edu.tongji.server.stub;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Properties;

import License.license;
import PM.PerformanceManager;
import edu.tongji.FaultManagement;
import tj.reuse.ConfigComponent;

public interface TJServerInterface extends Remote {
	
	public String query(String studentName) throws RemoteException;

    public Properties getprops() throws RemoteException;
    public ConfigComponent getcm() throws RemoteException;
    public FaultManagement getfm() throws RemoteException;
    public license getli() throws RemoteException;
    public PerformanceManager getpm() throws RemoteException;
}
