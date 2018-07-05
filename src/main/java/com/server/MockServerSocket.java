package com.server;

import java.io.IOException;
import java.net.ServerSocket;

public class MockServerSocket extends ServerSocket {

    private MockSocket mockSocket;

    public MockServerSocket() throws IOException {
        this.mockSocket = new MockSocket();
    }

    public MockServerSocket(MockSocket socket) throws IOException {
        this.mockSocket = socket;
    }

    public MockSocket accept(){
        return this.mockSocket;
    }

    public MockSocket getMockSocket(){
        return mockSocket;
    }
}
