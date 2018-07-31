package com.server;

import com.server.mocks.MockSocket;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

public class MockSocketTest {

    @Test
    public void getOutputStreamReturnsByteArrayOutputStream() {

        MockSocket mockSocket = new MockSocket();

        ByteArrayOutputStream result = mockSocket.getOutputStream();

        assertEquals(result.getClass(), ByteArrayOutputStream.class);
    }

    @Test
    public void getInputStreamReturnsByteArrayInputStream() {

        MockSocket mockSocket = new MockSocket();

        ByteArrayInputStream result = mockSocket.getInputStream();

        assertEquals(result.getClass(), ByteArrayInputStream.class);
    }

    @Test
    public void getInputStreamReturnsRequestHeader() {

        MockSocket mockSocket = new MockSocket();
        mockSocket.setRequestHeader("foobar", "/");
        ByteArrayInputStream result = mockSocket.getInputStream();
        int n = result.available();
        byte[] bytes = new byte[n];
        result.read(bytes, 0, n);
        String string = new String(bytes);
        assertEquals("foobar / HTTP/1.1\r\n\r\n", string);
    }
}