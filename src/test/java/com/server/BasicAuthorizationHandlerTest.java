package com.server;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;

import static org.junit.Assert.*;

public class BasicAuthorizationHandlerTest {

    @Test
    public void createLineReturnsAuthenticationChallengeWhenLogsAreRequestedWithoutCredentials() {
        String path = "/logs";
        String method = "GET";
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        ResponseParams responseParams = new ResponseParamsBuilder().setResponseCode(401).build();
        BasicAuthorizationHandler handler = new BasicAuthorizationHandler(requestParams);
        String expected = "WWW-Authenticate: Basic";

        assertEquals(expected, handler.createLine(requestParams, responseParams));
    }

    @Test
    public void createLineReturnsAuthenticationChallengeLineWhenLogsAreRequestedWithIncorrectCredentials() {
        String path = "/logs";
        String method = "GET";
        RequestParams requestParams = new RequestParamsBuilder().setPath(path)
                                                                .setMethod(method)
                                                                .setAuthorizationCredentials("faker")
                                                                .build();
        ResponseParams responseParams = new ResponseParamsBuilder().setResponseCode(401).build();
        BasicAuthorizationHandler handler = new BasicAuthorizationHandler(requestParams);
        String expected = "WWW-Authenticate: Basic";

        assertEquals(expected, handler.createLine(requestParams, responseParams));
    }

    @Test
    public void createLineReturnsNoAuthenticationChallengeLineWhenLogsAreRequestedWithCorrectCredentials() {
        String path = "/logs";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/get-authorization");
        String testDir = resourcesDirectory.getAbsolutePath();
        RequestParams requestParams = new RequestParamsBuilder().setPath(path)
                .setMethod(method)
                .setDirectory(testDir)
                .setAuthorizationCredentials("GXyLLg==")
                .build();
        ResponseParams responseParams = new ResponseParamsBuilder().setResponseCode(200).build();
        BasicAuthorizationHandler handler = new BasicAuthorizationHandler(requestParams);
        String expected = "";

        assertEquals(expected, handler.createLine(requestParams, responseParams));
    }

    @Test
    public void IsValidCredentialsReturnsFalseWhenCredentialsAreNull() throws IOException {
        RequestParams requestParams = new RequestParamsBuilder().setMethod("GET")
                .setPath("/logs")
                .build();
        BasicAuthorizationHandler handler = new BasicAuthorizationHandler(requestParams);

        assertEquals(false, handler.isValidCredentials());
    }

    @Test
    public void IsValidCredentialsReturnsFalseWhenCredentialsAreBlank() throws IOException {
        RequestParams requestParams = new RequestParamsBuilder().setMethod("GET")
                                                                .setPath("/logs")
                                                                .setAuthorizationCredentials("")
                                                                .build();
        BasicAuthorizationHandler handler = new BasicAuthorizationHandler(requestParams);

        assertEquals(false, handler.isValidCredentials());
    }

    @Test
    public void IsValidCredentialsReturnsFalseWhenIncorrectCredentialsAreProvided() throws IOException {
        RequestParams requestParams = new RequestParamsBuilder().setMethod("GET")
                .setPath("/logs")
                .setAuthorizationCredentials("FAKER")
                .build();
        BasicAuthorizationHandler handler = new BasicAuthorizationHandler(requestParams);

        assertEquals(false, handler.isValidCredentials());
    }
}
