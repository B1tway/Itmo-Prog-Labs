package network.server;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.locks.ReentrantLock;

public class SenderThread extends Thread {
    private Socket socket;
    private Server server;
    private Response response;
    private ObjectOutputStream output;
    public SenderThread(Server server, Socket socket, Response response, ObjectOutputStream output) {
        this.socket = socket;
        this.response = response;
        this.output = output;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            if (!socket.isClosed()) {
                ReentrantLock lock = new ReentrantLock();
                lock.lock();
                output.reset();
                output.writeObject(response);
                sendToAll();
                output.flush();

                lock.unlock();

            }
        } catch (IOException e) {
             e.printStackTrace();
        }


    }

    private void sendToAll() throws IOException {
        Response response = new Response(server.getStorage());
        for (ConnectionThread thread : server.getConnections()) {
            if (!thread.isAlive()) {
                server.getConnections().remove(thread);
                continue;
            }
            thread.outputStream.writeObject(response);
            thread.outputStream.reset();
        }
    }
}
