package demo.sb.ssjwt.common.env.cmpo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * 注册 Jackson 中的 mapper 为 spring-bean
 */
@Component
public class JacksonBean {

    /**
     * bean：ObjectMapper
     */
    public static ObjectMapper OM = null;

    public JacksonBean() {
        OM = new ObjectMapper();
    }
}
