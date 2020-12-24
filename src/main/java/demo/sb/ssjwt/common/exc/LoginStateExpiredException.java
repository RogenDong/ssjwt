package demo.sb.ssjwt.common.exc;

import demo.sb.ssjwt.common.enums.ErrMsg;
import demo.sb.ssjwt.common.env.cmpo.ErrMsgProp;
import lombok.Getter;

/**
 * 登录状态已过期
 */
@Getter
public class LoginStateExpiredException extends CheckedException {
    private final int code = ErrMsg.EM10011.getCode();

    public LoginStateExpiredException() {
        super(ErrMsgProp.getMessage(ErrMsg.EM10011));
    }
}
