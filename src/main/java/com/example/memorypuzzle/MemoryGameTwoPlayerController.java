package com.example.memorypuzzle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class MemoryGameTwoPlayerController implements Initializable {
    private ArrayList<MemoryCard> cardsInGame;

    @FXML
    private Button backButton;

    @FXML
    private Label correctLabel1;

    @FXML
    private Label correctLabel2;

    @FXML
    private Label guessesLabel1;

    @FXML
    private Label guessesLabel2;

    @FXML
    private ImageView imageView;

    @FXML
    private FlowPane imagesFlowPane;

    @FXML
    private Button playButton;

    private MemoryCard card1, card2;
    private int numOfGuesses1;
    private int numOfMatches1;

    private int numOfGuesses2;
    private int numOfMatches2;

    private int whoIs = 1;


    @FXML
    void BackView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Room.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
                imageView.disableProperty().set(false);
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
            imageView.disableProperty().set(true);
        } else if (card2 == null) {
            if (whoIs == 1){
                numOfGuesses1++;
            }else if (whoIs == 2){
                numOfGuesses2++;
            }
            card2 = cardsInGame.get(indexOfCard);
            imageView.setImage(card2.getImage());
            checkForMatch();
            updateLabels();
        }

    }

    @FXML
    void playAgain() {
        card1 = null;
        card2 = null;
        DeckOfCards deck = new DeckOfCards();
        deck.shuffle();
        cardsInGame = new ArrayList<>();

        numOfGuesses1 = 0;
        numOfMatches1 = 0;
        numOfGuesses2 = 0;
        numOfMatches2 = 0;

        correctLabel1.setText(Integer.toString(0));
        guessesLabel1.setText(Integer.toString(0));
        correctLabel2.setText(Integer.toString(0));
        guessesLabel2.setText(Integer.toString(0));

        for (int i = 0; i < imagesFlowPane.getChildren().size() / 2; i++) {
            Card cardDealt = deck.dealTopCard();
            cardsInGame.add(new MemoryCard(cardDealt.getSuit(), cardDealt.getFaceName()));
            cardsInGame.add(new MemoryCard(cardDealt.getSuit(), cardDealt.getFaceName()));
        }
        Collections.shuffle(cardsInGame);
        flipAllCards();
    }

    private void updateLabels() {
        correctLabel1.setText(Integer.toString(numOfMatches1));
        guessesLabel1.setText(Integer.toString(numOfGuesses1));
        correctLabel2.setText(Integer.toString(numOfMatches2));
        guessesLabel2.setText(Integer.toString(numOfGuesses2));

    }

    private void checkForMatch() {
        if (card1.isSameCard(card2) & (card1 != card2) && (!card1.isMatched()) && (!card2.isMatched()))  {
            if (whoIs == 1){
                numOfMatches1++;
            }else if(whoIs == 2){
                numOfMatches2++;
            }
            card1.setMatched(true);
            card2.setMatched(true);
        }else {
            if(whoIs == 1){
                whoIs = 2;
            }else if(whoIs == 2){
                whoIs = 1;
            }

        }
        card1 = null;
        card2 = null;
    }

}
