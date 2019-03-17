package ru.ezhov.file.uploader.interfaces;

import java.io.Closeable;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelHandler extends Closeable
{
    public List<String> getListHeader() throws Exception;

    public void openBook() throws Exception;

    public Workbook getWorkbook();

    public Sheet getSheet();

    public Iterator<Row> getIteratorRow();

    public String getNameWorkbook();
}
