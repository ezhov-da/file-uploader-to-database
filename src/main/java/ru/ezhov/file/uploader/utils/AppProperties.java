package ru.ezhov.file.uploader.utils;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Получаем общие настройки приложения
 */
public class AppProperties
{
    private static final Logger LOG = Logger.getLogger(AppProperties.class.getName());
    private static Properties properties;
    private static AppProperties appProperties;

    private AppProperties() throws IOException
    {
        properties = new Properties();
        properties.load(AppProperties.class.getResourceAsStream("/config_app.properties"));
    }


    public static String getPropProperty(String key) throws IOException
    {
        if (appProperties == null)
        {
            appProperties = new AppProperties();
        }
        return properties.getProperty(key);

    }

}
