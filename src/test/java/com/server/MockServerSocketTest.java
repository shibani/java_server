package com.server;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.*;

public class MockServerSocketTest {

    @Test
    public void acceptReturnsAMockSocket() throws IOException {

        MockServerSocket mockServerSocket = new MockServerSocket();

        MockSocket result = mockServerSocket.accept();

        assertEquals(result.getClass(), MockSocket.class);
    }

    @Test
    public void acceptReturnsProvidedMockSocket() throws IOException {
        MockSocket mockSocket = new MockSocket();
        MockServerSocket mockServerSocket = new MockServerSocket(mockSocket);
        MockSocket result = mockServerSocket.accept();

        assertSame(mockSocket, result);
    }

}