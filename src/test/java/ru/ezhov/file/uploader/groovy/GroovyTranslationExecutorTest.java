package ru.ezhov.file.uploader.groovy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GroovyTranslationExecutorTest {

    public GroovyTranslationExecutorTest() {
    }

    @Test
    public void testExecuteTranslit() {
        GroovyTranslationExecutor groovyTranslationExecutor = new GroovyTranslationExecutor();
        String result = groovyTranslationExecutor.executeTranslation("привет_<> how № дела -");
        assertEquals("PRIVET__HOW__DELA", result);
        result = groovyTranslationExecutor.executeTranslation("№");
        assertEquals("EMPTY", result);
    }

}
