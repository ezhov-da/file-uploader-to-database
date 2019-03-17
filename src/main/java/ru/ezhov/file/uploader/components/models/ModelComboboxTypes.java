package ru.ezhov.file.uploader.components.models;

import groovy.lang.GroovyShell;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

import ru.ezhov.file.uploader.groovy.GroovyTranslationExecutor;
import ru.ezhov.file.uploader.types.TypeSql;

public class ModelComboboxTypes extends DefaultComboBoxModel<TypeSql> {

    private static final Logger LOG = Logger.getLogger(ModelComboboxTypes.class.getName());
    private final GroovyShell groovyShell;
    protected List<TypeSql> listTypes;

    public ModelComboboxTypes() throws Exception {
        groovyShell = new GroovyShell();

        try (InputStreamReader inputStreamReader
                     = new InputStreamReader(
                GroovyTranslationExecutor.class
                        .getResourceAsStream("/sql_types.groovy"), "UTF-8")) {
            List<LinkedHashMap> linkedHashMaps
                    = (List<LinkedHashMap>) groovyShell.evaluate(inputStreamReader);
            this.listTypes = new ArrayList<>();
            for (LinkedHashMap hashMap : linkedHashMaps) {
                String html = String.valueOf(hashMap.get("html"));
                String typeCreateTable = String.valueOf(hashMap.get("typeCreateTable"));

                String val = String.valueOf(hashMap.get("typeSql"));
                int typeSql = Integer.valueOf(val);

                listTypes.add(new TypeSql(html, typeCreateTable, typeSql));
            }
        }
    }

    @Override
    public int getSize() {
        return listTypes.size();
    }

    @Override
    public TypeSql getElementAt(int i) {
        return listTypes.get(i);
    }

}
