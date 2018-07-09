package com.server;

import java.net.*;
import java.io.*;

public class SimpleGet {

    private ServerConfig serverConfig;
    private RequestRouter requestRouter;

    SimpleGet(ServerConfig serverConfig, RequestRouter requestRouter){
        this.serverConfig = serverConfig;
        this.requestRouter = requestRouter;
    }

    public void runServer() throws IOException {

        while(running()) {

            ServerSocket serverSocket = createServerSocket();
            Socket clientSocket = openSocket(serverSocket);

            BufferedReader in = openInputStream(clientSocket);

            RequestHeaderReader requestHeaderReader = new RequestHeaderReader(in);
            RequestHeaderParser requestHeaderParser = new RequestHeaderParser(requestHeaderReader.getHeader());

            PrintWriter out = openOutputStream(clientSocket);

            String method = requestHeaderParser.getMethod();
            String path = requestHeaderParser.getPath();
            int responseCode = requestRouter.getResponseCode(path, method);

            ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(responseCode, method);
            if (method.equals("OPTIONS")) {
                String[] allowedMethods = requestRouter.getAllowedMethods(path);
                String allAllowedMethods = String.join(", ", allowedMethods);
                responseHeaderBuilder.addLine("Allow", allAllowedMethods);
            }

            out.println(responseHeaderBuilder.getHeader());

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
