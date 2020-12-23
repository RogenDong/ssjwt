package demo.sb.ssjwt.common.exc;

import demo.sb.ssjwt.common.enums.ErrMsg;
import demo.sb.ssjwt.common.env.cmpo.ErrMsgProp;
import lombok.Getter;

/**
 * 登录号和密码未填 TODO 前端需要识别
 */
@Getter
public class CredentialNotEnterException extends CheckedException {
    private final int code = ErrMsg.EM10001.getCode();

    public CredentialNotEnterException() {
        super(ErrMsgProp.getMessage(ErrMsg.EM10001));
    }
}
