package com.server;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;

import static org.junit.Assert.*;

public class HTTPServerManagerTest {
    @Test
    public void runServerSendsHTTPOKHeader () throws IOException {
        int portNumber = 5000;
        File resourcesDirectory = new File("src/test/resources/test-listing");
        String directoryPath = resourcesDirectory.getAbsolutePath();
        ServerConfig serverConfig = new ServerConfig(portNumber, directoryPath);

        final MockServerSocket serverSocket = new MockServerSocket();
        RequestRouter requestRouter = new RequestRouter();

        HTTPServerManager HTTPServerManager = new HTTPServerManager(serverConfig, requestRouter) {
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

        HTTPServerManager.runServer();

        assertTrue(serverSocket.getMockSocket().getOutgoingString().contains("200"));
    }

    @Test
    public void runServerSendsHTTPNotFound () throws IOException {
        int portNumber = 5000;
        File resourcesDirectory = new File("src/test/resources/test-listing");
        String directoryPath = resourcesDirectory.getAbsolutePath();

        ServerConfig serverConfig = new ServerConfig(portNumber, directoryPath);

        final MockServerSocket serverSocket = new MockServerSocket();
        MockRequestRouter mockRequestRouter = new MockRequestRouter();
        mockRequestRouter.setResponseCode(404);

        HTTPServerManager HTTPServerManager = new HTTPServerManager(serverConfig, mockRequestRouter) {
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

        HTTPServerManager.runServer();

        assertTrue(serverSocket.getMockSocket().getOutgoingString().contains("404"));
    }

    @Test
    public void runServerSendsHTTPMethodNotAllowed () throws IOException {
        int portNumber = 5000;
        File resourcesDirectory = new File("src/test/resources/test-listing");
        String directoryPath = resourcesDirectory.getAbsolutePath();

        ServerConfig serverConfig = new ServerConfig(portNumber, directoryPath);

        final MockServerSocket serverSocket = new MockServerSocket();
        MockRequestRouter mockRequestRouter = new MockRequestRouter();
        mockRequestRouter.setResponseCode(405);

        HTTPServerManager HTTPServerManager = new HTTPServerManager(serverConfig, mockRequestRouter) {
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

        HTTPServerManager.runServer();

        assertTrue(serverSocket.getMockSocket().getOutgoingString().contains("405"));
    }

    @Test
    public void createServerSocketReturnsAServerSocketWithConfigPortNumber() throws IOException {
        int portNumber = 5000;
        String directoryPath = "/path/to/dir";
        ServerConfig serverConfig = new ServerConfig(portNumber, directoryPath);
        final RequestRouter requestRouter = new RequestRouter();
        HTTPServerManager HTTPServerManager = new HTTPServerManager(serverConfig, requestRouter);

        ServerSocket serverSocket = HTTPServerManager.createServerSocket();

        assertEquals(serverSocket.getLocalPort(), portNumber);
    }

    @Test
    public void runningReturnsTrue(){

        int portNumber = 5000;
        String directoryPath = "/path/to/dir";
        ServerConfig serverConfig = new ServerConfig(portNumber, directoryPath);
        final RequestRouter requestRouter = new RequestRouter();
        HTTPServerManager HTTPServerManager = new HTTPServerManager(serverConfig, requestRouter);

        assertTrue(HTTPServerManager.running());
    }

    @Test
    public void runServerSends200OKForOptionsMethod () throws IOException {
        int portNumber = 5000;
        String directoryPath = "/path/to/dir";

        ServerConfig serverConfig = new ServerConfig(portNumber, directoryPath);
        MockSocket mockSocket = new MockSocket();
        mockSocket.setRequestHeader("OPTIONS", "/method_options");
        final MockServerSocket serverSocket = new MockServerSocket(mockSocket);
        RequestRouter requestRouter = new RequestRouter();

        HTTPServerManager HTTPServerManager = new HTTPServerManager(serverConfig, requestRouter) {
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

        HTTPServerManager.runServer();

        MockSocket m = serverSocket.getMockSocket();
        String outgoingString = m.getOutgoingString();
        assertEquals(true, outgoingString.contains("200"));
    }

    @Test
    public void runServerSendsImageJpeg() throws IOException {
        int portNumber = 5000;
        File resourcesDirectory = new File("src/test/resources/test-image-contents");
        String testDir = resourcesDirectory.getAbsolutePath();

        ServerConfig serverConfig = new ServerConfig(portNumber, testDir);
        MockSocket mockSocket = new MockSocket();
        mockSocket.setRequestHeader("GET", "/image.jpeg");
        final MockServerSocket serverSocket = new MockServerSocket(mockSocket);
        RequestRouter requestRouter = new RequestRouter();

        HTTPServerManager HTTPServerManager = new HTTPServerManager(serverConfig, requestRouter) {
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

        HTTPServerManager.runServer();

        MockSocket m = serverSocket.getMockSocket();
        String outgoingString = m.getOutgoingString();
        assertEquals(true, outgoingString.contains("200"));
    }
}