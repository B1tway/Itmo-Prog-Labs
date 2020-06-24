package network.client;

import network.server.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

public class AcceptingThread extends Thread {
    Socket socket;
    Client client;
    ObjectInputStream in;

    public AcceptingThread(Client client) throws IOException {
        this.client = client;
        this.socket = client.getClientSocket();
        this.in = client.getInputStream();
    }

    @Override
    public void run() {
        Response response = null;

        while (!socket.isClosed()) {
            try {

                response = (Response) in.readObject();
                if (response.getStorage() == null) {
                    String message = new String(response.getData());
                    if (message.equals("Вы успешно авторизовались\n")) {
                        client.setUser(response.getUser());
                    }
                    client.getHandler().writeln(message);
                } else {
                    client.setStorage(response.getStorage());
                    client.getHandler().getSpaceManager().setStorage(response.getStorage());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
