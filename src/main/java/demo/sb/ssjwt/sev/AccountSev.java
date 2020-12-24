package demo.sb.ssjwt.sev;

import demo.sb.ssjwt.mod.vo.SignInVo;
import demo.sb.ssjwt.mod.vo.SignUpVo;

/**
 * 服务接口：账户数据访问
 */
public interface AccountSev {

    /**
     * 注册
     * @param vo 注册参数
     * @return 账户id
     */
    Long saveAccount(SignUpVo vo);

    /**
     * 登录
     * @param vo 登录参数
     * @return 账户信息
     */
    Long signIn(SignInVo vo);
}
