package ru.ezhov.file.uploader.components.render;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import ru.ezhov.file.uploader.types.TypeSql;

public class ComboBoxRender extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        TypeSql typeSql = (TypeSql) value;
        label.setText(typeSql.getHtmlText());

        return label;
    }

}
