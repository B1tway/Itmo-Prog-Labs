package network.client.controllers;

import javafx.fxml.FXML;

import java.awt.*;
import java.awt.event.ActionEvent;

public class TestController {
    @FXML
    private Button button;
    @FXML
    private void click(ActionEvent event) {
        button.setSize(new Dimension(100, 100));
    }
}
