package demo.sb.ssjwt.common.exc;

import demo.sb.ssjwt.common.util.CommonConst;
import demo.sb.ssjwt.common.util.StringUtil;
import lombok.Getter;

public class CheckedException extends RuntimeException {
    @Getter
    protected int code = CommonConst.DEF_FAILURE_CODE;
    protected final String message;

    public CheckedException(String message) {
        super(StringUtil.isNullOrBlank(message) ? CommonConst.DEF_FAILURE_MSG : message);
        this.message = message;
    }

    public CheckedException() {
        this(CommonConst.DEF_FAILURE_MSG);
    }

    public CheckedException(Throwable cause) {
        String m;
        if (cause != null && !StringUtil.isNullOrBlank(m = cause.getMessage())) {
            message = m;
        } else {
            message = CommonConst.DEF_FAILURE_MSG;
        }
    }

    public <E extends CheckedException> CheckedException(E e) {
        if (e != null) {
            code = e.code;
            String m = e.getMessage();
            if (StringUtil.isNullOrBlank(m)) {
                message = m;
                return;
            }
        }
        message = CommonConst.DEF_FAILURE_MSG;
    }
}

