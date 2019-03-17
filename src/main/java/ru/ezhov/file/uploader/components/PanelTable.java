package ru.ezhov.file.uploader.components;

import java.awt.BorderLayout;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import ru.ezhov.file.uploader.components.models.ModelTableField;
import ru.ezhov.file.uploader.beans.TableData;
import ru.ezhov.file.uploader.components.models.Column;
import ru.ezhov.file.uploader.components.models.ModelComboboxTypes;
import ru.ezhov.file.uploader.components.models.TableColumns;
import ru.ezhov.file.uploader.components.render.ComboBoxRender;
import ru.ezhov.file.uploader.interfaces.TableDataColumn;

/**
 * Панель для отображения данных
 */
public class PanelTable extends JPanel implements TableDataColumn
{

    private static final Logger LOG = Logger.getLogger(PanelTable.class.getName());
    private JTable table;
    private ModelTableField modelTableField;
    private ModelComboboxTypes modelComboboxTypes;

    public PanelTable(ModelTableField modelTableField,
            ModelComboboxTypes modelComboboxTypes)
    {
        super(new BorderLayout());
        this.modelTableField = modelTableField;
        this.modelComboboxTypes = modelComboboxTypes;
        table = new JTable(modelTableField);

        Column column = TableColumns.LIST_COLUMNS.get(0);

        TableColumn tableColumnShow = table.getColumn(column.getName());
        tableColumnShow.setMaxWidth(column.getPrefWidth());
        tableColumnShow.setPreferredWidth(column.getPrefWidth());

        column = TableColumns.LIST_COLUMNS.get(3);
        TableColumn tableColumn = table.getColumn(column.getName());
        JComboBox comboBox = new JComboBox(modelComboboxTypes);
        comboBox.setMaximumRowCount(modelComboboxTypes.getSize());
        comboBox.setRenderer(new ComboBoxRender());
        DefaultCellEditor defaultCellEditor = new DefaultCellEditor(comboBox);
        tableColumn.setCellEditor(defaultCellEditor);

        column = TableColumns.LIST_COLUMNS.get(4);
        tableColumn = table.getColumn(column.getName());
        tableColumn.setMaxWidth(column.getPrefWidth());
        tableColumn.setPreferredWidth(column.getPrefWidth());

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setModelTableField(final ModelTableField modelTableField)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                PanelTable.this.modelTableField = modelTableField;
                PanelTable.this.modelTableField.fireTableDataChanged();
            }
        });

    }

    @Override
    public List<TableData> getTableData()
    {
        return modelTableField.getTableDataList();
    }
}
