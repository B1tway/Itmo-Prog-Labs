package network.server;

import utils.Handler;
import сommands.Command;
import сommands.EmptyCommand;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ConnectionThread extends Thread {
    ExecutorService executorService;
    Socket socket;
    Handler handler;

    public ConnectionThread(ExecutorService executorService, Socket socket, Handler handler) {
        this.executorService = executorService;
        this.socket = socket;
        this.handler = handler;
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
            System.out.println(cmd.getCommandName());
            OutputStream outputStream = new ByteArrayOutputStream();
            cmd.setCmdManager(handler.getCmdManeger());
            handler.setPrintWriter(new PrintWriter(outputStream));
            try {
                cmd.execute(cmd.getArgs());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String message = outputStream.toString();
            System.out.println(message);
            new SenderThread(socket, new Response(message)).start();
//            Runnable task = new QueryTask(socket, handler);
//            executorService.execute(task);

//                ExecutorTask executorTask = new ExecutorTask(socket, handler, cmd);
//                executorService.submit(executorTask);
        }
    }
}
