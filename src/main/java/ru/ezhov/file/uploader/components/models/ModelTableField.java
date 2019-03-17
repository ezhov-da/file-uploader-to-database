package ru.ezhov.file.uploader.components.models;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import ru.ezhov.file.uploader.beans.TableData;
import ru.ezhov.file.uploader.interfaces.TranslationExecutor;
import ru.ezhov.file.uploader.types.TypeSql;

public class ModelTableField extends AbstractTableModel {

    private static final Logger LOG = Logger.getLogger(ModelTableField.class.getName());
    private List<TableData> tableDataList;
    private TranslationExecutor translationExecutor;
    private ModelComboboxTypes modelComboboxTypes;

    public ModelTableField(List<String> columns,
            TranslationExecutor translationExecutor,
            ModelComboboxTypes modelComboboxTypes) {
        this.translationExecutor = translationExecutor;
        this.modelComboboxTypes = modelComboboxTypes;
        setNewModel(columns);
    }

    public void setModel(List<String> columns) {
        setNewModel(columns);
    }

    private void setNewModel(List<String> columns) {
        tableDataList = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {

            String str = columns.get(i);

            TableData data = new TableData(
                    i + 1,
                    str,
                    translationExecutor.executeTranslation(str),
                    modelComboboxTypes.getElementAt(0));
            tableDataList.add(data);
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return tableDataList.size();
    }

    @Override
    public int getColumnCount() {
        return TableColumns.LIST_COLUMNS.size();
    }

    @Override
    public Object getValueAt(int i, int i1) {
        TableData tableData = tableDataList.get(i);
        switch (i1) {
            case 0:
                return tableData.getNumRow();
            case 1:
                return tableData.getOriginalColumnName();
            case 2:
                return tableData.getTranslationColumnName();
            case 3:
                return tableData.getTypeSql();
            case 4:
                return tableData.isUse();
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int i) {
        return TableColumns.LIST_COLUMNS.get(i).getName();
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        TableData tableData = tableDataList.get(i);
        switch (i1) {

            case 2:
                String input = String.valueOf(o);
                String translit = translationExecutor.executeTranslation(input);
                tableData.setTranslationColumnName(translit);
                fireTableRowsUpdated(i, i1);
                break;
            case 3:
                tableData.setTypeSql((TypeSql) o);
                fireTableRowsUpdated(i, i1);
                break;
            case 4:
                boolean select = !tableData.isUse();
                tableData.setUse(select);
                fireTableRowsUpdated(i, i1);
                break;
        }

    }

    @Override
    public Class<?> getColumnClass(int i) {
        return TableColumns.LIST_COLUMNS.get(i).getClassData();
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return TableColumns.LIST_COLUMNS.get(i1).isEditable();
    }

    public List<TableData> getTableDataList() {
        return tableDataList;
    }

}
