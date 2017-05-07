package sample;

import java.io.IOException;

public class Connector implements Runnable {
    @Override
    public void run() {
        try {
            //new Thread(new MySocket("207.154.204.110", 5000)).start();
            new Thread(new MySocket("localhost", 5000)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
