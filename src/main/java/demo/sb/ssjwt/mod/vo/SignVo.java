package demo.sb.ssjwt.mod.vo;

import lombok.Data;

/**
 * 值对象：登录、注册
 */
@Data
abstract class SignVo {

    /**
     * 登录号
     */
    String codename;

    /**
     * 密码
     */
    private String password;

}
