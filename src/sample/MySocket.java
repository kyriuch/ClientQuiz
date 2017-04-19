package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.Socket;
public class MySocket extends Socket implements Runnable {

    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    ObjectInputStream objectInputStream;
    private boolean isRunning;

    MySocket(String host, int port) throws IOException {
        super(host, port);
    }

    private void proceedIncomingString(String string) {
        try {
            if(string.equals("QUESTION_COMING")) {
                QuestionPlusAnswer questionPlusAnswer = (QuestionPlusAnswer) objectInputStream.readObject();

            } else if(string.equals("WRONG")){
                // zle
            } else if(string.equals("CORRECT")) {
                // git
            }
        } catch(IOException | ClassNotFoundException e) {
            isRunning = false;
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        isRunning = true;

        try {
            printWriter = new PrintWriter(getOutputStream(), true);
            bufferedReader = new BufferedReader(new InputStreamReader(getInputStream()));
            objectInputStream = new ObjectInputStream(getInputStream());
        } catch (IOException e) {
            isRunning = false;
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = FXMLLoader.load(getClass().getResource("sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Controller controller = fxmlLoader.getController();
        controller.currentQuestion.setText("ELO");

        while(isRunning) {
            try {
                String line = bufferedReader.readLine();

                proceedIncomingString(line);
            } catch (IOException e) {
                isRunning = false;
                e.printStackTrace();
            }


        }
    }

    public void stop() {
        isRunning = false;
    }
}
