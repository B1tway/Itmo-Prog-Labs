package network.client;

import java.io.IOException;

public class ClientThread extends Thread {
    private Client client;
    public ClientThread(Client client) {
        this.client = client;
    }
    @Override
    public void run() {
        try {
            client.run("localhost",5555);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
