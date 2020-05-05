import network.client.Client;
import utils.Handler;

import java.io.IOException;

public class MainClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Client client = new Client(new Handler());
        client.connect("host",5959);
        client.run();
    }
}