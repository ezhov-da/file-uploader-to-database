package ru.ezhov.file.uploader.types;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * Тип для отображения выпадающего списка
 */
public class TypeSql {

    private static final Logger LOG = Logger.getLogger(TypeSql.class.getName());
    private String htmlView;
    private String nameForShow;
    private int typesSql;

    public TypeSql(String htmlView, String nameForShow, int typesSql) {
        this.htmlView = htmlView;
        this.nameForShow = nameForShow;
        this.typesSql = typesSql;
    }

    public String getHtmlView() {
        return htmlView;
    }

    public void setHtmlView(String htmlView) {
        this.htmlView = htmlView;
    }

    public int getTypesSql() {
        return typesSql;
    }

    public void setTypesSql(int typesSql) {
        this.typesSql = typesSql;
    }

    public String getNameForShow() {
        return nameForShow;
    }

    public void setNameForShow(String nameForShow) {
        this.nameForShow = nameForShow;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.nameForShow);
        hash = 29 * hash + this.typesSql;
        return hash;
    }

    public String getHtmlText() {
        return String.format(htmlView, nameForShow);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TypeSql other = (TypeSql) obj;
        if (!Objects.equals(this.nameForShow, other.nameForShow)) {
            return false;
        }
        return this.typesSql == other.typesSql;
    }

    @Override
    public String toString() {
        return nameForShow;
    }

}
