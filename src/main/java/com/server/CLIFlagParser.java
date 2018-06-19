package com.server;

import java.io.IOException;

public class CLIFlagParser {

    public ServerConfig parse(String args) throws IOException {
        String[] splitArgs = args.split(" ");
        int portNumber;
        String directory;

        try{
            if(splitArgs[0].equals("-p") && splitArgs[2].equals("-d")){
                portNumber = Integer.parseInt(splitArgs[1]);
                directory = splitArgs[3];
            } else if(splitArgs[0].equals("-d") && splitArgs[2].equals("-p")){
                portNumber = Integer.parseInt(splitArgs[3]);
                directory = splitArgs[1];
            } else {
                throw new IOException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IOException();
        }
        return new ServerConfig(portNumber, directory);
    }

}
