
# Online Chat Application

This is a simple online chat application implemented in Java using socket programming. It allows multiple clients to connect to a central server and chat with each other by sending and receiving text messages.

## Features

- Multi-client support: Multiple clients can connect to the server simultaneously.
- Unique client IDs: Each connected client is assigned a unique ID for easy identification.
- Message broadcasting: Messages sent by a client are broadcasted to all other connected clients.
- Client disconnect handling: The server handles clients disconnecting gracefully.

## Installation

1. Make sure you have Java Development Kit (JDK) installed on your system.
2. Clone or download the repository containing the source code files.

## Usage

1. Start the server by running the `ChatServer` class:
    javac src/ChatServer.java
    java -cp src ChatServer
The server will start listening for incoming client connections on port 8080.

2. Open a new terminal or command prompt window and run the `ChatClient` class to connect to the server:
    javac src/ChatClient.java
    java -cp src ChatClient
You can run multiple instances of the `ChatClient` class to simulate multiple clients connecting to the server.

3. In each client window, enter your messages and press Enter to send them to the server. The server will broadcast the messages to all connected clients.

4. To exit a client, type "exit" and press Enter.

## Code Structure

### ChatServer.java

- `ChatServer` class: The main class that starts the server and listens for incoming client connections.
- `ClientHandler` class: An inner class that extends the `Thread` class and handles communication with each connected client.

### ChatClient.java

- `ChatClient` class: The main class that represents the chat client. It connects to the server, sends messages, and receives messages from other clients.

## Implementation Details

### Server Implementation

- The `ChatServer` class creates a `ServerSocket` and listens for incoming client connections on port 8080.
- When a client connects, a new `ClientHandler` thread is created to handle communication with that client.
- The `ClientHandler` assigns a unique ID to the client and maintains a `PrintWriter` for sending messages to the client.
- When a client sends a message, the `ClientHandler` broadcasts the message to all other connected clients using the `broadcastMessage` method.
- The `ClientHandler` also handles clients disconnecting by removing their `PrintWriter` from the set of writers and closing the client socket.

### Client Implementation

- The `ChatClient` class creates a `Socket` and connects to the server at the specified IP address and port.
- It creates a `PrintWriter` for sending messages to the server and a `BufferedReader` for receiving messages from the server.
- A separate thread is started to continuously read messages from the server and print them to the console.
- The main thread reads user input from the console and sends it to the server using the `PrintWriter`.
- To exit the client, the user types "exit" and the client closes the connection to the server.

## Contributors

- [Daniel Bitengo](https://github.com/Dancode-188)