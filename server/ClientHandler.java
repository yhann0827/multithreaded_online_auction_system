/* Class Description:
 * ClientHandler class handles a single client connection on the server side.
 * 
 * run() method reads input from the client, processes it using processInput method from the AuctionProtocol
 * class and write the responses back to the client. Every valid client request is written in 
 * "log.txt" that is created on the server directory. InetAddress is used to get cient's IP address. 
 * Lastly, all the resources are closed for this connection.
 */
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class ClientHandler extends Thread {
    private Socket socket = null;
    
    public ClientHandler(Socket socket) {
	super("ClientHandler");
	this.socket = socket;
    }

    public void run() {
	try {
	    // Create input and output stream to/from the client
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
	    //Protocol object for this client
	    InetAddress inet = socket.getInetAddress();
            String request = in.readLine();
            AuctionProtocol auc = new AuctionProtocol();
            String response = auc.processInput(request, inet.getHostAddress());
            out.write(response);
            out.flush();
            
	    // Logging. Only valid client request will be recorded.
            if (request.contains("show") || request.contains("item") || request.contains("bid")) {
            	Date date = new Date();
            	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
            	SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
            	String dateStr = dateFormat.format(date);
            	String timeStr = timeFormat.format(date);
            	String log = dateStr + " | " + timeStr + " | " + inet.getHostAddress() + " | " + request; 

            	//Write the request to log.txt file on the server directory, 'true' parameter opens the file in append mode
            	FileWriter fw = new FileWriter("log.txt", true);
            	fw.write(log + "\n");
            	fw.close();
            }

	    // Free up resources for this connection.
	    out.close();
	    in.close();
	    socket.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }   
}


