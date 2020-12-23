package demo.sb.ssjwt.common.env.conf.filter;

import demo.sb.ssjwt.common.env.cmpo.JWTokenUtil;
import demo.sb.ssjwt.common.exc.LoginStateExpiredException;
import demo.sb.ssjwt.common.exc.NotLoginException;
import demo.sb.ssjwt.common.mod.R;
import demo.sb.ssjwt.common.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 过滤普通请求，验证请求中包含的认证 token
 *
 * @see BasicAuthenticationFilter
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain
    ) throws IOException, ServletException {
        ReqRspUtil reqRspUtil = ReqRspUtil.build(request, response, this.getClass());
        UsernamePasswordAuthenticationToken auth;
        boolean rspWrote = false;
        String jwt = request.getHeader(JWTokenUtil.that.getHeaderKey());
        try {
            // 解析 token，格式不对 or 过期时 JJWT 会抛出异常
            auth = getAuthentication(jwt);
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException) {
                // token 过期，需要重新登录 TODO 自动续签
                reqRspUtil.writeRspResult(new R<>(new LoginStateExpiredException(), null), null);
                rspWrote = true;
            } else {
                log.error("JWT-token 解析失败！token 正文：{}", jwt);
                log.error(e.getMessage(), e);
            }
            auth = null;
        }
        if (auth == null) {
            // 身份获取失败
            if (!rspWrote) {
                // 提示登录
                reqRspUtil.writeRspResult(new R<>(new NotLoginException(), null), null);
            }
        } else {
            // 获取成功，将身份提供给 spring-security 上下文
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.debug("request account: {}", auth.getCredentials());
            super.doFilterInternal(request, response, chain);
        }
    }

    /**
     * 解析 token 获取认证信息
     *
     * @param token jwt
     * @return spring-security 认证信息
     */
    private static UsernamePasswordAuthenticationToken getAuthentication(String token) throws Exception {
        if (StringUtil.isNullOrBlank(token) || token.startsWith(JWTokenUtil.that.getPrefix())) {
            return null;
        }
        // 若 token 过期，JJWT 会自动抛出异常
        Claims claims = JWTokenUtil.toClaims(token.replace(JWTokenUtil.that.getPrefix(), ""));
        List<SimpleGrantedAuthority> rules = Arrays.stream(claims.getAudience().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, rules);
    }
}
