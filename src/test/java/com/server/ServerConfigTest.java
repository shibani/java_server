package com.server;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class ServerConfigTest {

    @Test
    public void port() {
        int portNumber = 5000;
        String directory = "/path/to/jar";

        final ServerConfig serverConfig = new ServerConfig(portNumber, directory);

        assertEquals(5000, serverConfig.getPortNumber());
    }

    @Test
    public void directory() {
        int portNumber = 5000;
        String directory = "/path/to/jar";

        final ServerConfig serverConfig = new ServerConfig(portNumber, directory);

        assertEquals("/path/to/jar", serverConfig.getDirectory());
    }

}