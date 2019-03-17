package ru.ezhov.file.uploader.beans;

public class JdbcBean {
    private String name;
    private String clazz;
    private String url;

    public JdbcBean(String name, String clazz, String url) {
        this.name = name;
        this.clazz = clazz;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        String s = "%s - [%s]";
        return String.format(s, name, url);
    }

}
