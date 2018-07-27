package com.server;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Hashtable;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.Base64;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.*;

public class BasicAuthorizationHandler implements IResponseHeaderHandler {
    private Hashtable basicAuthHashtable;
    public static final String AUTH_KEY = "WWW-Authenticate";
    public static final String AUTH_SCHEME = "Basic";
    private static final char[] PASSWORD = "MaximumSecurity".toCharArray();
    private static final byte[] SALT = {
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,};
    private static final String CONFIG_PATH = "/config/config_settings.txt";
    private RequestParams requestParams;

    BasicAuthorizationHandler(RequestParams requestParams){
        this.requestParams = requestParams;
        this.basicAuthHashtable = new Hashtable();
        basicAuthHashtable.put("1", "LSpdBnfYT8XLmh5+WafSQI3iSByQ8ZJA");
    }

    public String createLine(RequestParams requestParams, ResponseParams responseParams){
        String basicAuthLine = "";
        if (requestParams.getPath().equals("/logs") && responseParams.getResponseCode() == 401) {
            basicAuthLine = AUTH_KEY + ": " + AUTH_SCHEME;
        }
        return basicAuthLine;
    }

    public boolean isValidCredentials() throws IOException {
        String credentials = this.requestParams.getAuthorizationCredentials();
        if (credentials == null || credentials.isEmpty()) {
            return false;
        }

        try {
            String storedCredentials = getCredentials(this.requestParams.getDirectory() + CONFIG_PATH);
            if (storedCredentials.equals(encrypt(credentials.getBytes()))) {
                return true;
            }
        } catch (GeneralSecurityException e) {
            return false;
        }
        return false;
    }

    private String encode(byte[] credentials) {
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials);
        return encodedCredentials;
    }

    private String encrypt(byte[] credentials) throws GeneralSecurityException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return encode(pbeCipher.doFinal(credentials));
    }

    private String getCredentials(String filePath) throws IOException {
        String credentials = "";
        File file = new File(filePath);
        if (file.exists()) {
            Path path = Paths.get(filePath);
            byte[] data = Files.readAllBytes(path);
            credentials = new String(data);
        } else {
            credentials = (String)this.basicAuthHashtable.get("1");
        }
        return credentials;
    }
}

