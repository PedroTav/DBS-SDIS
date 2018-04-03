package main.backup;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.CRC32;
import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.net.*;


public class Server implements Backup {

    public static String serverID, version, peer_ap;
    public static InetAddress mcA, mdbA, mdrA;
	public static int mcP, mdbP, mdrP;
	public static MulticastSocket mcS, mdbS, mdrS;

    public Server() {
    }

    public String backupFile(String Path, int Degree) throws RemoteException, IOException {

        Path filepath = Paths.get(Path);
        byte[] fileArray;
        fileArray = Files.readAllBytes(filepath);

        File file = new File(filepath.toString());
        int FILE_SIZE = (int) file.length();
        int CHUNK_SIZE = 64000;

        System.out.println("Filename:" + filepath.getFileName().toString());
        System.out.println("Path:" + filepath.toString());

        int NUMBER_OF_CHUNKS = 0;
        byte[] temporary = null;

        try {
            InputStream inStream = null;
            int totalBytesRead = 0;

            try {
                inStream = new BufferedInputStream(new FileInputStream(file));
                String destFiles = ".//fileChunks//" + filepath.getFileName().toString();
                System.out.println(destFiles);
                File destFile = new File(destFiles);
                destFile.mkdirs();
                
                while (totalBytesRead < FILE_SIZE) {
                    String PART_NAME = "chunk_" + NUMBER_OF_CHUNKS + ".bin";
                    int bytesRemaining = FILE_SIZE - totalBytesRead;
                    if (bytesRemaining < CHUNK_SIZE) // Remaining Data Part is Smaller Than CHUNK_SIZE
                    // CHUNK_SIZE is assigned to remain volume
                    {
                        CHUNK_SIZE = bytesRemaining;
                        System.out.println("CHUNK_SIZE: " + CHUNK_SIZE);
                    }
                    temporary = new byte[CHUNK_SIZE]; //Temporary Byte Array
                    int bytesRead = inStream.read(temporary, 0, CHUNK_SIZE);

                    if (bytesRead > 0) // If bytes read is not empty
                    {
                        totalBytesRead += bytesRead;
                        NUMBER_OF_CHUNKS++;
                    }

                    write(temporary, ".//fileChunks//" + filepath.getFileName().toString() +"//" + PART_NAME);
                    System.out.println("Total Bytes Read: " + totalBytesRead);
                }

            } finally {
                inStream.close();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Message stored = new Message(Message.MsgType.STORED, "1.0", "1", "1", "10");   
        return stored.toString();
    }

    public String restoreFile(String Path) throws RemoteException {

        Path filepath = Paths.get(Path);
        System.out.println(filepath.getFileName().toString());
        byte AllFilesContent[] = null;
        
        String f = ".//fileChunks//" + filepath.getFileName().toString();
        System.out.println(f);
        File sourceDir = new File(f);
        
        
        int TOTAL_SIZE = 0;
        int FILE_NUMBER = sourceDir.list().length;
        int FILE_LENGTH = 0;
        int CURRENT_LENGTH=0;
        String DESTINATION_DIR = ".//restoredFiles//" + filepath.getFileName().toString();
        
        
        File[] files = new File[FILE_NUMBER];

        for(int i = 0; i < FILE_NUMBER; i++) {
        	
        	String source = f + "//chunk_" + i + ".bin";
        	//System.out.println(source);
        	files[i] = new File(source); 
        	TOTAL_SIZE+= files[i].length();
        	
        }
        
        
        try {
        	AllFilesContent= new byte[TOTAL_SIZE]; // Length of All Files, Total Size
        	InputStream inStream = null;

        	for ( int j=0; j<FILE_NUMBER; j++)
        	{
        		inStream = new BufferedInputStream ( new FileInputStream( files[j] ));
        		FILE_LENGTH = (int) files[j].length();
        		inStream.read(AllFilesContent, CURRENT_LENGTH, FILE_LENGTH);
        		CURRENT_LENGTH+=FILE_LENGTH;
        		inStream.close();
        	}

        }
        catch (FileNotFoundException e)
        {
        	System.out.println("File not found " + e);
        }
        catch (IOException ioe)
        {
        	System.out.println("Exception while reading the file " + ioe);
        }
        finally 
        {
        	write (AllFilesContent,DESTINATION_DIR);
        }

        System.out.println("Merge was executed successfully.!");
        
        return "COMPLETE!";
    }

    public String deleteFile(String Path) throws RemoteException {
    	
    	Path filepath = Paths.get(Path);
        System.out.println(filepath.getFileName().toString());
    	
        String f = ".//fileChunks//" + filepath.getFileName().toString();
        System.out.println(f);
        File sourceDir = new File(f);
        
        deleteDir(sourceDir);
        
        
        return "COMPLETE";
    }
    
    public String manageStorage(int Maxdiskspace) throws RemoteException {
        return Integer.toString(Maxdiskspace);
    }

    public String retrieveInfo() throws RemoteException {
        return "State: Good";
    }

    void write(byte[] DataByteArray, String DestinationFileName) {
        try {
            OutputStream output = null;
            try {
                output = new BufferedOutputStream(new FileOutputStream(DestinationFileName));
                output.write(DataByteArray);
                System.out.println("Writing Process Was Performed");
            } finally {
                output.close();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    
    public static void main(String args[]) {
    
        if (args.length != 9) {
			System.out.println("Usage: Server <protocolVersion> <serverID> <peerAP> <MulticastChannelAddress> <MulticastChannelPort> <MulticastBackupAddress> <MulticastBackupPort> <MulticastRestoreAddress> <MulticastRestorePort>");
			System.exit(1);
        }
        
        version = args[0];
        serverID = args[1];
		peer_ap = args[2];

        try {
        
            /*mcA = InetAddress.getByName(args[3]);
            mcP = Integer.parseInt(args[4]);

            mdbA = InetAddress.getByName(args[5]);
            mdbP = Integer.parseInt(args[6]);

            mdrA = InetAddress.getByName(args[7]);
            mdrP = Integer.parseInt(args[8]);*/
        
            Server obj = new Server();
            Backup stub = (Backup) UnicastRemoteObject.exportObject(obj, 0);
            
            Registry registry = null;
            
            try {
				registry = LocateRegistry.getRegistry();
			
			}
			catch (RemoteException e) { 
				registry = LocateRegistry.createRegistry(1099);
			}
 
            registry.rebind(peer_ap, stub);

            /*mcS = new MulticastSocket(mcP);
            mdbS = new MulticastSocket(mdbP);
            mdrS = new MulticastSocket(mdrP);

            mcS.joinGroup(mcA);
            mdbS.joinGroup(mdbA);
            mdrS.joinGroup(mdrA);*/
            
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

    }
}
