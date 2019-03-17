package ru.ezhov.file.uploader;

import ru.ezhov.file.uploader.components.*;
import ru.ezhov.file.uploader.components.models.ModelComboboxTypes;
import ru.ezhov.file.uploader.components.models.ModelTableField;
import ru.ezhov.file.uploader.groovy.GroovyTranslationExecutor;
import ru.ezhov.file.uploader.groovy.JdbcSettingsModel;
import ru.ezhov.file.uploader.interfaces.TranslationExecutor;
import ru.ezhov.file.uploader.utils.ActiveLibraryPath;
import ru.ezhov.file.uploader.utils.QueryCreateTableGenerator;
import ru.ezhov.file.uploader.utils.QueryInsertGenerator;
import ru.ezhov.file.uploader.utils.SettingsInstaller;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Application {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        try {
            LogManager
                    .getLogManager()
                    .readConfiguration(
                            Application.class.getResourceAsStream("/log.properties"));
        } catch (Exception ex) {
            LOG.log(Level.OFF, "Don't load properties logger", ex);
        }

        try {
            ActiveLibraryPath.setPath();
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Throwable ex) {
                        //
                    }

                    DispatcherBind dispatherBind = new DispatcherBind();

                    SettingsInstaller settingsInstaller = new SettingsInstaller();
                    try {
                        settingsInstaller.create();
                    } catch (IOException ex) {
                        LOG.log(Level.WARNING, "Не удалось сохранить настройки JDBC драйверов в папке пользователя");
                    }

                    TranslationExecutor translationExecutor = new GroovyTranslationExecutor();
                    JdbcSettingsModel jdbcSettingsModel = new JdbcSettingsModel();

                    try {
                        jdbcSettingsModel.load();
                        dispatherBind.setJdbcHolderAbstract(jdbcSettingsModel);
                    } catch (Exception ex) {
                        LOG.log(Level.WARNING, "Не удалось загрузить настройки JDBC драйверов");
                    }

                    PanelSelectedFile panelSelectedFile = new PanelSelectedFile();
                    panelSelectedFile.addPropertyChangeListener(dispatherBind);

                    PanelConnections panelConnections = new PanelConnections(jdbcSettingsModel);

                    PanelNameTable panelNameTable = new PanelNameTable();
                    panelNameTable.addPropertyChangeListener(dispatherBind);

                    ModelTableField modelTableField = null;
                    ModelComboboxTypes modelComboboxTypes = null;
                    PanelTable panelTable = null;
                    try {
                        modelComboboxTypes = new ModelComboboxTypes();

                        modelTableField = new ModelTableField(
                                new ArrayList<String>(),
                                translationExecutor,
                                modelComboboxTypes);
                        modelTableField.addTableModelListener(dispatherBind);

                        panelTable = new PanelTable(modelTableField, modelComboboxTypes);
                    } catch (Exception ex) {
                        Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                        System.exit(10);
                    }

                    PanelCreateQueryText panelCreateTable = new PanelCreateQueryText(new QueryCreateTableGenerator());

                    PanelCreateQueryText panelCreateInserQuery = new PanelCreateQueryText(new QueryInsertGenerator());

                    PanelExecute panelExecute = new PanelExecute(dispatherBind.getActionExecute());

                    dispatherBind.setCodeTemplateCreate(panelCreateTable);
                    dispatherBind.setCodeTemplateInsert(panelCreateInserQuery);
                    dispatherBind.setFileSelected(panelSelectedFile);
                    dispatherBind.setTableDataColumn(panelTable);
                    dispatherBind.setTableNameHolder(panelNameTable);
                    dispatherBind.setModelTableField(modelTableField);

                    JFrame frame = new BasicFrame(
                            panelSelectedFile,
                            panelConnections,
                            panelNameTable,
                            panelTable,
                            panelCreateTable,
                            panelCreateInserQuery,
                            panelExecute,
                            PanelLog.getInstance()
                    );
                    frame.setSize(1000, 600);
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                    //
                }
            }
        });
    }
}
