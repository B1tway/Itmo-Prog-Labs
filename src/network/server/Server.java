package network.server;

import network.Response;
import utils.Handler;
import сommands.Command;

import java.io.*;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Server {
    private ServerSocket server;
    private boolean running = false;
    private Handler handler;
    private ObjectOutputStream objectOutputStream;
    private OutputStream out;

    public Server(Handler handler) throws IOException {
        setHandler(handler);
        handler.getCmdManeger().getCommand("load").execute(null);
        out = new ByteArrayOutputStream();
        handler.setPrintWriter(new PrintWriter(out));

    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void startServer(int port) throws IOException {
        try {
            server = new ServerSocket(port);
            running = true;
        } catch (IOException exp) {
            System.out.println("Не удалось запустить сервер с даннам портом");
        }
    }

    private void stopServer() throws IOException {
        running = false;
        server.close();
    }

    public void run(int port) throws IOException {
        startServer(port);
        while (running) {
            try {
                Socket socket = server.accept();
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                while (socket.isConnected()) {
                    try {
                        Command cmd = readCommand(socket);
                        executeCommand(cmd);
                    } catch (Exception exp) {
                        System.out.println("Ошибка запроса");
                    }
                    try {
                        String message = out.toString();
                        System.out.println(message);
                        Response answ = new Response(message);
                        handler.setPrintWriter(new PrintWriter(socket.getOutputStream()));
                        socket.getOutputStream().flush();
                        handler.writeln(message);

                        //sendResponse(socket, answ);
                    } catch (Exception exp) {
                        System.out.println("Ошибка ответа");
                    }


                }
            } catch (IOException exp) {
            }

        }
    }


    private Command readCommand(Socket socket) throws IOException, ClassNotFoundException {
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream in = new ObjectInputStream(inputStream);
        socket.getOutputStream().flush();
        Command cmd = (Command) in.readObject();
        return cmd;
    }

    private void executeCommand(Command cmd) throws IOException {
        cmd.setCmdManager(handler.getCmdManeger());
        cmd.execute(cmd.getArgs());
    }

    private void sendResponse(Socket socket, Response response) throws IOException {
        socket.getOutputStream().write(123);
        socket.getOutputStream().flush();
//        ObjectOutputStream current = new ObjectOutputStream(socket.getOutputStream());
//        current.flush();
//        current.writeObject(response);
//        current.flush();
//        current.close();
    }

}
