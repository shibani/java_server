package com.server;

import com.server.mocks.MockRequestRouter;
import com.server.mocks.MockSocket;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientWorkerTest {

    @Test
    public void runSendsA200OKToClientSocket() {

        MockSocket clientSocket = new MockSocket();
        clientSocket.getOutputStream();

        MockRequestRouter mockRequestRouter = new MockRequestRouter();
        mockRequestRouter.setResponseCode(200);

        ServerConfig serverConfig = new ServerConfig(5000, "/");

        clientSocket.setRequestHeader("GET", "/");

        ClientWorker clientWorker = new ClientWorker(clientSocket,
                mockRequestRouter,
                serverConfig);

        clientWorker.run();

        assertTrue(clientSocket.getOutgoingString().contains("200"));
    }

    @Test
    public void runSendsA404NotFoundToClientSocket() {

        MockSocket clientSocket = new MockSocket();
        clientSocket.getOutputStream();

        MockRequestRouter mockRequestRouter = new MockRequestRouter();
        mockRequestRouter.setResponseCode(404);

        ServerConfig serverConfig = new ServerConfig(5000, "/");

        clientSocket.setRequestHeader("GET", "/FOO");

        ClientWorker clientWorker = new ClientWorker(clientSocket,
                mockRequestRouter,
                serverConfig);

        clientWorker.run();

        assertTrue(clientSocket.getOutgoingString().contains("404"));
    }
}