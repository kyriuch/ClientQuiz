package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Label currentQuestion;

    public void setCurrentQuestion(String s) {
        Platform.runLater(() -> currentQuestion.setText(s));
    }

    public Controller getController() {
        return this;
    }
}
