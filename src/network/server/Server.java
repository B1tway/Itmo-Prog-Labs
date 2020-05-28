package network.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Handler;
import сommands.Command;
import сommands.EmptyCommand;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;


public class Server {
    private static Logger logger;
    private ServerSocket server;
    private boolean running = false;
    private Handler handler;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private OutputStream out;
    private InputStream in;
    private ArrayList<Session> sessions;
    private static ForkJoinPool executor;

    public Server(Handler handler) throws IOException {
        setHandler(handler);
        handler.loadCollection();
        executor = new ForkJoinPool(2);
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
            sessions = new ArrayList<>();
            server.setSoTimeout(2000);
            running = true;
            logger.debug("Сервер запущен");
        } catch (IOException exp) {
            logger.debug("Не удалось запустить сервер с даннам портом");
        }
    }

    private void stopServer() throws IOException {
        running = false;
        handler.getCmdManeger().getCommand("save");
        server.close();
        System.exit(0);
    }

    public void run(int port) throws IOException {
        startServer(port);
        Callable<Socket> socketCallable = getTaskSocket(server);
        while (running) {
            try {
                Future<Socket> socketFuture = executor.submit(socketCallable);

            }
            catch (Exception exp) {
                exp.printStackTrace();
            }




        }
    }


    private Command readCmd(Socket socket) {
        Command cmd = new EmptyCommand();
        try {
            InputStream socketInput = socket.getInputStream();
            ObjectInputStream inputStream = new ObjectInputStream(socketInput);
            cmd = (Command) inputStream.readObject();
        } catch (IOException | ClassNotFoundException exp) {
            exp.printStackTrace();
        }
        return cmd;
    }

    private boolean executeCommand(Command cmd) {
        try {
            cmd.setCmdManager(handler.getCmdManeger());
            return cmd.execute(cmd.getArgs());
        } catch (IOException exp) {
            exp.printStackTrace();
            return false;
        }
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
//        handler.setPrintWriter(new PrintWriter(System.out));
//        Command cmd = handler.nextCommand(4000);
//        if (!handler.isEmptyInput()) executeCommand(cmd);
//        handler.setPrintWriter(new PrintWriter(out));
    }

    private Callable<Command> getTaskCommand(Socket socket) {
        return () -> {
            InputStream socketInput = socket.getInputStream();
            ObjectInputStream inputStream = new ObjectInputStream(socketInput);
            Command cmd = (Command) inputStream.readObject();
            return cmd;
        };
    }
    private Callable<Socket> getTaskSocket(ServerSocket server) {
        return () -> {
            return server.accept();
        };
    }

    private Runnable getTask(Socket socket) {
        return () -> {
            Command cmd = readCmd(socket);
            executeCommand(cmd);
        };
    }


}
