package ru.ezhov.file.uploader;

import ru.ezhov.file.uploader.beans.TableData;
import ru.ezhov.file.uploader.components.models.ModelTableField;
import ru.ezhov.file.uploader.excelreader.ExcelFormat;
import ru.ezhov.file.uploader.excelreader.ExcelHandlerImpl;
import ru.ezhov.file.uploader.groovy.GroovyNameTableCreator;
import ru.ezhov.file.uploader.interfaces.*;
import ru.ezhov.file.uploader.utils.JdbcUrlLoader;
import ru.ezhov.file.uploader.utils.WorkbookExecutor;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DispatcherBind implements PropertyChangeListener, TableModelListener {
    private static final Logger LOG = Logger.getLogger(DispatcherBind.class.getName());
    private CodeTemplate codeTemplateCreate;
    private CodeTemplate codeTemplateInsert;
    private ExcelHandler excelHandler;
    private JdbcHolderAbstract jdbcHolderAbstract;

    private FileSelected fileSelected;
    private TableDataColumn tableDataColumn;
    private TableNameHolder tableNameHolder;
    private Action actionExecute;

    private ModelTableField modelTableField;

    public DispatcherBind() throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    excelHandler.close();
                } catch (IOException ex) {
                    Logger.getLogger(DispatcherBind.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        actionExecute = new ActionExecute();

    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        String propString = pce.getPropertyName();
        if ("selectedFile".equals(propString)) {
            if (excelHandler != null) {
                try {
                    excelHandler.close();
                } catch (IOException ex) {
                    Logger.getLogger(DispatcherBind.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            File file = fileSelected.getSelectedFile();
            try {
                InputStream inputStream = new FileInputStream(file);

                ExcelFormat excelFormat = file.getAbsolutePath().endsWith("xls")
                        ? ExcelFormat.HSSF : ExcelFormat.XSSF;

                excelHandler = new ExcelHandlerImpl(excelFormat, inputStream, file.getName());
                excelHandler.openBook();

                final List<String> list = excelHandler.getListHeader();

                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        GroovyNameTableCreator groovyNameTableCreator = new GroovyNameTableCreator();
                        String nameTable = groovyNameTableCreator.getNameTable();
                        tableNameHolder.setNameTable(nameTable);

                        modelTableField.setModel(list);
                        ecxecuteChanges();
                    }
                });

            } catch (Exception ex) {
                Logger.getLogger(DispatcherBind.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if ("textFieldNameDriver".equals(propString)) {

            ecxecuteChanges();

        }
    }

    public JdbcHolderAbstract getJdbcHolderAbstract() {
        return jdbcHolderAbstract;
    }

    public void setJdbcHolderAbstract(JdbcHolderAbstract jdbcHolderAbstract) {
        this.jdbcHolderAbstract = jdbcHolderAbstract;
    }

    public void setExcelHandler(ExcelHandler excelHandler) {
        this.excelHandler = excelHandler;
    }

    public void setFileSelected(FileSelected fileSelected) {
        this.fileSelected = fileSelected;
    }

    public void setTableDataColumn(TableDataColumn tableDataColumn) {
        this.tableDataColumn = tableDataColumn;
    }

    public void setTableNameHolder(TableNameHolder tableNameHolder) {
        this.tableNameHolder = tableNameHolder;
    }

    public void setModelTableField(ModelTableField modelTableField) {
        this.modelTableField = modelTableField;
    }

    @Override
    public void tableChanged(TableModelEvent tme) {
        ecxecuteChanges();
    }

    public void setCodeTemplateCreate(CodeTemplate codeTemplateCreate) {
        this.codeTemplateCreate = codeTemplateCreate;
    }

    public void setCodeTemplateInsert(CodeTemplate codeTemplateInsert) {
        this.codeTemplateInsert = codeTemplateInsert;
    }

    private void ecxecuteChanges() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String nameTable = tableNameHolder.getNameTable();
                List<TableData> tableDatas = modelTableField.getTableDataList();
                codeTemplateCreate.setCode(nameTable, tableDatas);
                codeTemplateInsert.setCode(nameTable, tableDatas);
            }
        });
    }

    public Action getActionExecute() {
        return actionExecute;
    }


    private class ActionExecute extends AbstractAction {
        private JdbcUrlLoader jdbcUrlLoader;


        {
            putValue(NAME, "!..!. Приступить к аннигиляции EXCEL !..!.");
        }

        public ActionExecute() throws IOException {
            jdbcUrlLoader = new JdbcUrlLoader();
        }


        @Override
        public void actionPerformed(ActionEvent ae) {
            String queryCreate = codeTemplateCreate.getText();
            String queryInsert = codeTemplateInsert.getText();

            List<TableData> listTableData = tableDataColumn.getTableData();

            try {

                Connection connection = jdbcUrlLoader.getConnection(jdbcHolderAbstract);
                final ExcelExecutor excelExecutor = new WorkbookExecutor(
                        connection,
                        queryCreate,
                        queryInsert,
                        listTableData,
                        excelHandler);

                Thread thread = new Thread() {
                    public void run() {
                        try {
                            excelExecutor.execute();
                        } catch (Exception ex) {
                            Logger.getLogger(DispatcherBind.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };

                thread.start();

            } catch (Exception ex) {
                Logger.getLogger(DispatcherBind.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
