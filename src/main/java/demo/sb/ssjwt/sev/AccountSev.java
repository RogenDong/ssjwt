package demo.sb.ssjwt.sev;

import demo.sb.ssjwt.mod.vo.SignUpVo;

/**
 * 服务接口：账户数据访问
 */
public interface AccountSev {

    /**
     * 注册
     * @param signUpVo 注册参数
     * @return 账户id
     */
    Long saveAccount(SignUpVo signUpVo);
}
