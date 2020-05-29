package network.server;

import utils.Handler;
import сommands.Command;
import сommands.CommandManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ExecutorTask implements Callable<String> {
    Handler handler;
    final Command cmd;

    public ExecutorTask(Handler handler, Command cmd) {
        this.handler = handler;
        this.cmd = cmd;
    }



    @Override
    public String call() throws Exception {
        OutputStream outputStream = new ByteArrayOutputStream();
        cmd.setCmdManager(handler.getCmdManeger());
        handler.setPrintWriter(new PrintWriter(outputStream));
        try {
            cmd.execute(cmd.getArgs());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String message = outputStream.toString();
        return message;
    }
}
