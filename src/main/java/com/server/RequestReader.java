package com.server;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestReader {

    private BufferedReader bufferedReader;

    RequestReader(BufferedReader bufferedReader) throws IOException {
        this.bufferedReader = bufferedReader;
    }

    public String getHeader() throws IOException {
        return readHeader();
    }

    public String getRequestedFileContents() throws IOException {
        return readRequestedFileContents();
    }

    private String readHeader() throws IOException {
        return read("\r\n");
    }

    private String readRequestedFileContents() throws IOException {
        return read("");
    }

    public String getBody() throws IOException {
        return readBody();
    }

    private String readBody() throws IOException {
        //return read("\r\n");
        return "test";
    }

    private String read(String separator) throws IOException {
        StringBuilder content = new StringBuilder();
        String line = this.bufferedReader.readLine();
        while(line != null && !(line).equals("")){
            content.append(line);
            content.append(separator);
            line = this.bufferedReader.readLine();
        }
        return content.toString().trim();
    }
}
