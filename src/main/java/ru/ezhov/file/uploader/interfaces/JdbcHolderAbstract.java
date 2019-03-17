package ru.ezhov.file.uploader.interfaces;

import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;
import ru.ezhov.file.uploader.beans.JdbcBean;

public abstract class JdbcHolderAbstract
        extends AbstractListModel<JdbcBean>
        implements MutableComboBoxModel<JdbcBean>, JdbcHolder
{

    @Override
    abstract public int getSize();

    @Override
    abstract public JdbcBean getElementAt(int index);

    @Override
    abstract public void load() throws Exception;

    @Override
    abstract public void save(String source) throws Exception;

    @Override
    abstract public String getSource();

    @Override
    abstract public JdbcBean getSelectedJdbcBean();
}
