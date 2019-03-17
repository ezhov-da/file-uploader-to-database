package ru.ezhov.file.uploader.components.models;

public class Column {

    private String name;
    private int minWidth;
    private int prefWidth;
    private Class classData;
    private boolean editable;

    public Column(String name, int minWidth, int prefWidth, Class classData, boolean editable) {
        this.name = name;
        this.minWidth = minWidth;
        this.prefWidth = prefWidth;
        this.classData = classData;
        this.editable = editable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public int getPrefWidth() {
        return prefWidth;
    }

    public void setPrefWidth(int prefWidth) {
        this.prefWidth = prefWidth;
    }

    public Class getClassData() {
        return classData;
    }

    public void setClassData(Class classData) {
        this.classData = classData;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

}
