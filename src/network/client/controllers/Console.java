package network.client.controllers;

import javafx.scene.control.TextArea;

import java.io.IOException;

public class Console {

    private TextArea area;

    public Console(TextArea ta) {
        this.area = ta;
    }

    public void write(int i) throws IOException {
        area.appendText(String.valueOf((char) i));

    }

}

