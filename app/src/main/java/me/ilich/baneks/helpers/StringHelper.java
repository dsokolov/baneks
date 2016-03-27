package me.ilich.baneks.helpers;

public class StringHelper {

    public static String substring(String full, String start, String end) {
        int a = full.indexOf(start);
        int b = full.indexOf(end, a);
        return full.substring(a + start.length(), b);
    }

    public static String substring(String full, String start, String end, int startIndex) {
        int a = full.indexOf(start, startIndex);
        int b = full.indexOf(end, a);
        return full.substring(a + start.length(), b);
    }

    public static boolean contains(String full, String part) {
        return full.contains(part);
    }

}

