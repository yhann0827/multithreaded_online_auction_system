/*Class Description:
 * Client class connects the client to the server that is running on the same machine 'localhost' at 
 * port 6023.
 * 
 * Client constructor establishes a connection with the server and set ups input and output streams for
 * communication. 
 * 
 * auction() accepts command line argument as input, then the brackets and commas of
 * the input string array are removed before it is written to the server using the output stream. Then, 
 * it waits for a response from the server and prints it to the console. The system will only accept input 
 * via command line arguments. 
 */

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Client {

    private static Socket socket = null;
    private static PrintWriter output = null;
    private static BufferedReader input = null;

    public Client(){
    	try {
    		
            // Create a socket that assumes the server is running on the localhost machine on port 6023.
            socket = new Socket( "localhost", 6023 );

            // Chain a writing stream
            output = new PrintWriter(socket.getOutputStream(), true);

            // Chain a reading stream
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        }
        catch (UnknownHostException e) {
            System.err.println("Don't know about host.\n");
            System.exit(1);
        }
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to host.\n");
            System.exit(1);
        }
    }

    public void auction(String[] args) {    	
	String result;
	String request=null;
	try {
	    //Remove the bracket and commas of the string array before writing it to the server
	    for(int i = 0; i < 1; i++) {
		request = Arrays.toString(args).replace("[", "").replace("]", "").replace(",", "");
	    }
			
	    //Write user's command to the server and read the result from server 
	    output.println(request);
	    while ((result = input.readLine()) != null) {
	 	System.out.println(result);
	    } 
	}catch(IOException e) {
	     System.err.println("Failed to get I/O from the user.\n");
	}
    }
    
    
    public static void main(String[] args){
	Client client = new Client();
	client.auction(args);
		
    }

} 