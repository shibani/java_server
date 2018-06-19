package com.server;

public class ServerConfig {

    private int portNumber;
    private String directory;

    ServerConfig(int portNumber, String directory){
        this.portNumber = portNumber;
        this.directory = directory;
    }

    public int getPortNumber(){
        return portNumber;
    }

    public String getDirectory(){
        return directory;
    }
}
