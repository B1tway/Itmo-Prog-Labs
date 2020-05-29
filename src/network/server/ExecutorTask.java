package network.server;

import network.client.User;
import utils.Handler;
import сommands.Command;
import сommands.CommandManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutorTask implements Callable<String> {
    Handler handler;
    final Command cmd;
    User user;
    public ExecutorTask(Handler handler, Command cmd, User user) {
        this.handler = handler;
        this.cmd = cmd;
        this.user = user;
    }



    @Override
    public String call() throws Exception {
        ReentrantLock locker = new ReentrantLock();
        locker.lock();
        OutputStream outputStream = new ByteArrayOutputStream();
        cmd.setCmdManager(handler.getCmdManeger());
        handler.setUser(user);
        System.out.println(user.getName());
        handler.setPrintWriter(new PrintWriter(outputStream));
        try {
            cmd.execute(cmd.getArgs());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String message = outputStream.toString();
        locker.unlock();
        return message;
    }
}
