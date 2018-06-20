package com.server;
import java.net.*;
import java.io.*;

public class SimpleGet {

    private ServerConfig serverConfig;

    SimpleGet(ServerConfig serverConfig){
        this.serverConfig = serverConfig;
    }

    public void runServer() throws IOException {
        ServerSocket serverSocket = createServerSocket();
        Socket clientSocket = openSocket(serverSocket);
        PrintWriter out = openOutputStream(clientSocket);
        sendHTTPOkHeader(out);
    }

    protected ServerSocket createServerSocket() throws IOException {
        return new ServerSocket(serverConfig.getPortNumber());
    }

    private Socket openSocket(ServerSocket serverSocket) throws IOException {
        return serverSocket.accept();
    }

    private PrintWriter openOutputStream(Socket socket) throws IOException {
        PrintWriter printWriter;
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        return printWriter;
    }

    private void sendHTTPOkHeader(PrintWriter out){
        out.println("200 ok");
    }
}
