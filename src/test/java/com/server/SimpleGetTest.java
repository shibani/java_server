package com.server;

import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;

public class SimpleGetTest {

    @Test
    public void runServerSendsHTTPOKHeader () throws IOException {
        int portNumber = 5000;
        String directoryPath = "/path/to/dir";
        ServerConfig serverConfig = new ServerConfig(portNumber, directoryPath);

        final MockServerSocket serverSocket = new MockServerSocket();

        SimpleGet simpleGet = new SimpleGet(serverConfig) {
            @Override
            protected ServerSocket createServerSocket() throws IOException {
                return serverSocket;
            }
        };

        simpleGet.runServer();

        assertTrue(serverSocket.getMockSocket().getOutgoingString().contains("200"));
    }

    @Test
    public void createServerSocketReturnsAServerSocketWithConfigPortNumber() throws IOException {
        int portNumber = 5000;
        String directoryPath = "/path/to/dir";
        ServerConfig serverConfig = new ServerConfig(portNumber, directoryPath);
        SimpleGet simpleGet = new SimpleGet(serverConfig);

        ServerSocket serverSocket = simpleGet.createServerSocket();

        assertEquals(serverSocket.getLocalPort(), portNumber);
    }
}