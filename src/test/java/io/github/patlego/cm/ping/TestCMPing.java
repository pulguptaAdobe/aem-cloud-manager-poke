package io.github.patlego.cm.ping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import io.github.patlego.cm.ping.models.CMInstance;

public class TestCMPing {
    

    @Test
    public void testUrlGeneratorWithBothTerminating() throws URISyntaxException {
        CMInstance cmInstance = new CMInstance();
        cmInstance.setUrl("http://localhost:4502/");
        cmInstance.setPath("/test");

        CMPing cmPing = new CMPing(cmInstance);
        String url = cmPing.buildUrl();

        String[] urlParts = url.split("\\?");

        assertEquals("http://localhost:4502/test", urlParts[0]);
    }

    @Test
    public void testUrlGeneratorWithNoTerminating() throws URISyntaxException {
        CMInstance cmInstance = new CMInstance();
        cmInstance.setUrl("http://localhost:4502");
        cmInstance.setPath("test");

        CMPing cmPing = new CMPing(cmInstance);
        String url = cmPing.buildUrl();

        String[] urlParts = url.split("\\?");

        assertEquals("http://localhost:4502/test", urlParts[0]);
    }

    @Test
    public void testUrlGeneratorWithOnlyOneTerminating() throws URISyntaxException {
        CMInstance cmInstance = new CMInstance();
        cmInstance.setUrl("http://localhost:4502/");
        cmInstance.setPath("test");

        CMPing cmPing = new CMPing(cmInstance);
        String url = cmPing.buildUrl();

        String[] urlParts = url.split("\\?");

        assertEquals("http://localhost:4502/test", urlParts[0]);
    }

    @Test
    public void testUrlGeneratorWithOnlyOneTerminating2() throws URISyntaxException {
        CMInstance cmInstance = new CMInstance();
        cmInstance.setUrl("http://localhost:4502");
        cmInstance.setPath("/test");

        CMPing cmPing = new CMPing(cmInstance);
        String url = cmPing.buildUrl();

        String[] urlParts = url.split("\\?");

        assertEquals("http://localhost:4502/test", urlParts[0]);
    }

}
