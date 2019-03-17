package ru.ezhov.file.uploader.groovy;

import groovy.lang.GroovyShell;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.codehaus.groovy.control.CompilationFailedException;
import ru.ezhov.file.uploader.beans.JdbcBean;
import ru.ezhov.file.uploader.interfaces.JdbcHolderAbstract;
import ru.ezhov.file.uploader.utils.Settings;

/**
 * Модель файла настройки подключений
 */
public class JdbcSettingsModel extends JdbcHolderAbstract {

    private final String fileSettingsFromUserHome;
    private String source;
    private List<JdbcBean> jdbcBeans;
    private JdbcBean selectedJdbcBean;

    public JdbcSettingsModel() {
        jdbcBeans = new ArrayList<>();
        fileSettingsFromUserHome = Settings.PATH_TO_FILE_SETTINGS;
    }

    private void read() throws CompilationFailedException, IOException {
        GroovyShell groovyShell = new GroovyShell();
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(fileSettingsFromUserHome), Settings.CHARSET);) {
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append("\n");
            }
        }
        source = stringBuilder.toString();
        Object object = groovyShell.evaluate(source);
        LinkedHashMap<String, Object> list = (LinkedHashMap<String, Object>) object;
        List<Map<String, String>> listWithCinnections = (List<Map<String, String>>) list.get("connections");
        jdbcBeans = new ArrayList<>();
        for (Map<String, String> map : listWithCinnections) {
            String name = String.valueOf(map.get("name"));
            String clazz = String.valueOf(map.get("class.loader"));
            String url = String.valueOf(map.get("url"));

            JdbcBean jdbcBean = new JdbcBean(name, clazz, url);
            jdbcBeans.add(jdbcBean);
        }
        selectedJdbcBean = jdbcBeans.get(0);
    }

    @Override
    public int getSize() {
        return jdbcBeans.size();
    }

    @Override
    public JdbcBean getElementAt(int index) {
        return jdbcBeans.get(index);
    }

    @Override
    public void load() throws Exception {
        read();
    }

    @Override
    public void save(String source) throws Exception {

        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(fileSettingsFromUserHome));) {
            try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, Settings.CHARSET);) {
                outputStreamWriter.write(source, 0, source.length());
            }
        }
        read();
        fireContentsChanged(this, 0, jdbcBeans.size());
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public JdbcBean getSelectedJdbcBean() {
        return selectedJdbcBean;
    }

    @Override
    public void addElement(JdbcBean item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeElement(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insertElementAt(JdbcBean item, int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeElementAt(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selectedJdbcBean = (JdbcBean) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selectedJdbcBean;
    }
}
