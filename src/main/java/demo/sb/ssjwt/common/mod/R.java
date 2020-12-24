package demo.sb.ssjwt.common.mod;

import demo.sb.ssjwt.common.exc.CheckedException;
import demo.sb.ssjwt.common.util.CommonConst;
import demo.sb.ssjwt.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * 请求结果（result）
 *
 * @param <T> {@link #data}的 Java 类型
 */
@ToString
@AllArgsConstructor
public class R<T> {
    /**
     * 结果集主体
     */
    public T data;
    /**
     * 异常编码
     */
    public int code;
    /**
     * 异常消息文本
     */
    public String message;

    public R(Exception e, T data) {
        code = CommonConst.DEF_FAILURE_CODE;
        this.data = data;
        String m;
        if (e != null && StringUtil.isNullOrBlank(m = e.getMessage())) {
            message = m;
        } else {
            message = CommonConst.DEF_FAILURE_MSG;
        }
    }

    public <CE extends CheckedException> R(CE ce, T data) {
        code = CommonConst.DEF_FAILURE_CODE;
        this.data = data;
        if (ce != null) {
            if (!StringUtil.isNullOrBlank(ce.getMessage())) {
                message = ce.getMessage();
            }
            code = ce.getCode();
        } else {
            message = CommonConst.DEF_FAILURE_MSG;
        }
    }

    public R(String msg, int c) {
        code = c;
        data = null;
        message = msg;
    }

    public static <T> R<T> ok(T data) {
        return ok(data, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        if (StringUtil.isNullOrBlank(msg)) {
            msg = CommonConst.DEF_SUCCESS_MSG;
        }
        return new R<>(data, CommonConst.DEF_SUCCESS_CODE, msg);
    }

    public static <T> R<T> fail(T data) {
        return fail(data, null);
    }

    public static <T> R<T> fail(T data, String msg) {
        if (StringUtil.isNullOrBlank(msg)) {
            msg = CommonConst.DEF_FAILURE_MSG;
        }
        return new R<>(data, CommonConst.DEF_FAILURE_CODE, msg);
    }
}
