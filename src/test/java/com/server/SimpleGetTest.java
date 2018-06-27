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
        final MockRequestRouter mockRequestRouter = new MockRequestRouter();
        mockRequestRouter.setCheckPathStub(true);

        SimpleGet simpleGet = new SimpleGet(serverConfig, mockRequestRouter) {
            int runCount = 1;

            @Override
            protected Boolean running(){
                if(runCount > 0){
                    runCount -= 1;
                    return true;
                } else {
                    return false;
                }
            }

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
        final RequestRouter requestRouter = new RequestRouter();
        SimpleGet simpleGet = new SimpleGet(serverConfig, requestRouter);

        ServerSocket serverSocket = simpleGet.createServerSocket();

        assertEquals(serverSocket.getLocalPort(), portNumber);
    }

    @Test
    public void runningReturnsTrue(){

        int portNumber = 5000;
        String directoryPath = "/path/to/dir";
        ServerConfig serverConfig = new ServerConfig(portNumber, directoryPath);
        final RequestRouter requestRouter = new RequestRouter();
        SimpleGet simpleGet = new SimpleGet(serverConfig, requestRouter);

        assertTrue(simpleGet.running());

    }

    @Test
    public void runServerSendsHTTPNotFound () throws IOException {
        int portNumber = 5000;
        String directoryPath = "/path/to/dir";

        ServerConfig serverConfig = new ServerConfig(portNumber, directoryPath);

        final MockServerSocket serverSocket = new MockServerSocket();
        final MockRequestRouter mockRequestRouter = new MockRequestRouter();
        mockRequestRouter.setCheckPathStub(false);

        SimpleGet simpleGet = new SimpleGet(serverConfig, mockRequestRouter) {
            int runCount = 1;

            @Override
            protected Boolean running(){
                if(runCount > 0){
                    runCount -= 1;
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            protected ServerSocket createServerSocket() throws IOException {
                return serverSocket;
            }
        };

        simpleGet.runServer();

        assertTrue(serverSocket.getMockSocket().getOutgoingString().contains("404"));
    }
}