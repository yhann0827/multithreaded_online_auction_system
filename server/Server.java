/*
 *Class Description:
 *Server class that implements a server for the online auction system. The server listens on  
 *port 6023 for incoming client connections. The server can handle 30 client connections
 *simultaneously as it has a thread pool of fixed size 30. Executor is used to manage the
 *thread-pool. Once a client connects, the server will create a new thread to handle client's
 *request and submit a new instance of ClientHandler class to the executor service. The server 
 *will run continuously and accept new client connections until the listening variable is set
 *to false. Then, the server socket and executor are closed when they are no longer used.
 */ 

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

public class Server {
	
    private static ServerSocket server = null;
    private static ExecutorService service = null;
    private static boolean listening = true;
    
    public static void main(String[] args) throws IOException {
        // Open a listening port
        try {
          server = new ServerSocket(6023);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 6023.");
            System.exit(-1);
        }

       // Initialise the executor that has a fixed thread-pool with 30 connections.
       service = Executors.newFixedThreadPool(30);

       // Submit a new client handler to the thread pool for each client.
       while( listening ) {
    	   try {
    	       Socket client = server.accept();
    	       service.submit( new ClientHandler(client) );
    	   }catch(IOException e) {
    	       System.err.println("Could not accept client connection");
    	   }
       }
    
       //Close the server and shut down the executor
       server.close();
       service.shutdown();
    }
}