package demo.sb.ssjwt.common.env.cmpo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTokenUtil {
    /**
     * 有效时间
     */
    private int expiration;

    /**
     * 当前服务的密钥
     */
    private String secret;

    /**
     * 由当前服务签发
     */
    private String issuer;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 请求/响应头的 key
     */
    private String headerKey;

    /**
     * 当前 spring-bean 的引用
     */
    @Setter(AccessLevel.PRIVATE)
    public static JWTokenUtil that = null;

    /**
     * 初始化静态引用
     */
    public JWTokenUtil() {
        that = this;
    }

    /**
     * 签发人
     */
    public static final String ISSUER = "iss";

    /**
     * 过期时间点
     */
    public static final String EXP_TIME = "exp";

    /**
     * 主题
     */
    public static final String SUBJECT = "sub";

    /**
     * 受众
     */
    public static final String AUDIENCE = "aud";

    /**
     * 生效时间
     */
    public static final String NOT_BEFORE = "nbf";

    /**
     * 签发时间
     */
    public static final String ISS_AT = "iat";

    /**
     * JWT 编号
     */
    public static final String JWT_ID = "jti";

    /**
     * 签名算法
     */
    private static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

    /**
     * 颁发新的 token
     *
     * @param subject  主体信息
     * @param audience 权限信息
     * @return token
     */
    public static String newToken(String subject, String audience) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SUBJECT, subject);
        claims.put(AUDIENCE, audience);

        return Jwts.builder()
                .setSubject(subject)
                .setAudience(audience)
                .setIssuer(that.issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + that.expiration))
                .setClaims(claims)
                .signWith(algorithm, that.secret)
                .compact();
    }

    /**
     * 解析 token
     *
     * @return token 内涵的信息
     */
    public static Claims toClaims(String token) {
        return Jwts.parser().setSigningKey(that.secret).parseClaimsJws(token).getBody();
    }

    /**
     * 检查 token 是否过期
     */
    public static void isExp(String token) {
        toClaims(token).getExpiration();
    }
}
