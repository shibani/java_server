package com.server;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ResponseBodyBuilder {
    private byte[] body;
    private RequestRouter requestRouter;
    public ResponseParams responseParams;

    ResponseBodyBuilder(RequestRouter requestRouter){
        this.requestRouter = requestRouter;
    }

    public byte[] getBody(RequestParams requestParams, ResponseParams responseParams) throws IOException {
        this.responseParams = responseParams;
        String path = requestParams.getPath();
        String method = requestParams.getMethod();
        if (path.equals("/file1")) {
            this.body = fileContentsBody(requestParams);
        } else if (path.equals("/patch-content.txt")) {
            this.body = patchContentBody(requestParams);
        } else if (path.equals("/coffee")) {
            this.body = coffeeBody(requestParams);
        } else if (path.equals("/cookie")) {
            this.body = cookieBody(requestParams);
        } else if (path.equals("/eat_cookie")) {
            this.body = eatCookieBody(requestParams);
        } else if (path.equals("/")) {
            this.body = directoryLinksBody(requestParams);
        } else if (path.equals("/parameters")) {
            this.body = parametersBody(requestParams);
        } else if (path.equals("/image.jpeg") || path.equals("/image.png") || path.equals("/image.gif")) {
            this.body = imageBody(requestParams);
        } else if (path.equals("/partial_content.txt")) {
            this.body = partialContentBody(requestParams);
        } else if (path.equals("/cat-form/data") && method.equals("GET")) {
            this.body = readBody(requestParams);
        } else if (path.equals("/cat-form") && method.equals("POST")) {
            this.body = createBody(requestParams);
        } else if (path.equals("/cat-form/data") && method.equals("PUT")) {
            this.body = updateBody(requestParams);
        } else if (path.equals("/cat-form/data") && method.equals("DELETE")) {
            this.body = deleteBody(requestParams);
        } else if (path.equals("/logs")) {
            this.body = basicAuthorizationBody(requestParams);
        } else {
            this.body = new byte[0];
        }
        return this.body;
    }

    private byte[] parametersBody(RequestParams requestParams) {
        String parameterBody = "";
        for (Object key : requestParams.getQueryComponent().keySet()) {
            parameterBody = parameterBody + key.toString() + " = " + requestParams.getQueryComponent().get(key) + "\n";
        }
        String value = parameterBody.trim();
        return value.getBytes();
    }

    private byte[] coffeeBody(RequestParams requestParams){
        return "I'm a teapot".getBytes();
    }

    private byte[] cookieBody(RequestParams requestParams){
        return "Eat".getBytes();
    }

    public byte[] eatCookieBody(RequestParams requestParams) {
        String value = "mmmm " + requestParams.getCookies().get("type");
        return value.getBytes();
    }

    private byte[] directoryLinksBody(RequestParams requestParams) throws IOException {
        String dirPath = requestParams.getDirectory() + requestParams.getPath();
        String linkedFilesBody = getLinkedFiles(dirPath);
        String value = "";
        if(!linkedFilesBody.equals("")) {
            value = htmlBuilder().replace("$body", linkedFilesBody);
        }
        return value.getBytes();
    }

    private byte[] fileContentsBody(RequestParams requestParams) throws IOException {
        String filePath = requestParams.getDirectory() + requestParams.getPath();
        File file = new File(filePath);
        return getFileContents(file);
    }

    private byte[] getFileContents(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        RequestReader reader = new RequestReader(bufferedReader);
        return reader.getRequestedFileContents().getBytes();
    }

    private String getLinkedFiles(String dirPath){
        StringBuilder fileNames = new StringBuilder();
        final File folder = new File(dirPath);

        if(folder.listFiles() != null){
            for (final File fileEntry : folder.listFiles()) {
                if(!(fileEntry.getName().equals(".DS_Store"))){
                    fileNames.append(htmlLinkBuilder(fileEntry));
                }
            }
            return fileNames.toString();
        } else {
            return "";
        }
    }

    private String htmlLinkBuilder(File file){
        StringBuilder fileLink = new StringBuilder();
        fileLink.append("<a href=\"")
                .append("/" + file.getName())
                .append("\">")
                .append(file.getName())
                .append("</a>")
                .append("<br />");
        return fileLink.toString();
    }

    private String htmlBuilder() {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>HTTP Server</title>" +
                "</head>" +
                "<body>$body</body>" +
                "</html>";
    }

    private byte[] imageBody(RequestParams requestParams) throws IOException {
        File f = new File(requestParams.getDirectory() + requestParams.getPath());
        ByteArrayOutputStream outputStream = null;
        byte[] imageContents;

        try {
            ImageInputStream inputStream = ImageIO.createImageInputStream(f);
            outputStream = new ByteArrayOutputStream();
            int byteToBeRead = -1;
            while ((byteToBeRead = inputStream.read()) != -1) {
                outputStream.write(byteToBeRead);
            }
            imageContents = outputStream.toByteArray();
            outputStream.flush();
        }
        finally {
            outputStream.close();
        }
        return imageContents;
    }

    private byte[] partialContentBody(RequestParams requestParams) throws IOException {
        String partialContent = "";
        int start = requestParams.getRange().get("start");
        int stop = requestParams.getRange().get("stop");

        String filePath = requestParams.getDirectory() + requestParams.getPath();
        File file = new File(filePath);
        String contents = new String(getFileContents(file));

        ResponseParamsBuilder responseParamsBuilder = new ResponseParamsBuilder()
                .setResponseCode(206)
                .setContentLength((int) file.length());

        if (start >= 0 && stop >= start && file.length() >= stop) {
            partialContent = contents.substring(start, stop + 1);
        } else if (start == -1 && file.length() >= stop) {
            start = (int) file.length() - stop;
            stop = (int) file.length() - 1;
            partialContent = contents.substring(start, stop) + "\n";
        } else if (stop == -1) {
            stop = (int) file.length() - 1;
            partialContent = contents.substring(start, stop) + "\n";
        } else {
            this.responseParams = responseParamsBuilder.setResponseCode(416).build();
            return "".getBytes();
        }

        this.responseParams = responseParamsBuilder.setContentRange(start, stop).build();
        return partialContent.getBytes();
    }

    private byte[] readBody(RequestParams requestParams) throws IOException {
        String filePath = requestParams.getDirectory() + requestParams.getPath();
        File file = new File(filePath);
        if(file.exists()){
            this.responseParams = new ResponseParamsBuilder().setResponseCode(200).build();
            return getFileContents(file);
        } else {
            this.responseParams = new ResponseParamsBuilder().setResponseCode(404).build();
            return new byte[0];
        }
    }

    private byte[] createBody(RequestParams requestParams) throws IOException {
        String fileInput = requestParams.getBody();
        String resourceName = "/data";

        File file = createFile(requestParams, resourceName);
        writeToFile(fileInput, file);
        buildResponseParams(file, "/data", requestParams);

        return new byte[0];
    }

    private byte[] updateBody(RequestParams requestParams) throws IOException {
        String fileInput = requestParams.getBody();
        String filePath = requestParams.getDirectory() + requestParams.getPath();

        File file = new File(filePath);
        if(file.exists()) {
            writeToFile(fileInput, file);
        }
        this.responseParams = new ResponseParamsBuilder().setResponseCode(200).build();

        return new byte[0];
    }

    private byte[] deleteBody(RequestParams requestParams){
        String filePath = requestParams.getDirectory() + requestParams.getPath();
        File file = new File(filePath);
        if(file.exists()) {
            file.delete();
        }
        this.responseParams = new ResponseParamsBuilder().setResponseCode(200).build();

        return new byte[0];
    }

    private File createFile(RequestParams requestParams, String resourceName) throws IOException {
        String filePath = requestParams.getDirectory() + requestParams.getPath() + resourceName;
        File file = new File(filePath);
        file.createNewFile();
        return file;
    }

    private void writeToFile(String str, File file) throws IOException {
        if(file.exists()){
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(str);
            writer.close();
        }
    }

    private byte[] basicAuthorizationBody(RequestParams requestParams) throws IOException {
        byte[] logs = new byte[0];
        int responseCode;

        BasicAuthorizationHandler handler = new BasicAuthorizationHandler(requestParams);
        String credentials = requestParams.getAuthorizationCredentials();

        if (credentials == null || !handler.isValidCredentials()) {
            responseCode = 401;
        }
        else {
            File logFile = new File(requestParams.getDirectory() + "/logs.txt");
            if (logFile.exists()) {
                logs = getFileContents(logFile);
            }
            responseCode = 200;
        }

        ResponseParamsBuilder responseParamsBuilder = new ResponseParamsBuilder();
        this.responseParams = responseParamsBuilder.setResponseCode(responseCode)
                                                    .build();
        return logs;
    }

    private void buildResponseParams(File file, String resourceName, RequestParams requestParams) {
        if (file.exists()) {
            this.responseParams = new ResponseParamsBuilder().setResponseCode(201).setLocationHeader(requestParams.getPath() + resourceName).build();
        } else {
            this.responseParams = new ResponseParamsBuilder().setResponseCode(500).build();
        }
    }

    private byte[] patchContentBody(RequestParams requestParams) throws IOException {
        byte[] body = "".getBytes();
        ResponseParamsBuilder responseParamsBuilder = new ResponseParamsBuilder();

        String filePath = requestParams.getDirectory() + requestParams.getPath();
        File file = new File(filePath);

        if (requestParams.getMethod().equals("GET")) {
            this.responseParams = responseParamsBuilder.setResponseCode(200).build();
            body = getFileContents(file);
        } else if(requestParams.getMethod().equals("PATCH")){
            String fileSHA1 = "";
            fileSHA1 = stringToSHA1(new String(getFileContents(file)));
            if (requestParams.getIfMatch().equals(fileSHA1)) {
                this.responseParams = responseParamsBuilder.setResponseCode(204).build();
                writeToFile(requestParams.getBody(), file);
            } else {
                this.responseParams = responseParamsBuilder.setResponseCode(409).build();
            }
        }
        return body;
    }

    private String stringToSHA1(String contents) throws UnsupportedEncodingException {
        try {
            byte[] contentsBytes = contents.getBytes("utf8");
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.reset();
            messageDigest.update(contentsBytes);
            return String.format("%040x", new BigInteger(1, messageDigest.digest()));
        } catch (NoSuchAlgorithmException e){
            return "";
        }
    }

}
