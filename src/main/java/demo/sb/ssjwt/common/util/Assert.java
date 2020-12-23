package demo.sb.ssjwt.common.util;

import demo.sb.ssjwt.common.exc.CheckedException;

public class Assert {

    public static void hasText(String str, String msg) {
        hasText(str, new CheckedException(msg));
    }

    public static <CE extends CheckedException> void hasText(String str, CE ce) {
        if (StringUtil.isNullOrBlank(str)) {
            throw ce;
        }
    }
}
