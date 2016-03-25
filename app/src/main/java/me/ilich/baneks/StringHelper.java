package me.ilich.baneks;

public class StringHelper {

    public static String substring(String full, String start, String end) {
        int a = full.indexOf(start);
        int b = full.indexOf(end, a);
        return full.substring(a + start.length(), b);
    }

}
