package network.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.client.Client;
import сommands.Command;
import сommands.EmptyCommand;
import сommands.LoginCommand;
import сommands.ShowCommand;

import java.io.IOException;

public class LoginController implements Controller {
    private Client client;
    private Parent root;
    private Stage window;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;


    public String getLogin() {
        return loginField.getText();
    }
    public String getPassword() {
        return passwordField.getText();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    public void clickLogin(ActionEvent actionEvent) throws IOException, InterruptedException {
        Command cmd = client.getHandler().getCmdManeger().getCommand("login");
        String login = getLogin();
        String password =getPassword();
        cmd.setUser(getLogin(), getPassword());
        client.sendCommand(cmd);
        Thread.sleep(250);
        if (client.isLogin()) {
           changeScene();
        }
    }
    @FXML
    public void clickRegister(ActionEvent actionEvent) throws IOException, InterruptedException {
        Command cmd = client.getHandler().getCmdManeger().getCommand("register");
        cmd.setUser(getLogin(), getPassword());
        client.sendCommand(cmd);
        Thread.sleep(250);
        if (client.isLogin()) {
           changeScene();
        }
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }
    public void changeScene() throws IOException {
        FXMLLoader main = new FXMLLoader();
        main.setLocation(getClass().getResource("/res/mainscene.fxml"));
        Parent mainRoot = main.load();
        MainSceneController MainController = main.getController();
        MainController.initialize();
        MainController.setRoot(mainRoot);
        MainController.loadData(client);
        MainController.setTable();
        MainController.setClient(client);
        window.setScene(new Scene(mainRoot,600,400));
        MainController.setStage(window);
    }
}
