module com.example.memorypuzzle {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.logging;


    opens com.example.memorypuzzle to javafx.fxml;
    exports com.example.memorypuzzle;

}