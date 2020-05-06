package network.client;

import network.Response;
import utils.Handler;
import сommands.Command;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public class Client {
    private Handler handler;
    private SocketChannel channel;
    private ByteBuffer buffer;
    private ByteArrayInputStream input;
    private ByteArrayOutputStream ouput;
    private ObjectOutputStream objectOutputStream;

    public Client(Handler handler) {
        buffer = ByteBuffer.allocate(8192);
        buffer.clear();
        setHandler(handler);
        ouput = new ByteArrayOutputStream();
        input = new ByteArrayInputStream(buffer.array());
    }

    public void connect(String host, int port) throws IOException {
        handler.writeln("\"===============\\nConnecting to the network.client.server...\\n Host: \" + host + \"\\n Port: \" + port");
        try {
            channel = SocketChannel.open(new InetSocketAddress(InetAddress.getByName(null), port));
            channel.configureBlocking(false);
        } catch (ConnectException exp) {
            System.out.println("Сервер не отвечает");
        }

    }

    public void run() throws IOException, ClassNotFoundException, InterruptedException {
        while (channel.isConnected()) {
            ouput = new ByteArrayOutputStream();
            ouput.flush();
            sendData(write());
            ouput.flush();
            Thread.sleep(30);
            ouput.flush();
            Response response = readData();
            ouput.flush();
            String message = parseServerAnswer(response);
            handler.writeln(message);


        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }


    private void sendData(byte[] data) throws IOException {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    private Response readData() throws IOException, ClassNotFoundException, InterruptedException {

        int skip = buffer.position();
        buffer.clear();
        byte[] temp = new byte[0];
        while (true) {
            int receivedBytesCount = channel.read(buffer);
            if (receivedBytesCount > 0) {
                do {
                    buffer.flip();
                    if (!buffer.hasArray()) {
                        return null;
                    }
                    temp = concat(temp, buffer.array());
                    buffer.clear();
                    Thread.sleep(50);
                    receivedBytesCount = channel.read(buffer);
                } while (receivedBytesCount != 0);
                break;
            }
        }
        System.out.println(Arrays.toString(temp));
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(temp));
        Response response = (Response) in.readObject();
        ouput.flush();
        return response;

    }

    private byte[] write() throws IOException {
        Command cmd = handler.nextCommand();
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

}
