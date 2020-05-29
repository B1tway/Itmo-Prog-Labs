package network.server;

import network.client.User;

import java.net.Socket;

public class Session {
    private User user;
    private boolean right = false;

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
