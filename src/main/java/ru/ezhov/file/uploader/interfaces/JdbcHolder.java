package ru.ezhov.file.uploader.interfaces;

import ru.ezhov.file.uploader.beans.JdbcBean;

public interface JdbcHolder{

    public void load() throws Exception;

    public void save(String source) throws Exception;

    public String getSource();

    public JdbcBean getSelectedJdbcBean();
}
