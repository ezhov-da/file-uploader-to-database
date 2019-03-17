package ru.ezhov.file.uploader.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import ru.ezhov.file.uploader.beans.TableData;
import ru.ezhov.file.uploader.interfaces.ExcelExecutor;
import ru.ezhov.file.uploader.interfaces.ExcelHandler;

/**
 * Обработчик внесения данных
 */
public class WorkbookExecutor implements ExcelExecutor {
    private static final Logger LOG = Logger.getLogger(WorkbookExecutor.class.getName());
    private Connection connection;
    private String textCreateTable;
    private String textInsertTable;
    private List<TableData> tableDatas;
    private ExcelHandler excelHandler;

    public WorkbookExecutor(
            Connection connection,
            String textCreateTable,
            String textInsertTable,
            List<TableData> tableDatas,
            ExcelHandler excelHandler) {
        this.connection = connection;
        this.textCreateTable = textCreateTable;
        this.textInsertTable = textInsertTable;
        this.tableDatas = tableDatas;
        this.excelHandler = excelHandler;
    }

    @Override
    public void execute() throws Exception {
        Workbook workbook = excelHandler.getWorkbook();
        if (workbook != null) {
            int rowCounter = 1;
            try (Connection connect = connection) {
                try (PreparedStatement preparedStatement =
                             connect.prepareStatement(textCreateTable);) {
                    preparedStatement.execute();
                    LOG.log(Level.INFO, "Запрос выполнен:\n{0}", textCreateTable);
                }
                List<TableData> tableDatasOnlyInsert = new ArrayList<>();
                for (TableData tableData : tableDatas) {
                    tableDatasOnlyInsert.add(tableData);
                }

                try (PreparedStatement preparedStatement =
                             connect.prepareStatement(textInsertTable);) {
                    Iterator<Row> iterator = excelHandler.getIteratorRow();
                    while (iterator.hasNext()) {
                        Row row = iterator.next();
                        List<Cell> listCells = new ArrayList();
                        for (TableData tableData : tableDatasOnlyInsert) {
                            listCells.add(row.getCell(tableData.getNumRow() - 1));
                        }

                        for (int i = 0; i < listCells.size(); i++) {
                            Object object = null;
                            Cell cell = listCells.get(i);
                            if (cell != null) {
                                switch (cell.getCellType()) {

                                    case Cell.CELL_TYPE_BLANK:
                                        object = null;
                                        break;
                                    case Cell.CELL_TYPE_BOOLEAN:
                                        object = String.valueOf(cell.getBooleanCellValue());
                                        break;
                                    case Cell.CELL_TYPE_ERROR:
                                        object = String.valueOf(cell.getErrorCellValue());
                                        break;
                                    case Cell.CELL_TYPE_FORMULA:
                                        object = cell.getCellFormula();
                                        break;
                                    case Cell.CELL_TYPE_NUMERIC:
                                        object = cell.getNumericCellValue();
                                        break;
                                    case Cell.CELL_TYPE_STRING:
                                        object = cell.getStringCellValue();
                                        break;
                                }
                            } else {
                                object = null;
                            }

                            TableData tableData = tableDatasOnlyInsert.get(i);

                            int param = i + 1;
                            if (object == null) {
                                preparedStatement.setObject(
                                        param,
                                        object,
                                        Types.NULL);
                            } else {
                                preparedStatement.setObject(
                                        param,
                                        object,
                                        tableData.getTypeSql().getTypesSql());
                            }

                        }
                        rowCounter++;
                        preparedStatement.addBatch();

                        if (rowCounter % 1000 == 0) {
                            preparedStatement.executeBatch();
                            LOG.log(Level.INFO, "Уже внесено: {0} строк.", rowCounter);
                        }
                    }
                    preparedStatement.executeBatch();
                    LOG.log(Level.INFO, "Данные внесены. Всего: {0} строк.", rowCounter);
                    excelHandler.close();

                    LOG.log(Level.INFO, "Книга  {0} закрыта.", excelHandler.getNameWorkbook());
                }
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Ошибка обработки. Строка: {0} ", rowCounter);
                throw e;
            }
        } else {
            LOG.log(Level.SEVERE, "Откройте или переоткройте книгу");
        }
    }

    @Override
    public void cancel() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(WorkbookExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
