package network.server;

import сommands.Command;
import сommands.EmptyCommand;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

public class GetCommandTask implements Callable<Command> {
    final Socket socket;
    public GetCommandTask(Socket socket) {
        this.socket = socket;
    }
    @Override
    public Command call() throws Exception {
        Command cmd = new EmptyCommand();
        try {
            InputStream socketInput = socket.getInputStream();
            ObjectInputStream inputStream = new ObjectInputStream(socketInput);
            cmd = (Command) inputStream.readObject();
        } catch (IOException | ClassNotFoundException exp) {
            exp.printStackTrace();
        }

        return cmd;
    }
}
