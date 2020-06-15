package network.server;

import сollection.SpaceStorage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UpdateThread extends Thread {
    List<Socket> sockets;
    Server server;

    public UpdateThread(Server server) {
        this.server = server;
        this.sockets = server.getSockets();
    }

    @Override
    public void run() {
        for (Socket socket : sockets) {
            if (socket.isClosed()) {
                sockets.remove(socket);
                continue;
            }
            try {
                Response response = new Response(server.getStorage());
                ObjectOutputStream objectOutputStream= new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
