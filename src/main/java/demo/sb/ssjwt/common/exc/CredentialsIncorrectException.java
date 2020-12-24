package demo.sb.ssjwt.common.exc;

import demo.sb.ssjwt.common.enums.ErrMsg;
import demo.sb.ssjwt.common.env.cmpo.ErrMsgProp;
import lombok.Getter;

/**
 * 登录号或密码错误
 */
@Getter
public class CredentialsIncorrectException extends CheckedException {
    private final int code = ErrMsg.EM10000.getCode();

    public CredentialsIncorrectException() {
        super(ErrMsgProp.getMessage(ErrMsg.EM10000));
    }
}
