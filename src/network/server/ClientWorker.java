package network.server;

import utils.Handler;
import сommands.Command;
import сommands.EmptyCommand;

import java.io.*;
import java.net.Socket;

public class ClientWorker implements Runnable {
    private Handler handler;
    private Socket socket;

    public ClientWorker(Handler handler, Socket socket) {
        this.handler = handler;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream socketInput = socket.getInputStream();
            ObjectInputStream inputStream = new ObjectInputStream(socketInput);
            Command cmd = (Command) inputStream.readObject();
            OutputStream outputStream = new ByteArrayOutputStream();
            cmd.setCmdManager(handler.getCmdManeger());
            handler.setPrintWriter(new PrintWriter(outputStream));
            cmd.execute(cmd.getArgs());
            String message = outputStream.toString();
            System.out.println(message + "0");
            new SenderThread(socket, new Response(message)).start();
        } catch (IOException | ClassNotFoundException exp) {
            exp.printStackTrace();
        }

    }
}
