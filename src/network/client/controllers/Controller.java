package network.client.controllers;

import javafx.scene.Parent;
import javafx.stage.Stage;
import network.client.Client;

public interface Controller {
    public void setWindow(Stage window);
    public void setRoot(Parent root);
    public void setClient(Client client);
}
