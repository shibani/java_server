package com.server;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class MockSocketTest {

    @Test
    public void getOutputStreamReturnsByteArrayOutputStream() throws IOException {

        MockSocket mockSocket = new MockSocket();

        ByteArrayOutputStream result = mockSocket.getOutputStream();

        assertEquals(result.getClass(), ByteArrayOutputStream.class);
    }

    @Test
    public void getInputStreamReturnsByteArrayInputStream() throws IOException {

        MockSocket mockSocket = new MockSocket();

        ByteArrayInputStream result = mockSocket.getInputStream();

        assertEquals(result.getClass(), ByteArrayInputStream.class);
    }


}