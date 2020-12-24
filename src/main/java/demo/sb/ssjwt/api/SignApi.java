package demo.sb.ssjwt.api;

import demo.sb.ssjwt.common.mod.R;
import demo.sb.ssjwt.mod.vo.SignInVo;
import demo.sb.ssjwt.sev.AccountSev;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("sign/")
public class SignApi {

    private final AccountSev accountSev;

    @PostMapping("in")
    public R<Long> signIn(@RequestBody SignInVo vo) {
        Long id = accountSev.signIn(vo);
        if (id == null) {
            return R.fail(-1L);
        }
        return R.ok(id);
    }

    // TODO logout api
}
