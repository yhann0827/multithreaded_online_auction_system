/*Class Description:
 * Auction items and the current bid for each item is stored in a HashMap called "items". 
 * IP address of the client who placed the bid is stored in a separate HashMap called bidIP.
 * 
 * show() method return all items in the auction, which consists of the name, current bid and
 * client's IP address that made the bid. If the auction is empty, it returns a message saying 
 * there is nothing in the auction
 * 
 * addItem() method adds a new item to the auction with a starting price of 0.0. "Success" is 
 * returned if the item is successfully added, otherwise "Failure" if the item already exists 
 * in the auction.
 * 
 * bid() method checks if the item exists in the auction and whether the bid is greater than 
 * the current price before updating the price of the item. It stores the IP address of the 
 * client who placed the bid. It returns "Accepted" if the bid is accepted, "Rejected" if the 
 * new bid is lower than old bid and "Failure" if the item does not exist in the server, or if 
 * the bid price is invalid.
 * 
 * processInput() takes in a String input from the user, split it into tokens of words and 
 * determine the command based on the first word. Appropriate method is called based on the 
 * command. If the command is invalid, it returns "Invalid Command!".
 * 
 */

import java.util.HashMap;
import java.util.Map;

public class AuctionProtocol {
    private static HashMap<String, Double> items = new HashMap<String, Double>();
    private static HashMap<String, String> bidIP = new HashMap<String, String>();
    
    public String show() {
        StringBuilder output = new StringBuilder();
        if (items.isEmpty()) {
            return "There are currently no items in this auction.";
        } else {
            for (Map.Entry<String, Double> obj : items.entrySet()) {
                String itemName = obj.getKey();
                double price = obj.getValue();
                String clientIP;
                if(bidIP.containsKey(itemName)) {
                    clientIP = bidIP.get(itemName);
                } else 
                    clientIP = "<no bids>";
                
                output.append(itemName).append(" : ").append(price).append(" : ").append(clientIP).append("\n");
            }
            return output.toString();
        }
        
    }
    
    public String addItem(String itemName, double price) {
    	if (!items.containsKey(itemName)) {
            items.put(itemName, price);
            return "Success.";
        } else {
            return "Failure.";
        }   
    }
    
    public String bid(String itemName, double price, String clientIP) {
        if (!items.containsKey(itemName) || price == 0 || price < 0) {
            return "Failure.";
        } else if (items.containsKey(itemName) && price > items.get(itemName)){
            items.put(itemName, price);
            bidIP.put(itemName, clientIP);
            return "Accepted.";
        }else {
            return "Rejected.";
        }
    }
    
    public String processInput(String input, String clientIP) {
        String[] words = input.split(" ");
        String command = words[0];
        String output = null;
        if(command.equalsIgnoreCase("show")) {
            output = show();          
        }
        else if(command.equalsIgnoreCase("item")) {
            if(words.length!=2) {
        	output="Invalid command! Command should follow the format: java Client item <string>";
            }else {
	        String itemName = words[1]; 
	        output = addItem(itemName, 0.0); 
            }
        }
        else if(command.equalsIgnoreCase("bid")) {
            if(words.length!=3){
            	output="Invalid command! Command should follow the format: java Client bid <item> <value>";
            }else {
            	String bidItemName = words[1];
            	Double bidAmount = Double.parseDouble(words[2]);
            	output = bid(bidItemName, bidAmount, clientIP);  
            }
        }
        else
            output = "Invalid Command!";
               
        return output;
    }
}