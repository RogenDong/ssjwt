package demo.sb.ssjwt.mod.vo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 值对象：登入参数
 */
@NoArgsConstructor
public class SignInVo extends SignVo {
    public SignInVo(String codename, String password) {
        this.codename = codename;
        this.setPassword(password);
    }
}
