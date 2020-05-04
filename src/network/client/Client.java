package network.client;

import utils.Handler;
import сommands.Command;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    private Handler handler;
    private SocketChannel channel;
    private ByteBuffer buffer;
    public Client() {
        buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        handler = new Handler();
    }
    private void connect(String host, int port) throws IOException {
        handler.writeln("\"===============\\nConnecting to the network.client.server...\\n Host: \" + host + \"\\n Port: \" + port");
        channel = SocketChannel.open(new InetSocketAddress(host,port));
        channel.configureBlocking(false);
    }
    private void nextCommand() throws IOException {
        String userInput;
        while (channel.isConnected()) {

        }

    }
    private void write(Command cmd) {
        if(cmd == null) {

        }


    }
}
