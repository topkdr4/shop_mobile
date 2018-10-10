package ru.vetoshkin.shop_mobile.util;
import java.io.PrintWriter;
import java.io.StringWriter;





public class Util {

    public static boolean isEmpty(String source) {
        return source == null || source.trim().length() == 0;
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw  = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    private Util() {

    }
}
