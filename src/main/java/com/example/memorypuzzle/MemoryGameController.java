package com.example.memorypuzzle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class MemoryGameController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Label correctLabel;

    @FXML
    private Label guessesLabel;

    @FXML
    public FlowPane imagesFlowPane;

    private ArrayList<MemoryCard> cardsInGame;
    private MemoryCard card1, card2;
    private int numOfGuesses;
    private int numOfMatches;

    @FXML
    void BackView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void playAgain() {
        card1 = null;
        card2 = null;
        DeckOfCards deck = new DeckOfCards();
        deck.shuffle();
        cardsInGame = new ArrayList<>();

        numOfGuesses = 0;
        numOfMatches = 0;
        correctLabel.setText(Integer.toString(0));
        guessesLabel.setText(Integer.toString(0));

        for (int i = 0; i < imagesFlowPane.getChildren().size() / 2; i++) {
            Card cardDealt = deck.dealTopCard();
            cardsInGame.add(new MemoryCard(cardDealt.getSuit(), cardDealt.getFaceName()));
            cardsInGame.add(new MemoryCard(cardDealt.getSuit(), cardDealt.getFaceName()));
        }
        Collections.shuffle(cardsInGame);
        flipAllCards();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeImageView();
        playAgain();
    }

    private void initializeImageView() {
        for (int i = 0; i < imagesFlowPane.getChildren().size(); i++) {
            ImageView imageView = (ImageView) imagesFlowPane.getChildren().get(i);
            imageView.setImage(new Image(Card.class.getResourceAsStream("images/back_of_card.png")));
            imageView.setUserData(i);

            imageView.setOnMouseClicked(mouseEvent -> {
                flipCard((int) imageView.getUserData());
            });
        }
    }

    private void flipAllCards() {
        for (int i = 0; i < cardsInGame.size(); i++) {
            ImageView imageView = (ImageView) imagesFlowPane.getChildren().get(i);
            MemoryCard card = cardsInGame.get(i);
            if (card.isMatched()) {
                imageView.setImage(card.getImage());
            } else {
                imageView.setImage(card.getBackOfCardImage());
            }
        }
    }

    private void flipCard(int indexOfCard) {
        if (card1 == null && card2 == null) {
            flipAllCards();
        }
        ImageView imageView = (ImageView) imagesFlowPane.getChildren().get(indexOfCard);
        if (card1 == null) {
            card1 = cardsInGame.get(indexOfCard);
            imageView.setImage(card1.getImage());
        } else if (card2 == null) {
            numOfGuesses++;
            card2 = cardsInGame.get(indexOfCard);
            imageView.setImage(card2.getImage());
            checkForMatch();
            updateLabels();
        }

    }

    private void updateLabels() {
        correctLabel.setText(Integer.toString(numOfMatches));
        guessesLabel.setText(Integer.toString(numOfGuesses));

    }

    private void checkForMatch() {
        if (card1.isSameCard(card2) && (card1 != card2)) {
            numOfMatches++;
            card1.setMatched(true);
            card2.setMatched(true);
        }
        card1 = null;
        card2 = null;
    }
}
