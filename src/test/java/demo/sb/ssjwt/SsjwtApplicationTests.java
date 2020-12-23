package demo.sb.ssjwt;

import demo.sb.ssjwt.common.enums.ErrMsg;
import demo.sb.ssjwt.common.env.cmpo.ErrMsgProp;
import demo.sb.ssjwt.ds.UserInfoDs;
import demo.sb.ssjwt.mod.dao.UserInfoDao;
import demo.sb.ssjwt.mod.vo.SignUpVo;
import demo.sb.ssjwt.mod.vo.UserInfoVo;
import demo.sb.ssjwt.sev.AccountSev;
import demo.sb.ssjwt.sev.UserInfoSev;
import demo.sb.ssjwt.sev.impl.AccountDetailsSev;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
class SsjwtApplicationTests {

	@Autowired
	private UserInfoSev userInfoSev;
	@Autowired
	private ErrMsgProp errMsgProp;
	@Autowired
	private AccountSev accountSev;
	@Autowired
	private UserInfoDs userInfoDs;
	@Autowired
	private PasswordEncoder passwordEncoder;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Test
	void contextLoads() {
		for (ErrMsg en : ErrMsg.values()) {
			log.info("error message: {}", ErrMsgProp.getMessage(en));
		}
	}

	@Test
	void findUserByNicknameLike() {
		for (UserInfoVo vo : userInfoSev.findByNicknameLike("客服")) {
			log.info("user info: {}", vo.toString());
		}
	}

	@Test
	void signUp() {
		SignUpVo vo = new SignUpVo();
		vo.setCodename("passer");
		vo.setPassword(passwordEncoder.encode("passer"));
		vo.setNickname("游客");
		Long aid = accountSev.saveAccount(vo);
		Long uid = userInfoSev.saveUserInfo(aid, vo);
		Optional<UserInfoDao> opt = userInfoDs.findById(uid);
		if (opt.isPresent()) {
			UserInfoDao info = opt.get();
			log.info("user info: {}", info);
			log.info("account info: {}", info.getAccount());
		} else {
			log.error("sign up failure");
		}
	}

	@Test
	void updateUserInfo() {
		SignUpVo vo = new SignUpVo();
		vo.setCodename("operator");
		vo.setPassword(passwordEncoder.encode("operator"));
		vo.setNickname("客服人员");
		Long aid = accountSev.saveAccount(vo);
		Long uid = userInfoSev.saveUserInfo(aid, vo);
		Optional<UserInfoDao> opt = userInfoDs.findById(uid);
		if (opt.isPresent()) {
			UserInfoDao info = opt.get();
			log.info("user info: {}", info);
			log.info("account info: {}", info.getAccount());
		} else {
			log.error("update failure");
		}
	}
}
