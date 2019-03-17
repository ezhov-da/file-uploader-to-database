package ru.ezhov.file.uploader.components;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class PanelSelectedFileTest {

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
                        JFrame frame = new JFrame("_________");
                        PanelSelectedFile panelTable = new PanelSelectedFile();

                        frame.add(panelTable);
                        frame.setSize(1000, 600);
                        frame.setLocationRelativeTo(null);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setVisible(true);

                    }
                }
        );
    }

}
