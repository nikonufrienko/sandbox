
public class Main {
    private static PingCheckServer pcs;
    public static void main(String[] args) {
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
