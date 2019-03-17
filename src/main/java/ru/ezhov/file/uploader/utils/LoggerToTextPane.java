package ru.ezhov.file.uploader.utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import ru.ezhov.file.uploader.components.PanelLog;

public class LoggerToTextPane extends ConsoleHandler
{
    private static final Logger LOG = Logger.getLogger(LoggerToTextPane.class.getName());
    private PanelLog panelLog;

    public LoggerToTextPane()
    {
        panelLog = PanelLog.getInstance();
    }

    @Override
    public void publish(LogRecord lr)
    {
        super.publish(lr);
        String text = super.getFormatter().format(lr);
        text = text.replaceAll("\n", "<br>");
        Level level = lr.getLevel();
        String htmeText;
        if (Level.INFO.getName().equals(level.getName()))
        {
            htmeText = "<font color=\"green\">" + text + "</font>";
        } else
        {
            htmeText = "<font color=\"red\">" + text + "</font>";
        }

        panelLog.setText(htmeText);
    }


}
