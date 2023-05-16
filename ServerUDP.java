import java.net.*;
import java.io.*;

public class ServerUDP {
    private DatagramSocket socket;
    private InetAddress clientAddress;
    private int clientPort;

    public ServerUDP(int port) throws SocketException {
        socket = new DatagramSocket(port);
        System.out.println("Server started");
        System.out.println("Waiting for a client ...");
    }

    public void start() throws IOException {
        byte[] buffer = new byte[1024];

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        clientAddress = packet.getAddress();
        clientPort = packet.getPort();

        System.out.println("Client connected: " + clientAddress.getHostAddress() + ":" + clientPort);

        Thread readThread = new Thread(() -> {
            while (true) {
                try {
                    byte[] receivedData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length);
                    socket.receive(receivePacket);

                    String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Client: " + message);
                } catch (IOException e) {
                    System.out.println("Error receiving message from client: " + e.getMessage());
                    break;
                }
            }
        });
        readThread.start();

        Thread writeThread = new Thread(() -> {
            while (true) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("Server: ");
                    String response = reader.readLine();

                    byte[] sendData = response.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                    socket.send(sendPacket);
                } catch (IOException e) {
                    System.out.println("Error sending message to client: " + e.getMessage());
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
        socket.close();
    }

    public static void main(String args[]) throws IOException {
        ServerUDP server = new ServerUDP(5000);
        server.start();
    }
}
