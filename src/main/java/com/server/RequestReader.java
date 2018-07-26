package com.server;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class RequestReader {

    private BufferedReader bufferedReader;
    private int contentLength;

    RequestReader(BufferedReader bufferedReader) throws IOException {
        this.bufferedReader = bufferedReader;
    }

    public String getHeader() throws IOException {
        String header = read("\r\n");
        String finalResponse = "";
        if (this.contentLength > 0){
            char[] body = new char[this.contentLength];
            bufferedReader.read(body, 0, this.contentLength);
            finalResponse = header + "\r\n\r\n" + new String(body);
        } else {
            finalResponse = header;
        }
        return finalResponse;
    }

    public String getRequestedFileContents() throws IOException {
        return read("");
    }

    private String read(String separator) throws IOException {
        StringBuilder content = new StringBuilder();
        String line = this.bufferedReader.readLine();
        while(line != null && !(line).equals("")){
            content.append(line);
            if(line.contains("Content-Length")){
                this.contentLength = parseInt(line.split(":")[1].trim());
            }
            content.append(separator);
            line = this.bufferedReader.readLine();
        }
        return content.toString().trim();
    }
}
