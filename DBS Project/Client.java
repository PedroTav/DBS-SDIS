package main.backup;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

	private Client() {
	}

	public static void main(String[] args) {

		String peer_ap = "";

		if (args.length != 0) {
			peer_ap = args[0];
		} else {
			System.out.println("Usage: java Client <peer_ap> <sub_protocol> <opnd_1> <opnd_2>");
		}

		try {
            String host = peer_ap;
            System.setProperty("java.rmi.server.hostname",host);
			Registry registry = LocateRegistry.getRegistry();
			System.out.println("1");
			
			Backup stub = (Backup) registry.lookup("Backup");
			System.out.println("2");
			String response = null;

			System.out.println("3");
			
			switch (args[1]) {
			
			case "BACKUP":
				if (args.length == 4) {
					String path = args[2];
					int degree = Integer.parseInt(args[3]);
					response = stub.backupFile(path, degree);
				} else
					System.out.println("Not enough arguments!");
				break;

			case "RESTORE":
				if (args.length == 3) {
					String path = args[2];
					response = stub.restoreFile(path);
				}
				break;

			case "DELETE":
				if (args.length == 3) {
					String path = args[2];
					response = stub.deleteFile(path);
				}
				break;

			case "RECLAIM":
				if (args.length == 3) {
					int space = Integer.parseInt(args[2]);
					response = stub.manageStorage(space);
				}
				break;

			case "STATE":
				if (args.length == 2) {
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
