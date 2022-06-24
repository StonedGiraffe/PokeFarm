package me.ghost.pokefarm.util.screen;

import me.ghost.pokefarm.Main;
import me.ghost.pokefarm.util.pogo.Position;
import me.ghost.pokefarm.util.system.FileHelper;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;

public class ImageParser {



    public static Position locateImage(Mat screenshot, Mat toFind, String name) { //todo finish
        Position result = new Position("-1", "-1", name);
        if (screenshot == null || toFind == null) {
            Main.debug("screenshot or toFind is null? (should never happen)");
            return result;
        }
        if (screenshot.empty() || toFind.empty()) {
            Main.debug("screenshot or toFind is empty?");
            return result;
        }
        Point matchLoc;
        Mat tempI = new Mat(); // init temp mat
        Imgproc.matchTemplate(screenshot, toFind, tempI, Imgproc.TM_CCOEFF_NORMED); // perform template matching
        Core.MinMaxLocResult mmr = Core.minMaxLoc(tempI); // get the result location, set the returned x and y
        matchLoc = mmr.maxLoc;
        result.setX(String.valueOf(matchLoc.x));
        result.setY(String.valueOf(matchLoc.y));
        if (Main.debug) {
            Main.debug("locateImage() with name: " + name);
            Main.debug("X: " + matchLoc.x + " | Y: " + matchLoc.y);
            File outImg = new File(FileHelper.getWorkDir(), name + "_matched.png");
            // draw a rectangle around the matched area
            Imgproc.rectangle(screenshot, matchLoc, new Point(matchLoc.x + toFind.cols(), matchLoc.y + toFind.rows()), new Scalar(255, 0, 0));
            Main.debug("Writing matched.png");
            Imgcodecs.imwrite(outImg.getPath(), screenshot);
        }
        return result;
    }







}
