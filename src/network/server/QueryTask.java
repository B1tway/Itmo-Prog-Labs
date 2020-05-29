package network.server;

import utils.Handler;
import сommands.Command;
import сommands.EmptyCommand;

import java.io.*;
import java.net.Socket;

public class QueryTask implements Runnable {
    Socket socket;
    Handler handler;
    public QueryTask(Socket socket, Handler handler) {
        this.socket = socket;
        this.handler = handler;
    }
    @Override
    public void run() {
        Command cmd = new EmptyCommand();
        try {
            InputStream socketInput = socket.getInputStream();
            ObjectInputStream inputStream = new ObjectInputStream(socketInput);
            cmd = (Command) inputStream.readObject();
        } catch (IOException | ClassNotFoundException exp) {
            exp.printStackTrace();
        }
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
    }
}
