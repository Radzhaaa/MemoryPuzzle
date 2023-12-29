package com.example.memorypuzzle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.Objects;
import java.util.Random;

public class MenuController {

    @FXML
    private Button backbutton;

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
    private String sending;


    @FXML
    void initialize() {
        fieldName.visibleProperty().bind(new SimpleBooleanProperty(false));
        labelName.visibleProperty().bind(new SimpleBooleanProperty(false));
        fieldSessionId.visibleProperty().bind(new SimpleBooleanProperty(false));
        labelSessionId.visibleProperty().bind(new SimpleBooleanProperty(false));
        btnNext.visibleProperty().bind(new SimpleBooleanProperty(false));
        backbutton.visibleProperty().bind(new SimpleBooleanProperty(false));
    }

    @FXML
    void connectRoom(ActionEvent event) {
        fieldName.visibleProperty().bind(new SimpleBooleanProperty(true));
        labelName.visibleProperty().bind(new SimpleBooleanProperty(true));
        fieldSessionId.visibleProperty().bind(new SimpleBooleanProperty(true));
        labelSessionId.visibleProperty().bind(new SimpleBooleanProperty(true));
        btnNext.visibleProperty().bind(new SimpleBooleanProperty(true));
        btnCreate.visibleProperty().bind(new SimpleBooleanProperty(false));
        btnConnect.visibleProperty().bind(new SimpleBooleanProperty(false));
        backbutton.visibleProperty().bind(new SimpleBooleanProperty(true));
        btnNext.setText("Войти");
        sending = "join_session";
    }

    @FXML
    void createRoom(ActionEvent event) {
        fieldName.visibleProperty().bind(new SimpleBooleanProperty(true));
        labelName.visibleProperty().bind(new SimpleBooleanProperty(true));
        fieldSessionId.visibleProperty().bind(new SimpleBooleanProperty(false));
        labelSessionId.visibleProperty().bind(new SimpleBooleanProperty(false));
        btnNext.visibleProperty().bind(new SimpleBooleanProperty(true));
        btnCreate.visibleProperty().bind(new SimpleBooleanProperty(false));
        btnConnect.visibleProperty().bind(new SimpleBooleanProperty(false));
        backbutton.visibleProperty().bind(new SimpleBooleanProperty(true));
        btnNext.setText("Создать");
        sending = "create_session";
    }

    @FXML
    void returnBack(ActionEvent event) {
        fieldName.visibleProperty().bind(new SimpleBooleanProperty(false));
        labelName.visibleProperty().bind(new SimpleBooleanProperty(false));
        fieldSessionId.visibleProperty().bind(new SimpleBooleanProperty(false));
        labelSessionId.visibleProperty().bind(new SimpleBooleanProperty(false));
        btnNext.visibleProperty().bind(new SimpleBooleanProperty(false));
        btnCreate.visibleProperty().bind(new SimpleBooleanProperty(true));
        btnConnect.visibleProperty().bind(new SimpleBooleanProperty(true));
        backbutton.visibleProperty().bind(new SimpleBooleanProperty(false));

    }

    @FXML
    void handleNext(ActionEvent event) {
        if (Objects.equals(sending, "join_session")) {
            String name = fieldName.getText();
            String roomName = fieldSessionId.getText();
            if (name.isEmpty() || roomName.isEmpty()) {
                System.out.println("Вы не написали имя или имя комнаты. Попробуйте еще раз");
            } else {
                Main.client.joinSession(name, roomName);
            }
        } else if (Objects.equals(sending, "create_session")) {
            String name = fieldName.getText();
            String roomNameGenerator = randomRoomNameGenerator();
            if (name == null) {
                System.out.println("Вы не ввели имя");
            } else {
                try {
                    Main.client.createSession(name, roomNameGenerator);
                } catch (NumberFormatException ex) {
                    System.out.println("Неправильное имя комнаты");
                }
            }
        }
    }

    public String randomRoomNameGenerator() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        return randomString;
    }

}

