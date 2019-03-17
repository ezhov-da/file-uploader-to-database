package ru.ezhov.file.uploader.interfaces;

import java.util.List;
import ru.ezhov.file.uploader.beans.TableData;

public interface CodeTemplate
{
    public void setCode(String nameTable, List<TableData> listColumn);

    public String getText();
}
