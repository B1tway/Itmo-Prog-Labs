package network.client.controllers;

import javafx.scene.control.TextArea;
import network.server.Response;

public class ResponseController {
    public TextArea message;
    public void addMessage(String response) {
        message.appendText(response);
    }
}
