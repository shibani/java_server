package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class MockRequestRouterTest {

    @Test
    public void checkPathReturnsFalseByDefault(){

        MockRequestRouter mockRequestRouter = new MockRequestRouter();

        assertFalse(mockRequestRouter.checkPath(""));
    }

    @Test
    public void checkPathReturnsTrueWhenSetToTrue(){

        MockRequestRouter mockRequestRouter = new MockRequestRouter();

        mockRequestRouter.setCheckPathStub(true);

        assertTrue(mockRequestRouter.checkPath(""));
    }

    @Test
    public void checkPathReturnsFalseWhenSetToFalse(){

        MockRequestRouter mockRequestRouter = new MockRequestRouter();

        mockRequestRouter.setCheckPathStub(false);

        assertFalse(mockRequestRouter.checkPath(""));
    }
}