import network.client.Client;
import network.server.Server;
import utils.Handler;

import java.io.*;

/**
 * The type Main.
 */
public class Main {
    public static String delEdges(String s) {
        return s.substring(1, s.length() - 1);
    }

    /**
     * Главный класс.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Handler handler = new Handler();
        String mode = handler.readLineWithMessage("Режим", false).toLowerCase();
        switch (mode) {
            case ("cli"):
                while (true) handler.next();
            case ("server"):
                Server server = new Server(handler);
                server.run(5659);
                break;
            case ("client"):
                Client client = new Client(handler);
                client.run("host",5656);
        }
//        handler.getSpaceManager().getSortedCollection();
//        handler.getCmdManeger().getCommand("load").execute(null);
//        while (true) {
//            handler.next();
//        }

    }
}
