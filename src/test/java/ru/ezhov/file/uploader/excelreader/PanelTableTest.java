package ru.ezhov.file.uploader.excelreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ru.ezhov.file.uploader.components.models.ModelTableField;
import ru.ezhov.file.uploader.components.PanelTable;
import ru.ezhov.file.uploader.components.models.ModelComboboxTypes;
import ru.ezhov.file.uploader.groovy.GroovyTranslationExecutor;
import ru.ezhov.file.uploader.interfaces.ExcelHandler;
import ru.ezhov.file.uploader.interfaces.TranslationExecutor;

public class PanelTableTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {

                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        } catch (Throwable ex) {
                            //
                        }
                        InputStream inputStream = null;

                        File file = new File("test_load_excel.xlsx");
                        System.out.println(file.getAbsolutePath());

                        try {
                            inputStream = new FileInputStream(file);
                            final ExcelHandler excelHandler = new ExcelHandlerImpl(ExcelFormat.XSSF, inputStream, file.getName());
                            excelHandler.openBook();
                            Runtime.getRuntime().addShutdownHook(new Thread() {

                                @Override
                                public void run() {
                                    try {
                                        excelHandler.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(PanelTableTest.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }

                            });
                            TranslationExecutor translationExecutor = new GroovyTranslationExecutor();

                            ModelComboboxTypes comboboxTypes = new ModelComboboxTypes();

                            ModelTableField modelTableField =
                                    new ModelTableField(
                                            excelHandler.getListHeader(),
                                            translationExecutor,
                                            comboboxTypes);

                            PanelTable panelTable = new PanelTable(modelTableField, comboboxTypes);
                            JFrame frame = new JFrame("_________");
                            frame.add(panelTable);
                            frame.setSize(1000, 600);
                            frame.setLocationRelativeTo(null);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame.setVisible(true);

                        } catch (Throwable ex) {

                        }
                    }
                }
        );
    }
}
