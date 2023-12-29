package com.example.memorypuzzle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import lombok.Setter;
import java.util.ArrayList;


public class GameController {
    @FXML
    private FlowPane imagesFlowPane;
    @FXML
    private Label guessesLabel1;
    @FXML
    private Label guessesLabel2;
    @FXML
    private Label correctLabel1;
    @FXML
    private Label correctLabel2;
    @FXML
    private Label playerName1;
    @FXML
    private Label playerName2;
    @Setter
    private ArrayList<MemoryCard> deck = new ArrayList<>();
    @Setter
    private ArrayList<MemoryCard> openedCards = new ArrayList<>();

    public void setPlayerName1(String username) {
        this.playerName1.setText(username);
    }

    public void setPlayerName2(String username) {
        this.playerName2.setText(username);
    }

    public void setLabels(String match1, String match2, String guess1, String guess2) {
        this.guessesLabel1.setText(guess1);
        this.guessesLabel2.setText(guess2);
        this.correctLabel1.setText(match1);
        this.correctLabel2.setText(match2);
    }

    public void setDisableActions(boolean flag) {
        for(Node nd: imagesFlowPane.getChildren()) {
            nd.setDisable(!flag);
        }
    }

    public void setImages() {
        imagesFlowPane.getChildren().clear();
        for(MemoryCard card: deck) {
            ImageView imageView = new ImageView();
            if(!openedCards.contains(card)) {
                imageView.setImage(card.getBackOfCardImage());
                imageView.setFitWidth(60);
                imageView.setFitHeight(100);
                imageView.setUserData(String.valueOf(deck.indexOf(card)));
                imageView.setOnMouseClicked(this::flip);
            }
            else {
                imageView.setImage(card.getImage());
                imageView.setFitWidth(60);
                imageView.setFitHeight(100);
                imageView.setDisable(true);
                imageView.setUserData(String.valueOf(deck.indexOf(card)));
            }
            imagesFlowPane.getChildren().add(imageView);
        }
    }

    private void flip(MouseEvent mouseEvent) {
        String index = (String) ((Node) mouseEvent.getSource()).getUserData();
        Main.client.sendGameMove("flip_card", index);
        System.out.println("отправление карты под индексом: " + index);
    }

}
