package com.example.memorypuzzle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.Getter;

public class WaitController {
    @Getter
    @FXML
    private Label roomNameLabel;

    public void setRoomNameLabel(String name) {
        roomNameLabel.setText(name);
    }

}
