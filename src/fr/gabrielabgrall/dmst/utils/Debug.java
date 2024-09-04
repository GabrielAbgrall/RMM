package fr.gabrielabgrall.dmst.utils;

public class Debug {

    protected static boolean debug = false;

    public static void Log(Object... data) {
        if(!debug) return;

        String line = "";
        for (Object d : data) {
            line += d.toString();
        }
        System.out.println(line);
    }
}