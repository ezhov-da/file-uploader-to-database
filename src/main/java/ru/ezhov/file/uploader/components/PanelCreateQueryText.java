package ru.ezhov.file.uploader.components;

import java.awt.BorderLayout;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import ru.ezhov.file.uploader.beans.TableData;
import ru.ezhov.file.uploader.interfaces.CodeTemplate;
import ru.ezhov.file.uploader.utils.QueryGenerator;

public class PanelCreateQueryText extends JPanel implements CodeTemplate
{
    private static final Logger LOG = Logger.getLogger(PanelCreateQueryText.class.getName());
    private RSyntaxTextArea textPane;

    private QueryGenerator generator;

    public PanelCreateQueryText(QueryGenerator generator)
    {
        super(new BorderLayout());
        this.generator = generator;
        textPane = new RSyntaxTextArea();
        textPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
        RTextScrollPane sp = new RTextScrollPane(textPane);
        sp.setLineNumbersEnabled(true);
        add(sp, BorderLayout.CENTER);
    }

    @Override
    public void setCode(String nameTable, List<TableData> listColumn)
    {
        textPane.setText(generator.generate(nameTable, listColumn));
    }

    @Override
    public String getText()
    {
        return textPane.getText();
    }

}
