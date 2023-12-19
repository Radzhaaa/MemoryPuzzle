package com.example.memorypuzzle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomController implements Initializable {
    @FXML
    private TextField nameTextField;

    @FXML
    private Button backButton;

    @FXML
    private Button connectRoomButton;

    @FXML
    private Button createRoomButton;
    String name;





    @FXML
    void BackView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void ConnectRoom(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ConnectToRoom.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void createRoom(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateRoom.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.connectRoomButton.setDisable(true);
        this.createRoomButton.setDisable(true);

        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                this.connectRoomButton.setDisable(false);
                this.createRoomButton.setDisable(false);
            } else {
                this.connectRoomButton.setDisable(true);
                this.createRoomButton.setDisable(true);
            }
        });
    }

    public void saveName(ActionEvent event) {
        this.name = this.nameTextField.getText();
//        System.out.println(name);
    }
}
