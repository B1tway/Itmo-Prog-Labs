package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;
    private boolean running = false;
    private void startServer(int port) throws IOException {
        server = new ServerSocket(port);
        running = true;
    }
    private void stopServer() throws IOException {
        running = false;
        server.close();
    }
    public void run(int port) {
        try {
            startServer(port);
            while (running) {
                Socket socket = server.accept();
            }
        } catch (IOException exp) {

        }
    }
}
