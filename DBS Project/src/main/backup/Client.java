package main.backup;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private Client() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Backup stub = (Backup) registry.lookup("Backup");
            String response = null;
            
            switch (args[1]) {
            
            case "BACKUP":
            	
            	if(args.length == 4) {

            		String path = args[2];
            		int degree = Integer.parseInt(args[3]);
            		response = stub.backupFile(path, degree);
            		
            	} else System.out.println("Not enough arguments!");
            	break;
            
            case "RESTORE":
            	
            	if(args.length == 3) {
            		
            		String path = args[2];
            		response = stub.restoreFile(path);
            		
            	}
            	break;

            case "DELETE":

            	if(args.length == 3) {

            		String path = args[2];
            		response = stub.deleteFile(path);

            	}
            	break;
            
            case "MANAGE":
            	
            	if(args.length == 3) {
            		
            		int space = Integer.parseInt(args[2]);
            		response = stub.manageStorage(space);
            		
            	}
            	break;
            	
            case "INFO":
            	
            	if(args.length == 2) {
            		
            		response = stub.retrieveInfo();
            		
            	}
            	break;
            	
            default:
            	break;
            
            }
            
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
