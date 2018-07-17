package com.server;

import java.net.*;
import java.io.*;

public class HTTPServerManager {

    private ServerConfig serverConfig;
    private RequestRouter requestRouter;

    HTTPServerManager(ServerConfig serverConfig, RequestRouter requestRouter){
        this.serverConfig = serverConfig;
        this.requestRouter = requestRouter;
    }

    public void runServer() throws IOException {

        while(running()) {

            ServerSocket serverSocket = createServerSocket();
            Socket clientSocket = openSocket(serverSocket);

            BufferedReader in = openInputStream(clientSocket);

            RequestReader requestHeaderReader = new RequestReader(in);
            RequestHeaderParser requestHeaderParser = new RequestHeaderParser(requestHeaderReader.getHeader());

            PrintWriter out = openOutputStream(clientSocket);

            RequestParams requestParams = requestHeaderParser.getRequestParams();

            ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(requestRouter);
            ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(requestRouter, serverConfig.getDirectory());
            ResponseBuilder responseBuilder = new ResponseBuilder(responseHeaderBuilder, responseBodyBuilder);

            out.println(responseBuilder.getResponse(requestParams));

            stopServer(serverSocket, clientSocket);
        }
    }

    protected Boolean running(){
        return true;
    }

    protected ServerSocket createServerSocket() throws IOException {
        return new ServerSocket(serverConfig.getPortNumber());
    }

    private Socket openSocket(ServerSocket serverSocket) throws IOException {
        return serverSocket.accept();
    }

    private BufferedReader openInputStream(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return bufferedReader;
    }

    private PrintWriter openOutputStream(Socket socket) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        return printWriter;
    }

    private void stopServer(ServerSocket serverSocket, Socket clientSocket) throws IOException {
        clientSocket.close();
        serverSocket.close();
    }
}
