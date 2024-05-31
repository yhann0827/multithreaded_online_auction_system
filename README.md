# Online Auction System with Multithreaded Client and Server

## Overview
This project involves designing and developing client and multi-threaded server applications in Java to create a simple online auction system.

## Features
- **List Auction Items**: Displays all items currently in the auction.
- **Add New Items**: Allows clients to add new items to the auction.
- **Place Bids**: Clients can place bids on existing items.

## Server Application
- **Continuous Operation**: The server runs continuously, handling multiple client requests.
- **Thread Pool Management**: Utilizes `ExecutorService` to manage a fixed thread-pool with 30 connections.
- **Item Storage**: Stores all auction items and their current bids.
- **Bid Tracking**: Stores the IP address of the client for each accepted bid.
- **Validation**: Ensures that items with duplicate names cannot be added and bids are non-negative.
- **Logging**: Logs every valid client request in `log.txt`.

## Client Application
- **Commands**:
  - `show`: Lists all items in the auction.
  - `item <string>`: Adds a new item to the auction.
  - `bid <item> <value>`: Places a bid on an item.
- **Output**:
  - Displays success or failure messages for operations.
  - Shows current bids and client IPs for items.

## Communication Protocol
- **TCP Sockets**: All client-server communication is done using TCP sockets.
- **Command Line Arguments**: Client instructions are provided via command line arguments.

## Example Usage
```shell
# Launch the server
cd cwk/server
java Server

# Launch the client
cd cwk/client
java Client show
java Client item Table
java Client show
java Client bid Table 10.0
java Client show
java Client bid Table 9.0
exit
```

## Example Output
<img width="359" alt="auction_server" src="https://github.com/yhann0827/online_auction_system/assets/111119306/790f29da-b064-478c-bf11-a3dfe7dbaf07">
<img width="373" alt="auction_client" src="https://github.com/yhann0827/online_auction_system/assets/111119306/173946f0-8fb9-4726-8da9-d0e12b62908b">

