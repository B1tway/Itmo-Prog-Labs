package network.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Handler;
import сommands.Command;
import сommands.EmptyCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ConnectionThread extends Thread {
    ExecutorService executorService;
    Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    Handler handler;
    Server server;
    Logger logger;
    Session session;
    List<ConnectionThread> connections;

    public ConnectionThread(Server server, ExecutorService executorService, Socket socket, Handler handler) throws IOException {
        this.server = server;
        this.executorService = executorService;
        this.socket = socket;
        this.handler = handler;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        connections = server.getConnections();
        session = new Session();
        logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    }


    @Override
    public void run() {
        new SenderThread(server, socket, new Response(handler.getStorage()), outputStream).start();
        while (!socket.isClosed()) {
            try {
                outputStream.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            GetCommandTask task = new GetCommandTask(socket, inputStream);
            Future<Command> cmdFuture = executorService.submit(task);
            Command cmd = new EmptyCommand();
            if (cmd.getUser() != null) session.setUser(cmd.getUser());
            try {
                cmd = cmdFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            logger.debug(cmd.getCommandName());
            String message = null;
            if (session.getRight() || cmd.getCommandName().equals("login") || cmd.getCommandName().equals("register")) {
                session.setUser(cmd.getUser());
                ExecutorTask exe = new ExecutorTask(handler, cmd, session.getUser());
                Future<String> messageFuture = executorService.submit(exe);
                try {
                    message = messageFuture.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                message = "У вас нет прав, авторизуйтесь";
            }
            Response response = new Response(message);
            if (message.equals("Вы успешно авторизовались\n")) {
                session.setUser(cmd.getUser());
                session.setRight(true);
            }
            response.setUser(session.getUser());
            new SenderThread(server,socket, response, outputStream).start();
//            sendToAll();
        }
        logger.debug("Пользователь отключился");
    }

    private void sendToAll() {
        logger.debug("Обновляем всех");
        Response response = new Response("abba");
//        Response response = new Response(server.getStorage());
        for (ConnectionThread thread : connections) {
            if (!thread.isAlive()) {
                connections.remove(thread);
                continue;
            }
            new SenderThread(server, thread.socket, response, thread.outputStream).start();
        }
    }

}
