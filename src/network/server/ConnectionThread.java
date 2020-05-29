package network.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Handler;
import сommands.Command;
import сommands.EmptyCommand;

import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ConnectionThread extends Thread {
    ExecutorService executorService;
    Socket socket;
    Handler handler;
    ForkJoinPool pool;
    Logger logger;
    public ConnectionThread(ExecutorService executorService, Socket socket, Handler handler) {
        this.executorService = executorService;
        this.socket = socket;
        this.handler = handler;
        pool = new ForkJoinPool();
        logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            GetCommandTask task = new GetCommandTask(socket);
            Future<Command> cmdFuture = executorService.submit(task);
            Command cmd = new EmptyCommand();
            try {
                cmd = cmdFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            logger.debug(cmd.getCommandName());
//            OutputStream outputStream = new ByteArrayOutputStream();
//            cmd.setCmdManager(handler.getCmdManeger());
//            handler.setPrintWriter(new PrintWriter(outputStream));
//            try {
//                cmd.execute(cmd.getArgs());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            ExecutorTask exe = new ExecutorTask(handler,cmd);
            Future<String> messageFuture = executorService.submit(exe);
            String message = null;
            try {
                message = messageFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            new SenderThread(socket, new Response(message)).start();
//            Runnable task = new QueryTask(socket, handler);
//            executorService.execute(task);

//                ExecutorTask executorTask = new ExecutorTask(socket, handler, cmd);
//                executorService.submit(executorTask);
        }
    }
}
