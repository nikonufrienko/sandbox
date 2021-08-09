import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class PingCheckServer extends Thread {
    private int port;
    volatile private DatagramSocket serverSocket;
    final private int BUFFER_SIZE = 1024;
    public PingCheckServer(int port) {
        super();
        this.port = port;
    }

    @Override
    public void run() {
        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            this.serverSocket = serverSocket;
            byte[] inputBuffer = new byte[BUFFER_SIZE];
            while (!isInterrupted()) {
                DatagramPacket inputPacket = new DatagramPacket(inputBuffer, BUFFER_SIZE);
                serverSocket.receive(inputPacket);
                InetAddress clientAddress = inputPacket.getAddress();
                int clientPort = inputPacket.getPort();
                serverSocket.send(new DatagramPacket(inputPacket.getData(), inputPacket.getLength(), clientAddress, clientPort));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}