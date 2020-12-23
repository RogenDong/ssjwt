package demo.sb.ssjwt.common.env.cmpo;

import demo.sb.ssjwt.common.enums.ErrMsg;
import demo.sb.ssjwt.common.util.CommonConst;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 配置注入受检异常消息
 */
@Component
@PropertySource("classpath:const/err-msg.properties")
public class ErrMsgProp {

    /**
     * 配置读取工具
     */
    private final Environment env;

    private static ErrMsgProp prop = null;

    public ErrMsgProp(Environment environment) {
        this.env = environment;
        ErrMsgProp.prop = this;
    }

    /**
     * 获取异常消息
     *
     * @param msgCode 异常消息枚举
     * @return 异常消息内容
     */
    public static String getMessage(ErrMsg msgCode) {
        if (prop == null || prop.env == null || msgCode == null) {
            return CommonConst.DEF_FAILURE_MSG;
        }
        return prop.env.getProperty(String.valueOf(msgCode.getCode()), CommonConst.DEF_FAILURE_MSG);
    }
}
