package com.example.memorypuzzle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateRoomController {

    @FXML
    private Button backButton;

    @FXML
    private Button createRoomButton;

    @FXML
    private Label roomCode;

    @FXML
    void BackView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Room.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void CreateRoom(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("memory-game-two-player.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}