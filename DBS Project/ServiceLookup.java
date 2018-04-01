package main.backup;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.IOException;

public interface ServiceLookup extends Remote {
	String backup(String path, int degree) throws RemoteException, IOException;

	String restore(String path) throws RemoteException;

	String delete(String path) throws RemoteException;

	String reclaim(int Maxdiskspace) throws RemoteException;

	String state() throws RemoteException;
}
