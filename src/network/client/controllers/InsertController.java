package network.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.client.Client;
import сollection.*;
import сommands.Command;
import сommands.EmptyCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InsertController {
    Stage stage;
    Client client;
    Command cmd;
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
    @FXML
    public Button exeButton;
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

    public void setCmd(Command cmd) {
        this.cmd = cmd;
    }

    public Command getCmd() {
        return cmd;
    }

    public SpaceMarine getSpaceMarine() {
        try {
            String marineName = name.getText();
            Coordinates coordinates;
            float healthMarine = 0;
            AstartesCategory categoryMarine = null;
            Weapon weaponMarine = null;
            MeleeWeapon meleeWeapon = null;
            Chapter chapter = null;

            if (client.getHandler().isLong(x.getText()) && client.getHandler().isLong(y.getText())) {
                coordinates = new Coordinates(Long.parseLong(x.getText()), Long.parseLong(y.getText()));
            }
            else {
                return null;
            }
            if (client.getHandler().isFloat(health.getText())) {
                healthMarine = Float.parseFloat(health.getText());
            } else {
                return null;
            }
            if (client.getHandler().isCategory(category.getText())) {
                categoryMarine = AstartesCategory.valueOf(category.getText());
            }

            if (client.getHandler().isWeapon(weapon.getText())) {
                weaponMarine = Weapon.valueOf(weapon.getText());
            } else {
                return null;
            }
            if (client.getHandler().isMeleeWeapon(melee.getText())) {
                meleeWeapon = meleeWeapon.valueOf(melee.getText());
            } else {
                return null;
            }
            if (client.getHandler().isInteger(count.getText())) {
                chapter = new Chapter(chapterName.getText(), parent.getText(), world.getText(), Integer.parseInt(count.getText()));
            } else {
                return null;
            }
            SpaceMarine marine = new SpaceMarine(marineName,coordinates, healthMarine, categoryMarine, weaponMarine, meleeWeapon, chapter);
            return marine;
        } catch (IllegalArgumentException exp) {
            exp.printStackTrace();
            return null;
        }

    }


    public void setClient(Client client) {
        this.client = client;
    }


    public void execute(ActionEvent actionEvent) throws IOException {
        SpaceMarine marine = getSpaceMarine();
        if(marine != null) {
            List<Object> list = new ArrayList<>();
            list.add(marine);
            cmd.setArgsObject(list);
            client.sendCommand(cmd);
        }
        else client.sendCommand(new EmptyCommand());
    }
}
