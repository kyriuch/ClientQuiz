package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                Logger logger = LoggerFactory.getLogger(MySocket.class);
                logger.info("Starting reading object");
                Question question = (Question) objectInputStream.readObject();
                logger.info("Read object: " + question);

                Main.controller.setCurrentQuestion(question.getContent());
            } else if(string.equals("WRONG")){
            } else if(string.equals("CORRECT")) {
            }
        } catch(IOException | ClassNotFoundException e) {

            isRunning = false;
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        isRunning = true;
        Logger logger = LoggerFactory.getLogger(MySocket.class);
        logger.info("Starting socket - " + String.valueOf(this));

        try {
            printWriter = new PrintWriter(getOutputStream(), true);
            bufferedReader = new BufferedReader(new InputStreamReader(getInputStream()));
            objectInputStream = new ObjectInputStream(new BufferedInputStream(getInputStream()));
        } catch (IOException e) {
            isRunning = false;
            e.printStackTrace();
        }



        while(isRunning) {
            try {
                String line = bufferedReader.readLine();
                logger.info("Got line - " + line);

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
