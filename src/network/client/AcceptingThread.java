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
    }

    @Override
    public void run() {
        Response response = null;

        while (!socket.isClosed()) {
            try {
                InputStream is = socket.getInputStream();
                ObjectInputStream inputStream = new ObjectInputStream(is);
                response = (Response) inputStream.readObject();
                if (response.getStorage() == null) {
                    String message = new String(response.getData());
                    if (message.equals("Вы успешно авторизовались\n")) {
                        client.setUser(response.getUser());
                    }
                    client.getHandler().writeln(message);
                } else {
                    client.setStorage(response.getStorage());
                    client.getHandler().writeln(response.getStorage().size());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
