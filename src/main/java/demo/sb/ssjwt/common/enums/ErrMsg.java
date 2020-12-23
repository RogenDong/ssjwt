package demo.sb.ssjwt.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常消息枚举
 */
@Getter
@AllArgsConstructor
public enum ErrMsg {
    EM10000(10000),
    EM10001(10001),

    EM10010(10010),
    EM10011(10011);
    private final int code;
}
