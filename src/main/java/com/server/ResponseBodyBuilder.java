package com.server;

import sun.misc.Request;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.*;

public class ResponseBodyBuilder {
    private byte[] body;
    private String publicDir = "";
    private RequestRouter requestRouter;

    ResponseBodyBuilder(RequestRouter requestRouter, String directory){
        this.requestRouter = requestRouter;
        this.publicDir = directory;
    }

    public byte[] getBody(RequestParams requestParams) throws IOException {
        if (requestParams.getPath().equals("/file1")) {
            String filePath = this.publicDir + requestParams.getPath();
            File file = new File(filePath);
            this.body = getFileContents(file).getBytes();
        } else if (requestParams.getPath().equals("/coffee")) {
            this.body = coffeeBody(requestParams).getBytes();
        } else if (requestParams.getPath().equals("/cookie")) {
            this.body = cookieBody(requestParams).getBytes();
        } else if (requestParams.getPath().equals("/eat_cookie")) {
            this.body = eatCookieBody(requestParams).getBytes();
        } else if (requestParams.getPath().equals("/")) {
            this.body = directoryListingBody(requestParams.getPath()).getBytes();
        } else if (requestParams.getPath().equals("/parameters")) {
            this.body = parametersBody(requestParams).getBytes();
        } else if (requestParams.getPath().contains("/image")){
            this.body = imageBody(requestParams);
        } else {
            this.body = new byte[0];
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

    private String directoryListingBody(String path){
        String dirPath = this.publicDir + path;
        return getFiles(dirPath);
    }

    private String getFiles(String dirPath){
        StringBuilder fileNames = new StringBuilder();
        final File folder = new File(dirPath);

        if(folder.listFiles() != null){
            for (final File fileEntry : folder.listFiles()) {
                fileNames.append(fileEntry.getName());
                fileNames.append(" ");
            }
            return fileNames.toString();
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

    private byte[] imageBody(RequestParams requestParams) throws IOException {
        String testDir = "";
        if (requestParams.getPath().contains("/image.jpeg")){
            testDir = "/Users/ranizilpelwar/documents/github/cob_spec/public/image.jpeg";
        }
        else if (requestParams.getPath().contains("/image.png")){
            testDir = "/Users/ranizilpelwar/documents/github/cob_spec/public/image.png";
        }
        else if (requestParams.getPath().contains("/image.gif")){
            testDir = "/Users/ranizilpelwar/documents/github/cob_spec/public/image.gif";
        }

        File f = new File(testDir);

        ImageInputStream iis = ImageIO.createImageInputStream(f);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //baos.write(header.getBytes());

        int byteToBeRead = -1;
        while((byteToBeRead = iis.read())!=-1){
            baos.write(byteToBeRead);
        }
        byte[] mybytearray = baos.toByteArray();
//        BufferedOutputStream outputStream = new BufferedOutputStream(clientSocket.getOutputStream());
//        outputStream.write(mybytearray);


//        //Save byte array to compare to original image
//        try (FileOutputStream fos = new FileOutputStream("/users/ranizilpelwar/documents/github/java_server/src/test/resources/test-imagejpeg-contents/imageoutput.jpeg"))
//        {
//            fos.write(mybytearray);
//        }

        baos.flush();
        baos.close();

        return mybytearray;
    }
}
