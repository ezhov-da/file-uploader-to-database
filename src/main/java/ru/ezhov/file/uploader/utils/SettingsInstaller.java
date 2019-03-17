package ru.ezhov.file.uploader.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class SettingsInstaller {

    public void create() throws FileNotFoundException, IOException {
        String fileNameSettings = Settings.FILE_SETTINGS;

        String appDirectory = Settings.PATH_TO_DIRECTORY_SETTINGS;

        File fileDirectory = new File(appDirectory);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
            File settingsGroovyFile = new File(Settings.PATH_TO_FILE_SETTINGS);
            try (InputStream inputStream = SettingsInstaller.class.getResourceAsStream("/" + fileNameSettings);) {
                try (Scanner scanner = new Scanner(inputStream, Settings.CHARSET);) {
                    try (FileOutputStream fileOutputStream = new FileOutputStream(settingsGroovyFile);) {
                        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, Settings.CHARSET);) {
                            while (scanner.hasNextLine()) {
                                outputStreamWriter.append(scanner.nextLine());
                                outputStreamWriter.append("\n");
                            }
                        }
                    }
                }
            }
        }
    }
}
