package demo.sb.ssjwt.common.util;

public class StringUtil {
    public static boolean isNullOrBlank(String value) {
        return value == null || value.replace("\\S", "").length() < 1;
    }
}
