package demo.sb.ssjwt;

import demo.sb.ssjwt.common.enums.ErrMsg;
import demo.sb.ssjwt.common.env.cmpo.ErrMsgProp;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SsjwtApplicationTests {

	@Autowired
	private ErrMsgProp errMsgProp;

	@Test
	void contextLoads() {
		final Logger log = LoggerFactory.getLogger(SsjwtApplicationTests.class);
		for (ErrMsg eme : ErrMsg.values()) {
			log.info("error message: {}", ErrMsgProp.getMessage(eme));
		}
	}

}
