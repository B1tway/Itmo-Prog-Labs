package network;

import java.io.Serializable;

public class Request implements Serializable {
    private byte[] data;
    private int size;

    public Request(byte[] data) {
        this.data = data;
        this.size = data.length;
    }

    public Request(String message) {
        this(message.getBytes());
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
