package com.example.memorypuzzle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MemoryGameController implements Initializable {

    @FXML
    private Label correctLabel;

    @FXML
    private Label guessesLabel;

    @FXML
    private FlowPane imagesFlowPane;

    @FXML
    void playAgain(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       initializeImageView();
    }

    private void initializeImageView(){
        for(int i = 0; i < imagesFlowPane.getChildren().size();i++){
            ImageView imageView = (ImageView) imagesFlowPane.getChildren().get(i);
            imageView.setImage(new Image(Card.class.getResourceAsStream("images/back_of_card.png")));
            imageView.setUserData(i);

            imageView.setOnMouseClicked(mouseEvent -> {
                System.out.println(imageView.getUserData());
            });
        }
    }
}
