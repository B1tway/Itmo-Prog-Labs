package network.server;

import сommands.Command;
import сommands.EmptyCommand;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Callable;

public class GetCommandTask implements Callable<Command> {
    final Socket socket;
    ObjectInputStream input;
    public GetCommandTask(Socket socket, ObjectInputStream inputStream) {
        this.socket = socket;
        this.input = inputStream;
    }
    @Override
    public Command call() throws IOException {
        Command cmd = new EmptyCommand();
        try {
            cmd = (Command) input.readObject();
        } catch (IOException | ClassNotFoundException exp) {
            socket.close();
        }
        System.out.println(cmd.toString());
        return cmd;
    }
}
