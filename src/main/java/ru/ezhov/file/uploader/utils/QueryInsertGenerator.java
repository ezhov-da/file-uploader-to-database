package ru.ezhov.file.uploader.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ru.ezhov.file.uploader.beans.TableData;

/**
 * Класс, который генерирует запрос для вставки в БД
 */
public class QueryInsertGenerator extends QueryGenerator {

    private static final Logger LOG
            = Logger.getLogger(QueryInsertGenerator.class.getName());
    private static final String HEAD_CREATE = "INSERT INTO %s\n";
    private static final String BODY_CREATE_COLUMN = "%s -- %s\n";
    private static final String PRE_FIRST_COLUMN = "\t ";
    private static final String PRE_SECONDS_COLUMNS = "\t,";
    private static final String VALUES = "VALUES\n";

    private static final String BODY_QUESTION = "? -- %s\n";
    private static final String OPEN_BRACKET = "(\n";
    private static final String CLOSE_BRACKET = ")\n";

    @Override
    public String generate(
            String nameTable,
            List<TableData> tableDatas) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(HEAD_CREATE, nameTable));
        stringBuilder.append(OPEN_BRACKET);
        int size = tableDatas.size();

        List<TableData> fillCounter = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            TableData tableData = tableDatas.get(i);
            if (tableData.isUse()) {
                String original = tableData.getOriginalColumnName();
                original = original.replaceAll("%", "%%");
                if (fillCounter.isEmpty()) {
                    stringBuilder.append(
                            String.format(
                                    PRE_FIRST_COLUMN + BODY_CREATE_COLUMN,
                                    tableData.getTranslationColumnName(),
                                    original
                            ));
                } else {
                    stringBuilder.append(
                            String.format(
                                    PRE_SECONDS_COLUMNS + BODY_CREATE_COLUMN,
                                    tableData.getTranslationColumnName(),
                                    original
                            ));
                }
                fillCounter.add(tableData);
            }
        }
        stringBuilder.append(CLOSE_BRACKET);
        stringBuilder.append(VALUES);
        stringBuilder.append(OPEN_BRACKET);

        fillCounter.clear();
        for (int i = 0; i < size; i++) {
            TableData tableData = tableDatas.get(i);
            if (tableData.isUse()) {
                String original = tableData.getOriginalColumnName();
                original = original.replaceAll("%", "%%");
                String questionSymbol = String.format(BODY_QUESTION, original);

                if (fillCounter.isEmpty()) {
                    stringBuilder.append(PRE_FIRST_COLUMN).append(questionSymbol);
                } else {
                    stringBuilder.append(PRE_SECONDS_COLUMNS).append(questionSymbol);
                }
                fillCounter.add(tableData);
            }
        }
        stringBuilder.append(CLOSE_BRACKET);

        return stringBuilder.toString();

    }

}
