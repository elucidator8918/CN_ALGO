import java.io.*;
import java.net.*;

public class ClientUDP {
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort;

    public ClientUDP(String address, int port) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        serverAddress = InetAddress.getByName(address);
        serverPort = port;
        System.out.println("Connected");
    }

    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line = "";

        while (!line.equals("Bye")) {
            try {
                System.out.print("Client: ");
                line = reader.readLine();

                byte[] sendData = line.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                socket.send(sendPacket);

                byte[] receivedData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length);
                socket.receive(receivePacket);

                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(response);
            } catch (IOException e) {
                System.out.println("Error communicating with server: " + e.getMessage());
            }
        }

        socket.close();
    }

    public static void main(String[] args) throws IOException {
        ClientUDP client = new ClientUDP("localhost", 5000);
        client.start();
    }
}
