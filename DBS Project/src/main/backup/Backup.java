package main.backup;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Backup extends Remote {
    String backupFile(String Path, int Degree) throws RemoteException;
    String restoreFile(String Path) throws RemoteException;
    String deleteFile(String Path) throws RemoteException;
    String manageStorage(int Maxdiskspace) throws RemoteException;
    String retrieveInfo() throws RemoteException;
    
}