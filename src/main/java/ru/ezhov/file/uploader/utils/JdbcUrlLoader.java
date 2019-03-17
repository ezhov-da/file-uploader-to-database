package ru.ezhov.file.uploader.utils;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.file.uploader.beans.JdbcBean;
import ru.ezhov.file.uploader.interfaces.JdbcHolderAbstract;

/**
 * Получаем подключение к БД
 */
public class JdbcUrlLoader
{
    private static final Logger LOG = Logger.getLogger(JdbcUrlLoader.class.getName());
    private URLClassLoader classLoader;

    public JdbcUrlLoader() throws IOException
    {
        load();
    }

    private void load() throws IOException
    {
        String folder = AppProperties.getPropProperty("folder.jdbc.jars");
        File file = new File(folder);
        LOG.log(Level.INFO, "Загрузка jar из: {0}", file.getAbsolutePath());
        File[] files = file.listFiles(new FileFilter()
        {

            @Override
            public boolean accept(File file)
            {
                return file.getName().endsWith(".jar");
            }
        });

        URL[] urls = new URL[files.length];
        for (int i = 0; i < files.length; i++)
        {
            try
            {
                urls[i] = files[i].toURI().toURL();
            } catch (MalformedURLException ex)
            {
                Logger.getLogger(JdbcUrlLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        classLoader = new URLClassLoader(urls);

    }

    public Connection getConnection(JdbcHolderAbstract jdbcHolderAbstract) throws Exception
    {

        JdbcBean jdbcBean = jdbcHolderAbstract.getSelectedJdbcBean();
        Class clazz = classLoader.loadClass(jdbcBean.getClazz());
        Driver driver = (Driver) clazz.newInstance();
        Connection connection = driver.connect(jdbcBean.getUrl(), null);
        return connection;
    }


}
