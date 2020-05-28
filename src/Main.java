import network.client.Client;
import network.server.Server;
import utils.DataBaseManager;
import utils.Handler;
import utils.RandomStringGenerator;
import сommands.Command;

import java.io.IOException;
import java.sql.SQLException;

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

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, SQLException {

        Handler handler = new Handler();
        String mode = handler.readLineWithMessage("Режим", false).toLowerCase();
        switch (mode) {
            case ("cli"):
                DataBaseManager man = new DataBaseManager();
                handler.setDataBaseManager(man);
                handler.loadCollection();
                while (true) {
                    Command cmd = handler.nextCommand();
                    handler.execute(cmd);
                }
            case ("server"):
                int port = handler.readInt("Введите порт",false);
                DataBaseManager manager = new DataBaseManager();
                Server server = new Server(handler);
                handler.setDataBaseManager(manager);
                handler.loadCollection();

                server.run(port);
                break;
            case ("client"):
                String host = handler.readLineWithMessage("Введите host", false);
                int portClient = handler.readInt("Введите порт",false);
                Client client = new Client(handler);
                client.run(host,portClient);
        }

//        handler.getSpaceManager().getSortedCollection();
//        handler.getCmdManeger().getCommand("load").execute(null);
//        while (true) {
//            handler.next();
//        }

    }
}
