package edu.tongji;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import edu.tongji.server.impl.TJServer;
import edu.tongji.server.stub.TJServerInterface;

public class Main {

	/**
	 * @param args
	 * @throws RemoteException
	 */
	public static void main(String[] args) throws RemoteException {
		PropertyConfigurator.configure("log4j.properties");

		TJServer server = new TJServer();

		TJServerInterface serverInterface = (TJServerInterface) UnicastRemoteObject
				.exportObject(server, 0);

		Registry registry = LocateRegistry.createRegistry(2015);
		registry.rebind("TJServer", serverInterface);

		System.out.println("In service...");
	}

}