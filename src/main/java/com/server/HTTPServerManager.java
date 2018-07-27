package com.server;

import java.net.*;
import java.io.*;

public class HTTPServerManager {

    private ServerConfig serverConfig;
    private RequestRouter requestRouter;
    private boolean logRequests;

    HTTPServerManager(ServerConfig serverConfig, RequestRouter requestRouter){
        this.serverConfig = serverConfig;
        this.requestRouter = requestRouter;
        this.logRequests = false;
    }

    HTTPServerManager(ServerConfig serverConfig, RequestRouter requestRouter, boolean logRequests){
        this.serverConfig = serverConfig;
        this.requestRouter = requestRouter;
        this.logRequests = logRequests;
    }

    public void runServer() throws IOException {
        ServerSocket serverSocket = createServerSocket();
        while(running()) {
            Socket clientSocket = openSocket(serverSocket);
            ClientWorker clientWorker = new ClientWorker(clientSocket, this.logRequests, this.serverConfig, this.requestRouter);
            Thread thread = new Thread(clientWorker);
            thread.start();
        }
    }

    protected Boolean running(){
        return true;
    }

    protected ServerSocket createServerSocket() throws IOException {
        return new ServerSocket(serverConfig.getPortNumber());
    }

    private String getFirstLine(String request) {
        return request.split("\r\n")[0].trim();
    }

    private void sendResponse(OutputStream clientSocketOutputStream, byte[] response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            bufferedOutputStream = new BufferedOutputStream(clientSocketOutputStream);
            byteArrayOutputStream.write(response);
            byte[] result = byteArrayOutputStream.toByteArray();
            bufferedOutputStream.write(result);
            byteArrayOutputStream.flush();
        }
        finally {
            byteArrayOutputStream.close();
            bufferedOutputStream.close();
        }
    }

    private Socket openSocket(ServerSocket serverSocket) throws IOException {
        return serverSocket.accept();
    }

    private BufferedReader openInputStream(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return bufferedReader;
    }

    private void stopServer(ServerSocket serverSocket, Socket clientSocket) throws IOException {
        clientSocket.close();
        serverSocket.close();
    }
}
