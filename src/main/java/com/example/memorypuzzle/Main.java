package com.example.memorypuzzle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
    private Parent root;

    public Main() throws IOException {
        root = loader.load();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(root);
        stage.setTitle("Memory Puzzle");
        stage.setScene(scene);
        stage.show();
    }
    public Parent getRoot(){
        return root;
    }

    public void setController(MenuController controller){
        loader.setController(controller);
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}