package com.server;
import java.net.*;
import java.io.*;

public class SimpleGet {

    private ServerConfig serverConfig;

    SimpleGet(ServerConfig serverConfig){
        this.serverConfig = serverConfig;
    }

    public void runServer() throws IOException {

        while(running()) {

            ServerSocket serverSocket = createServerSocket();
            Socket clientSocket = openSocket(serverSocket);

            BufferedReader in = openInputStream(clientSocket);

            RequestHeaderReader requestHeaderReader = new RequestHeaderReader(in);
            RequestHeaderParser requestHeaderParser = new RequestHeaderParser(requestHeaderReader.getHeader());
            RequestRouter requestRouter = new RequestRouter(requestHeaderParser.getPath());

            PrintWriter out = openOutputStream(clientSocket);

            if (requestRouter.getResult() == 200){
                sendHTTPOkHeader(out);
            }

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

    private void sendHTTPOkHeader(PrintWriter out){
        out.println("HTTP/1.1 200 OK");
    }

    private void stopServer(ServerSocket serverSocket, Socket clientSocket) throws IOException {
        clientSocket.close();
        serverSocket.close();
    }
}
