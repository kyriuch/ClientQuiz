package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Tomek on 04.05.2017.
 */
public class Home implements Initializable {

    @FXML
    Label currentQuestionLabel;

    @FXML
    Label userNameLabel;

    @FXML
    TextField answerTextField;

    @FXML
    ListView<Text> chatListView;

    @FXML
    ListView<Text> usersListView;

    @FXML
    TextField chatTextField;

    public void setCurrentQuestion(String currentQuestion) {
        Platform.runLater(() -> currentQuestionLabel.setText(currentQuestion));
    }

    public void setUserName(String userName) {
        Platform.runLater(() -> userNameLabel.setText(userName + "!"));
    }

    public void addChatMessage(String chatMessage) {
        Platform.runLater(() -> {
            Text text = new Text(chatMessage);
            text.setFill(Color.WHITE);
            text.wrappingWidthProperty().bind(chatListView.widthProperty().subtract(25));
            chatListView.getItems().add(text);
        });
    }

    public void addUser(String userName) {
        Platform.runLater(() -> {
            Text text = new Text(userName);
            text.setFill(Color.WHITE);
            text.wrappingWidthProperty().bind(usersListView.widthProperty().subtract(25));
            usersListView.getItems().add(text);
        });
    }

    @FXML
    private void sendAnswer() throws IOException {
        if(answerTextField.getText().isEmpty()) {
            return;
        }

        MySocket.sendAnswer(new Answer(answerTextField.getText()));
        answerTextField.setText("");
    }

    @FXML
    private void sendChat() throws IOException {
        if(chatTextField.getText().isEmpty()) {
            return;
        }

        MySocket.sendChatMessage(new ChatMessage("NORMAL", Main.userName, chatTextField.getText()));
        chatTextField.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatTextField.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                try {
                    sendChat();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        answerTextField.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                try {
                    sendAnswer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
