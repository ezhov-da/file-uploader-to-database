package ru.ezhov.file.uploader.utils;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import ru.ezhov.file.uploader.beans.TableData;
import ru.ezhov.file.uploader.types.TypeSql;

public class QueryCreateTableGeneratorTest {

    public QueryCreateTableGeneratorTest() {
    }

    @Test
    public void testGenerate() {
        List<TableData> tableDatas = new ArrayList<>();
        tableDatas.add(new TableData(0, "aaa", "bbbb", new TypeSql("sdasgasgagd", "VARCHAR(100)", Types.VARCHAR)));
        tableDatas.add(new TableData(0, "aaa", "bbbb", new TypeSql("sdasgasgagd", "VARCHAR(100)", Types.VARCHAR)));
        tableDatas.add(new TableData(0, "aaa", "bbbb", new TypeSql("sdasgasgagd", "VARCHAR(100)", Types.VARCHAR)));
        QueryGenerator queryGenerator = new QueryCreateTableGenerator();
        String string = queryGenerator.generate("dbo.T_E_TEST", tableDatas);
        System.out.println(string);
    }

}
