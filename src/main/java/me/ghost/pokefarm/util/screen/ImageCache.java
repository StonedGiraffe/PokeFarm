package me.ghost.pokefarm.util.screen;

import me.ghost.pokefarm.Main;
import me.ghost.pokefarm.util.system.FileHelper;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;

public class ImageCache {

    public static Mat main_menu_icon = new Mat();
    public static Mat go_plus_icon = new Mat();
    public static Mat pgs_menu_icon = new Mat();

    public static boolean init() {
        if (Main.debug) Main.debug("ImageCache init()");
        File base = new File(FileHelper.getWorkDir(), "pokefarm");
        File root = new File(base, "images");
        File main_menu = new File(root, "main_menu.png");
        File go_plus = new File(root, "go_plus.png");
        File pgs_menu = new File(root, "pgs_menu.png");
        if (!main_menu.exists() || !go_plus.exists() || !pgs_menu.exists()) {
            if (Main.debug) Main.debug("ImageCache init failed, one or more images missing from disk.");
            return false;
        }
        main_menu_icon = Imgcodecs.imread(main_menu.getAbsolutePath(), Imgcodecs.IMREAD_COLOR);
        go_plus_icon = Imgcodecs.imread(go_plus.getAbsolutePath(), Imgcodecs.IMREAD_COLOR);
        pgs_menu_icon = Imgcodecs.imread(pgs_menu.getAbsolutePath(), Imgcodecs.IMREAD_COLOR);
        if (Main.debug) Main.debug("ImageCache init finished.");
        return true;
    }


}
