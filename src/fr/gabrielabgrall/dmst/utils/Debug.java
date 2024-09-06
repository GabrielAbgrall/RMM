package fr.gabrielabgrall.dmst.utils;

public class Debug {

    protected static boolean debug = false;

    public static void log(Object... data) {
        if(!debug) return;
        print(data);
    }

    public static void error(Object... data) {
        print("\\u001B[31m", data, "\\u001B[0m");
    }

    public static void setDebug(boolean debug) {
        Debug.debug = debug;
    }

    public static void print(Object... data) {
        StringBuilder s = new StringBuilder();
        for (int i = 1; i<data.length; i++) {
            s.append(data[i]);   
        }
        System.out.printf("%ty-%<tm-%<tj %<tH-%<tM-%<tS | %17s | %s\n", System.currentTimeMillis(), data[0], s.toString());
    }
}