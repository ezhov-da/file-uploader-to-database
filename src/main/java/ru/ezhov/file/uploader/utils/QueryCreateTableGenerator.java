package ru.ezhov.file.uploader.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ru.ezhov.file.uploader.beans.TableData;

/**
 * Класс, который генерирует запрос для создания таблицы
 */
public class QueryCreateTableGenerator extends QueryGenerator {

    private static final Logger LOG
            = Logger.getLogger(QueryCreateTableGenerator.class.getName());
    private static final String HEAD_CREATE = "CREATE TABLE %s \n(\n";
    private static final String BODY_CREATE = "%s %s -- %s\n";
    private static final String PRE_FIRST_COLUMN = "\t ";
    private static final String PRE_SECONDS_COLUMNS = "\t,";
    private static final String END_CREATE = ")";

    @Override
    public String generate(String nameTable,
                           List<TableData> tableDatas) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(HEAD_CREATE, nameTable));

        List<TableData> fillCounter = new ArrayList<>();

        int size = tableDatas.size();
        for (int i = 0; i < size; i++) {
            TableData tableData = tableDatas.get(i);
            if (tableData.isUse()) {
                String original = tableData.getOriginalColumnName();
                original = original.replaceAll("%", "%%");
                if (fillCounter.isEmpty()) {
                    stringBuilder.append(
                            String.format(
                                    PRE_FIRST_COLUMN + BODY_CREATE,
                                    tableData.getTranslationColumnName(),
                                    tableData.getTypeSql().getNameForShow(),
                                    original
                            ));
                } else {
                    stringBuilder.append(
                            String.format(
                                    PRE_SECONDS_COLUMNS + BODY_CREATE,
                                    tableData.getTranslationColumnName(),
                                    tableData.getTypeSql().getNameForShow(),
                                    original
                            ));

                }
                fillCounter.add(tableData);
            }
        }
        stringBuilder.append(END_CREATE);
        return stringBuilder.toString();

    }

}
