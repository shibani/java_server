package com.server;
import java.net.*;
import java.io.*;

public class SimpleGet {

    private ServerConfig serverConfig;

    SimpleGet(ServerConfig serverConfig){
        this.serverConfig = serverConfig;
    }

    public void runServer() throws IOException {
        //ServerSocket serverSocket = new ServerSocket(serverConfig.getPortNumber())
        //Socket clientSocket = openSocket(serverSocket);
        //PrintWriter out = openOutputStream(clientSocket);
        //sendHTTPOkHeader(out);
        //closeOutputStream(out);
        //closeSocket(serverSocket);

        //create while loop
    }

    public Socket openSocket(ServerSocket serverSocket) {
    }


    public PrintWriter openOutputStream(Socket socket){

    }

    public void sendHTTPOkHeader(PrintWriter out){
        //out.println("200 ok");
    }

    public void closeOutputStream(PrintWriter out){
        //out.close();
    }

    public void closeSocket(ServerSocket serverSocket){
        //serverSocket.close();
    }
}
