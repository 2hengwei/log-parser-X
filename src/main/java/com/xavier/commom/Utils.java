package com.xavier.commom;

/**
 * @author zhengwei
 * @create 2017-08-25
 */
public class Utils {

    public static boolean isVariabelName(char c) {
        int digit = (int) c;
        if ((95 == digit)
                || (48 <= digit && digit <= 57)
                || (65 <= digit && digit <= 90)
                || (97 <= digit && digit <= 122)) {
            return true;
        }
        return false;
    }

    public static boolean isDollar(char c) {
        return Constant.Symbol.CHAR_DOLLAR == c;
    }

    public static String replaceEscapeCode(String text) {
        String newValue = text.replaceAll("\\s", "\\\\s")
                .replaceAll("\\[", "\\\\[");
        return newValue;
    }

    public static boolean isNoValue(String value) {
        if (null == value || value.equals(Constant.Symbol.HYPHEN)|| value.length() == 0 ) {
            return true;
        }
        return false;
    }

}
