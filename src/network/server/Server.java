package network.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Handler;
import сollection.SpaceStorage;
import сommands.Command;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;


public class Server {
    private static Logger logger;
    private ServerSocket server;
    private boolean running = false;
    private Handler handler;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private OutputStream out;
    private static ExecutorService pool;
    private List<ConnectionThread> connections;

    public Server(Handler handler) throws IOException {
        setHandler(handler);
        pool = new ForkJoinPool(2);
        connections = new CopyOnWriteArrayList<>();
        handler.loadCollection();
        out = new ByteArrayOutputStream();
        logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        handler.setPrintWriter(new PrintWriter(out));


    }
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void startServer(int port) throws IOException {
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(3000);
            running = true;
            logger.debug("Сервер запущен");
        } catch (IOException exp) {
            logger.debug("Не удалось запустить сервер с даннам портом");
        }
    }

    private void stopServer() throws IOException {
        running = false;
        server.close();
        System.exit(0);
    }

    public void run(int port) throws IOException, ExecutionException, InterruptedException {
        startServer(port);
        Socket socket = null;
        runCli();
        while (running) {

            try {
                socket = server.accept();
                ConnectionThread connectionThread = new ConnectionThread(this, pool, socket, handler);
                connections.add(connectionThread);
                connectionThread.start();
                logger.debug("Полезователь подключился");

            } catch (IOException exp) {

            }


        }

    }


    private Command readCommand(Socket socket) throws IOException, ClassNotFoundException {
        Command cmd = (Command) objectInputStream.readObject();
        return cmd;
    }

    private Command readCmd(Socket socket) throws IOException, ClassNotFoundException {
        InputStream socketInput = socket.getInputStream();
        ObjectInputStream inputStream = new ObjectInputStream(socketInput);
        Command cmd = (Command) inputStream.readObject();
        return cmd;
    }

    private void executeCommand(Command cmd) throws IOException, SQLException {
        cmd.setCmdManager(handler.getCmdManeger());
        cmd.execute(cmd.getArgs());
    }
    public void addSocket(ConnectionThread connectionThread) {
        connections.add(connectionThread);
    }

    private void sendResponse(Socket socket, Response response) throws IOException {
        objectOutputStream.writeObject(response);
        objectOutputStream.flush();
    }

    private byte[] getResponse(Response response) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(response);
        return outputStream.toByteArray();
    }

    private void sendResponse(Socket socket, byte[] bytes) throws IOException {
        socket.getOutputStream().write(bytes);
    }

    private void runCli() throws IOException {
        System.out.println("\n");
        new CliThread(handler).start();
    }

    public SpaceStorage getStorage() {
        return handler.getSpaceManager().getStorage();
    }

    public List<ConnectionThread> getConnections() {
        return connections;
    }
}