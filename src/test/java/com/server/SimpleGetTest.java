package com.server;

import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleGetTest {

    @Test
    public void runServerSendsHTTPOKHeader () throws IOException {
        int portNumber = 5000;
        String directoryPath = "/path/to/dir";
        ServerConfig serverConfig = new ServerConfig(portNumber, directoryPath);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream("foo".getBytes());

        final ServerSocket serverSocket = mock(ServerSocket.class);
        final Socket socket = mock(Socket.class);

        when(serverSocket.accept()).thenReturn(socket);
        when(socket.getOutputStream()).thenReturn(outputStream);
        when(socket.getInputStream()).thenReturn(inputStream);

        SimpleGet simpleGet = new SimpleGet(serverConfig) {
            @Override
            protected ServerSocket createServerSocket() throws IOException {
                return serverSocket;
            }
        };

        simpleGet.runServer();

        assertTrue(outputStream.toString().contains("200"));

    }

    @Test
    public void serverSocketReturnsAServerSocketWithConfigPortNumber() throws IOException {
        int portNumber = 5000;
        String directoryPath = "/path/to/dir";
        ServerConfig serverConfig = new ServerConfig(portNumber, directoryPath);
        SimpleGet simpleGet = new SimpleGet(serverConfig);

        ServerSocket serverSocket = simpleGet.createServerSocket();

        assertEquals(serverSocket.getLocalPort(), portNumber);
    }
}