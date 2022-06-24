package me.ghost.pokefarm.util.system;

import me.ghost.pokefarm.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class CommandHelper {

    public static String runCommandWithResult(String command) {
        try {
            String[] cmd = new String[]{"cmd", "/c", command};
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            StringBuilder b = new StringBuilder();
            String line;
            Process p = pb.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = r.readLine()) != null) b.append(line);
            r.close();
            return b.toString();
        } catch (Exception e) {
            Main.debug("Error running command: " + command);
            Main.debug("Exception: " + e);
            return null;
        }
    }

    public static void runCommand(String command) {
        try {
            String[] cmd = new String[]{"cmd", "/c", command};
            new ProcessBuilder(cmd).start().waitFor();
        } catch (Exception e) {
            Main.debug("Error running command: " + command);
            Main.debug("Exception: " + e);
        }
    }

    public static void sleep(long s, int t) {
        try {
            switch (t) {
                case 1 -> TimeUnit.MILLISECONDS.sleep(s);
                case 2 -> TimeUnit.SECONDS.sleep(s);
                case 3 -> TimeUnit.MINUTES.sleep(s);
            }
        } catch (Exception ignored) {}
    }

}
