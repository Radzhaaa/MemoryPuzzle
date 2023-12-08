package com.example.memorypuzzle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MemoryGameTwoPlayerController {

    @FXML
    private Button backButton;

    @FXML
    private Label correctLabelOne;

    @FXML
    private Label correctLabelTwo;

    @FXML
    private Label guessesLabelOne;

    @FXML
    private Label guessesLabelTwo;

    @FXML
    private ImageView imageView;

    @FXML
    private FlowPane imagesFlowPane;

    @FXML
    private Button playButton;

    @FXML
    void BackView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Room.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void playAgain(ActionEvent event) {

    }

}
