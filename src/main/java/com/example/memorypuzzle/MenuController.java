package com.example.memorypuzzle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class MenuController {

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnNext;

    @FXML
    private TextField fieldName;

    @FXML
    private TextField fieldSessionId;

    @FXML
    private Label labelName;

    @FXML
    private Label labelSessionId;

    private SimpleBooleanProperty connecting = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty actionHapping = new SimpleBooleanProperty(false);
    private String sending;


    @FXML
    void initialize() {
        fieldName.visibleProperty().bind(actionHapping);
        labelName.visibleProperty().bind(actionHapping);
        fieldSessionId.visibleProperty().bind(connecting);
        labelSessionId.visibleProperty().bind(connecting);
        btnNext.visibleProperty().bind(actionHapping);
    }

    @FXML
    void connectRoom(ActionEvent event) {
        actionHapping.set(true);
        connecting.set(true);
        sending = "join_session";
    }

    @FXML
    void createRoom(ActionEvent event) {
        actionHapping.set(true);
        connecting.set(false);
        sending = "create_session";
    }

    @FXML
    void handleNext(ActionEvent event) {
        if (Objects.equals(sending, "join_session")) {
            String name = fieldName.getText();
            String s_id = fieldSessionId.getText();
            if (name == null || s_id == null) {
                System.out.println("Вы не написали имя или айди комнаты. Попробуйте еще раз");
            } else {
                Main.client.joinSession(name, s_id);
            }
        } else if (Objects.equals(sending, "create_session")) {
            String name = fieldName.getText();
            String session_id = UUID.randomUUID().toString();
            if (name == null) {
                System.out.println("Вы не ввели имя");
            } else {
                try {
                    Main.client.createSession(name, session_id);
                } catch (NumberFormatException ex) {
                    System.out.println("Неправильный айди комнаты");
                }
            }
        }
    }
}

