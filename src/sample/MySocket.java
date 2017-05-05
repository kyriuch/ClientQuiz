package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class MySocket extends Socket implements Runnable {

    private static ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private boolean isRunning;

    public static void sendChatMessage(ChatMessage chatMessage) throws IOException {
        objectOutputStream.writeObject(new TcpMessage(chatMessage, ChatMessage.class));
    }

    public static void sendAnswer(Answer answer) throws IOException {
        objectOutputStream.writeObject(new TcpMessage(answer, Answer.class));
    }

    MySocket(String host, int port) throws IOException {
        super(host, port);
    }

    @Override
    public void run() {
        isRunning = true;

        Logger logger = LoggerFactory.getLogger(MySocket.class);
        logger.info("Starting socket - " + String.valueOf(this));

        try {
            objectOutputStream = new ObjectOutputStream(getOutputStream());
            objectInputStream = new ObjectInputStream(getInputStream());
        } catch (IOException e) {
            isRunning = false;
            e.printStackTrace();
        }

        while (isRunning) {
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
        objectOutputStream.close();
    }

    private void proceedIncomingTcpMessage(final TcpMessage tcpMessage) throws IOException {
        Logger logger = LoggerFactory.getLogger(MySocket.class);

        if (tcpMessage.getOutClass().equals(Question.class)) {
            tcpMessage.setHandler((obj) -> Main.controller.setCurrentQuestion(((Question) obj).getContent()));
        } else if (tcpMessage.getOutClass().equals(ChatMessage.class)) {
            tcpMessage.setHandler((obj) -> {
                if(((ChatMessage) obj).getType().equalsIgnoreCase("NORMAL")) {
                    Main.controller.addChatMessage(
                            (((ChatMessage) obj).getHour() + " " +
                                    ((ChatMessage) obj).getUser() + ": " + ((ChatMessage) obj).getMessage()));
                } else if(((ChatMessage) obj).getType().equalsIgnoreCase("SERVER")) {
                    Main.controller.addChatMessage(
                            ((ChatMessage) obj).getHour() + " SERVER - "+ ((ChatMessage) obj).getMessage());
                }
            });

        }

        tcpMessage.executeHandler();
    }
}
