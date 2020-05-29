package network.server;

import network.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Handler;
import сommands.Command;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


public class Server {
    private static Logger logger;
    private ServerSocket server;
    private boolean running = false;
    private Handler handler;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private OutputStream out;
    private static  ExecutorService pool;

    public Server(Handler handler) throws IOException {
        setHandler(handler);
        pool = new ForkJoinPool(2);

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
//            server.setSoTimeout(2000);
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
        Socket cur = null;
        Socket socket = null;
        while (running) {
            try {
                socket = server.accept();
                new ConnectionThread(pool, socket,handler).start();
                logger.debug("Полезователь подключился");
//                ClientWorker worker = new ClientWorker(handler,socket);
//                pool.execute(worker);
//                System.out.println(cmd.getCommandName());
//                ExecutorTask executorTask = new ExecutorTask(socket, handler, cmd);
//                pool.submit(executorTask);
            } catch (IOException exp) {
                exp.printStackTrace();
                runCli();
            }
//            GetCommandTask task = new GetCommandTask(socket);
//            Future<Command> cmdFuture = pool.submit(task);
//            Command cmd = cmdFuture.get();
//            cur = socket;
//            System.out.println(cmd.getCommandName());
//            System.out.println(socket.isClosed());
//            socket.close();
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

    private void executeCommand(Command cmd) throws IOException {
        cmd.setCmdManager(handler.getCmdManeger());
        cmd.execute(cmd.getArgs());
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
//        handler.setPrintWriter(new PrintWriter(System.out));
//        Command cmd = handler.nextCommand(4000);
//        if (!handler.isEmptyInput()) executeCommand(cmd);
//        handler.setPrintWriter(new PrintWriter(out));
    }


}