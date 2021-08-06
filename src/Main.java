import java.io.IOException;
import java.net.SocketException;

public class Main {
    static PingCheckServer pcs;
    public static void main(String[] args) throws IOException {
        pcs = new PingCheckServer(7);
        pcs.start();
        Runtime.getRuntime().addShutdownHook(
                new Thread(Main::stopPCS)
        );
    }
    public static void stopPCS() {
        pcs.interrupt();
    }
}
