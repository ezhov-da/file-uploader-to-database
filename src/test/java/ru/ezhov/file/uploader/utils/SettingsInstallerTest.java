package ru.ezhov.file.uploader.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

public class SettingsInstallerTest {

    public SettingsInstallerTest() {
    }

    @Test
    public void testCreate() {
        try {

            SettingsInstaller settingsInstaller = new SettingsInstaller();
            try {
                settingsInstaller.create();
            } catch (IOException ex) {

            }

            SettingsInstaller folderHomeCreator = new SettingsInstaller();
            folderHomeCreator.create();
        } catch (IOException ex) {
            Logger.getLogger(SettingsInstallerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
