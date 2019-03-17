package ru.ezhov.file.uploader.groovy;

import org.junit.Test;

public class JdbcSettingsReaderTest {

    public JdbcSettingsReaderTest() {
    }

    @Test
    public void testLoad() throws Exception {
        JdbcSettingsModel jdbcSettingsReader = new JdbcSettingsModel();
        jdbcSettingsReader.load();
    }

}
