package ru.ezhov.file.uploader.components;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelExecute extends JPanel
{

    private JButton button;

    public PanelExecute(Action action)
    {
        button = new JButton(action);
        add(button);
    }

}
