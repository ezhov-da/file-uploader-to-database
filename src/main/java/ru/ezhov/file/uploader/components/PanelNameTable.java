package ru.ezhov.file.uploader.components;

import java.awt.BorderLayout;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import ru.ezhov.file.uploader.interfaces.TableNameHolder;

public class PanelNameTable extends JPanel implements TableNameHolder {

    private static final Logger LOG = Logger.getLogger(PanelNameTable.class.getName());
    private JLabel label;
    private JTextField textFieldNameDriver;

    public PanelNameTable() {
        super(new BorderLayout());
        label = new JLabel("Введите название создаваемой таблицы:");
        textFieldNameDriver = new JTextField();

        add(label, BorderLayout.WEST);
        add(textFieldNameDriver, BorderLayout.CENTER);
        textFieldNameDriver.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String text = textFieldNameDriver.getText();
                        PanelNameTable.this.firePropertyChange(
                                "textFieldNameDriver",
                                "",
                                text
                        );
                    }
                });

            }
        });
    }

    @Override
    public String getNameTable() {
        return textFieldNameDriver.getText();
    }

    @Override
    public void setNameTable(String nameTable) {
        textFieldNameDriver.setText(nameTable);
    }

}
