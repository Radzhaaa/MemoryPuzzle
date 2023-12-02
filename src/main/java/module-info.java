module com.example.memorypuzzle {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.memorypuzzle to javafx.fxml;
    exports com.example.memorypuzzle;
}