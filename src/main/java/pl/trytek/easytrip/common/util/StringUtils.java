package pl.trytek.easytrip.common.util;

public class StringUtils {

    public static final String API = "api/";
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
