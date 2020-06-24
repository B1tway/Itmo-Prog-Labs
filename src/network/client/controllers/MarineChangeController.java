package network.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import network.client.Client;
import сollection.*;
import сommands.UpdateCommand;

import java.awt.*;
import java.io.IOException;

public class MarineChangeController {
    Stage stage;
    Client client;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public TextField name;
    @FXML
    public TextField x;
    @FXML
    public TextField y;
    @FXML
    public TextField health;
    @FXML
    public TextField category;
    @FXML
    public TextField weapon;
    @FXML
    public TextField melee;
    @FXML
    public TextField chapterName;
    @FXML
    public TextField parent;
    @FXML
    public TextField count;
    @FXML
    public TextField world;
    private SpaceMarine marine;

    public void setMarine(SpaceMarine marine) {
        this.marine = marine;
    }
    public void loadMarine() {
        if (marine != null) {
            name.setText(marine.getName());
            x.setText(Long.toString(marine.getCoordinates().getX()));
            y.setText(Long.toString(marine.getCoordinates().getY()));
            health.setText(String.valueOf(marine.getHealth()));
            weapon.setText(String.valueOf(marine.getWeaponType()));
            melee.setText(String.valueOf(marine.getMeleeWeapon()));
            chapterName.setText(marine.getChapter().getName());
            parent.setText(marine.getChapter().getParentLegion());
            count.setText(String.valueOf(marine.getChapter().getMarinesCount()));
            world.setText(marine.getChapter().getWorld());
        }
    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }

    public SpaceMarine getSpaceMarine() {
        try {
            marine.setName(name.getText());
            marine.setCoordinates(new Coordinates(Long.parseLong(x.getText()),Long.parseLong(y.getText())));
            marine.setHealth(Float.parseFloat(health.getText()));
            try {
                marine.setCategory(AstartesCategory.valueOf(category.getText()));
            }
            catch (IllegalArgumentException exp) {
                marine.setCategory(null);
            }
            marine.setWeaponType(Weapon.valueOf(weapon.getText()));
            marine.setMeleeWeapon(MeleeWeapon.valueOf(melee.getText()));
            marine.setChapter(new Chapter(chapterName.getText(), parent.getText(), world.getText(), Integer.parseInt(count.getText())));
            System.out.println(marine.toString());
            return marine;
        } catch (IllegalArgumentException exp){
            exp.printStackTrace();
            return null;
        }

    }


    public void setClient(Client client) {
        this.client = client;
    }

    public void update(ActionEvent actionEvent) throws IOException {
        SpaceMarine marine = getSpaceMarine();
        if(marine != null) {
            UpdateCommand updateCommand = (UpdateCommand) client.getHandler().getCmdManeger().getCommand("update");
            updateCommand.clearAll();
            updateCommand.setUser(client.getUser());
            String[] args = new String[1];
            args[0] = String.valueOf(marine.getId());
            updateCommand.setArgs(args);
            updateCommand.addObject(marine);
            client.sendCommand(updateCommand);
        }
    }
}
