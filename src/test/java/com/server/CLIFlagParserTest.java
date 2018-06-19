package com.server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CLIFlagParserTest {

    @Test
    public void successful_parse() throws IOException {
        String args = "-p 5000 -d /path/to/directory";

        final CLIFlagParser parser = new CLIFlagParser();
        final ServerConfig serverConfig = parser.parse(args);

        assertEquals(5000, serverConfig.getPortNumber());
        assertEquals("/path/to/directory", serverConfig.getDirectory());
    }

    @Test
    public void successful_parse_with_swapped_flags() throws IOException {
        String args = "-d /path/to/directory -p 5000";

        final CLIFlagParser parser = new CLIFlagParser();
        final ServerConfig serverConfig = parser.parse(args);

        assertEquals(5000, serverConfig.getPortNumber());
        assertEquals("/path/to/directory", serverConfig.getDirectory());
    }

    @Test(expected = IOException.class)
    public void failed_parse_with_swapped_flags() throws IOException {
        String args = "-f /path/to/directory -p 5000";

        final CLIFlagParser parser = new CLIFlagParser();
        parser.parse(args);
    }


    @Test(expected = IOException.class)
    public void failed_parse_with_missing_portnumber() throws IOException {
        String args = "-d /path/to/directory";

        final CLIFlagParser parser = new CLIFlagParser();
        parser.parse(args);
    }

    @Test(expected = IOException.class)
    public void failed_parse_with_missing_directory() throws IOException {
        String args = "-p 5000";

        final CLIFlagParser parser = new CLIFlagParser();
        parser.parse(args);
    }
}