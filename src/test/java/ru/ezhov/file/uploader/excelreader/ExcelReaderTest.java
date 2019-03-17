package ru.ezhov.file.uploader.excelreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

public class ExcelReaderTest {

    public ExcelReaderTest() {
    }

    @Test
    public void testRead() {
        InputStream inputStream = null;
        try {
            File file = new File("test_load_excel.xlsx");
            System.out.println(file.getAbsolutePath());
            inputStream = new FileInputStream(file);
            try (ExcelHandlerImpl excelReader = new ExcelHandlerImpl(ExcelFormat.XSSF, inputStream, file.getName());) {
                excelReader.openBook();
                List<String> stringList = excelReader.getListHeader();
                for (String string : stringList) {
                    System.out.println(string);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ExcelReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelReaderTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
