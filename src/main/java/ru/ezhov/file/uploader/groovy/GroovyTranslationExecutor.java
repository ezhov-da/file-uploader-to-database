package ru.ezhov.file.uploader.groovy;

import groovy.lang.GroovyShell;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.ezhov.file.uploader.interfaces.TranslationExecutor;

public class GroovyTranslationExecutor implements TranslationExecutor {

    private final GroovyShell groovyShell;

    public GroovyTranslationExecutor() {
        groovyShell = new GroovyShell();
    }

    @Override
    public String executeTranslation(String textForTranslation) {
        try (InputStreamReader inputStreamReader
                     = new InputStreamReader(
                GroovyTranslationExecutor.class
                        .getResourceAsStream("/translite.groovy"), StandardCharsets.UTF_8)) {
            groovyShell.setProperty("inputWord", textForTranslation);
            Object objectResult = groovyShell.evaluate(inputStreamReader);
            return String.valueOf(objectResult);
        } catch (Exception ex) {
            Logger.getLogger(GroovyTranslationExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
