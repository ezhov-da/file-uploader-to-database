package ru.ezhov.file.uploader.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActiveLibraryPath {
    private static final Logger LOG = Logger.getLogger(ActiveLibraryPath.class.getName());

    public static final synchronized void setLibraryPath(String path) {
        try {
            System.setProperty("java.library.path", path);
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "error load library path", ex);
        }
    }

    public static final synchronized void setPath() throws IOException {
        LOG.info("устанавливаем пути dll");
        String libx64 = AppProperties.getPropProperty("d11.64");
        LOG.log(Level.INFO, "64: {0}", libx64);
        String libx86 = AppProperties.getPropProperty("d11.86");
        LOG.log(Level.INFO, "86: {0}", libx86);
        File filex64 = new File(libx64);
        File filex86 = new File(libx86);
        String ocArch = System.getProperty("sun.arch.data.model");
        String fullPath = null;
        if ("64".equals(ocArch)) {
            fullPath = filex64.getAbsolutePath();
            LOG.log(Level.INFO, "64: {0}", fullPath);
        } else if ("32".equals(ocArch)) {
            fullPath = filex86.getAbsolutePath();
            LOG.log(Level.INFO, "32: {0}", fullPath);
        }
        setLibraryPath(fullPath);
    }
}
