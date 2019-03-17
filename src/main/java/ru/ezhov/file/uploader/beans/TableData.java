package ru.ezhov.file.uploader.beans;

import ru.ezhov.file.uploader.types.TypeSql;

/**
 * Данные, которые будем получать из таблицы
 */
public class TableData {

    private boolean use = true;
    private int numRow;
    private String originalColumnName;
    private String translationColumnName;
    private TypeSql typeSql;

    public TableData(
            int numRow,
            String originalColumnName,
            String translationColumnName,
            TypeSql typeSql
    ) {
        this.numRow = numRow;
        this.originalColumnName = originalColumnName;
        this.translationColumnName = translationColumnName;
        this.typeSql = typeSql;
    }

    public String getOriginalColumnName() {
        return originalColumnName;
    }

    public void setOriginalColumnName(String originalColumnName) {
        this.originalColumnName = originalColumnName;
    }

    public String getTranslationColumnName() {
        return translationColumnName;
    }

    public void setTranslationColumnName(String translationColumnName) {
        this.translationColumnName = translationColumnName;
    }

    public TypeSql getTypeSql() {
        return typeSql;
    }

    public void setTypeSql(TypeSql typeSql) {
        this.typeSql = typeSql;
    }

    public void setUse(boolean isUse) {
        this.use = isUse;
    }

    public boolean isUse() {
        return use;
    }

    public int getNumRow() {
        return numRow;
    }

    public void setNumRow(int numRow) {
        this.numRow = numRow;
    }

}
