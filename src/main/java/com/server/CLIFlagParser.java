package com.server;

public class CLIFlagParser {

    public ServerConfig parse(String args){
        String[] splitArgs = args.split(" ");
        int portNumber;
        String directory;

        if(splitArgs[0].equals("-p")){
            portNumber = Integer.parseInt(splitArgs[1]);
            directory = splitArgs[3];
        } else {
            portNumber = Integer.parseInt(splitArgs[3]);
            directory = splitArgs[1];
        }
        //do regex
        //create serverconfig object
        //return serverconfig object
        //handle errors
        return new ServerConfig(portNumber, directory);
    }

}
