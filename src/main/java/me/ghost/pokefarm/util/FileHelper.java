package me.ghost.pokefarm.util;

import me.ghost.pokefarm.Main;

import java.io.File;
import java.security.CodeSource;

public class FileHelper {

    public static File getThis() {
        CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
        try {
            return new File(codeSource.getLocation().toURI().getPath());
        } catch (Exception e) {
            return null;
        }
    }

}
