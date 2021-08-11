
public class Main {
    private static final int PORT = 49121;
    private static PingCheckServer pcs;
    public static void main(String[] args) {
        pcs = new PingCheckServer(PORT);
        pcs.start();
        Runtime.getRuntime().addShutdownHook(
                new Thread(Main::stopPCS)
        );
    }
    public static void stopPCS() {
        pcs.interrupt();
    }
}
