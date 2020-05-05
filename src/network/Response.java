package network;

import java.io.Serializable;

public class Response implements Serializable {
    private byte[] data;
    private int size;


    public Response(byte[] data) {
        this.data = data;
        this.size = data.length;
    }


    public Response(String message) {
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
