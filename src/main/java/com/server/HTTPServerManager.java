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
        while(running()) {
            ServerSocket serverSocket = null;
            Socket clientSocket = null;
            try {
                serverSocket = createServerSocket();
                clientSocket = openSocket(serverSocket);

                BufferedReader in = openInputStream(clientSocket);

                RequestReader requestReader = new RequestReader(in);
                String request = requestReader.getRequest();

                if (this.logRequests) {
                    Logger logger = new Logger(serverConfig.getDirectory() + "/logs.txt");
                    logger.log(getFirstLine(request));
                }

                RequestParser requestParser = new RequestParser(request, serverConfig.getDirectory());
                RequestParams requestParams = requestParser.getRequestParams();
                ResponseBuilder responseBuilder = new ResponseBuilder(requestRouter);

                sendResponse(clientSocket.getOutputStream(), responseBuilder.getResponse(requestParams));
            }
            finally {
                stopServer(serverSocket, clientSocket);
            }
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
