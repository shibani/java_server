package com.server;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestReader {

    private String header;
    private String body;

    RequestReader(BufferedReader bufferedReader) throws IOException {
        this.header = readHeader(bufferedReader);
        this.body = readBody(bufferedReader);
    }

    private String readHeader(BufferedReader br) throws IOException {
        String thisLine;
        StringBuilder header = new StringBuilder();
        while (!(thisLine = br.readLine()).equals("")){
            header.append(thisLine + "\n");
        }
        return header.toString().trim();
    }

    private String readBody(BufferedReader br) throws IOException{
        String thisLine;
        StringBuilder body = new StringBuilder();
        while ((thisLine = br.readLine()) != null){
            body.append(thisLine);
        }
        return body.toString();
    }

    public String getHeader(){
        return header;
    }

    public String getBody(){
        return body;
    }

}
