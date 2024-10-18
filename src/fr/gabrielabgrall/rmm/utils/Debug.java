package fr.gabrielabgrall.rmm.utils;

public class Debug {

    protected static boolean debug = false;
    
    public static void setDebug(boolean debug) {
        Debug.debug = debug;
    }

    public static void log(Object... data) {
        if(!debug) return;
        print(data);
    }

    public static void error(Object... data) {
        print(data);
    }

    public static void print(Object... data) {
        StringBuilder s = new StringBuilder();
        for (Object d : data) {
            s.append(d);   
        }
        System.out.printf("%ty-%<tm-%<tj %<tH-%<tM-%<tS | %s | %s\n", System.currentTimeMillis(), Thread.currentThread().getName(), s.toString());
    }
}