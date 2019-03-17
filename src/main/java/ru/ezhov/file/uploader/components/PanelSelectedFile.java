package ru.ezhov.file.uploader.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import ru.ezhov.file.uploader.interfaces.FileSelected;

public class PanelSelectedFile extends JPanel implements FileSelected {

    private static final Logger LOG = Logger.getLogger(PanelSelectedFile.class.getName());
    private JLabel labelText;
    private JTextField textField;
    private JButton buttonOpenFile;
    private JFileChooser fileChooser;
    private File selectedFile;

    public PanelSelectedFile() {
        super(new BorderLayout());
        labelText = new JLabel("Выберите файл:");

        textField = new JTextField();
        textField.setEditable(false);
        buttonOpenFile = new JButton("...");
        fileChooser = new JFileChooser();
        fileChooser.setAccessory(this);
        fileChooser.setDialogTitle("Выберите файл Excel(xls,xlsx)");
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        buttonOpenFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                int open = fileChooser.showOpenDialog(PanelSelectedFile.this);
                if (open == JFileChooser.APPROVE_OPTION) {
                    File old = selectedFile;
                    selectedFile = fileChooser.getSelectedFile();
                    PanelSelectedFile.this.firePropertyChange("selectedFile", null, selectedFile);
                    textField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File file) {
                String name = file.getAbsolutePath();
                return file.isDirectory() || name.endsWith("xls") || name.endsWith("xlsx");
            }

            @Override
            public String getDescription() {
                return "Excel(*.xls,*.xlsx)";
            }
        });

        add(labelText, BorderLayout.WEST);
        add(textField, BorderLayout.CENTER);
        add(buttonOpenFile, BorderLayout.EAST);
    }

    public JTextField getTextField() {
        return textField;
    }

    public JButton getButtonOpenFile() {
        return buttonOpenFile;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    @Override
    public File getSelectedFile() {
        return selectedFile;
    }

}
