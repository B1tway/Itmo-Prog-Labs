package network.server;

import сollection.SpaceStorage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UpdateThread extends Thread {
    List<ObjectOutputStream> sockets;
    Server server;

    public UpdateThread(Server server) {
        this.server = server;
        this.sockets = server.getSockets();
    }

    @Override
    public void run() {
        for (ObjectOutputStream socket : sockets) {

            try {
                Response response = new Response(server.getStorage());
                socket.writeObject(response);
            } catch (SocketException exp) {
                sockets.remove(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
