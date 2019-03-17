package ru.ezhov.file.uploader.groovy;

import groovy.lang.GroovyShell;

import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GroovyNameTableCreator {

    private final GroovyShell groovyShell;

    public GroovyNameTableCreator() {
        groovyShell = new GroovyShell();
    }

    public String getNameTable() {
        try (InputStreamReader inputStreamReader
                     = new InputStreamReader(
                GroovyTranslationExecutor.class
                        .getResourceAsStream("/name_table_creator.groovy"), "UTF-8")) {
            Object objectResult = groovyShell.evaluate(inputStreamReader);
            return String.valueOf(objectResult);
        } catch (Exception ex) {
            Logger.getLogger(GroovyTranslationExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
