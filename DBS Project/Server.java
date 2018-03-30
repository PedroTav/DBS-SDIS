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

public class Server implements Backup {

    public Server() {
    }

    public String backupFile(String Path, int Degree) throws IOException {

        Path filepath = Paths.get(Path);
        byte[] fileArray;
        fileArray = Files.readAllBytes(filepath);

        File file = new File(filepath.toString());
        int FILE_SIZE = (int) file.length();
        int CHUNK_SIZE = 64000;

        String destFiles = ".//fileChunks//" + filepath.getFileName().toString();
        System.out.println(destFiles);
        File destFile = new File(destFiles);
        destFile.mkdirs();

        System.out.println("Filename:" + filepath.getFileName().toString());
        System.out.println("Path:" + filepath.toString());

        int NUMBER_OF_CHUNKS = 0;
        byte[] temporary = null;

        try {
            InputStream inStream = null;
            int totalBytesRead = 0;

            try {
                inStream = new BufferedInputStream(new FileInputStream(file));

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

        return "COMPLETE";

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

    public String restoreFile(String Path) {

        Path filepath = Paths.get(Path);
        System.out.println(filepath.getFileName().toString());

        File file = new File(filepath.toString());

        return Path;
    }

    /*public void mergeParts(String name, String DESTINATION_PATH) {
        File[] file = new File[nameList.size()];
        byte AllFilesContent[] = null;

        int TOTAL_SIZE = 0;
        int FILE_NUMBER = nameList.size();
        int FILE_LENGTH = 0;
        int CURRENT_LENGTH = 0;

        for (int i = 0; i < FILE_NUMBER; i++) {
            file[i] = new File(nameList.get(i));
            TOTAL_SIZE += file[i].length();
        }

        try {
            AllFilesContent = new byte[TOTAL_SIZE]; // Length of All Files, Total Size
            InputStream inStream = null;

            for (int j = 0; j < FILE_NUMBER; j++) {
                inStream = new BufferedInputStream(new FileInputStream(file[j]));
                FILE_LENGTH = (int) file[j].length();
                inStream.read(AllFilesContent, CURRENT_LENGTH, FILE_LENGTH);
                CURRENT_LENGTH += FILE_LENGTH;
                inStream.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the file " + ioe);
        } finally {
            write(AllFilesContent, DESTINATION_PATH);
        }

        System.out.println("Merge was executed successfully.!");

    }*/

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
