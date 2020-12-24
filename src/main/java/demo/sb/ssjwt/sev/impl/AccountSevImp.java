package demo.sb.ssjwt.sev.impl;

import demo.sb.ssjwt.ds.AccountDs;
import demo.sb.ssjwt.mod.dao.AccountDao;
import demo.sb.ssjwt.mod.vo.SignInVo;
import demo.sb.ssjwt.mod.vo.SignUpVo;
import demo.sb.ssjwt.sev.AccountSev;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Long saveAccount(SignUpVo vo) {
        Optional<AccountDao> old = accountDs.getByCodename(vo.getCodename());
        AccountDao nnn = new AccountDao();
        nnn.setCodename(vo.getCodename());
        nnn.setPassword(vo.getPassword());
        old.ifPresent(it -> nnn.setId(it.getId()));
        return accountDs.save(nnn).getId();
    }

    @Override
    public Long signIn(SignInVo vo) {
        Optional<AccountDao> opt = accountDs.getByCodename(vo.getCodename());
        if (opt.isPresent()) {
            AccountDao dao = opt.get();
            if (passwordEncoder.matches(vo.getPassword(), dao.getPassword())) {
                return dao.getId();
            }
        }
        return null;
    }
}
