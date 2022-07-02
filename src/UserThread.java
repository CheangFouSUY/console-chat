import java.io.*;
import java.net.*;
 
/**
 * This thread handles connection for each connected client, so the server
 * can handle multiple clients at the same time.
 * $bye to quit, $change to change room
 */

public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;
    private String channel;

    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        this.channel = "public";
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            printUsers();

            String userName = reader.readLine();
            server.addUserName(userName);

            String serverMessage = "<- System -> [New connection: " + userName + "]";
            server.broadcast(serverMessage, this);

            String clientMessage;
            String tmp[];
            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                tmp = clientMessage.split(" ");
                if(tmp[0].toLowerCase().equals("$change"))
                    this.setChannel(tmp[1]);
                else {
                    server.broadcast(serverMessage, this);
                }

            } while (!clientMessage.equals("$bye"));

            server.removeUser(userName, this);
            socket.close();

            serverMessage = "<- System ->[" + userName + " has exited.]";
            server.broadcast(serverMessage, this);

        } catch (IOException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Sends a list of online users to the newly connected user.
    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }

    // Sends a message to the client.
    void sendMessage(String message) {
        writer.println(message);
    }

    void setChannel(String channel) {
        this.channel = channel;
    }

    String getChannel() {
        return this.channel;
    }
}