package demo.sb.ssjwt.sev.impl;

import demo.sb.ssjwt.ds.AccountDs;
import demo.sb.ssjwt.mod.dao.AccountDao;
import demo.sb.ssjwt.mod.vo.SignUpVo;
import demo.sb.ssjwt.sev.AccountSev;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 服务实现：账户数据访问
 * @see AccountSev
 */
@Service
@AllArgsConstructor
public class AccountSevImp implements AccountSev {

    private final AccountDs accountDs;

    @Override
    @Transactional
    public Long saveAccount(SignUpVo signUpVo) {
        Optional<AccountDao> old = accountDs.getByCodename(signUpVo.getCodename());
        AccountDao nnn = new AccountDao();
        nnn.setCodename(signUpVo.getCodename());
        nnn.setPassword(signUpVo.getPassword());
        old.ifPresent(it -> nnn.setId(it.getId()));
        return accountDs.save(nnn).getId();
    }
}
