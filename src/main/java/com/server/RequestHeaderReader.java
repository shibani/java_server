package com.server;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestHeaderReader {

    private String header;

    RequestHeaderReader(BufferedReader bufferedReader) throws IOException {
        this.header = readHeader(bufferedReader);
    }

    private String readHeader(BufferedReader br) throws IOException {
        String line;
        StringBuilder header = new StringBuilder();
        while(!(line = br.readLine()).equals("")){
            header.append(line);
        }
        return header.toString().trim();
    }

    public String getHeader(){
        return header;
    }
}
