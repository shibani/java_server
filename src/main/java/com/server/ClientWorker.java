package com.server;

import java.io.*;
import java.net.Socket;

public class ClientWorker implements Runnable {

    private Socket clientSocket;
    private RequestRouter requestRouter;
    private ServerConfig serverConfig;
    private boolean logsRequests;

    ClientWorker(Socket clientSocket,
                      RequestRouter requestRouter,
                      ServerConfig serverConfig,
                      boolean logsRequests){
        this.clientSocket = clientSocket;
        this.requestRouter = requestRouter;
        this.serverConfig = serverConfig;
        this.logsRequests = logsRequests;
    }

    ClientWorker(Socket clientSocket,
                 RequestRouter requestRouter,
                 ServerConfig serverConfig){
        this.clientSocket = clientSocket;
        this.requestRouter = requestRouter;
        this.serverConfig = serverConfig;
        this.logsRequests = false;
    }


    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            RequestReader requestReader = new RequestReader(bufferedReader);
            String requestString = requestReader.getRequest();

            if (this.logsRequests) {
                Logger logger = new Logger(serverConfig.getDirectory() + "/logs.txt");
                logger.log(getFirstLine(requestString));
            }

            RequestParser requestParser = new RequestParser(requestString, serverConfig.getDirectory());
            RequestParams requestParams = requestParser.getRequestParams();
            ResponseBuilder responseBuilder = new ResponseBuilder(requestRouter);

            byte[] response = responseBuilder.getResponse(requestParams);

            sendResponse(clientSocket.getOutputStream(), response);
            clientSocket.close();
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
}
