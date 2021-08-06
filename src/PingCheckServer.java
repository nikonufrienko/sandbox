import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class PingCheckServer extends Thread {
    int port;
    private DatagramSocket serverSocket;

    public PingCheckServer(int port) throws SocketException {
        super();
        this.port = port;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try (DatagramSocket serverSocket = new DatagramSocket(port)) {
                this.serverSocket = serverSocket;
                while (!isInterrupted()) {
                    byte[] inputBuffer = new byte[1024];
                    DatagramPacket inputPacket = new DatagramPacket(inputBuffer, 1024);
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

    @Override
    public void interrupt() {
        super.interrupt();
        serverSocket.close();
    }
}