package demo.sb.ssjwt.sev.impl;

import demo.sb.ssjwt.common.exc.CredentialNotEnterException;
import demo.sb.ssjwt.common.exc.CredentialsIncorrectException;
import demo.sb.ssjwt.common.util.Assert;
import demo.sb.ssjwt.ds.AccountDs;
import demo.sb.ssjwt.ds.RuleMappingDs;
import demo.sb.ssjwt.mod.dao.AccountDao;
import demo.sb.ssjwt.mod.dao.RuleMappingDao;
import demo.sb.ssjwt.mod.vo.AccountDetailVo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountDetailsSev implements UserDetailsService {

    private final AccountDs accountDs;
    private final RuleMappingDs ruleMappingDs;

    @Override
    public UserDetails loadUserByUsername(String codename) throws UsernameNotFoundException {
        Assert.hasText(codename, new CredentialNotEnterException());
        Optional<AccountDao> optional = accountDs.getByCodename(codename);
        AccountDao account = optional.orElseThrow(CredentialsIncorrectException::new);
        List<RuleMappingDao> ruleMapping = ruleMappingDs.findByAccount_Id(account.getId());
        return new AccountDetailVo(codename, account.getPassword(), ruleMapping);
    }
}
