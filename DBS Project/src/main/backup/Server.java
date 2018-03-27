package main.backup;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
        
public class Server implements Backup {
        
    public Server() {}

    public String backupFile(String Path, int Degree) {
        return Path + Integer.toString(Degree);
        
    }
    
    public String restoreFile(String Path) {
        return Path;
    }
    
    public String deleteFile(String Path) {
        return Path;
    }
    
    public String manageStorage(int Maxdiskspace) {
        return Integer.toString(Maxdiskspace);
    }
    
    public String retrieveInfo() {
        return "Hello, world!";
    }
    
    public static void main(String args[]) {
        
        try {
            Server obj = new Server();
            Backup stub = (Backup) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Backup", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}