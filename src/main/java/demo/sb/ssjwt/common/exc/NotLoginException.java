package demo.sb.ssjwt.common.exc;

import demo.sb.ssjwt.common.enums.ErrMsg;
import demo.sb.ssjwt.common.env.cmpo.ErrMsgProp;

/**
 * 未登录
 */
public class NotLoginException extends CheckedException {
    private final int code = ErrMsg.EM10010.getCode();

    public NotLoginException() {
        super(ErrMsgProp.getMessage(ErrMsg.EM10010));
    }
}
