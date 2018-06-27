package com.server;

public class HTTPServer {

    public static void main(String[] args) throws Exception {

        CLIFlagParser cliFlagParser = new CLIFlagParser();

        ServerConfig serverConfig = cliFlagParser.parse(args);

        RequestRouter requestRouter = new RequestRouter();

        SimpleGet simpleGet = new SimpleGet(serverConfig, requestRouter);

        simpleGet.runServer();
    }
}
