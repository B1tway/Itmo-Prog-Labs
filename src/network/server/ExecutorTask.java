package network.server;

import utils.Handler;
import сommands.Command;
import сommands.CommandManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ExecutorTask implements Runnable {
    final Socket socket;
    Handler handler;
    final Command command;

    public ExecutorTask(Socket socket, Handler handler, Command cmd) {
        this.socket = socket;
        this.handler = handler;
        this.command = cmd;
    }

    @Override
    public void run() {
            try {
                OutputStream outputStream = new ByteArrayOutputStream();
                command.setCmdManager(handler.getCmdManeger());
                handler.setPrintWriter(new PrintWriter(outputStream));
                command.execute(command.getArgs());
                String message = outputStream.toString();
                System.out.println(message);
                new SenderThread(socket, new Response(message)).start();
            } catch (IOException exp) {
                exp.printStackTrace();
            }


    }
}
