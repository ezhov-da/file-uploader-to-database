package ru.ezhov.file.uploader.utils;

import java.io.File;

public class Settings {
    public static final String CHARSET = "UTF-8";
    public static final String USER_HOME_PATH = System.getProperty("user.home");
    public static final String DIRECTORY_SETTINGS_PROJECT = ".excel_loader";
    public static final String FILE_SETTINGS = "jdbc_settings.groovy";
    public static final String PATH_TO_DIRECTORY_SETTINGS = USER_HOME_PATH + File.separator + DIRECTORY_SETTINGS_PROJECT;
    public static final String PATH_TO_FILE_SETTINGS = PATH_TO_DIRECTORY_SETTINGS + File.separator + FILE_SETTINGS;
}
