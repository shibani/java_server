package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class CLIFlagParserTest {

    @Test
    public void successful_parse() {
        String args = "-p 5000 -d /path/to/directory";

        final CLIFlagParser parser = new CLIFlagParser();
        final ServerConfig serverConfig = parser.parse(args);

        assertEquals(5000, serverConfig.getPortNumber());
        assertEquals("/path/to/directory", serverConfig.getDirectory());
    }

    @Test
    public void successful_parse_with_swapped_flags() {
        String args = "-d /path/to/directory -p 5000";

        final CLIFlagParser parser = new CLIFlagParser();
        final ServerConfig serverConfig = parser.parse(args);

        assertEquals(5000, serverConfig.getPortNumber());
        assertEquals("/path/to/directory", serverConfig.getDirectory());
    }

}