package demo.sb.ssjwt.api;

import demo.sb.ssjwt.common.env.cmpo.JWTokenUtil;
import demo.sb.ssjwt.common.mod.R;
import demo.sb.ssjwt.common.util.StringUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试 API
 */
@RestController
public class TestApi {

    /**
     * 必须认证的 api
     *
     * @param request 请求数据
     * @return 账户号
     */
    @GetMapping("must")
    public R<String> must(HttpServletRequest request) {
        R<String> result = R.fail(null);
        if (request == null) {
            return result;
        }
        String jwt = request.getHeader(JWTokenUtil.that.getHeaderKey());
        if (StringUtil.isNullOrBlank(jwt)) {
            result.data = "";
        } else {
            result = R.ok(jwt);
        }
        return result;
    }

    /**
     * 不用认证的 api
     *
     * @return hello world!
     */
    @GetMapping("ign")
    public R<String> ignore() {
        return R.ok("hello world!");
    }
}
