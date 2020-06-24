package network.client;

import ch.qos.logback.classic.boolex.GEventEvaluator;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import network.client.controllers.MainSceneController;
import network.client.controllers.TableCell;
import network.server.Response;
import network.server.SenderThread;
import utils.Handler;
import сollection.SpaceMarine;
import сollection.SpaceStorage;
import сommands.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.ByteBuffer;

public class Client {
    private Handler handler;
    private MainSceneController controller;
    private ByteBuffer buffer;
    private String currentHost;
    private int currentPort;
    private User user;
    private Socket clientSocket;
    private SpaceStorage storage;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private SendingThread sender;
    public Client(Handler handler) {
        buffer = ByteBuffer.allocate(2048);
        buffer.clear();
        setHandler(handler);
        storage = handler.getStorage();

    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void connect(String host, int port) throws IOException {
        handler.writeln("Try to connect");
        try {
            clientSocket = new Socket(InetAddress.getByName(host), port);
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            this.currentHost = host;
            this.currentPort = port;
            new AcceptingThread(this);
            System.out.println("Connect successful");
        } catch (ConnectException | NullPointerException exp) {
            System.out.println("Сервер не отвечает");
            System.exit(0);
        }


    }
    public void sendCommand(Command cmd) throws IOException {
        outputStream.reset();
        sender.sendCommand(cmd);
    }
    public Handler getHandler() {
        return handler;
    }

    public void run(String host, int port) throws IOException {
        connect(host, port);
        sender = new SendingThread(this);
        sender.start();
        new AcceptingThread(this).start();
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
    public void setController(MainSceneController controller) {
        this.controller = controller;
    }

    private void sendData(Command cmd) throws IOException {
        outputStream.writeObject(cmd);
    }
    public void updateData() {
        controller.loadData();
        controller.setTable();
    }
    public void setStorage(SpaceStorage storage) {
        this.storage = storage;
        if (controller!= null)
        updateData();
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

    public ObservableList<TableCell> getElements() {
        SpaceMarine[] marines = handler.getStorage().toArray();
        ObservableList<TableCell> cells = FXCollections.observableArrayList();
        for (SpaceMarine marine : marines) {
            TableCell cell = new TableCell(handler.getSpaceManager().findKey(marine), marine);
            cells.add(cell);
        }
        return cells;
    }
    public boolean isLogin() {
        if(user == null) return false;
        return true;
    }
}
