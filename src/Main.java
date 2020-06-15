import javafx.application.Application;
import javafx.stage.Stage;
import network.client.Client;
import network.server.Server;
import utils.DataBaseManager;
import utils.Handler;
import utils.RandomStringGenerator;
import сommands.Command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

/**
 * The type Main.
 */
public class Main extends Application {
    public static String delEdges(String s) {
        return s.substring(1, s.length() - 1);
    }

    /**
     * Главный класс.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, SQLException, ExecutionException {

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
                handler.setDataBaseManager(manager);
                Server server = new Server(handler);


                server.run(port);
                break;
            case ("client"):
                String host = "localhost";
                int portClient = 5555;
                Client client = new Client(handler);
                client.run(host,portClient);
                Application.launch();

        }

//        handler.getSpaceManager().getSortedCollection();
//        handler.getCmdManeger().getCommand("load").execute(null);
//        while (true) {
//            handler.next();
//        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello world Application");
        stage.show();
    }
}
