package com.example.memorypuzzle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class GameController {
    // fitHeight="100.0" fitWidth="100.0" pickOnBounds="true" preserveRatio="true"
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
    private ArrayList<MemoryCard> table = new ArrayList<>();
    private ArrayList<MemoryCard> openedCards = new ArrayList<>();

    public void setPlayerName1(String nick) {
        this.playerName1.setText(nick);
    }

    public void setPlayerName2(String nick) {
        this.playerName2.setText(nick);
    }

    public void setLabels(String match1, String match2, String guess1, String guess2) {
        this.guessesLabel1.setText(guess1);
        this.guessesLabel2.setText(guess2);
        this.correctLabel1.setText(match1);
        this.correctLabel2.setText(match2);
    }

    public void setTable(ArrayList<MemoryCard> t) {
        this.table = t;
    }
    public void setOpenedCards(ArrayList<MemoryCard> t) {
        this.openedCards = t;
    }

    public void setDisableActions(boolean flag) {
        for(Node nd: imagesFlowPane.getChildren()) {
            nd.setDisable(!flag);
        }
    }

    public void setImages() {
        imagesFlowPane.getChildren().clear();
        for(MemoryCard cd: table) {
            ImageView iv = new ImageView();
            if(!openedCards.contains(cd)) {
                iv.setImage(cd.getBackOfCardImage());
                iv.setFitWidth(60);
                iv.setFitHeight(100);
                iv.setUserData(cd.getId());
                iv.setOnMouseClicked(this::flip);
            }
            else {
                iv.setImage(cd.getImage());
                iv.setFitWidth(60);
                iv.setFitHeight(100);
                iv.setDisable(true);
                iv.setUserData(cd.getId());
            }
            imagesFlowPane.getChildren().add(iv);
        }
    }

    private void flip(MouseEvent mouseEvent) {
        String id = (String) ((Node) mouseEvent.getSource()).getUserData();
        Main.client.sendGameMove("flip_card", id);
        System.out.println("отправление карты: " + id);
    }
}
