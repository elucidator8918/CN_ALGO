import java.net.*;
import java.io.*;

@SuppressWarnings("deprecation")
public class ServerTCP 
{
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private DataInputStream input = null;

    public ServerTCP(int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("Server started");
        System.out.println("Waiting for a client ...");

        socket = server.accept();
        System.out.println("Client accepted");

        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        input = new DataInputStream(System.in);
        out = new DataOutputStream(socket.getOutputStream());

        Thread readThread = new Thread(() -> {
            while (true) {
                try {
                    synchronized (this) {
                        String message = in.readUTF();
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading message from client: " + e.getMessage());
                    break;
                }
            }
        });
        readThread.start();

        Thread writeThread = new Thread(() -> {
            while (true) {
                try {
                    System.out.print("Server: ");
                    String response = input.readLine();
                    out.writeUTF("Server: " + response);
                    out.flush();
                } catch (IOException e) {
                    System.out.println("Error writing message to client: " + e.getMessage());
                    break;
                }
            }
        });
        writeThread.start();

        try {
            readThread.join();
            writeThread.join();
        } catch (InterruptedException e) {
            System.out.println("Threads interrupted: " + e.getMessage());
        }

        closeConnection();
    }

    private void closeConnection() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    public static void main(String args[]) throws IOException {
        ServerTCP server = new ServerTCP(5000);
    }
}
