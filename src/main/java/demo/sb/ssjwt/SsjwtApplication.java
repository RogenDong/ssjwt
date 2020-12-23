package demo.sb.ssjwt;

import demo.sb.ssjwt.common.env.cmpo.ErrMsgProp;
import demo.sb.ssjwt.common.env.conf.SecurityConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		ErrMsgProp.class,
		SecurityConf.class
})
public class SsjwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsjwtApplication.class, args);
	}

}
