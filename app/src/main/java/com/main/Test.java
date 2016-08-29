package com.main;

/**
 * Created by luchenjie on 8/29/16.
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(toHexString("1"));
    }

    private static String toHexString(String s) {
        String str = "";

        for(int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }

        return str;
    }
}
