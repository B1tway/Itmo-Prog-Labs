package network.client.controllers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Ball {
    double x, y, r;
    int speedX, speedY;
    Color color;

    public Ball(int x, int y) {
        Random random = new Random();
        this.x = x;
        this.y = y;
        this.color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        r = 50;
        speedX = 40 - random.nextInt(40) - 20;
        speedY = random.nextInt(40) - 20;
    }

    void update() {
        this.x += speedX;
        this.y += speedY;
        this.speedX = Math.max(1, this.speedX - 1);
        this.speedY = Math.max(1, this.speedY - 1);
        if (x < 0 || x > 600) {
            speedX *= -4;
        }
        if (y < 0 || y > 400) {
            speedY *= -4;
        }

    }

    void show(GraphicsContext context) {
        update();
        context.setFill(color);
        context.fillOval(x, y, r, r);
    }
}
