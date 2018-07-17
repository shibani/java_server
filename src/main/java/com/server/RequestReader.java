package com.server;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestReader {

    private BufferedReader bufferedReader;

    RequestReader(BufferedReader bufferedReader) throws IOException {
        this.bufferedReader = bufferedReader;
    }

    public String getHeader() throws IOException {
        return readHeader(this.bufferedReader);
    }

    public String getBody() throws IOException {
        return readBody(this.bufferedReader);
    }

    private String readHeader(BufferedReader br) throws IOException {
        return read(br, "\r\n");
    }

    private String readBody(BufferedReader br) throws IOException {
        return read(br, "");
    }

    private String read(BufferedReader br, String separator) throws IOException {
        StringBuilder content = new StringBuilder();
        String line = br.readLine();
        while(line != null && !(line).equals("")){
            content.append(line);
            content.append(separator);
            line = br.readLine();
        }
        return content.toString().trim();
    }
}
