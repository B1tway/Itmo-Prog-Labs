package network.client.controllers;

import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.awt.*;

public class CanvasFieldController {
    Ball[] balls;
    @FXML
    Canvas canvas;
    Ball ball = new Ball(200, 200);

    public void render() throws InterruptedException {
        ball.show(canvas.getGraphicsContext2D());

    }
}

