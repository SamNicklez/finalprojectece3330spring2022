import javax.swing.*;

/**
 * This is the main driver for the Server
 * @author Sam Maschmann
 * @see Server
 */
public class ServerDriver {
    /**
     * This method will automatically be called when the Server
     * is run.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Server application = new Server();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.waitForPackets();
    }
}



