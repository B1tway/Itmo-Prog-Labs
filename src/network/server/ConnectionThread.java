package network.server;

import network.client.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Handler;
import сommands.Command;
import сommands.EmptyCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ConnectionThread extends Thread {
    ExecutorService executorService;
    Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    Handler handler;
    Server server;
    Logger logger;
    Session session;
    public ConnectionThread(Server server, ExecutorService executorService, Socket socket, Handler handler) throws IOException {
        this.server = server;
        this.executorService = executorService;
        this.socket = socket;
        this.handler = handler;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());

        session = new Session();
        logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    }


    @Override
    public void run() {
        server.addSocket(outputStream);
        while (!socket.isClosed()) {
            GetCommandTask task = new GetCommandTask(socket, inputStream);
            Future<Command> cmdFuture = executorService.submit(task);
            Command cmd = new EmptyCommand();
            if(cmd.getUser() != null) session.setUser(cmd.getUser());
            try {
                cmd = cmdFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            logger.debug(cmd.getCommandName());
            String message = null;
            if(session.getRight() || cmd.getCommandName().equals("login") || cmd.getCommandName().equals("register")) {
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
            }
            else {
                message = "У вас нет прав, авторизуйтесь";
            }
            Response response = new Response(message);
            if (message.equals("Вы успешно авторизовались\n")) {
                session.setUser(cmd.getUser());
                session.setRight(true);
            }
            response.setUser(session.getUser());
            new SenderThread(socket, response,outputStream).start();
            new UpdateThread(server).start();
        }
        logger.debug("Пользователь отключился");
    }
}
