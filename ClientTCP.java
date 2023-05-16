import java.io.*;
import java.net.*;

@SuppressWarnings("deprecation")
public class ClientTCP {
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;

    public ClientTCP(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            input = new DataInputStream(System.in);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (UnknownHostException u) {
            System.out.println(u);
            return;
        } catch (IOException i) {
            System.out.println(i);
        }

        String line = "";

        while (!line.equals("Bye")) {
            try {
                synchronized (this) {
                    System.out.print("Client: ");
                    line = input.readLine();
                    out.writeUTF("Client: " + line);
                    out.flush();

                    String response = in.readUTF();
                    System.out.println(response);
                }
            } catch (IOException i) {
                System.out.println(i);
            }
        }

        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        ClientTCP client = new ClientTCP("localhost", 5000);
    }
}

