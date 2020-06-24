import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import network.client.Client;
import network.client.ClientThread;
import network.client.controllers.Ball;
import network.client.controllers.CanvasFieldController;
import network.client.controllers.LoginController;
import network.client.controllers.MainSceneController;
import utils.Handler;
import сommands.InfoCommand;
import сommands.ShowCommand;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

public class MainClient extends Application {
    public static Client client;
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {


        launch(args);

    }


    @Override
    public void start(Stage stage) throws Exception {
        try {
            client = new Client(new Handler());
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/res/loginscreen.fxml"));
            new ClientThread(client).start();
            Parent root = loader.load();
            LoginController loginController = loader.getController();
            loginController.setClient(client);
            loginController.setWindow(stage);

//            loader.setLocation(getClass().getResource("/res/CanvasField.fxml"));
//            Parent canvasField = loader.load();
//            CanvasFieldController canvasController = loader.getController();

            stage.setTitle("Hello, world");
//            Scene canvas = new Scene(canvasField, 600, 400);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
//            stage.setScene(canvas);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
    public Scene getScene(String fmxl) throws IOException {
        String path = "/res/" + fmxl;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        Parent current = loader.load();
        return  new Scene(current, 600, 400);
    }

}
