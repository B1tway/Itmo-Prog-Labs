import javafx.application.Application;
import javafx.stage.Stage;
import network.client.Client;
import utils.Handler;

import java.io.IOException;

public class MainClient extends Application {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Client client = new Client(new Handler());
        client.run("localhost", 5555);


    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
