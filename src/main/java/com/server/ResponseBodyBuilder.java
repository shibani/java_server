package com.server;

import java.io.*;

public class ResponseBodyBuilder {
    private String body = "";
    private String publicDir = "";
    private RequestRouter requestRouter;

    ResponseBodyBuilder(RequestRouter requestRouter, String directory){
        this.requestRouter = requestRouter;
        this.publicDir = directory;
    }

    public String getBody(RequestParams requestParams) throws IOException {
        if (requestParams.getPath().equals("/file1")) {
            String filePath = this.publicDir + requestParams.getPath();
            File file = new File(filePath);
            this.body = getFileContents(file);
        } else if (requestParams.getPath().equals("/coffee")) {
            this.body = coffeeBody(requestParams);
        } else if (requestParams.getPath().equals("/cookie")) {
            this.body = cookieBody(requestParams);
        } else if (requestParams.getPath().equals("/eat_cookie")) {
            this.body = eatCookieBody(requestParams);
        } else if (requestParams.getPath().equals("/")) {
            this.body = directoryLinksBody(requestParams.getPath());
        } else if (requestParams.getPath().equals("/parameters")) {
            this.body = parametersBody(requestParams);
        } else {
            this.body = "";
        }
        return this.body;
    }

    private String parametersBody(RequestParams requestParams) {
        String parameterBody = "";
        for (Object key : requestParams.getQueryComponent().keySet()) {
            parameterBody = parameterBody + key.toString() + " = " + requestParams.getQueryComponent().get(key) + "\n";
        }
        return parameterBody.trim();
    }

    private String coffeeBody(RequestParams requestParams){
        return "I'm a teapot";
    }

    private String cookieBody(RequestParams requestParams){
        return "Eat";
    }

    public String eatCookieBody(RequestParams requestParams) {
        return "mmmm " + requestParams.getCookies().get("type");
    }

    private String directoryLinksBody(String path) throws IOException {
        String dirPath = this.publicDir + path;
        String linkedFilesBody = getLinkedFiles(dirPath);
        if(!linkedFilesBody.equals("")) {
            String htmlString = htmlBuilder().replace("$body", linkedFilesBody);
            return htmlString;
        } else {
            return "";
        }
    }

    private String getFileContents(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        RequestReader reader = new RequestReader(bufferedReader);
        return reader.getRequestedFileContents();
    }

    private String getLinkedFiles(String dirPath){
        StringBuilder fileNames = new StringBuilder();
        final File folder = new File(dirPath);

        if(folder.listFiles() != null){
            for (final File fileEntry : folder.listFiles()) {
                if(!(fileEntry.getName().equals(".DS_Store"))){
                    fileNames.append("<a href=\"")
                        .append("/" + fileEntry.getName())
                        .append("\">")
                        .append(fileEntry.getName())
                        .append("</a>")
                        .append("<br />");
                }
            }
            return fileNames.toString();
        } else {
            return "";
        }
    }

    private String htmlBuilder() {
        return "<!DOCTYPE html><html><head><title>HTTP Server</title></head><body>$body</body></html>";
    }
}
