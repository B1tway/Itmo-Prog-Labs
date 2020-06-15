package network.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CollesionDetection extends Application {
   


    @Override
    public void start(Stage stage) throws Exception {
        Pane canvas = new Pane();
        Scene scene = new Scene(canvas, 800, 600);
        stage.setTitle("Collision detect");
        stage.setScene(scene);
        stage.show();
        Circle circle = new Circle(15, Color.GREEN);
        circle.relocate(100, 100);
        canvas.getChildren().addAll(circle);
    }
}
