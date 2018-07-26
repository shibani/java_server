package com.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private String logFilePath;

    Logger(String filePath) {
        this.logFilePath = filePath;
    }

    public void log(String request) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(this.logFilePath, true));
            writer.append(request);
            writer.append("\r\n");
        }
        finally {
            writer.close();
        }
    }
}
