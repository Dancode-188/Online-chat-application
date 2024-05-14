import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Represents a simple chat client that connects to a chat server,
 * sends messages to the server, and receives messages from the server.
 */
public class ChatClient {
    private static final String SERVER_IP = "localhost"; // Server IP address
    private static final int PORT = 8080; // Port number the server is listening on

    /**
     * The main method of the chat client.
     * It establishes a connection to the server, starts a thread to receive messages,
     * and allows the user to send messages to the server.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try (
            // Establishes a socket connection to the server
            Socket socket = new Socket(SERVER_IP, PORT);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected to the server.");
            System.out.println("Enter your messages, type 'exit' to quit.");

            // Thread to continuously receive messages from the server
            Thread receiveThread = new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = reader.readLine()) != null) {
                        System.out.println("Server: " + serverMsg);
                        System.out.print("> "); // Add prompt for user input
                    }
                } catch (IOException e) {
                    System.err.println("Server connection closed.");
                }
            });
            receiveThread.start();

            String userInput;
            while (true) {
                System.out.print("> "); // Display prompt for user input
                userInput = scanner.nextLine();
                writer.println(userInput); // Send user input to the server
                if (userInput.equalsIgnoreCase("exit")) {
                    break; // Exit the loop if user types 'exit'
                }
            }

            System.out.println("Closing connection.");
        } catch (IOException e) {
            System.err.println("Client exception: " + e.getMessage());
        }
    }
}
