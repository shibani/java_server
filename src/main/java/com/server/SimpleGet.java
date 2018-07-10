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

            ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(responseCode);

            if (method.equals("OPTIONS") && path.equals("/method_options")) {
                responseHeaderBuilder.addLine("Allow", "GET, POST, PUT, OPTIONS, HEAD");
            } else if (method.equals("OPTIONS") && path.equals("/method_options2")) {
                responseHeaderBuilder.addLine("Allow", "GET, OPTIONS, HEAD");
            } else if (method.equals("GET") && path.equals("/redirect")) {
                responseHeaderBuilder.addLine("Location", "/");
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
