package com.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;

public class MockSocket extends Socket{

    private ByteArrayInputStream incomingStream;
    private ByteArrayOutputStream outgoingStream;

    public ByteArrayOutputStream getOutputStream(){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        outgoingStream = os;
        return os;
    }

    public ByteArrayInputStream getInputStream(){
        ByteArrayInputStream is = new ByteArrayInputStream("GET / HTTP/1.1\r\n\r\n".getBytes());
        incomingStream = is;
        return is;
    }

    public String getIncomingString(){
        return incomingStream.toString();
    }

    public String getOutgoingString() {
        return outgoingStream.toString();
    }
}
