package demo.sb.ssjwt.common.env.conf;

import demo.sb.ssjwt.common.env.conf.filter.JsonLoginFilter;
import demo.sb.ssjwt.common.env.conf.filter.JwtAuthorizationFilter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 配置 Spring-Security
 *
 * @see WebSecurityConfigurerAdapter
 */
@Setter
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConfigurationProperties(prefix = "spring.security")
public class SecurityConf extends WebSecurityConfigurerAdapter {

    /**
     * 登录接口
     */
    private String signInApi;
    /**
     * 注册用户接口
     */
    private String signUpApi;
    /**
     * 退出登录
     */
    private String logoutApi;
    /**
     * 必须校验身份
     */
    private String[] must;
    /**
     * 不用校验身份
     */
    private String[] ignore;

    /**
     * 将加解密处理工具配置为 spring-bean
     *
     * @return 使用 BCrypt 算法的加解密工具
     * @see BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (http == null) {
            return;
        }
        AuthenticationManager manager = authenticationManager();
        // 配置需要登录/不需登录的请求 fixme 配置的过滤没有起效
        http.authorizeRequests()
//                .antMatchers(must).hasAnyRole(allRule)
//                .antMatchers(ignore).permitAll()
//                .antMatchers("/**/*.*").permitAll()
//                .antMatchers(signInApi, signUpApi, logoutApi).permitAll();
                .anyRequest().authenticated();
        http.cors().and().httpBasic();
        // 添加普通请求的过滤器
        http.addFilter(new JwtAuthorizationFilter(manager))
                // 添加登录请求的过滤器
                .addFilter(new JsonLoginFilter(signInApi, manager))
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 配置登录请求和响应
        http.formLogin().loginProcessingUrl(signInApi).and()
                // 配置注销处理
                .logout().logoutRequestMatcher(new AntPathRequestMatcher(logoutApi, "DELETE"));
        // 配置跨域
        http.csrf().disable();
    }
}
