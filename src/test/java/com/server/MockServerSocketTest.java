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

}