package com.example.memorypuzzle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Button playButton;

    @FXML
    private ChoiceBox<?> cardsCountChoiceBox;

    private  String[] countOfCards = {"10","20","30"};


    @FXML
    void startGame(ActionEvent event) throws IOException {
//        String selectedCardsCount = (String) cardsCountChoiceBox.getValue();
//        int cardsCount = Integer.parseInt(selectedCardsCount);
        Parent root = FXMLLoader.load(getClass().getResource("memory-game.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void BackView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        cardsCountChoiceBox.getItems().addAll(countOfCards);
//        cardsCountChoiceBox.setValue("10");
//        cardsCountChoiceBox.setConverter(new IntegerStringConverter());
    }
}
