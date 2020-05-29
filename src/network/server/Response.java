package network.server;

import network.client.User;

import java.io.Serializable;

public class Response implements Serializable {
    private byte[] data;
    private int size;
    private User user;

    public Response(byte[] data) {
        this.data = data;
        this.size = data.length;
    }


    public Response(String message) {
        this(message.getBytes());
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getData() {
        return data;
    }

    public int getSize() {
        return size;
    }

    public String getDataString() {
        return new String(data);
    }
}
