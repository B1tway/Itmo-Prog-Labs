package network.client;

import network.server.Response;
import сommands.Command;
import сommands.EmptyCommand;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendingThread extends Thread {
    Socket socket;
    Client client;
    ObjectOutputStream outputStream;

    public SendingThread(Client client) throws IOException {
        this.client = client;
        this.socket = client.getClientSocket();
        this.outputStream = client.getOutputStream();
    }

    @Override
    public void run() {
        Response response = null;

        while (!socket.isClosed()) {
            try {
                Command cmd = writeCommand();
                outputStream.writeObject(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}

    private Command writeCommand() throws IOException {
        Command cmd = client.getHandler().nextCommand();
        if (client.getUser() != null) cmd.setUser(client.getUser());
        if (cmd.getCommandName().equals("exit")) {
            System.out.println("Завершение работы");
            System.exit(0);
        }
        if (cmd.getCommandName().equals("save")) {
            System.out.println("Команда не доступна пользователя");
            cmd = new EmptyCommand();
        }
        return cmd;
    }
}
