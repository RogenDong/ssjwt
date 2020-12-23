package demo.sb.ssjwt.common.exc;

import demo.sb.ssjwt.common.util.CommonConst;
import demo.sb.ssjwt.common.util.StringUtil;
import lombok.Getter;

public class CheckedException extends RuntimeException {
    @Getter
    protected int code = CommonConst.DEF_FAILURE_CODE;
    protected String message = CommonConst.DEF_FAILURE_MSG;

    public CheckedException() {
        super(CommonConst.DEF_FAILURE_MSG);
    }

    public CheckedException(String message) {
        super(message);
    }

    public CheckedException(Throwable cause) {
        String m;
        if (cause != null && !StringUtil.isNullOrBlank(m = cause.getMessage())) {
            message = m;
        }
    }

    public <E extends CheckedException> CheckedException(E e) {
        if (e != null) {
            code = e.code;
            String m = e.getMessage();
            if (StringUtil.isNullOrBlank(m)) {
                this.message = m;
            }
        }
    }

    @Override
    public String getMessage() {
        String s = super.getMessage();
        return StringUtil.isNullOrBlank(s) ? this.message : s;
    }
}

