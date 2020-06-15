package network.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class SenderThread extends Thread {
    private Socket socket;
    private Response response;
    public SenderThread(Socket socket, Response response) {
        this.socket = socket;
        this.response = response;
    }

    @Override
    public void run() {
        try {
            if (!socket.isClosed()) sendResponse(socket, response);
        } catch (IOException e) {
             e.printStackTrace();
        }


    }

    private void sendResponse(Socket socket, Response response) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(response);
    }
}
