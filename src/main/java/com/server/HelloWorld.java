package com.server;

public class HelloWorld {

    public static void main(String[] args) throws Exception {

        //ServerConfig serverConfig = CLIFlagParser.parse(args);

        String string = "-p 5000 -d /path/to/directory";
        System.out.println(string.split(" ")[0]);

    }
}
