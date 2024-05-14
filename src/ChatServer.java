import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This class represents a simple chat server that allows multiple clients to connect
 * and chat with each other.
 */
public class ChatServer {
    private static final int PORT = 8080; // Port number the server listens on
    private static HashSet<PrintWriter> clientWriters = new HashSet<>(); // Set of client PrintWriter objects
    private static int clientId = 1; // Counter for assigning client IDs

    /**
     * The main method of the server, which starts the server and waits for clients to connect.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Server started. Waiting for clients...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accepts client connection
                System.out.println("Client connected: " + clientSocket);
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                clientWriters.add(writer); // Adds client's PrintWriter to the set
                new ClientHandler(clientSocket, writer).start(); // Starts a new thread to handle client
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }

    /**
     * This class represents a thread for handling each connected client.
     */
    static class ClientHandler extends Thread {
        private Socket clientSocket; // Client's socket
        private PrintWriter writer; // PrintWriter for sending messages to the client
        private int id; // Client's ID

        /**
         * Constructor for the ClientHandler class.
         *
         * @param socket The client's socket
         * @param writer The PrintWriter for sending messages to the client
         */
        public ClientHandler(Socket socket, PrintWriter writer) {
            this.clientSocket = socket;
            this.writer = writer;
            this.id = clientId++; // Assigns a unique ID to the client
        }

        /**
         * The run method of the thread, which handles communication with the client.
         */
        public void run() {
            try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                writer.println("Welcome to the chat! Your ID is: " + id); // Sends welcome message to client
                String clientMsg;
                while ((clientMsg = reader.readLine()) != null) {
                    System.out.println("Received from client " + id + ": " + clientMsg);
                    broadcastMessage("Client " + id + ": " + clientMsg); // Broadcasts client's message to all clients
                }
            } catch (IOException e) {
                System.err.println("Client disconnected: " + clientSocket);
            } finally {
                if (writer != null) {
                    clientWriters.remove(writer); // Removes client's PrintWriter from the set
                }
                try {
                    clientSocket.close(); // Closes client's socket
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Broadcasts a message to all connected clients.
         *
         * @param message The message to broadcast
         */
        private void broadcastMessage(String message) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message); // Sends message to each client's PrintWriter
            }
        }
    }
}
