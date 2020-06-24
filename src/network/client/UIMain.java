package network.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.client.controllers.MainSceneController;
import utils.Handler;

import java.io.InputStream;

public class UIMain extends Application {
    public static Client client;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        client = new Client(new Handler());
        new ClientThread(client).start();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/res/mainscene.fxml"));
        Parent root = loader.load();
        MainSceneController controller = loader.getController();
        stage.setTitle("Hello, world");
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }
}
