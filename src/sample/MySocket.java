package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MySocket extends Socket implements Runnable {

    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private boolean isRunning;

    MySocket(String host, int port) throws IOException {
        super(host, port);
    }

    @Override
    public void run() {
        isRunning = true;

        try {
            printWriter = new PrintWriter(getOutputStream(), true);
            bufferedReader = new BufferedReader(new InputStreamReader(getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        printWriter.println("Witaj serwer");

        while(isRunning) {
            try {
                System.out.println(bufferedReader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
                isRunning = false;
            }
        }
    }

    public void stop() {
        isRunning = false;
    }
}
