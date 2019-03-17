package ru.ezhov.file.uploader.excelreader;

import ru.ezhov.file.uploader.interfaces.ExcelHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Обработчик Excel
 */
public class ExcelHandlerImpl implements ExcelHandler {
    private static final Logger LOG = Logger.getLogger(ExcelHandlerImpl.class.getName());
    private ExcelFormat excelFormat;
    private InputStream inputStream;
    private Workbook book;
    private Sheet sheet;
    private Iterator<Row> iteratorRow;
    private String nameWb;

    public ExcelHandlerImpl(ExcelFormat excelFormat, InputStream inputStream, String nameWb) {
        this.excelFormat = excelFormat;
        this.inputStream = inputStream;
        this.nameWb = nameWb;
    }

    @Override
    public List<String> getListHeader() throws Exception {
        if (book == null) {
            throw new IllegalArgumentException(
                    "Откройте или переоткройте книгу.");
        }
        sheet = book.getSheetAt(0);
        iteratorRow = sheet.rowIterator();
        List<String> list = new ArrayList<>();
        while (iteratorRow.hasNext()) {
            Row row = iteratorRow.next();
            Iterator<Cell> iteratorCell = row.cellIterator();
            while (iteratorCell.hasNext()) {
                Cell cell = iteratorCell.next();
                list.add(cell.getStringCellValue());
            }
            break;
        }
        return list;
    }

    @Override
    public void openBook() throws Exception {
        switch (excelFormat) {
            case HSSF:
                book = new HSSFWorkbook(inputStream);
                break;
            case XSSF:
                book = new XSSFWorkbook(inputStream);
                break;
            default:
                throw new IllegalArgumentException("Некорректный формат файла");
        }
    }

    @Override
    public void close() throws IOException {
        if (book != null) {
            book.close();
            book = null;
        }
    }

    @Override
    public Workbook getWorkbook() {
        return book;
    }

    @Override
    public Sheet getSheet() {
        return sheet;
    }

    @Override
    public Iterator<Row> getIteratorRow() {
        return iteratorRow;
    }

    @Override
    public String getNameWorkbook() {
        return nameWb;
    }


}
