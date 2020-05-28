package network.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Handler;
import сommands.Command;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private static Logger logger;
    private ServerSocket server;
    private boolean running = false;
    private Handler handler;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private OutputStream out;

    public Server(Handler handler) throws IOException {
        setHandler(handler);
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

        while (running) {
            try (Socket socket = server.accept()) {
                logger.debug("Полезователь подключился");
                while (socket.isConnected()) {
                    try {

                        Command cmd = readCmd(socket);
                        logger.debug("Пользователь запустил команду" + cmd.getCommandName());
                        executeCommand(cmd);
                        logger.debug("Команда заверщена");
                    } catch (IOException exp) {
                        logger.debug("Полезователь отключился");
                        break;
                    } catch (ClassNotFoundException e) {
                        logger.debug("Пришел неизвестный класс");
                    }
                    try {
                        String message = out.toString();
                        Response answ = new Response(message);
                        sendResponse(socket, getResponse(answ));
                        logger.debug("Отправлен ответ пользователю");
                        out.close();
                        out = new ByteArrayOutputStream();
                        handler.setPrintWriter(new PrintWriter(out));
                    } catch (Exception exp) {
                        logger.debug("Ошибка ответа");
                        exp.printStackTrace();
                        System.exit(0);
                    }
                    runCli();

                }
            } catch (IOException exp) {
                runCli();
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
        handler.setPrintWriter(new PrintWriter(System.out));
        Command cmd = handler.nextCommand(4000);
        if (!handler.isEmptyInput()) executeCommand(cmd);
        handler.setPrintWriter(new PrintWriter(out));
    }


}
