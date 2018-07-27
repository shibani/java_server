package com.server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientWorker implements Runnable{

    private Socket clientSocket;
    private Boolean logRequests;
    private ServerConfig serverConfig;
    private RequestRouter requestRouter;

    ClientWorker(Socket clientSocket, Boolean logRequests, ServerConfig serverConfig, RequestRouter requestRouter) throws IOException {
        this.clientSocket = clientSocket;
        this.logRequests = logRequests;
        this.serverConfig = serverConfig;
        this.requestRouter = requestRouter;
    }

    public void run() {
        BufferedReader in = null;
        try {
            in = openInputStream(this.clientSocket);
            RequestReader requestReader = new RequestReader(in);
            String request = requestReader.getRequest();

            if (this.logRequests) {
                Logger logger = new Logger(serverConfig.getDirectory() + "/logs.txt");
                logger.log(getFirstLine(request));
            }

            RequestParser requestParser = new RequestParser(request, serverConfig.getDirectory());
            RequestParams requestParams = requestParser.getRequestParams();
            ResponseBuilder responseBuilder = new ResponseBuilder(requestRouter);

            sendResponse(this.clientSocket.getOutputStream(), responseBuilder.getResponse(requestParams));
        } catch (IOException e) {
            e.printStackTrace();
    }
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

    private BufferedReader openInputStream(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return bufferedReader;
    }
}
