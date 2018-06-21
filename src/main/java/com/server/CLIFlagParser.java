package com.server;

import java.io.IOException;

public class CLIFlagParser {

    public ServerConfig parse(String[] args) throws IOException {
        int portNumber;
        String directory;

        try{
            if(args[0].equals("-p") && args[2].equals("-d")){
                portNumber = Integer.parseInt(args[1]);
                directory = args[3];
            } else if(args[0].equals("-d") && args[2].equals("-p")){
                portNumber = Integer.parseInt(args[3]);
                directory = args[1];
            } else {
                throw new IOException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IOException();
        }
        return new ServerConfig(portNumber, directory);
    }

}
