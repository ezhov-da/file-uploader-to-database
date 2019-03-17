package ru.ezhov.file.uploader.components.models;

import java.util.ArrayList;
import java.util.List;

import ru.ezhov.file.uploader.types.TypeSql;

public class TableColumns {

    public static final List<Column> LIST_COLUMNS = new ArrayList<Column>() {
        {
            add(new Column("№", 25, 25, Integer.class, false));
            add(new Column("Оригинальное название", -1, -1, String.class, false));
            add(new Column("Название после транслита", -1, -1, String.class, true));
            add(new Column("Тип поля", -1, -1, TypeSql.class, true));
            add(new Column("...", 25, 25, Boolean.class, true));
        }
    };
}
