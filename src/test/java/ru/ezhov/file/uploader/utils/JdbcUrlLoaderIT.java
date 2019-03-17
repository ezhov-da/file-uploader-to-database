package ru.ezhov.file.uploader.utils;

import java.sql.Connection;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import ru.ezhov.file.uploader.groovy.JdbcSettingsModel;

@Ignore
public class JdbcUrlLoaderIT {

    @Test
    public void testGetConnection() throws Exception {
        ActiveLibraryPath.setPath();
        JdbcSettingsModel jdbcSettingsModel = new JdbcSettingsModel();
        jdbcSettingsModel.load();
        JdbcUrlLoader jdbcUrlLoader = new JdbcUrlLoader();
        try (Connection connection = jdbcUrlLoader.getConnection(jdbcSettingsModel)) {
            assertNotNull(connection);
        }
    }
}
