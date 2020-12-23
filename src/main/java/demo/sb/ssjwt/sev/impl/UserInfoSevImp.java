package demo.sb.ssjwt.sev.impl;

import demo.sb.ssjwt.ds.AccountDs;
import demo.sb.ssjwt.ds.UserInfoDs;
import demo.sb.ssjwt.mod.dao.AccountDao;
import demo.sb.ssjwt.mod.dao.UserInfoDao;
import demo.sb.ssjwt.mod.vo.SignUpVo;
import demo.sb.ssjwt.mod.vo.UserInfoVo;
import demo.sb.ssjwt.sev.UserInfoSev;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 服务实现：用户信息访问
 *
 * @see UserInfoSev
 */
@Service
@AllArgsConstructor
public class UserInfoSevImp implements UserInfoSev {

    private final AccountDs accountDs;
    private final UserInfoDs userInfoDs;

    @Override
    public List<UserInfoVo> findByNicknameLike(String nickname) {
        List<UserInfoDao> infos = userInfoDs.findByNicknameLike("%" + nickname + "%");
        return infos.stream()
                .map(UserInfoVo::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long saveUserInfo(Long accountId, SignUpVo info) {
        Optional<AccountDao> accOpt = accountDs.findById(accountId);
        if (!accOpt.isPresent()) {
            return -1L;
        }
        AccountDao account = accOpt.get();
        Optional<UserInfoDao> old = userInfoDs.getByAccount_Id(accountId);
        UserInfoDao nnn = new UserInfoDao();
        nnn.setAccount(account);
        nnn.setNickname(info.getNickname());
        if (info.getGender() != null) {
            nnn.setGender(info.getGender());
        }
        old.ifPresent(it -> {
            nnn.setId(it.getId());
            nnn.setSignUpDate(it.getSignUpDate());
        });
        return userInfoDs.save(nnn).getId();
    }
}
