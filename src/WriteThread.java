import java.io.*;
import java.net.*;
 
/**
 * This thread is responsible for reading console's input and request to the server.
 */
public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;
 
    public WriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;
 
        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    public void run() {
 
        Console console = System.console();
 
        String userName = console.readLine("\nEnter your name: ");
        client.setUserName(userName);
        writer.println(userName);
 
        String text;
        String tmp[];
        do {
            text = console.readLine("[" + userName + "]: ");
            writer.println(text);
            tmp = text.split(" ");
            if(tmp[0].toLowerCase().equals("$change"))
                System.out.println("<- System ->[change to " + tmp[1] +"]");
        } while (!text.toLowerCase().equals("$bye"));
 
        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println("<- System ->[You have quitted]");
            System.exit(0);
        }
    }
}