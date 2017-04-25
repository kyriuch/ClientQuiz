package sample;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
public class MySocket extends Socket implements Runnable {

    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private boolean isRunning;

    MySocket(String host, int port) throws IOException {
        super(host, port);
    }

    private void proceedIncomingString(String string) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        TcpMessage tcpMessage = objectMapper.readValue(string, TcpMessage.class);

        Logger logger = LoggerFactory.getLogger(MySocket.class);
        logger.info(String.valueOf(tcpMessage));

        if(tcpMessage.getOutObject() instanceof Question) {
            logger.info("TUTAJ JESTEM");
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
