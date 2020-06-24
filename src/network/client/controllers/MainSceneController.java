package network.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import network.client.Client;
import network.server.Response;
import сommands.ClearCommand;
import сommands.Command;

import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.util.Scanner;

public class MainSceneController implements Controller{
    public TextArea cli;
    private Stage stage;
    private Parent root;
    private Client client;
    private Parent output;
    private ResponseController responseController;
    ObservableList<TableCell> cells;
    @FXML
    private TableView<TableCell> table = new TableView<>();
    @FXML
    private TableColumn<TableCell, String> keyColumm;
    @FXML
    private TableColumn<TableCell, Integer> idColumn;
    @FXML
    private TableColumn<TableCell, String> nameColumm;
    @FXML
    private TableColumn<TableCell, String> ownerColumm;
    @FXML
    private TextField console;
    @FXML
    private Button exucuteButton;

    public void initialize() {
        cells = FXCollections.observableArrayList();
        nameColumm.setCellValueFactory(new PropertyValueFactory<>("key"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        keyColumm.setCellValueFactory(new PropertyValueFactory<>("name"));
        ownerColumm.setCellValueFactory(new PropertyValueFactory<>("owner"));
        table.setRowFactory( tv -> {
            TableRow<TableCell> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/res/marinechangescreen.fxml"));
                        Parent change = fxmlLoader.load();
                        TableCell cell = row.getItem();
                        MarineChangeController changeController = fxmlLoader.getController();
                        changeController.setMarine(cell.getSpaceMarine());
                        changeController.loadMarine();
                        Scene sceneChange = new Scene(change, 1000, 200);
                        Stage stageChange = new Stage();
                        stageChange.setTitle("Update Window");
                        stageChange.setScene(sceneChange);
                        changeController.setClient(client);
                        stageChange.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
        try {
            FXMLLoader fLoader = new FXMLLoader();
            fLoader.setLocation(getClass().getResource("/res/ResponseScreen.fxml"));
            Parent response = fLoader.load();
            responseController = fLoader.getController();

            Scene sceneResp = new Scene(response, 450, 450);
            Stage stageResp = new Stage();
            stageResp.setTitle("Output");
            stageResp.setScene(sceneResp);
            stageResp.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void printMessage(String response) {
        responseController.addMessage(response);
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setWindow(Stage window) {
        this.stage = window;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void loadData(Client client)
    {
        cells.removeAll();
        cells.addAll(client.getElements());
    }
    public void loadData()
    {
        cells = FXCollections.observableArrayList();
        for (TableCell cell: client.getElements()) {
            cells.add(cell);
        }
    }
    public void setTable() {
        loadData();
        table.setItems(cells);
        table.refresh();
    }
    public String getCmdText() {
        return console.getText();
    }

    public void executeCommand(ActionEvent actionEvent) throws IOException {
        client.getHandler().setScanner(new Scanner(getCmdText()));
        try {
            Command cmd = client.getHandler().nextCommand();
            if(!cmd.getInput()) {
                client.sendCommand(cmd);
            }
            client.getHandler().setScanner(new Scanner(System.in));
        } catch (IOException exp) {
            System.out.println("kek");
        }

    }
    public void inputSpaceMarine(Command cmd) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/res/InsertMarine.fxml"));
        Parent change = fxmlLoader.load();
        InsertController changeController = fxmlLoader.getController();
        changeController.setCmd(cmd);
        Scene sceneChange = new Scene(change, 1000, 200);
        Stage stageChange = new Stage();
        stageChange.setTitle("Insert Window");
        stageChange.setScene(sceneChange);
        changeController.setClient(client);
        stageChange.show();
    }

}
