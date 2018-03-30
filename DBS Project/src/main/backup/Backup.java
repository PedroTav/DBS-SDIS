package main.backup;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Backup extends Remote {
    String backupFile(String Path, int Degree) throws RemoteException, IOException;
    String restoreFile(String Path) throws RemoteException;
    String deleteFile(String Path) throws RemoteException;
    String manageStorage(int Maxdiskspace) throws RemoteException;
    String retrieveInfo() throws RemoteException;
    
}