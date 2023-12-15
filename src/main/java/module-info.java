module com.example.memorypuzzle {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.logging;


    opens com.example.memorypuzzle to javafx.fxml;
    exports com.example.memorypuzzle;
    exports com.example.memorypuzzle.Server;
    opens com.example.memorypuzzle.Server to javafx.fxml;
    exports com.example.memorypuzzle.PClient;
    opens com.example.memorypuzzle.PClient to javafx.fxml;
    exports com.example.memorypuzzle.SeverLogic;
    opens com.example.memorypuzzle.SeverLogic to javafx.fxml;
}