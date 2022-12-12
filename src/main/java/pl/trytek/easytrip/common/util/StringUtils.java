package pl.trytek.easytrip.common.util;

public class StringUtils {

    public static final String RESPONSE_OK = "OK";
    public static final String BAD_REQUEST = "Bad Request";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access";
    public static final String NOT_FOUND = "Not Found";
    public static final String API = "api/";
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
