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

        BufferedReader in = openInputStream(clientSocket);
        in.readLine();

        PrintWriter out = openOutputStream(clientSocket);
        sendHTTPOkHeader(out);

        clientSocket.close();
        serverSocket.close();
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

    private void sendHTTPOkHeader(PrintWriter out){
        out.println("HTTP/1.1 200 OK");
    }
}
