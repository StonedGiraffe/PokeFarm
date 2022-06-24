package me.ghost.pokefarm.util.system;

import me.ghost.pokefarm.Main;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
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

    public static File getWorkDir() {
        CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
        try {
            return new File(codeSource.getLocation().toURI().getPath()).getParentFile();
        } catch (Exception e) {
            return null;
        }
    }

    public static File getBaseFolder() {
        return new File(getWorkDir(), "pokefarm");
    }

    public static InputStream getResource(String name) {
        try {
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static boolean writeResource(String name, File out) {
        InputStream is = getResource(name);
        if (is == null) return false;
        if (!out.exists()) {
            try {
                out.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        byte[] buff = new byte[1024];
        try {
            FileOutputStream fos = new FileOutputStream(out);
            while (is.read(buff, 0, buff.length) != -1) fos.write(buff);
            fos.close();
            is.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeRscImage(String name, File out) {
        BufferedImage im = getRscImg(name);
        if (im == null) return false;
        try {
            ImageIO.write(im, "png", out);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static BufferedImage getRscImg(String name) {
        InputStream is = getResource(name);
        if (is == null) return null;
        try {
            BufferedImage im = ImageIO.read(is);
            is.close();
            return im;
        } catch (Exception e) {
            Main.debug("getImage() error | wanted image: " + name);
            Main.debug("Exception: " + e);
            return null;
        }
    }

    public static Mat loadMat(File f) {
        try {
            return Imgcodecs.imread(f.getPath());
        } catch (Exception e) {
            Main.debug("loadMat() error | wanted file: " + f.getPath());
            Main.debug("Exception: " + e);
            return null;
        }
    }

    public static String streamToString(InputStream in) {
        if (in == null) return null;
        try {
            BufferedInputStream bin = new BufferedInputStream(in);
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            for (int result = bin.read(); result != -1; result = bin.read()) buf.write((byte) result);
            return buf.toString(StandardCharsets.UTF_8);
        } catch (Exception ignored) {
            return null;
        }
    }

}
