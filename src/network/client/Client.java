package network.client;

import network.server.Response;
import utils.Handler;
import сollection.SpaceStorage;
import сommands.Command;
import сommands.EmptyCommand;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    private Handler handler;
    private ByteBuffer buffer;
    private String currentHost;
    private int currentPort;
    private User user;
    private Socket clientSocket;
    private SpaceStorage storage;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    public Client(Handler handler) {
        buffer = ByteBuffer.allocate(2048);
        buffer.clear();
        setHandler(handler);
        CollesionDetection detection = new CollesionDetection();

    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void connect(String host, int port) throws IOException {
        handler.writeln("Try to connect");
        try {
            clientSocket = new Socket(InetAddress.getByName(host), port);
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.currentHost = host;
            this.currentPort = port;
            new AcceptingThread(this);
            System.out.println("Connect successful");
        } catch (ConnectException | NullPointerException exp) {
            System.out.println("Сервер не отвечает");
            System.exit(0);
        }


    }

    public Handler getHandler() {
        return handler;
    }

    public void run(String host, int port) throws IOException {
        connect(host, port);
        while (!clientSocket.isClosed()) {
            try {

//                Response response = (Response) inputStream.readObject();
//                handler.writeln(new String(response.getData()));
            } catch (IOException exp) {
                exp.printStackTrace();
                System.out.println("Соединение потеряно");
                System.exit(0);

            } catch (NullPointerException exp) {
                System.out.println("");
            }
            new AcceptingThread(this).start();

        }
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }


    private void sendData(Command cmd) throws IOException {
       outputStream.writeObject(cmd);
    }

    public void setStorage(SpaceStorage storage) {
        this.storage = storage;
    }

    public SpaceStorage getStorage() {
        return storage;
    }

    public Response readData() throws IOException, ClassNotFoundException, SocketException {
        Response response = (Response) inputStream.readObject();
        return response;
    }
    public ObjectInputStream getInputStream() {
        return inputStream;
    }


    private String parseServerAnswer(Response response) throws IOException, ClassNotFoundException {
        return new String(response.getData());
    }


    private void reconnect() {
        handler.writeln("Try to reconnect");
        try {
            clientSocket = new Socket(InetAddress.getByName(currentHost), currentPort);
            System.out.println("Reconnect successful");
        } catch (ConnectException | NullPointerException | UnknownHostException exp) {
            System.out.println("Сервер не отвечает");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Сервер не отвечает");
            System.exit(0);
        }
    }

    public User getUser() {
        return user;
    }
}
