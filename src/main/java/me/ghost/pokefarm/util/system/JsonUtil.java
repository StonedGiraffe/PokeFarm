package me.ghost.pokefarm.util.system;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtil {

    public static String getKey(String jsonString, String wantedKey) {
        Pattern jsonPattern = Pattern.compile("\""+wantedKey+"\" : \".*\"");
        Matcher matcher = jsonPattern.matcher(jsonString);
        if (matcher.find()) return matcher.group(0).split("\"")[3];
        return null;
    }

    public static Boolean getBool(String jsonString, String wantedKey) {
        return Boolean.parseBoolean(getKey(jsonString, wantedKey));
    }

    public static Integer getInt(String jsonString, String wantedKey) {
        String k = getKey(jsonString, wantedKey);
        if (k == null) return -1;
        return Integer.parseInt(k);
    }

    public static Double getDouble(String jsonString, String wantedKey) {
        String k = getKey(jsonString, wantedKey);
        if (k == null) return -1D;
        return Double.parseDouble(k);
    }

    public static Float getFloat(String jsonString, String wantedKey) {
        String k = getKey(jsonString, wantedKey);
        if (k == null) return -1F;
        return Float.parseFloat(k);
    }

    public static Long getLong(String jsonString, String wantedKey) {
        String k = getKey(jsonString, wantedKey);
        if (k == null) return -1L;
        return Long.parseLong(k);
    }
}
