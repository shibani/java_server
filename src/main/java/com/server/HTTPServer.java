package com.server;

public class HTTPServer {

    public static void main(String[] args) throws Exception {

        CLIFlagParser cliFlagParser = new CLIFlagParser();

        ServerConfig serverConfig = cliFlagParser.parse(args);

        SimpleGet simpleGet = new SimpleGet(serverConfig);

        simpleGet.runServer();
    }
}
