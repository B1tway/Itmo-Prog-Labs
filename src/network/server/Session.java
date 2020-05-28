package network.server;

import network.client.User;

import java.net.Socket;

public class Session {
    private User user;
    private boolean right = false;
    private Socket socket;
    public Session(Socket socket) {
        this.socket = socket;
    }
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Session(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
