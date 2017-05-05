package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private boolean isLogged = false;

    @FXML
    private TextField loginTextField;

    @FXML
    private void onLoginClicked() throws IOException {
        if(!isLogged) {
            if(loginTextField.getText().isEmpty()) {
                return;
            }

            FXMLLoader loader =  new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = loader.load();
            Main.controller = loader.getController();
            Scene scene = new Scene(root, 860, 670);
            scene.getStylesheets().add("sample/style.css");
            Main.controller.setUserName(Main.userName = loginTextField.getText());
            Main.stage.setTitle(Main.userName + " - Quizer");
            Main.stage.setScene(scene);


            new Thread(new Connector()).start();

            isLogged = true;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginTextField.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                try {
                    onLoginClicked();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
