package network.client;

import network.server.Response;
import utils.Handler;
import сommands.Command;
import сommands.EmptyCommand;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class Client {
    private Handler handler;
    private SocketChannel channel;
    private ByteBuffer buffer;
    private ByteArrayInputStream input;
    private ByteArrayOutputStream ouput;
    private ObjectOutputStream objectOutputStream;
    private String currentHost;
    private int currentPort;
    private Command currentCommand;
    private User user;
    private Socket client;

    public Client(Handler handler) {
        buffer = ByteBuffer.allocate(2048);
        buffer.clear();
        setHandler(handler);
        input = new ByteArrayInputStream(buffer.array());
    }

    public void connect(String host, int port) throws IOException {
        handler.writeln("Try to connect");
        try {
            client = new Socket(InetAddress.getByName(host), port);
            // channel = SocketChannel.open(new InetSocketAddress(InetAddress.getByName(host), port));
            // channel.configureBlocking(false);
            this.currentHost = host;
            this.currentPort = port;
            System.out.println("Connect successful");
        } catch (ConnectException | NullPointerException exp) {
            System.out.println("Сервер не отвечает");
            System.exit(0);
        }


    }

    public void run(String host, int port) throws IOException, ClassNotFoundException, InterruptedException {
        connect(host, port);
        while (!client.isClosed()) {
            try {
                sendData(write());
                Response response = readData();
                if (response.getCmdRes() && (currentCommand.getCommandName().equals("login") || currentCommand.getCommandName().equals("register"))) {
                    user = currentCommand.getUser();
                }
                String message = parseServerAnswer(response);
                handler.writeln(message);
            } catch (IOException exp) {

                System.out.println("Соединение потеряно");
                System.exit(0);

            } catch (NullPointerException exp) {
                System.out.println("");
            }


        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }


    private void sendData(byte[] data) throws IOException {

//        buffer.clear();
//        buffer.put(data);
//        buffer.flip();
//        while (buffer.hasRemaining()) {
//            try {
//                channel.write(buffer);
//            } catch (IOException exp) {
//                reconnect();
//                channel.write(buffer);
//            }
//        }
        OutputStream out = client.getOutputStream();
        out.write(data);
    }

    private Response readData() throws IOException, ClassNotFoundException, InterruptedException {

//        int skip = buffer.position();
//        buffer.clear();
//        byte[] temp = new byte[0];
//        while (true) {
//            int receivedBytesCount = 0;
//            try {
//                receivedBytesCount = channel.read(buffer);
//            } catch (IOException exp) {
//                reconnect();
//                receivedBytesCount = channel.read(buffer);
//            }
//            if (receivedBytesCount > 0) {
//                do {
//                    buffer.flip();
//                    if (!buffer.hasArray()) {
//                        return null;
//                    }
//                    temp = concat(temp, buffer.array());
//                    buffer.clear();
//                    Thread.sleep(50);
//                    try {
//                        receivedBytesCount = channel.read(buffer);
//                    } catch (IOException exp) {
//                        reconnect();
//                        receivedBytesCount = channel.read(buffer);
//                    }
//                } while (receivedBytesCount != 0);
//                break;
//            }
//        }
        InputStream is = client.getInputStream();
        ObjectInputStream inputStream = new ObjectInputStream(is);
        Response response = (Response) inputStream.readObject();
        return response;
    }

    private byte[] write() throws IOException {
        Command cmd = handler.nextCommand();
        currentCommand = cmd;
        if (cmd.getUser() == null && user != null) cmd.setUser(user);
        if (cmd.getCommandName().equals("exit")) {
            System.out.println("Завершение работы");
            System.exit(0);
        }
        if (cmd.getCommandName().equals("save")) {
            System.out.println("Команда не достапна пользователя");
            cmd = new EmptyCommand();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(cmd);
        out.flush();
        return outputStream.toByteArray();
    }

    private String parseServerAnswer(Response response) throws IOException, ClassNotFoundException {
        return new String(response.getData());
    }

    public byte[] concat(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    private byte[] fixHeaders(byte[] b) {
        short q = ObjectStreamConstants.STREAM_MAGIC;
        short z = ObjectStreamConstants.STREAM_VERSION;
        if (getShort(b, 0) != q || getShort(b, 2) != z) {
            byte[] newTemp = new byte[b.length + 4];
            putShort(newTemp, 0, q);
            putShort(newTemp, 2, z);
            for (int i = 0; i < b.length; i++) {
                newTemp[i + 4] = b[i];
            }
            return newTemp;
        }
        return b;
    }

    private void putShort(byte[] b, int off, short val) {
        b[off + 1] = (byte) (val);
        b[off] = (byte) (val >>> 8);
    }

    private short getShort(byte[] b, int off) {
        return (short) ((b[off + 1] & 0xFF) +
                (b[off] << 8));
    }

    private void reconnect() {
        handler.writeln("Try to reconnect");
        try {
            channel = SocketChannel.open(new InetSocketAddress(InetAddress.getByName(currentHost), currentPort));
            channel.configureBlocking(false);
            System.out.println("Reconnect successful");
        } catch (ConnectException | NullPointerException | UnknownHostException exp) {
            System.out.println("Сервер не отвечает");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Сервер не отвечает");
            System.exit(0);
        }
    }
}
