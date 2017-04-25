package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
public class MySocket extends Socket implements Runnable {

    private PrintWriter printWriter;
    private ObjectInputStream objectInputStream;
    private boolean isRunning;

    MySocket(String host, int port) throws IOException {
        super(host, port);
    }

    private void proceedIncomingTcpMessage(TcpMessage tcpMessage) throws IOException {
        if(tcpMessage.getOutType().equals(Question.class.getName())) {
            Question question = (Question) tcpMessage.getOutObject();
            Main.controller.setCurrentQuestion(question.getContent());
        }
    }

    @Override
    public void run() {
        isRunning = true;

        Logger logger = LoggerFactory.getLogger(MySocket.class);
        logger.info("Starting socket - " + String.valueOf(this));

        try {
            printWriter = new PrintWriter(getOutputStream(), true);
            objectInputStream = new ObjectInputStream(getInputStream());
        } catch (IOException e) {
            isRunning = false;
            e.printStackTrace();
        }

        while(isRunning) {
            try {
                TcpMessage tcpMessage = (TcpMessage) objectInputStream.readObject();
                logger.info("Got TcpMessage - " + String.valueOf(tcpMessage));

                proceedIncomingTcpMessage(tcpMessage);
            } catch (IOException e) {
                isRunning = false;
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    public void stop() throws IOException {
        isRunning = false;
        objectInputStream.close();
        printWriter.close();
    }
}
