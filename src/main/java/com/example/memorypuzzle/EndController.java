package com.example.memorypuzzle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndController {
    @FXML
    private Label labelState;

    public void setTextState(String str) {
        this.labelState.setText(str);
    }

}
