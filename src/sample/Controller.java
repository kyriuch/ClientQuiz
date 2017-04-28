package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    boolean isLogged = false;

    @FXML
    private Label currentQuestion;

    @FXML
    private TextField loginTextField;

    public void setCurrentQuestion(String s) {
        Platform.runLater(() -> currentQuestion.setText(s));
    }

    @FXML
    private void onLoginClicked() {
        if(!isLogged) {
            if(loginTextField.getText().isEmpty()) {
                return;
            }

            new Thread(new Connector()).start();
            currentQuestion.setStyle("visibility: visible");

            isLogged = true;
        }
    }

    public Controller getController() {
        return this;
    }
}
