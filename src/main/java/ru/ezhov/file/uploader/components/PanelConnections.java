package ru.ezhov.file.uploader.components;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import ru.ezhov.file.uploader.interfaces.AppConnection;
import ru.ezhov.file.uploader.interfaces.JdbcHolderAbstract;

public class PanelConnections extends JPanel implements AppConnection {

    private static final Logger LOG = Logger.getLogger(PanelConnections.class.getName());
    private final JLabel label;
    private final JButton button;
    private final JComboBox comboBox;
    private final JdbcHolderAbstract jdbcHolderAbstract;
    private EditorSourceJdbcSettingsDialog dialog;

    public PanelConnections(JdbcHolderAbstract jdbcHolderAbstract) {
        this.jdbcHolderAbstract = jdbcHolderAbstract;
        label = new JLabel("Выберите подключение к БД:");
        comboBox = new JComboBox();
        comboBox.setModel(jdbcHolderAbstract);
        button = new JButton("Редактировать список подключений");

        dialog = new EditorSourceJdbcSettingsDialog();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(label);
        add(comboBox);
        add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(true);
            }
        });
    }

    @Override
    public String getURLConnection() {
        return "";
    }

    private class EditorSourceJdbcSettingsDialog {

        private final JDialog dialog;
        private final RSyntaxTextArea syntaxTextArea;
        private final JButton buttonSave;

        public EditorSourceJdbcSettingsDialog() {
            dialog = new JDialog();

            syntaxTextArea = new RSyntaxTextArea();
            syntaxTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_GROOVY);
            RTextScrollPane sp = new RTextScrollPane(syntaxTextArea);
            sp.setLineNumbersEnabled(true);

            buttonSave = new JButton("Сохранить");

            JPanel panel = new JPanel(new BorderLayout());

            panel.add(sp, BorderLayout.CENTER);
            panel.add(buttonSave, BorderLayout.SOUTH);

            dialog.add(panel);
            dialog.setTitle("Редактор JDBC конфигурации");
            dialog.setSize(600, 500);
            dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
            dialog.setLocationRelativeTo(PanelConnections.this);

            buttonSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                jdbcHolderAbstract.save(syntaxTextArea.getText());
                            } catch (Exception ex) {
                                LOG.log(Level.SEVERE, "Не удалось записать файл", ex);
                            }
                        }
                    });

                }
            });
        }

        public void setVisible(boolean show) {
            if (show) {
                syntaxTextArea.setText(jdbcHolderAbstract.getSource());
            }

            dialog.setVisible(show);
        }

    }

}
