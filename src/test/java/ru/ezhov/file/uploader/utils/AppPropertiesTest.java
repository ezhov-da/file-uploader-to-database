package ru.ezhov.file.uploader.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppPropertiesTest {

    public AppPropertiesTest() {
    }

    @Test
    public void testGetPropProperty() throws Exception {
        String prop = AppProperties.getPropProperty("folder.jdbc.jars");
        System.out.println(prop);
        assertNotNull(prop);
    }

}
