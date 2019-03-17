package ru.ezhov.file.uploader.components;

import java.awt.BorderLayout;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class PanelLog extends JPanel
{
    private static final Logger LOG = Logger.getLogger(PanelLog.class.getName());
    private static PanelLog panelLog;
    private JEditorPane editorPane;

    private PanelLog()
    {
        super(new BorderLayout());
        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        add(new JScrollPane(editorPane), BorderLayout.CENTER);
    }

    public static PanelLog getInstance()
    {
        if (panelLog == null)
        {
            panelLog = new PanelLog();
        }
        return panelLog;
    }

    public void setText(final String text)
    {
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                String textFrom = editorPane.getText();
                editorPane.setText(text + textFrom);
                editorPane.setCaretPosition(0);
            }
        });

    }

}
