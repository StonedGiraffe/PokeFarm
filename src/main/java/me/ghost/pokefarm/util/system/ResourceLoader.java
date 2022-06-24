package me.ghost.pokefarm.util.system;

import me.ghost.pokefarm.Main;

import java.io.File;
import java.util.List;

public class ResourceLoader {

    public static List<String> resources = List.of("go_plus.png", "main_menu.png", "pgs_menu.png"); //todo better way of doing this


    public static boolean init() {
        if (Main.debug) Main.debug("ResourceLoader init()");
        // setup folders
        File mainFolder = new File(FileHelper.getWorkDir(), "pokefarm");
        File rscFolder = new File(mainFolder, "images");
        if (!mainFolder.exists()) if (!mainFolder.mkdirs()) return false;
        if (!rscFolder.exists()) if (!rscFolder.mkdirs()) return false;
        // extract images from resources
        for (String rsc : resources) {
            File rscFile = new File(rscFolder, rsc);
            if (!rscFile.exists()) if (!FileHelper.writeRscImage(rsc, rscFile)) return false;
        }
        if (Main.debug) Main.debug("ResourceLoader init finished.");
        return true;
    }
}
