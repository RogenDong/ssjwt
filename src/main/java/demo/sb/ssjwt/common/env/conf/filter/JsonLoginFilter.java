package demo.sb.ssjwt.common.env.conf.filter;

import demo.sb.ssjwt.common.enums.ErrMsg;
import demo.sb.ssjwt.common.env.cmpo.ErrMsgProp;
import demo.sb.ssjwt.common.env.cmpo.JWTokenUtil;
import demo.sb.ssjwt.common.exc.CheckedException;
import demo.sb.ssjwt.common.mod.R;
import demo.sb.ssjwt.common.util.CommonConst;
import demo.sb.ssjwt.mod.vo.AccountDetailVo;
import demo.sb.ssjwt.mod.vo.SignInVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器：登录认证<br>
 * 处理登录参数<br>
 * 处理参数时无视请求头可能存在的 token<br>
 *
 * @see UsernamePasswordAuthenticationFilter
 */
public class JsonLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public JsonLoginFilter(String signInApi, AuthenticationManager manager) {
        super(manager);
        setFilterProcessesUrl(signInApi);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response
    ) throws AuthenticationException {
        String ct;
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null, null);
        if (request == null || (ct = request.getContentType()) == null || !ct.contains(CommonConst.APPLICATION_JSON)) {
            return getAuthenticationManager().authenticate(auth);
        }
        auth = ReqRspUtil.build(request, response, this.getClass())
                // 转化请求参数为 spring-security 需要的 token 实体
                .getByReq(SignInVo.class, auth, vo -> {
                    log.info("login account: {}", vo.getCodename());
                    return new UsernamePasswordAuthenticationToken(vo.getCodename(), vo.getPassword());
                });
        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult
    ) throws IOException, ServletException {
        if (response == null || authResult == null) {
            super.successfulAuthentication(request, response, chain, authResult);
            return;
        }
        ReqRspUtil reqRspUtil = ReqRspUtil.build(null, response, this.getClass());
        String token;
        try {
            // 认证信息
            AccountDetailVo info = (AccountDetailVo) authResult.getPrincipal();
            // 构建
            token = JWTokenUtil.that.getPrefix() + JWTokenUtil.newToken(info.getUsername(), info.getRules());
            log.info("get token account: {}", info.getUsername());
            log.info("token: {}", token);
        } catch (Exception e) {
            log.error("token 颁发失败！");
            log.error(e.getMessage(), e);
            reqRspUtil.writeRspResult(new R<>(new CheckedException(), null), null);
            return;
        }
        // 将成功消息写入响应结果
        reqRspUtil.writeRspResult(
                R.ok(token, CommonConst.DEF_SUCCESS_MSG),
                rsp -> rsp.setHeader(JWTokenUtil.that.getHeaderKey(), token)
        );
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if (request == null) {
            super.unsuccessfulAuthentication(request, response, failed);
            return;
        }
        // 将失败消息写入响应结果
        ReqRspUtil.build(null, response, this.getClass())
                .writeRspResult(R.fail(null, ErrMsgProp.getMessage(ErrMsg.EM10000)), null);
    }
}
