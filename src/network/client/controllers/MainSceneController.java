package network.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import network.client.Client;

import java.lang.ref.PhantomReference;

public class MainSceneController {
    private Stage stage;
    private Parent root;
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

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void loadData(Client client)
    {
        cells = client.getElements();
    }
    public void setTable() {

        table.setItems(cells);
        table.refresh();
    }
    public String getCmdText() {
        return console.getText();
    }

    public void executeCommand(ActionEvent actionEvent) {


    }
}
