package ru.ezhov.file.uploader.components;

import java.awt.BorderLayout;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class BasicFrame extends JFrame {

    private static final Logger LOG = Logger.getLogger(BasicFrame.class.getName());
    private JComponent panelSelectedFile;
    private JComponent panelConnections;
    private JComponent panelNameTable;
    private JComponent panelTable;
    private JComponent panelCreateTable;
    private JComponent panelCreateInserQuery;
    private JComponent panelExecute;
    private JComponent panelLogger;

    public BasicFrame(
            JComponent panelSelectedFile,
            JComponent panelConnections,
            JComponent panelNameTable,
            JComponent panelTable,
            JComponent panelCreateTable,
            JComponent panelCreateInserQuery,
            JComponent panelExecute,
            JComponent panelLogger
    ) {
        super("Внесение данных в БД из Excel");
        this.panelSelectedFile = panelSelectedFile;
        this.panelConnections = panelConnections;
        this.panelNameTable = panelNameTable;
        this.panelTable = panelTable;
        this.panelCreateTable = panelCreateTable;
        this.panelCreateInserQuery = panelCreateInserQuery;
        this.panelExecute = panelExecute;
        this.panelLogger = panelLogger;

        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
        panelTop.add(panelSelectedFile);
        panelTop.add(panelConnections);
        panelTop.add(panelNameTable);

        JPanel panelCenterTableColumn = new JPanel(new BorderLayout());
        panelCenterTableColumn.add(panelTop, BorderLayout.NORTH);
        panelCenterTableColumn.add(panelTable, BorderLayout.CENTER);

        JSplitPane splitPaneCreator =
                new JSplitPane(
                        JSplitPane.HORIZONTAL_SPLIT,
                        true,
                        panelCreateTable,
                        panelCreateInserQuery);
        splitPaneCreator.setDividerLocation(0.5);
        splitPaneCreator.setResizeWeight(0.5);

        JSplitPane splitCreatorLogger =
                new JSplitPane(
                        JSplitPane.VERTICAL_SPLIT,
                        true,
                        splitPaneCreator,
                        panelLogger);
        splitCreatorLogger.setDividerLocation(0.7);
        splitCreatorLogger.setResizeWeight(0.7);

        JPanel panelSplitCreator = new JPanel(new BorderLayout());
        panelSplitCreator.add(splitCreatorLogger, BorderLayout.CENTER);

        JSplitPane splitPaneBasic =
                new JSplitPane(
                        JSplitPane.VERTICAL_SPLIT,
                        true,
                        panelCenterTableColumn,
                        panelSplitCreator);
        splitPaneBasic.setDividerLocation(0.5);
        splitPaneBasic.setResizeWeight(0.5);

        JPanel panelBasic = new JPanel(new BorderLayout());
        panelBasic.add(splitPaneBasic, BorderLayout.CENTER);
        add(panelBasic, BorderLayout.CENTER);
        add(panelExecute, BorderLayout.SOUTH);
    }

}
