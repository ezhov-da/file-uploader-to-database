package ru.ezhov.file.uploader.utils;

import java.util.List;

import ru.ezhov.file.uploader.beans.TableData;

public abstract class QueryGenerator {

    public abstract String generate(String nameTable, List<TableData> tableData);
}
