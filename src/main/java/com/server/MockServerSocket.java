package com.server;

import java.io.IOException;
import java.net.ServerSocket;

public class MockServerSocket extends ServerSocket {

    private MockSocket mockSocket;

    public MockServerSocket() throws IOException {
    }

    public MockSocket accept(){
        MockSocket ms = new MockSocket();
        mockSocket = ms;
        return ms;
    }

    public MockSocket getMockSocket(){
        return mockSocket;
    }
}
