package util;

import jakarta.servlet.http.HttpServletRequest;

public class StringToLongUtil {
    public static long getLongParameter(String paramValue) {
        if (paramValue != null) {
            return Long.parseLong(paramValue.replaceAll("[^0-9]", ""));
        }
        throw new IllegalArgumentException("Parameter is missing or invalid");
    }
}
